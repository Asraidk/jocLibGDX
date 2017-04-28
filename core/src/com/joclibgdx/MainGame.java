package com.joclibgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MainGame extends Game {

	private AssetManager manager;

	public GameScreen gameScreen;
	public GameOverScreen gameOverScreen;
	public MenuScreen menuScreen;

	public AssetManager getManager() {
		return manager;
	}

	@Override
	public void create() {
	manager=new AssetManager();
		manager.load("cat.png",Texture.class);
		manager.load("cactus.png",Texture.class);
		manager.load("floor.png",Texture.class);
		manager.load("overfloor.png",Texture.class);
		manager.load("die.ogg",Sound.class);
		manager.load("jump.ogg",Sound.class);
		manager.load("song2.ogg",Music.class);
		manager.load("gameover.png",Texture.class);
		manager.load("logo.png",Texture.class);
		manager.finishLoading();
		//setScreen(new GameOverScreen(this));


		 gameScreen=new GameScreen(this);
		gameOverScreen=new GameOverScreen(this);
		menuScreen=new MenuScreen(this);

		setScreen(menuScreen);

	}


	/*imagenes=teturas TG-->tip potencies de dos milloren el rendiment!
	//private Texture png1,png2Accio,png3Enemic;
	//private TextureRegion regionShyGuy;//eix arriba per si volem tallar 0,0 es adalt
	//para trabajar  amb texturas en bloc
	//private SpriteBatch batch;
	//tamanys de la pantalla
	//private int llargada,alçada;
	//tamany imatges
	//private int llargadaLuigiQuito,alçadaLuigiQuieto;
	//compte amb la carrega d'arxius en el constructor ja que pot donar problemas alhora
	//que el libgdx no sap encara com fer-ho i farem coses en el create el codi critic
*/



	/*@Override
	public void create() {
		png1=new Texture("LuigiQuieto.png");
		png2Accio=new Texture("LuigiSalto.png");
		png3Enemic=new Texture("ShyGuy.png");
		batch=new SpriteBatch();

		//tamya de la pantalla donada
		llargada= Gdx.graphics.getWidth();
		alçada=Gdx.graphics.getHeight();
		//tamany imatge que em carregat
		llargadaLuigiQuito= png1.getWidth();
		alçadaLuigiQuieto=png1.getHeight();
		//part tall regio--> no la utilitzo la meva imantge va quadrarda ja =D si volem tallar de on a on 0,64 imatge tamnya 128 64
		//regionShyGuy=new TextureRegion(png3Enemic,0,64,128,64);
	}

	//liberar recurss muy importante!!!! per no deixar coses volan
	@Override
	public void dispose() {
		png1.dispose();
		png2Accio.dispose();
		png3Enemic.dispose();
		batch.dispose();
	}

	//render s'executa cada X emps fps--> metode que mostrara imatges, actualitzar el joc
	@Override
	public void render() {
		Gdx.gl.glClearColor(1,0,0.5f,1);
		//neteja pantalla abans de dibuizar
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//começar a dibuixar i acaba, important draw!!
		batch.begin();
		batch.draw(png1,0,0);
		batch.draw(png3Enemic,250,0);
		batch.end();
	}*/
}
