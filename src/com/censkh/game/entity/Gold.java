package com.censkh.game.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.censkh.game.Game;
import com.censkh.game.render.FrameInfo;
import com.censkh.game.render.Sprite;

public class Gold extends Entity {
	
	private final int ss = 10;
	private float yOff = Game.getRandom().nextInt(8);
	private boolean yOffUp = true;
	private final Sprite sprite = new Sprite("res/entity/gold.png", 40, 40).addState("copper", new FrameInfo[] {
			new FrameInfo(0, 0, ss), new FrameInfo(1, 0, ss), new FrameInfo(2, 0, ss), new FrameInfo(3, 0, ss)
	}).addState("gold", new FrameInfo[] {
			new FrameInfo(0, 1, ss), new FrameInfo(1, 1, ss), new FrameInfo(2, 1, ss), new FrameInfo(3, 1, ss)
	}).addState("cash", new FrameInfo[] {
			new FrameInfo(0, 2, ss), new FrameInfo(1, 2, ss), new FrameInfo(2, 2, ss), new FrameInfo(3, 2, ss)
	}).addState("sack", new FrameInfo[] {
			new FrameInfo(0, 3, ss), new FrameInfo(1, 3, ss), new FrameInfo(2, 3, ss), new FrameInfo(3, 3, ss)
	}).bake();
	private int value = 0;
	
	public Gold(float x, float y) {
		super(x, y);
		addVelocity(0, -2f);
	}
	
	@Override
	public void createHitbox() {
		int border = 16;
		setHitbox(new Rectangle(border, border, (int) getWidth() - border, (int) getHeight()-8));
	}
	
	@Override
	public void draw(Graphics2D g) {
		getSprite().draw(g, getSpriteState(), getX(), getY() - yOff);
	}
	
	public String getSpriteState() {
		if (getValue() > 100) {
			return "sack";
		} else if (getValue() > 50) {
			return "cash";
		} else if (getValue() > 10) {
			return "gold";
		} else {
			return "copper";
		}
	}
	
	@Override
	public void update() {
		super.update();
		if (isOnGround()) {
			float s = Game.getRandom().nextFloat() * 0.5f;
			if (yOffUp) {
				yOff+=s;
			} else {
				yOff-=s;
			}
		}
		if (yOff < 0) {
			yOff = 0;
			yOffUp = true;
		} else if (yOff > 12) {
			yOff = 12;
			yOffUp = false;
		}
		if (getMap().isCollision(this, getMap().getPlayer())) {
			getMap().getPlayer().addGold(getValue());
			remove();
		}
	}
	
	@Override
	public float getWidth() {
		return 40;
	}
	
	@Override
	public float getHeight() {
		return 40;
	}
	
	@Override
	public int getRenderLayer() {
		return Entity.LAYER_MAIN;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
}
