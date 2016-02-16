package com.censkh.game.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.censkh.game.Game;
import com.censkh.game.engine.util.CollisionType;
import com.censkh.game.map.Map;
import com.censkh.game.tile.Tile;

public abstract class Entity implements IEntity {
	
	public final static int LAYER_BACKGROUND = 0;
	public final static int LAYER_FOREGROUND = 2;
	public final static int LAYER_MAIN = 1;
	
	private float displayRadius = 1f;
	private float x;
	private float y;
	private float xSpeed = 0;
	private float ySpeed = 0;
	private float xVelocity = 0;
	private float yVelocity = 0;
	private Map map = null;
	private int fallTicks = 0;
	private int livedTicks = 0;
	public static final float baseSpeed = 2.5f;
	public static final float friction = 0.9f;
	public static final float maxSpeed = 12;
	private Rectangle hitbox;
	
	public Entity(float x, float y) {
		this.x = x;
		this.y = y;
		createHitbox();
	}
	
	public void createHitbox() {
		hitbox = new Rectangle(0, 0, (int) getWidth(), (int) getHeight());
	}
	
	public float getWeight() {
		return 1f;
	}
	
	public void moveTowardsPlayer(float speed) {
		Player player = (Player) getMap().getEntities(Player.class).get(0);
		float cx = Math.abs(getX() - player.getX());
		float cy = Math.abs(getY() - player.getY());
		if (cx>6*Tile.size || cy>6*Tile.size) {
			return;
		}
		if (cx<5) cx=0;
		if (cy<5) cy = 0;
		cx/=5;
		cy/=5;
		if (cx>speed) cx = speed;
		if (cy>speed) cy = speed;
		addVelocity(getX()>player.getX() ? -cx : cx, getY()>player.getY() ? -cy : cy);
	}
	
	@Override
	public void draw(Graphics2D g) {
	}
	
	@Override
	public void update() {
		livedTicks++;
		if (hasGravity()) {
			int i = getFallTicks();
			float g = 0.15f + (i*i*0.00002f);
			g*=getWeight();
			addVelocity(0, g > 3f ? 3f : g);
		}
		updateMovement();
		if (isLightSource()) {
			updateLighting();
		}
	}
	
	public float getLightXOffset() {
		return 0f;
	}
	
	public float getLightYOffset() {
		return 0f;
	}
	
	public int getLivedTicks() {
		return livedTicks;
	}
	
	public void updateMovement() {
		xVelocity *= friction;
		if (Math.abs(xVelocity) < 0.0001f) {
			xVelocity = 0f;
		}
		yVelocity *= friction;
		if (Math.abs(yVelocity) < 0.0001f) {
			yVelocity = 0f;
		}
		xSpeed = baseSpeed * xVelocity;
		ySpeed = baseSpeed * yVelocity;
		if (xSpeed > maxSpeed)
			xSpeed = maxSpeed;
		if (xSpeed < -maxSpeed)
			xSpeed = -maxSpeed;
		if (ySpeed > maxSpeed)
			ySpeed = maxSpeed;
		if (ySpeed < -maxSpeed)
			ySpeed = -maxSpeed;
		float nx = 0f, ny = 0f;
		float p = 0.01f;
		for (nx = Math.abs(xSpeed); nx > 0; nx -= p) {
			if (!getMap().isCollision(this, x + (xSpeed > 0 ? p : -p), y, CollisionType.TILE_SOLID) || !hasCollision()) {
				x += (xSpeed > 0 ? p : -p);
			} else {
				if (hasCollision()) xVelocity = 0f;
				break;
			}
		}
		for (ny = Math.abs(ySpeed); ny > 0; ny -= p) {
			if (!getMap().isCollision(this, x, y + (ySpeed > 0 ? p : -p), CollisionType.TILE_SOLID) || !hasCollision()) {
				y += (ySpeed > 0 ? p : -p);
			} else {
				if (hasCollision()) yVelocity=0f;
				break;
			}
		}
		if (!isOnGround()) {
			fallTicks++;
		} else {
			fallTicks = 0;
		}
	}
	
	public boolean isOnGround() {
		return getMap().isCollision(this, getX(), getY() + 1, CollisionType.TILE_SOLID);
	}
	
	public Color getLightColor() {
		return Color.red;
	}
	
	public void updateLighting() {
		if (displayRadius == -1f) {
			displayRadius = getLightRadius();
		}
		if (getLightFlicker() > 0) {
			if (displayRadius > getLightRadius() + getFlickerBoundary()) {
				displayRadius = getLightRadius() + getFlickerBoundary();
			} else if (displayRadius < getLightRadius() - getFlickerBoundary()) {
				displayRadius = getLightRadius() - getFlickerBoundary();
			}
			displayRadius += (Game.getRandom().nextFloat() - 0.5f) * 5;
		}
	}
	
	public float getFlickerBoundary() {
		return 5 * getLightFlicker();
	}
	
	public float getDisplayRadius() {
		return displayRadius;
	}
	
	public void addVelocity(float x, float y) {
		this.xVelocity += x;
		this.yVelocity += y;
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public Map getMap() {
		return map;
	}
	
	public void setMap(Map map) {
		this.map = map;
	}
	
	public boolean isLightSource() {
		return false;
	}
	
	public float getLightRadius() {
		return 50f;
	}
	
	public float getLightFlicker() {
		return 1f;
	}
	
	public boolean hasGravity() {
		return true;
	}
	
	public int getFallTicks() {
		return fallTicks;
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
	
	public float getXVelocity() {
		return xVelocity;
	}
	
	public float getYVelocity() {
		return yVelocity;
	}
	
	public void setHitbox(Rectangle hitbox) {
		this.hitbox = hitbox;
	}
	
	public boolean hasCollision() {
		return true;
	}
	
	public void remove() {
		getMap().removeEntity(this);
		try {
			finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
}
