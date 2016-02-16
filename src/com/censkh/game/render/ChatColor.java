package com.censkh.game.render;

import java.awt.Color;

public enum ChatColor {
	BLACK('0', new Color(0, 0, 0)),
	DARK_BLUE('1', new Color(0, 0, 170)),
	DARK_GREEN('2', new Color(0, 170, 0)),
	DARK_AQUA('3', new Color(0, 170, 170)),
	DARK_RED('4', new Color(170, 0, 0)),
	DARK_PURPLE('5', new Color(170, 0, 170)),
	GOLD('6', new Color(255, 170, 0)),
	GREY('7', new Color(170, 170, 170)),
	DARK_GREY('8', new Color(85, 85, 85)),
	BLUE('9', new Color(85, 85, 255)),
	GREEN('a', new Color(85, 255, 85)),
	AQUA('b', new Color(85, 255, 255)),
	RED('c', new Color(255, 85, 85)),
	LIGHT_PURPLE('d', new Color(255, 85, 255)),
	YELLOW('e', new Color(255, 255, 85)),
	WHITE('f', new Color(255, 255, 255)),
	NEW_LINE('n', Color.white, false);
	
	private final char cc;
	private final Color color;
	private final boolean isColor;
	
	private ChatColor(char cc, Color color, boolean isColor) {
		this.cc = cc;
		this.color = color;
		this.isColor = isColor;
	}
	
	private ChatColor(char cc, Color color) {
		this(cc, color, true);
	}
	
	@Override
	public String toString() {
		return "&" + cc;
	}
	
	public boolean equals(String code) {
		if (code.equals(toString())) {
			return true;
		}
		return false;
	}
	
	public Color getColor() {
		return color;
	}
	
	public static ChatColor get(String code) {
		for (ChatColor c : values()) {
			if (c.equals(code) && c.isColor) {
				return c;
			}
		}
		return null;
	}
	
	public static String stripColor(String text) {
		for (ChatColor c : values()) {
			text = text.replaceAll(c.toString(), "");
		}
		return text;
	}
	
}
