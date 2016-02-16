package com.censkh.game.gui.element;

import java.awt.Graphics2D;

public interface IGuiElement {
	
	public String getName();
	
	public void draw(Graphics2D g);
	
	public void update();
	
	public float getWidth();
	
	public float getHeight();
	
}
