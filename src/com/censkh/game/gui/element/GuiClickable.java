package com.censkh.game.gui.element;

import com.censkh.game.gui.GuiElement;
import com.censkh.game.sound.Sound;

public abstract class GuiClickable extends GuiElement {
	
	private boolean buttonPressed = false;
	private Runnable onClick = new Runnable() {
		@Override
		public void run() {
			if (isDebug()) {
				System.out.println("Debug click.");
			}
		}
	};
	private Runnable onHover = new Runnable() {
		@Override
		public void run() {
			if (isDebug()) {
				System.out.println("Debug hover.");
			}
		}
	};
	
	@Override
	public float[] createPadding() {
		return new float[] {
				8f, 8f, 8f, 8f,
		};
	}
	
	public Runnable getOnClick() {
		return onClick;
	}
	
	public void setOnClick(Runnable onClick) {
		this.onClick = onClick;
	}
	
	@Override
	public void update() {
		if (canHaveAttention())
			if (isHovering() || buttonPressed) {
				mouseAttention();
			}
		if (isHovering() && hasMouseAttention()) {
			if (isClicked()) {
				
				if (!buttonPressed) {
					Sound.click.play();
					buttonPressed = true;
				}
			} else {
				if (buttonPressed) {
					getOnClick().run();
					buttonPressed = false;
				}
				
			}
			getOnHover().run();
		} else {
			if (!hasMouseAttention())
				buttonPressed = false;
		}
		if (!isClicked() && buttonPressed) {
			buttonPressed = false;
		}
	}
	
	public Runnable getOnHover() {
		return onHover;
	}
	
	public void setOnHover(Runnable onHover) {
		this.onHover = onHover;
	}
	
	public boolean isButtonPressed() {
		return buttonPressed;
	}
	
}
