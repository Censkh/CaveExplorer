package com.censkh.game.gui.element;

import java.awt.event.KeyEvent;

import com.censkh.game.input.Keyboard;

public abstract class GuiSelectable extends GuiClickable {
	
	private boolean selected;
	
	@Override
	public void update() {
		if (isSelected()) {
			mouseAttention();
		}
		super.update();
		if (!canHaveAttention()) {
			return;
		}
		if (Keyboard.isKeyDown(KeyEvent.VK_ESCAPE)) {
			setSelected(false);
		}
		if (hasMouseAttention() && isHovering() && isClicked()) {
			setSelected(true);
		}
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}
