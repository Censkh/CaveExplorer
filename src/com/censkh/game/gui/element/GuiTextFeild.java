package com.censkh.game.gui.element;

import java.awt.Color;
import java.awt.Graphics2D;

import com.censkh.game.gui.GuiElement;
import com.censkh.game.render.RenderRectangle;

public class GuiTextFeild extends GuiSelectable {
	
	private float width = -1;
	private float height = -1;
	private Color color = Color.gray;
	
	@Override
	public String getName() {
		return "selectable.input.textfield";
	}
	
	@Override
	public GuiElement[] defaultElements() {
		return new GuiElement[] {
			new GuiText() {
				{
					setLabel("string");
					setText("default");
				}
			}
		};
	}
	
	@Override
	public void draw(Graphics2D g) {
		RenderRectangle.draw(g, getRelativeX(), getRelativeY(), getWidth(), getHeight(), getColor(), getDisplayAlpha());
		if (isSelected())
			RenderRectangle.draw(g, getRelativeX(), getRelativeY(), getWidth(), getHeight(), Color.white, getDisplayAlpha() * 0.5f);
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
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public void update() {
		super.update();
		if (isSelected()) {
			
		}
	}
	
	public GuiText getText() {
		return (GuiText) getElement("string");
	}
	
}
