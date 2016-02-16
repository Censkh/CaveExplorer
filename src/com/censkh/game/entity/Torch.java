package com.censkh.game.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import com.censkh.game.render.FrameInfo;
import com.censkh.game.render.Sprite;

public class Torch extends Entity {
	
	private Sprite sprite;
	
	public Torch(float x, float y) {
		super(x, y);
		sprite = new Sprite("res/entity/torch.png", 32, 32).addState("default", new FrameInfo[] {new FrameInfo(0, 0, 120),new FrameInfo(1, 0, 5),new FrameInfo(2, 0, 25),new FrameInfo(3, 0, 5)}).bake();
	}
	
	@Override
	public boolean hasGravity() {
		return false;
	}
	
	@Override
	public void update() {
		super.update();
	}
	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		sprite.draw(g,"default", getX() - 16, getY() - 16);
	}
	
	@Override
	public boolean isLightSource() {
		return true;
	}
	
	@Override
	public Color getLightColor() {
		return Color.cyan;
	}
	
	@Override
	public float getLightRadius() {
		return 100f;
	}
	
	@Override
	public float getLightFlicker() {
		return 1f;
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
		return LAYER_BACKGROUND;
	}
	
}
