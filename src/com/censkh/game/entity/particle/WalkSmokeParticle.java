package com.censkh.game.entity.particle;

import java.awt.Graphics2D;

import com.censkh.game.entity.Entity;
import com.censkh.game.render.FrameInfo;
import com.censkh.game.render.Sprite;

public class WalkSmokeParticle extends Particle {
	
	private Sprite sprite;
	
	public WalkSmokeParticle(float x, float y) {
		super(x, y);
		sprite = new Sprite("res/entity/smoke.png", 6, 6).addState(
				"default",
				new FrameInfo[] {
						new FrameInfo(0, 0, getLifetime() / 10),
						new FrameInfo(1, 0, getLifetime() / 10),
						new FrameInfo(2, 0, getLifetime() / 10),
						new FrameInfo(3, 0, getLifetime() / 10),
						new FrameInfo(4, 0, getLifetime() / 10),
						new FrameInfo(5, 0, getLifetime() / 10),
						new FrameInfo(6, 0, getLifetime() / 10),
						new FrameInfo(7, 0, getLifetime() / 10),
						new FrameInfo(8, 0, getLifetime() / 10),
						new FrameInfo(9, 0, getLifetime() / 10),
				}).bake();
	}
	
	@Override
	public void update() {
		super.update();
		addVelocity(0f, -0.01f);
	}
	
	@Override
	public void draw(Graphics2D g) {
		sprite.draw(g, "default", getX(), getY());
	}
	
	@Override
	public int getLifetime() {
		return 20;
	}
	
	@Override
	public float getWidth() {
		return 6;
	}
	
	@Override
	public float getHeight() {
		return 6;
	}
	
	@Override
	public int getRenderLayer() {
		return Entity.LAYER_MAIN;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
}
