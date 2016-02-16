package com.censkh.game.render;

import java.awt.image.BufferedImage;

public class Frame {
	
	private final BufferedImage image;
	private final int length;
	
	public Frame(BufferedImage image, int length) {
		this.image = image;
		this.length = length;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public int getLength() {
		return length;
	}
	
}
