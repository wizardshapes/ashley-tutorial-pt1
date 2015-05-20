package com.wizardshapes.tutorial;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wizardshapes.tutorial.components.TextureComponent;
import com.wizardshapes.tutorial.components.TransformComponent;
import com.wizardshapes.tutorial.systems.RenderingSystem;

public class GameScreen extends ScreenAdapter {
	AshleyTutorial1 game;
	Engine engine;
	
	Texture playerIdle;
	
	public GameScreen (AshleyTutorial1 game) {
		this.game = game;
		engine = new Engine();
		loadLevel();
	}
	
	@Override
	public void render (float delta) {
		engine.update(delta);
	}
	
	private void loadLevel() {
		playerIdle = new Texture(Gdx.files.internal("idle.png"));
		engine.addSystem(new RenderingSystem(game.batch));
		createPlayer();
		
	}
	
	private Entity createPlayer() {
		Entity entity = new Entity();
		TransformComponent position = new TransformComponent();
		TextureComponent texture = new TextureComponent();
		position.pos.set(0.0f, 0.0f, 0.0f);
		texture.region = new TextureRegion(playerIdle);
		entity.add(position);
		entity.add(texture);
		engine.addEntity(entity);
		return entity;
	}
}
