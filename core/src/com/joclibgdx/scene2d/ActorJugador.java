package com.joclibgdx.scene2d;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Portatil on 25/04/2017.
 */

public class ActorJugador extends Actor {

    private Texture jugador;

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    private boolean alive;

    public ActorJugador(Texture jugador){
        this.jugador=jugador;
        setSize(jugador.getWidth(),jugador.getHeight());
        this.alive=true;
    }
    @Override
    public void act(float delta) {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(jugador,getX(),getY());
    }
}
