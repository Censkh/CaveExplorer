package com.censkh.game.engine;

import java.util.List;

import com.censkh.game.Game;
import com.censkh.game.entity.Entity;
import com.censkh.game.entity.Player;
import com.censkh.game.map.Map;

public class Camera {
	
	private float x = 0;
	private float y = 0;
	private Map map;
	
	public Camera(Map map, float x, float y) {
		this.map = map;
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		List<Entity> el = getMap().getEntities(Player.class);
		if (el.size() > 0) {
			Player player = (Player) getMap().getEntities(Player.class).get(0);
			float px = player.getX() - (Game.getViewWidth() / 2);
			float py = player.getY() - (Game.getViewHeight() / 2);
			float s = 10;
			setX(x - ((x - px) / s));
			setY(y - ((y - py) / s));
		}
		
	}
	
	public Map getMap() {
		return map;
	}
	
	public void setMap(Map map) {
		this.map = map;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		if (y < 0)
			y = 0;
		if (y > getMap().getHeight() - Game.getViewHeight())
			y = getMap().getHeight() - Game.getViewHeight();
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		if (x < 0)
			x = 0;
		if (x > getMap().getWidth() - Game.getViewWidth())
			x = getMap().getWidth() - Game.getViewWidth();
		this.x = x;
	}
	
}
