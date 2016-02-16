package com.censkh.game.gui.element;

public class GuiTextNonStatic extends GuiText {
	
	private Runnable textUpdate;
	
	@Override
	public String getName() {
		return "text.nonstatic";
	}
	
	@Override
	public void update() {
		super.update();
		getTextUpdate().run();
	}
	
	public Runnable getTextUpdate() {
		return textUpdate;
	}
	
	public void setTextUpdate(Runnable textUpdate) {
		this.textUpdate = textUpdate;
	}
	
}
