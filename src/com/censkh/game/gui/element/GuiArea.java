package com.censkh.game.gui.element;

import java.awt.Color;
import java.awt.Graphics2D;

import com.censkh.game.gui.GuiElement;

public class GuiArea extends GuiElement {
	
	private float width = -1;
	private float height = -1;
	private Color color = new java.awt.Color(64, 64, 64, 128);
	
	@Override
	public String getName() {
		return "panel.colour";
	}
	
	@Override
	public void draw(Graphics2D g) {
		
	}
	
	@Override
	public float[] createPadding() {
		return new float[] {
				0f, 0f, 0f, 0f
		};
	}
	
	@Override
	public float getWidth() {
		if (width == -1) {
			float hWidth = 0;
			for (GuiElement e : getElements()) {
				if (e.getWidth() > hWidth)
					hWidth = e.getWidth();
			}
			return hWidth + getPadding()[0] + getPadding()[2];
		}
		return width + getPadding()[0] + getPadding()[2];
	}
	
	@Override
	public float getHeight() {
		if (height == -1) {
			float h = 0;
			for (GuiElement e : getElements()) {
				h += e.getHeight() + (e.getY() == -1 ? e.getElementPadding() : 0);
			}
			return h + getPadding()[1] + getPadding()[3];
		}
		return height + getPadding()[1] + getPadding()[3];
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	@Override
	public void update() {
		
	}
	
}
