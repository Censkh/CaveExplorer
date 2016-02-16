package com.censkh.game.render;

import java.util.ArrayList;
import java.util.List;

public class SpriteManager {
	
	private static SpriteManager instance;
	private final List<Sprite> sprites = new ArrayList<Sprite>();
	
	public SpriteManager() {
		instance = this;
	}
	
	public void update() {
		for (Sprite s : getSprites()) {
			s.update();
		}
	}
	
	public void add(Sprite sprite) {
		sprites.add(sprite);
	}
	
	public List<Sprite> getSprites() {
		return sprites;
	}
	
	public static SpriteManager getInstance() {
		return instance;
	}
	
}
