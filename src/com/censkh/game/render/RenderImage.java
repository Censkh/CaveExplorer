package com.censkh.game.render;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

public class RenderImage {
	
	private static AlphaComposite cacheAc = null;
	private static AffineTransform t = new AffineTransform();
	private static AffineTransform blankT = new AffineTransform();
	
	public static void draw(Graphics2D g, Image image, float x, float y) {
		draw(g, image, x, y, 1, 1);
	}
	
	public static void draw(Graphics2D g, Image image, float x, float y, float alpha) {
		draw(g, image, x, y, 1, 1, alpha);
	}
	
	public static void draw(Graphics2D g, Image image, float x, float y, float xs, float ys) {
		draw(g, image, x, y, xs, ys, 1f);
	}
	
	public static void draw(Graphics2D g, Image image, float x, float y, float xs, float ys, float alpha) {
		if (alpha != 1f) {
			cacheAc = (AlphaComposite) g.getComposite();
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		}
		t.setTransform(blankT);
		if (x != 0f || y != 0f)
			t.translate(x, y);
		if (xs != 1f || ys != 1f)
			t.scale(xs, ys);
		g.drawImage(image, t, null);
		if (alpha != 1f)
			g.setComposite(cacheAc);
	}
}
