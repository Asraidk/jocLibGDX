package com.joclibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.joclibgdx.entities.FloorEntity;
import com.joclibgdx.entities.PlayerEntity;
import com.joclibgdx.entities.SpikeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Portatil on 27/04/2017.
 */

public class GameScreen extends PantallaBase {

    private Stage stage;
    private World world;
    private PlayerEntity player;

    private List<FloorEntity> floorList= new ArrayList<FloorEntity>();
    private List<SpikeEntity> cactusList= new ArrayList<SpikeEntity>();

    private Sound jumpSound,dieSound;

    private Music byMusic;

    public GameScreen(final MainGame game){
        super(game);
        jumpSound= game.getManager().get("jump.ogg");
        dieSound= game.getManager().get("die.ogg");
        byMusic= game.getManager().get("song2.ogg");
        stage = new Stage(new FitViewport(640,360));
        world = new World (new Vector2(0,-10), true);

        world.setContactListener(new ContactListener() {

            private boolean areCollided(Contact contact,Object userA,Object userB){
                return (contact.getFixtureA().getUserData().equals(userA)&&contact.getFixtureB().getUserData().equals(userB)||
                        contact.getFixtureA().getUserData().equals(userB)&&contact.getFixtureB().getUserData().equals(userA));
            }
            @Override
            public void beginContact(Contact contact) {
                if(areCollided(contact,"player","floor")){
                    player.setJumping(false);
                    if(Gdx.input.isTouched()){
                        jumpSound.play();
                        player.setMustjumping(true);
                    }
                }
                if(areCollided(contact,"player","spike")){
                    if(player.isAlive()) {
                        player.setAlive(false);
                        byMusic.stop();
                        dieSound.play();

                        stage.addAction(
                                Actions.sequence(
                                        Actions.delay(1.5f),
                                        Actions.run(new Runnable(){
                                            @Override
                                            public void run() {
                                                game.setScreen(game.gameOverScreen);
                                            }
                                        })
                                )
                        );
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    @Override
    public void show() {
        Texture playerTexture = game.getManager().get("cat.png");
        Texture floorTexture = game.getManager().get("floor.png");
        Texture overfloorTexture = game.getManager().get("overfloor.png");
        Texture enemyTexture = game.getManager().get("cactus.png");
        player=new PlayerEntity(world,playerTexture,new Vector2(1,0.5f));

        floorList.add(new FloorEntity(world,floorTexture,overfloorTexture,0,1000,1));
        floorList.add(new FloorEntity(world,floorTexture,overfloorTexture,12,13,2));
        floorList.add(new FloorEntity(world,floorTexture,overfloorTexture,16,5,3));
        floorList.add(new FloorEntity(world,floorTexture,overfloorTexture,34,4,2));
        floorList.add(new FloorEntity(world,floorTexture,overfloorTexture,40,1,2));
        floorList.add(new FloorEntity(world,floorTexture,overfloorTexture,45,1,2));
        floorList.add(new FloorEntity(world,floorTexture,overfloorTexture,50,1,2));
        floorList.add(new FloorEntity(world,floorTexture,overfloorTexture,55,1,2));
        floorList.add(new FloorEntity(world,floorTexture,overfloorTexture,58,3,3));
        floorList.add(new FloorEntity(world,floorTexture,overfloorTexture,62,4,4));

        cactusList.add(new SpikeEntity(world,enemyTexture,5,1));
        cactusList.add(new SpikeEntity(world,enemyTexture,15,2));
        cactusList.add(new SpikeEntity(world,enemyTexture,24,2));
        cactusList.add(new SpikeEntity(world,enemyTexture,29,1));
        cactusList.add(new SpikeEntity(world,enemyTexture,30,1));
        cactusList.add(new SpikeEntity(world,enemyTexture,42,1));
        cactusList.add(new SpikeEntity(world,enemyTexture,43,1));
        cactusList.add(new SpikeEntity(world,enemyTexture,47,1));
        cactusList.add(new SpikeEntity(world,enemyTexture,48,1));
        cactusList.add(new SpikeEntity(world,enemyTexture,52,1));
        cactusList.add(new SpikeEntity(world,enemyTexture,53,1));


        stage.addActor(player);
        for (FloorEntity floor : floorList){
            stage.addActor(floor);
        }
        for (SpikeEntity spike : cactusList){
            stage.addActor(spike);
        }

        byMusic.setVolume(0.75f);
        byMusic.play();
    }

    @Override
    public void hide() {
        player.detach();
        player.remove();
        for (FloorEntity floor : floorList){
            floor.detach();
            floor.remove();
        }
        for (SpikeEntity spike : cactusList){
            spike.detach();
            spike.remove();
        }
    }

    @Override
    public void render(float delta) {


        Gdx.gl.glClearColor(0.4f,0.5f,0.8f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(player.getX() >150 && player.isAlive()) {
            stage.getCamera().translate(Constants.SPEED_PLAYER * delta * Constants.PIXELS_IN_METER, 0, 0);
        }

        if(Gdx.input.justTouched()){
            jumpSound.play();
            player.jump();
        }
        stage.act();
        world.step(delta,6,2);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }
}
