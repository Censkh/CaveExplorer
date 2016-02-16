package com.censkh.game.tile;

public class TileConstructure {
	
	private static TileConstructure current;
	
	private int x = -1;
	private int y = -1;
	private int layer = -1;
	private int tile = -1;
	
	public TileConstructure() {
		current = this;
	}
	
	public boolean finished() {
		return getX() != -1 && getY() != -1 && getLayer() != -1 && getTile() != -1;
	}
	
	public int completed() {
		int i = 0;
		if (getX() != -1) {
			i++;
		}
		if (getY() != -1) {
			i++;
		}
		if (getLayer() != -1) {
			i++;
		}
		if (getTile() != -1) {
			i++;
		}
		return i;
	}
	
	public void clear() {
		setX(-1);
		setY(-1);
		setLayer(-1);
		setTile(-1);
	}
	
	public int[] bake() {
		int[] i = new int[] {
				getX(), getY(), getLayer(), getTile()
		};
		clear();
		return i;
	}
	
	public static TileConstructure getCurrent() {
		return current;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getLayer() {
		return layer;
	}
	
	public void setLayer(int layer) {
		this.layer = layer;
	}
	
	public int getTile() {
		return tile;
	}
	
	public void setTile(int tile) {
		this.tile = tile;
	}
	
}
