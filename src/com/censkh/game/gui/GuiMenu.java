package com.censkh.game.gui;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.censkh.game.gui.menu.IGuiMenu;

public abstract class GuiMenu implements IGuiMenu {
	
	private final List<GuiElement> elements = new ArrayList<GuiElement>();
	private boolean visible;
	
	public GuiMenu() {
		setVisible(isDefaultVisible());
		elements.addAll(Arrays.asList(createElements()));
		for (GuiElement e : elements) {
			e.setMenu(this);
		}
	}
	
	public void render(Graphics2D g) {
		for (GuiElement e : getElements()) {
			e.render(g, 0 + e.getX(), 0 + e.getY());
		}
	}
	
	public void update() {
		for (GuiElement e : getElements()) {
			e.updateElements();
		}
	}
	
	public List<GuiElement> getElements() {
		return elements;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isDefaultVisible() {
		return true;
	}
	
}
