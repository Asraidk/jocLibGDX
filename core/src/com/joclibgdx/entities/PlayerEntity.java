package com.joclibgdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.joclibgdx.Constants;

import static com.joclibgdx.Constants.PIXELS_IN_METER;
import static com.joclibgdx.Constants.SPEED_PLAYER;

/**
 * Created by Portatil on 27/04/2017.
 */

public class PlayerEntity extends Actor {

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;
    private boolean alive=true;
    private boolean jumping=false;
    private boolean mustjumping=false;

    public boolean isMustjumping() {
        return mustjumping;
    }

    public void setMustjumping(boolean mustjumping) {
        this.mustjumping = mustjumping;
    }



    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }



    public PlayerEntity(World world, Texture texture, Vector2 position){
        this.world=world;
        this.texture=texture;

        BodyDef def =new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        body=world.createBody(def);

        PolygonShape luigiShape = new PolygonShape();
        luigiShape.setAsBox(0.5f,0.5f);
        fixture=body.createFixture(luigiShape,1);
        fixture.setUserData("player");
        luigiShape.dispose();

        setSize(PIXELS_IN_METER,PIXELS_IN_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x-0.5f)*PIXELS_IN_METER,
                    (body.getPosition().y-0.5f)*PIXELS_IN_METER);

        batch.draw(texture,getX(),getY(),getWidth(),getHeight());
    }

    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    @Override
    public void act(float delta) {
        //si toca saltar
        if(mustjumping){
            mustjumping=false;
            jump();
        }

        //avanzar si esta vivo!!!

        if(alive){
            float speedY = body.getLinearVelocity().y;
            body.setLinearVelocity(SPEED_PLAYER,speedY);
        }
        //fuerza que te inpulsa para el suleo, vigilar constantes
        if(jumping){
            body.applyForceToCenter(0,-2,true);
        }
    }
    public void jump(){
        if(!jumping && alive) {
            jumping=true;
            Vector2 position = body.getPosition();
            body.applyLinearImpulse(0, 6, position.x, position.y, true);
        }
    }
}
