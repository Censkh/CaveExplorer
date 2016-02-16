package com.censkh.game.gui.element;

import java.awt.Graphics2D;
import java.awt.Image;

import com.censkh.game.gui.GuiElement;
import com.censkh.game.render.RenderImage;

public class GuiImage extends GuiElement {
	
	private Image image = null;
	
	@Override
	public String getName() {
		return "image.plain";
	}
	
	public float[] createPadding() {
		return new float[] {
				0f, 0f, 0f, 0f
		};
	}
	
	@Override
	public void draw(Graphics2D g) {
		if (getImage() != null)
			RenderImage.draw(g, getImage(), getRelativeX(), getRelativeY(), getDisplayAlpha());
	}
	
	@Override
	public boolean isContainer() {
		return false;
	}
	
	@Override
	public void update() {
		
	}
	
	@Override
	public float getWidth() {
		float width = -1;
		if (image != null) {
			width = image.getWidth(null);
		}
		return width;
	}
	
	@Override
	public float getHeight() {
		float height = -1;
		if (image != null) {
			height = image.getHeight(null);
		}
		return height;
	}
	
	public Image getImage() {
		return image;
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
	
}
