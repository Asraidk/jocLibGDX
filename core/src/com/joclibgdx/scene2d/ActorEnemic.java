package com.joclibgdx.scene2d;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Portatil on 25/04/2017.
 */

public class ActorEnemic extends Actor {
    private Texture enemic;

    public ActorEnemic(Texture enemic){
        this.enemic=enemic;
        setSize(enemic.getWidth(),enemic.getHeight());
    }
    @Override
    public void act(float delta) {
        setX(getX()-250*delta);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(enemic,getX(),getY());
    }
}
