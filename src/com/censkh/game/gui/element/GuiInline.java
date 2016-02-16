package com.censkh.game.gui.element;

import java.awt.Graphics2D;

import com.censkh.game.gui.GuiElement;

public class GuiInline extends GuiElement {
	
	@Override
	public String getName() {
		return "structure.inline";
	}
	
	@Override
	public float[] createPadding() {
		return new float[] {
				0f, 8f, 8f, 8f,
		};
	}
	
	@Override
	public void render(Graphics2D g, float rx, float ry) {
		setRelativeX(rx);
		setRelativeY(ry);
		draw(g);
		float xOff = 0;
		for (GuiElement e : getElements()) {
			if (e.getX() == -1) {
				e.render(g, rx + getPadding()[0] + (e.getX() == -1 ? xOff : e.getX()), ry + getPadding()[1]);
				xOff += e.getWidth() + (e.getX() == -1 ? 0 : e.getX()) + e.getElementPadding();
			} else {
				e.render(g, rx + e.getX() + getPadding()[0], ry + getPadding()[1] + e.getY());
			}
			
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		
	}
	
	@Override
	public void update() {
		
	}
	
	@Override
	public float getWidth() {
		return getAccumlativeWidth() + getPadding()[2];
	}
	
	@Override
	public float getHeight() {
		return getLargestElementHeight() + getPadding()[3];
	}
	
}
