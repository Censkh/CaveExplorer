package com.censkh.game.gui.element;

import java.awt.Color;
import java.awt.Graphics2D;

import com.censkh.game.render.ButtonSkin;

public class GuiButton extends GuiClickable {
	
	private float width = -1;
	private float height = -1;
	private Color color = Color.gray;
	
	@Override
	public String getName() {
		return "clickable.button.plain";
	}
	
	@Override
	public void draw(Graphics2D g) {
		if (isButtonPressed() && isHovering())
			ButtonSkin.hoverSkin.render(g, getRelativeX(),getRelativeY(), getWidth(), getHeight(),getDisplayAlpha());
		else if ((isHovering() && hasMouseAttention()) || (isButtonPressed() && !isHovering()))
			ButtonSkin.clickingSkin.render(g, getRelativeX(),getRelativeY(), getWidth(), getHeight(),getDisplayAlpha());
		else 
			ButtonSkin.defaultSkin.render(g, getRelativeX(),getRelativeY(), getWidth(), getHeight(),getDisplayAlpha());
		
	}
	
	@Override
	public void update() {
		super.update();
		
	}
	
	@Override
	public float getWidth() {
		if (width == -1)
			return getLargestElementWidth() == -1 ? 16 : getLargestElementWidth() + getPadding()[0] + getPadding()[2];
		float w = width;
		if (getLargestElementWidth() > width) {
			w = getLargestElementWidth();
		}
		return w + getPadding()[0] + getPadding()[2];
	}
	
	@Override
	public float getHeight() {
		if (height == -1)
			return getLargestElementHeight() == -1 ? 16 : getLargestElementHeight() + getPadding()[0] + getPadding()[2];
		float h = height;
		if (getLargestElementHeight() > height) {
			h = getLargestElementHeight();
		}
		return h + getPadding()[1] + getPadding()[3];
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
}
