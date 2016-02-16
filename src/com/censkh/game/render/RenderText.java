package com.censkh.game.render;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class RenderText {
	
	public static void draw(Graphics2D g, Font font, String text, float x, float y, float alpha, boolean shadow) {
		Color currentColor = ChatColor.WHITE.getColor();
		Color newColor = currentColor;
		int lineWraps = 0;
		int drawFrom = 0;
		int xOff = 0;
		boolean drawCurrent = false;
		boolean wrapLine = false;
		for (int i = 0; i < text.length(); i++) {
			drawCurrent = false;
			wrapLine = false;
			if (i != text.length() - 1) {
				String s = text.substring(i, i + 2);
				if (s.equals("&n")) {
					wrapLine = true;
					drawCurrent = true;
				} else {
					ChatColor cc = ChatColor.get(s);
					if (cc != null) {
						newColor = cc.getColor();
						drawCurrent = true;
					}
				}
				
			} else {
				drawCurrent = true;
				i++;
			}
			if (drawCurrent) {
				
				drawPlain(g, font, text.substring(drawFrom, i), x + xOff, y + (font.getSize2D() * lineWraps), shadow, new Color((int) currentColor.getRed(), (int) currentColor.getGreen(),
						(int) currentColor.getBlue(), (int) (255 * alpha)));
				
				if (wrapLine) {
					lineWraps++;
					
				}
				if (newColor != currentColor) {
					currentColor = newColor;
				}
				if (!wrapLine)
					xOff += font.getSize2D() * text.substring(drawFrom, i).length();
				else
					xOff = 0;
				drawFrom = i + 2;
				
			}
		}
	}
	
	private static void drawPlain(Graphics2D g, Font font, String text, float x, float y, boolean shadow, Color color) {
		g.setFont(font);
		y += font.getSize();
		if (shadow) {
			g.setColor(new Color(0, 0, 0, (int) Math.floor(((float) color.getAlpha() / (float) 255) * 255)));
			g.drawString(text, x + 2, y + 2);
		}
		
		g.setColor(color);
		g.drawString(text, x, y);
	}
	
}
