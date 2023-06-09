package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.awt.*;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
	public static final int WORLD_WIDTH = 800;
	public static final int WORLD_HEIGHT = 600;
	SpriteBatch batch;
	Texture img;
	Sprite obj;
	OrthographicCamera camera;
	Viewport viewport;
	Music music;
	Sound sound;
	float vx=150.0f, vy=150.0f;
	
	@Override
	public void create () {
		camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
		camera.setToOrtho(true, WORLD_WIDTH, WORLD_HEIGHT);
		viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT,camera);

		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		obj = new Sprite(img);
		obj.flip(false, true);
		obj.setPosition(200,200);

		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);
		music.play();

		sound = Gdx.audio.newSound(Gdx.files.internal("sound.wav"));

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		camera.update();
		ScreenUtils.clear(0, 0, 0, 1);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		//batch.draw(img, 0, 0);
		obj.draw(batch);
		//obj.setRotation(45);
		batch.end();

		float elapsed = Gdx.graphics.getDeltaTime();
		//Gdx.graphics.getWidth();



		//update logic
		obj.setX(obj.getX() + vx * elapsed);
		obj.setY(obj.getY() + vy * elapsed);

		if(obj.getX() + obj.getWidth() >= WORLD_WIDTH) {
			sound.play();
			vx = -150.0f;

		}
		else if(obj.getX() <= 0) {
			sound.play();
			vx = 150.0f;
		}

		if(obj.getY() + obj.getHeight() >= WORLD_HEIGHT) {
			sound.play();
			vy = -150.0f;
		}
		else if(obj.getY() <= 0) {
			sound.play();
			vy = 150.0f;
		}
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewport.update(width, height);
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		music.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		//cek input
		if(keycode == Input.Keys.LEFT)
		{
			vy = 0.0f;
			vx = -150.0f;
		}
		else if(keycode == Input.Keys.RIGHT)
		{
			vy = 0.0f;
			vx = 150.0f;
		}
		else if(keycode == Input.Keys.UP)
		{
			vy = -150.0f;
			vx = 0.0f;
		}
		else if(keycode == Input.Keys.DOWN)
		{
			vy = 150.0f;
			vx = 0.0f;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		//obj.setPosition(screenX, screenY);
		Vector3 tp = new Vector3(screenX, screenY, 0);
		camera.unproject(tp);
		obj.setOriginBasedPosition(tp.x, tp.y);
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}
}
