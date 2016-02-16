package com.censkh.game.gui.element;

import java.awt.Graphics2D;

import com.censkh.game.render.RenderRectangle;

public class GuiPanel extends GuiArea {
	
	@Override
	public float[] createPadding() {
		return new float[] {
				8f, 8f, 8f, 8f
		};
	}
	
	@Override
	public void draw(Graphics2D g) {
		RenderRectangle.draw(g, getRelativeX(), getRelativeY(), getWidth(), getHeight(), getColor(), getDisplayAlpha());
	}
	
}
