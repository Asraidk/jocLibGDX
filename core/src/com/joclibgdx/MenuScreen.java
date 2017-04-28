package com.joclibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by Portatil on 28/04/2017.
 */

public class MenuScreen extends PantallaBase {
    private Stage stage;
    private Skin skin;
    private Image logo;
    private TextButton play;

    public MenuScreen(final MainGame game) {
        super(game);

        stage=new Stage(new FitViewport(640,360));
        skin=new Skin(Gdx.files.internal("skin/uiskin.json"));
        logo =new Image(game.getManager().get("logo.png", Texture.class));
        play=new TextButton("Play",skin);

        play.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameScreen);
            }
        });

        logo.setPosition(440- logo.getWidth()/2,320- logo.getHeight());
        play.setSize(200,100);
        play.setPosition(40,50);
        stage.addActor(play);
        stage.addActor(logo);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f,0.5f,0.8f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();

        stage.draw();
    }
}
