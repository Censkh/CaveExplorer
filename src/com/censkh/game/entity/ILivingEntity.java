package com.censkh.game.entity;

import com.censkh.game.render.Sprite;

public interface ILivingEntity {
	public float getDefaultMaxHealth();
	
	public Sprite createSprite();
}
