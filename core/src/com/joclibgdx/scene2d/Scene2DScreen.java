package com.joclibgdx.scene2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.joclibgdx.MainGame;
import com.joclibgdx.PantallaBase;

/**
 * Created by Portatil on 25/04/2017.
 */

public class Scene2DScreen extends PantallaBase {



    public Scene2DScreen(MainGame game) {
        super(game);
        texturaJugador = new Texture("LuigiQuieto.png");
        texturaEnemic = new Texture("ShyGuy.png");
    }

    private Stage stage;
    private ActorJugador jugador;
    private ActorEnemic enemic;
    private  Texture texturaJugador,texturaEnemic;

    @Override
    public void show(){


        stage= new Stage();
        stage.setDebugAll(true);

        jugador= new ActorJugador(texturaJugador);
        enemic= new ActorEnemic(texturaEnemic);
        stage.addActor(jugador);
        stage.addActor(enemic);

        jugador.setPosition(20,100);
        enemic.setPosition(400,100);
    }

    @Override
    public void hide() {
            stage.dispose();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.4f,0.5f,0.8f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

        comprovarColisions();
        stage.draw();
    }

    private void comprovarColisions(){
        if(jugador.isAlive() && jugador.getX()+jugador.getWidth() > enemic.getX()){
            System.out.println("Waludubdub");
            jugador.setAlive(false);
        }
    }

    @Override
    public void dispose() {
       texturaJugador.dispose();
    }
}
