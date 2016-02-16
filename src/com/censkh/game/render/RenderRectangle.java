package com.censkh.game.render;

import java.awt.Color;
import java.awt.Graphics2D;

public class RenderRectangle {
	
	public static void draw(Graphics2D g, float x, float y, float width, float height, Color color, float alpha) {
		g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) Math.floor(color.getAlpha() * alpha)));
		g.fillRect((int) x, (int) y, (int) width, (int) height);
	}
}
