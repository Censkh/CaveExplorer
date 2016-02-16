package com.censkh.game.entity.particle;

import com.censkh.game.entity.Entity;

public abstract class Particle extends Entity implements IParticle {
	
	public Particle(float x, float y) {
		super(x, y);
	}
	
	@Override
	public boolean hasGravity() {
		return false;
	}
	
	@Override
	public void update() {
		super.update();
		if (getLivedTicks() >= getLifetime()) {
			remove();
		}
	}
	
	@Override
	public boolean hasCollision() {
		return false;
	}
	
}
