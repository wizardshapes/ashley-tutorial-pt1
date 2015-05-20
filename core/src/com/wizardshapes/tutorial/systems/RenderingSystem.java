package com.wizardshapes.tutorial.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.wizardshapes.tutorial.components.TextureComponent;
import com.wizardshapes.tutorial.components.TransformComponent;

public class RenderingSystem extends IteratingSystem {
	
	static final float CAMERA_WIDTH = 30;
	static final float CAMERA_HEIGHT = 20;
	static final float PIXELS_TO_METRES = 1.0f / 32.0f;
	
	private Array<Entity> renderableEntities;
	
	private ComponentMapper<TextureComponent> textureMapper;
	private ComponentMapper<TransformComponent> transformMapper;
	
	private SpriteBatch batch;
	
	private OrthographicCamera camera;
	
	@SuppressWarnings("unchecked")
	public RenderingSystem(SpriteBatch batch) {
		super(Family.all(TransformComponent.class, TextureComponent.class).get());
		
		textureMapper = ComponentMapper.getFor(TextureComponent.class);
		transformMapper = ComponentMapper.getFor(TransformComponent.class);
		
		renderableEntities = new Array<Entity>();
		
		this.batch = batch;
		camera = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		camera.position.set(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2, 0);
		
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		
		for (Entity entity : renderableEntities) {
			TextureComponent texture = textureMapper.get(entity);
			TransformComponent transform = transformMapper.get(entity);
		
			float width = texture.region.getRegionWidth();
			float height = texture.region.getRegionHeight();
			float originX = width * 0.5f;
			float originY = height * 0.5f;
			
			batch.draw(texture.region,
					   transform.pos.x - originX, transform.pos.y - originY,
					   originX, originY,
					   width, height,
					   transform.scale.x * PIXELS_TO_METRES, transform.scale.y * PIXELS_TO_METRES,
					   MathUtils.radiansToDegrees * transform.rotation);
		}
		
		
		
		batch.end();
		renderableEntities.clear();
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		renderableEntities.add(entity);

	}

}
