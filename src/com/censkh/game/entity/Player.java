package com.censkh.game.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;

import com.censkh.game.engine.Settings;
import com.censkh.game.engine.util.CollisionType;
import com.censkh.game.engine.util.Direction;
import com.censkh.game.entity.enemy.Enemy;
import com.censkh.game.entity.particle.WalkSmokeParticle;
import com.censkh.game.input.Keyboard;
import com.censkh.game.map.MapManager;
import com.censkh.game.render.FrameInfo;
import com.censkh.game.render.Sprite;
import com.censkh.game.sound.Sound;
import com.censkh.game.tile.Tile;

public class Player extends LivingEntity {
	
	private int gold = 12;
	private float jumpBuffer = 0;
	private boolean jumpKeyPressed = false;
	private int attackTicks = 0;
	
	public Player(float x, float y) {
		super(x, y);
	}
	
	@Override
	public float getWeight() {
		return 1.5f;
	}
	
	@Override
	public void hurt() {
		addVelocity(0, -3);
		setNoDamageTicks(100);
	}
	
	public boolean isDirectionVelocityBased() {
		return false;
	}
	
	@Override
	public void kill() {
		
	}
	
	@Override
	public void update() {
		super.update();
		if (Keyboard.isKeyDown(Settings.keyMoveRight())) {
			setDirection(Direction.RIGHT);
			addVelocity(0.15f, 0f);
		}
		if (Keyboard.isKeyDown(Settings.keyMoveLeft())) {
			setDirection(Direction.LEFT);
			addVelocity(-0.15f, 0f);	
		}
		if (Keyboard.isKeyPressed(KeyEvent.VK_SHIFT)) {
			MapManager.getInstance().getRun().switchToNextMap();
		}
		if (Keyboard.isKeyPressed(Settings.keyAttack())&&getAttackTicks()==0) {
			setAttackTicks(20);
		}
		if (getAttackTicks()>0) {
			List<Entity> enemies = getMap().getCollisions(this,getX()+(getDirection()==Direction.LEFT ? -getWidth() : getWidth()),getY(), Enemy.class);
			for (Entity e :enemies) {
				((Enemy)e).damage(1f);
			}
			attackTicks--;
		}
		if (getLivedTicks() % 5 == 0 && isOnGround()) {
			if (getXVelocity() > 1.25f || getXVelocity() < -1.25f) {
				if (Keyboard.isKeyDown(Settings.keyMoveLeft()) || Keyboard.isKeyDown(Settings.keyMoveRight())) {
					getMap().createEntity(getX() + (getWidth() / 2) + 6, getY() + getHeight() - 6, WalkSmokeParticle.class);
					getMap().createEntity(getX() + (getWidth() / 2) - 10, getY() + getHeight() - 6, WalkSmokeParticle.class);
				}
			}
		}
		if (isOnGround() && Keyboard.isKeyPressed(Settings.keyJump())) {
			Sound.jump.play();
			addVelocity(0, -4f);
			jumpBuffer = 5;
			jumpKeyPressed = true;
		} else if (isOnGround()) {
			jumpKeyPressed=false;
		}
		
		
		if (!Keyboard.isKeyDown(Settings.keyJump()) || getMap().isCollision(this, getX(),getY()-1,CollisionType.TILE_SOLID)) {
			jumpKeyPressed = false;
		}
		if (getFallTicks() < 20 && isOnGround()==false && Keyboard.isKeyDown(Settings.keyJump()) && jumpKeyPressed) {
			addVelocity(0, -0.25f);
		}
		if (jumpBuffer > 0) {
			jumpBuffer--;
			addVelocity(0, -0.25f);
		} else if (jumpBuffer < 0) {
			jumpBuffer = 0;
		}
		
		if (Keyboard.isKeyPressed(KeyEvent.VK_ENTER)) {
			if (getDirection() == Direction.LEFT) {
				getMap().setTile(Tile.air, Tile.LAYER_MAIN, ((int) getX() / Tile.size), ((int) (getY() + 16) / Tile.size));
				getMap().setTile(Tile.air, Tile.LAYER_MAIN, ((int) getX() / Tile.size), ((int) (getY() + 16) / Tile.size) + 1);
			} else {
				getMap().setTile(Tile.air, Tile.LAYER_MAIN, ((int) getX() / Tile.size) + 1, ((int) (getY() + 16) / Tile.size));
				getMap().setTile(Tile.air, Tile.LAYER_MAIN, ((int) getX() / Tile.size) + 1, ((int) (getY() + 16) / Tile.size) + 1);
			}
		}
	}
	
	@Override
	public void createHitbox() {
		setHitbox(new Rectangle(4, 6, (int) getWidth() - 8, (int) getHeight() - 4));
	}
	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		getSprite().draw(g, "standing", getX() + (getDirection() == Direction.RIGHT ? 0 : getWidth()), getY(), getDirection() == Direction.RIGHT ? 1 : -1, 1,getNoDamageTicks()==0 ? 1f : 0.5f);
	}
	
	@Override
	public float getLightXOffset() {
		return getWidth() / 2;
	}
	
	@Override
	public float getLightYOffset() {
		return getHeight() / 2;
	}
	
	@Override
	public float getWidth() {
		return 36;
	}
	
	@Override
	public float getHeight() {
		return 64;
	}
	
	@Override
	public boolean isLightSource() {
		return true;
	}
	
	@Override
	public float getLightRadius() {
		return 200f;
	}
	
	@Override
	public float getLightFlicker() {
		return 5f;
	}
	
	@Override
	public int getRenderLayer() {
		return LAYER_MAIN;
	}
	
	@Override
	public float getDefaultMaxHealth() {
		return 1;
	}
	
	public int getGold() {
		return gold;
	}
	
	public void setGold(int gold) {
		this.gold = gold;
	}
	
	@Override
	public Sprite createSprite() {
		return new Sprite("res/entity/player.png", 36, 64).addState("standing", new FrameInfo[] {
				new FrameInfo(0, 0, 300), new FrameInfo(1, 0, 6), new FrameInfo(2, 0, 6), new FrameInfo(3, 0, 15), new FrameInfo(4, 0, 6), new FrameInfo(5, 0, 6)
		}).bake();
	}

	public void addGold(int value) {
		setGold(getGold()+value);
	}

	public int getAttackTicks() {
		return attackTicks;
	}

	public void setAttackTicks(int attackTicks) {
		this.attackTicks = attackTicks;
	}
	
}
