package com.censkh.game.entity;

import java.awt.Graphics2D;

import com.censkh.game.engine.util.Direction;
import com.censkh.game.render.Sprite;

public abstract class LivingEntity extends Entity implements ILivingEntity {
	
	private float maxHealth = getDefaultMaxHealth();
	private float health = maxHealth;
	private Direction direction;
	private final Sprite sprite;
	private int noDamageTicks = 0;
	
	public LivingEntity(float x, float y) {
		super(x, y);
		sprite = createSprite();
	}
	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	
	public void hurt() {
		
	}
	
	@Override
	public void update() {
		super.update();
		if (getNoDamageTicks()>0) {
			setNoDamageTicks(getNoDamageTicks() - 1);
		}
		if (isDirectionVelocityBased()) {
			if (getXVelocity() > 0) {
			setDirection(Direction.RIGHT);
		} else if (getXVelocity() < 0) {
			setDirection(Direction.LEFT);
		}
		}
		if (getHealth()<=0) {
			kill();
		}
	}
	
	public void kill() {
		remove();
	}
	
	public boolean isDirectionVelocityBased() {
		return true;
	}

	public void damage(float damage) {
		if (getNoDamageTicks()==0) {
			setHealth(getHealth()-damage);
			hurt();
		}
	}
	
	public float getHealth() {
		return health;
	}
	
	public void setHealth(float health) {
		this.health = health;
	}
	
	public float getMaxHealth() {
		return maxHealth;
	}
	
	public void setMaxHealth(float maxHealth) {
		this.maxHealth = maxHealth;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public Sprite getSprite() {
		return sprite;
	}

	public int getNoDamageTicks() {
		return noDamageTicks;
	}

	public void setNoDamageTicks(int noDamageTicks) {
		this.noDamageTicks = noDamageTicks;
	}
	
}
