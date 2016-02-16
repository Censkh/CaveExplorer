package com.censkh.game.render;

import java.awt.Point;

public class FrameInfo {
	
	private final Point position;
	private final int length;
	
	public FrameInfo(int x, int y, int length) {
		this.position = new Point(x, y);
		this.length = length;
	}
	
	public Point getPosition() {
		return position;
	}
	
	public int getLength() {
		return length;
	}
	
}
