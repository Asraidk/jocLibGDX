package com.joclibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Portatil on 25/04/2017.
 */

public class Box2DScreen extends PantallaBase {
    public Box2DScreen(MainGame game) {
        super(game);
    }

    private World world;

    private Box2DDebugRenderer renderer;

    private OrthographicCamera camera;

    private Body LuigiBody,TerraBody,PinchoBody;

    private Fixture LuigiFixture,TerraFixture,PinchoFixture;

    private boolean deveSaltar,luigiSaltando,LuigiVivo=true;

    @Override
    public void show() {
       world= new World(new Vector2(0,-10),true);
        renderer=new Box2DDebugRenderer();
        camera=new OrthographicCamera(16,9);
        camera.translate(2,4);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA=contact.getFixtureA(), fixtureB=contact.getFixtureB();

                /*if(fixtureA==LuigiFixture && fixtureB== TerraFixture){
                    if(Gdx.input.isTouched()){
                        deveSaltar=true;
                    }
                    luigiSaltando=false;
                }
                if(fixtureB==LuigiFixture && fixtureA== TerraFixture){
                    if(Gdx.input.isTouched()){
                        deveSaltar=true;
                    }
                    luigiSaltando=false;
                }*/

                if(fixtureA.getUserData().equals("player")&& fixtureB.getUserData().equals("floor") ||
                        fixtureA.getUserData().equals("floor")&& fixtureB.getUserData().equals("player")    ){

                    if(Gdx.input.isTouched()){
                        deveSaltar=true;
                    }
                    luigiSaltando=false;
                }
                if(fixtureA.getUserData().equals("player")&& fixtureB.getUserData().equals("enemic") ||
                        fixtureA.getUserData().equals("enemic")&& fixtureB.getUserData().equals("player")    ){
                    LuigiVivo=false;
                }

            }

            @Override
            public void endContact(Contact contact) {

                Fixture fixtureA=contact.getFixtureA(), fixtureB=contact.getFixtureB();

                if(fixtureA==LuigiFixture && fixtureB== TerraFixture){

                    luigiSaltando=true;
                }
                if(fixtureB==LuigiFixture && fixtureA== TerraFixture){

                    luigiSaltando=true;
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

        LuigiBody=world.createBody(createLuigiBodyDef());
        TerraBody=world.createBody(createTerraBodyDef());
        PinchoBody =world.createBody(createPinchoBodyDef(5));



        PolygonShape luigiShape = new PolygonShape();
        luigiShape.setAsBox(0.5f,0.9f);
        LuigiFixture=LuigiBody.createFixture(luigiShape,1);
        luigiShape.dispose();

        PolygonShape terraShape = new PolygonShape();
        terraShape.setAsBox(500,1);
        TerraFixture=TerraBody.createFixture(terraShape,1);
        terraShape.dispose();

        /*PolygonShape enemicShape = new PolygonShape();
        enemicShape.setAsBox(0.1f,0.1f);
        PinchoFixture=PinchoBody.createFixture(enemicShape,1);
        enemicShape.dispose();*/


        PinchoFixture =createPinchoFixture(PinchoBody);


        LuigiFixture.setUserData("player");
        TerraFixture.setUserData("floor");
        PinchoFixture.setUserData("enemic");

    }

    private BodyDef createLuigiBodyDef(){
        BodyDef def =new BodyDef();
        def.position.set(0,0);
        def.type = BodyDef.BodyType.DynamicBody;
        return def;
    }
    private BodyDef createTerraBodyDef(){
        BodyDef def =new BodyDef();
        def.position.set(0,-1);
        def.type = BodyDef.BodyType.StaticBody;
        return def;
    }
    private BodyDef createPinchoBodyDef(float x){
        BodyDef def =new BodyDef();
        def.position.set(x,0.5f);
        return def;
    }
    private Fixture createPinchoFixture(Body PinchoBody){
        Vector2[] vertices = new Vector2[3];
        vertices[0]=new Vector2(-0.5f,-0.5f);
        vertices[1]=new Vector2(0.5f,-0.5f);
        vertices[2]=new Vector2(0,0.5f);
        PolygonShape enemicShape = new PolygonShape();
        enemicShape.set(vertices);
        Fixture fix = PinchoBody.createFixture(enemicShape,1);
        enemicShape.dispose();
        return fix;
    }
    @Override
    public void dispose() {
        LuigiBody.destroyFixture(LuigiFixture);
        TerraBody.destroyFixture(TerraFixture);
        PinchoBody.destroyFixture(PinchoFixture);
        world.destroyBody(LuigiBody);
        world.destroyBody(TerraBody);
        world.destroyBody(PinchoBody);
        world.dispose();
        renderer.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(deveSaltar){
            deveSaltar =false;
            saltar();
        }

        if(Gdx.input.justTouched() && !luigiSaltando){
            deveSaltar=true;
        }



        if(LuigiVivo){
            float velocitatY = LuigiBody.getLinearVelocity().y;
            LuigiBody.setLinearVelocity(4,velocitatY);
        }



        world.step(delta,6,2);
        camera.update();
        renderer.render(world,camera.combined);
    }

    private void saltar(){
        Vector2 position=LuigiBody.getPosition();
        LuigiBody.applyLinearImpulse(0,10,position.x,position.y,true);
    }
}
