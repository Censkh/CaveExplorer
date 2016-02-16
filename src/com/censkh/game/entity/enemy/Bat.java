package com.censkh.game.entity.enemy;

import com.censkh.game.entity.Entity;
import com.censkh.game.render.FrameInfo;
import com.censkh.game.render.Sprite;

public class Bat extends Enemy {
	
	public Bat(float x, float y) {
		super(x, y);
	}
	
	@Override
	public void update() {
		super.update();
		moveTowardsPlayer(0.05f);
	}
	
	@Override
	public float getDefaultMaxHealth() {
		return 1;
	}
	
	@Override
	public Sprite createSprite() {
		return new Sprite("res/entity/bat.png", 32, 32).addState("idle", new FrameInfo[] {
			new FrameInfo(0, 0, 100)
		}).addState("move", new FrameInfo[] {
			new FrameInfo(0, 0, 100)
		}).addState("attack", new FrameInfo[] {
			new FrameInfo(0, 0, 100)
		}).bake();
	}
	
	@Override
	public float getWidth() {
		return 32;
	}
	
	@Override
	public float getHeight() {
		return 32;
	}
	
	@Override
	public int getRenderLayer() {
		return Entity.LAYER_MAIN;
	}
	
	@Override
	public boolean hasGravity() {
		return false;
	}
	
}
