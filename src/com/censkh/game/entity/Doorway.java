package com.censkh.game.entity;

import java.awt.Graphics2D;

import com.censkh.game.gui.element.GuiText;
import com.censkh.game.render.FrameInfo;
import com.censkh.game.render.RenderText;
import com.censkh.game.render.Sprite;

public class Doorway extends Entity {
	
	private boolean isUseable = false;	
	private Sprite sprite = new Sprite("res/entity/doorway.png",54,80).addState("default", new FrameInfo[] {new FrameInfo(0,0,100)}).bake();
	
	public Doorway(float x, float y) {
		super(x, y);
	}
	
	@Override
	public void draw(Graphics2D g) {
		sprite.draw(g, "default", getX(), getY());
		if (isUseable()&&getMap().isCollision(this, getMap().getPlayer())) {
			RenderText.draw(g, GuiText.defaultFont, "<Enter>", getX()-12, getY()-12, 1f, true);
		}
	}

	@Override
	public float getWidth() {
		return 54;
	}

	@Override
	public float getHeight() {
		return 80;
	}

	@Override
	public int getRenderLayer() {
		return Entity.LAYER_BACKGROUND;
	}
	
	@Override
	public boolean hasGravity() {
		return false;
	}

	public boolean isUseable() {
		return isUseable;
	}

	public void setUseable(boolean isUseable) {
		this.isUseable = isUseable;
	}
	
}
