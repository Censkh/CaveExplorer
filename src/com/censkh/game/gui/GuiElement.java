package com.censkh.game.gui;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.censkh.game.gui.element.IGuiElement;
import com.censkh.game.input.Mouse;

public abstract class GuiElement implements IGuiElement {
	
	private static boolean mouseGrabbed = false;
	private boolean wantsAttention = false;
	private GuiElement giveAttention = null;
	private String label = null;
	private boolean hasMouse = false;
	private float transitionAlpha = 0f;
	private GuiElement superContainer;
	private GuiElement container;
	private List<GuiElement> elements = null;
	private float x = -1;
	private float y = -1;
	private boolean debug;
	private float[] padding = createPadding();
	private boolean visible = true;
	private float alpha = 1f;
	private float relativeX = 0;
	private float relativeY = 0;
	private Point mouseGrabPoint;
	private Runnable onUpdate;
	private Runnable onDraw;
	private GuiElement mouseAttention;
	private boolean moveable = false;
	private Graphics2D onDrawGraphics;
	private GuiMenu menu = null;
	
	public float getElementPadding() {
		return 6f;
	}
	
	public GuiElement[] defaultElements() {
		return new GuiElement[] {};
	}
	
	public GuiElement getElement(String label) {
		for (GuiElement e : getElements()) {
			if (e.getLabel().equals(label)) {
				return e;
			}
		}
		return null;
	}
	
	public float[] createPadding() {
		return new float[] {
				8f, 8f, 8f, 8f
		};
	}
	
	public boolean isMoveable() {
		return moveable;
	}
	
	public boolean isContainer() {
		return true;
	}
	
	public void render(Graphics2D g, float rx, float ry) {
		onDrawGraphics = g;
		setRelativeX(rx);
		setRelativeY(ry);
		if (getDisplayAlpha() > 0f) {
			draw(g);
			if (onDraw != null)
				onDraw.run();
		}
		onDrawGraphics = null;
		float yOff = 0;
		for (GuiElement e : getElements()) {
			if (e.getY() == -1) {
				e.render(g, rx + (e.getX() == -1 ? 0 : e.getX()) + getPadding()[0], ry + getPadding()[1] + (e.getY() == -1 ? yOff : e.getY()));
				yOff += e.getHeight() + (e.getY() == -1 ? 0 : e.getY()) + e.getElementPadding();
			} else {
				e.render(g, rx + (e.getX() == -1 ? 0 : e.getX()) + getPadding()[0], ry + getPadding()[1] + e.getY());
			}
			
		}
	}
	
	public float getAccumlativeWidth() {
		float w = 0f;
		for (int i = 0; i < getElements().size(); i++) {
			GuiElement e = getElement(i);
			w += (i == 0 || i == getElements().size() - 1 ? 0 : e.getElementPadding()) + e.getWidth();
		}
		return w;
	}
	
	public GuiElement getElement(int i) {
		try {
			return getElements().get(i);
		} catch (Exception e) {
			return null;
		}
	}
	
	public float getAccumlativeHeight() {
		float h = 0f;
		for (int i = 0; i < getElements().size(); i++) {
			GuiElement e = getElement(i);
			h += (i == 0 || i == getElements().size() - 1 ? 0 : e.getElementPadding()) + e.getHeight();
		}
		return h;
	}
	
	public void updateSuper() {
		if (getSuperContainer() == this) {
			giveAttention = null;
		}
		wantsAttention = false;
		float a = 0.1f;
		if (isVisible()) {
			if (transitionAlpha != 1f) {
				transitionAlpha += a;
			}
		} else {
			
			if (transitionAlpha != 0f) {
				transitionAlpha -= a;
			}
		}
		if (transitionAlpha < 0f)
			transitionAlpha = 0f;
		if (transitionAlpha > 1f)
			transitionAlpha = 1f;
		if (getContainer() != null) {
			if (getX() < 0 + getPadding()[0] && getX() != -1) {
				setX(0 + getPadding()[0]);
			}
			if (getY() < 0 + getPadding()[1] && getY() != -1) {
				setY(0 + getPadding()[1]);
			}
			if (getX() > getContainer().getWidth() - getWidth() - getPadding()[2]) {
				setX(getContainer().getWidth() - getWidth() - getPadding()[2]);
			}
			if (getY() > getContainer().getHeight() - getHeight() - getPadding()[3]) {
				setY(getContainer().getHeight() - getHeight() - getPadding()[3]);
			}
		}
	}
	
	public boolean canHaveAttention() {
		return !isGrabbed() && mouseGrabbed == false;
	}
	
	public boolean isGrabbed() {
		if (container == null)
			return hasMouse;
		if (hasMouse)
			return true;
		return container.isGrabbed();
	}
	
	public boolean isHovering() {
		return Mouse.hovering(getRelativeX(), getRelativeY(), getWidth(), getHeight(), false);
	}
	
	public boolean isClicked() {
		return Mouse.buttonDown(MouseEvent.BUTTON1);
	}
	
	public float getDisplayAlpha() {
		return getAlpha() * (getContainer() == null ? 1f : getContainer().getDisplayAlpha()) * getTransitionAlpha();
	}
	
	public float getLargestElementWidth() {
		float lw = -1;
		for (GuiElement e : getElements()) {
			if (e.getWidth() > lw)
				lw = e.getWidth();
		}
		return lw;
	}
	
	public float getLargestElementHeight() {
		float lh = -1;
		for (GuiElement e : getElements()) {
			if (e.getHeight() > lh)
				lh = e.getHeight();
		}
		return lh;
	}
	
	public GuiElement add(GuiElement[] elements) {
		for (GuiElement e : elements) {
			if (!isContainer()) {
				System.out.println("Error: Tried to add gui element to a non-container.");
				return this;
			}
			getElements().add(e);
			e.setContainer(this);
			
		}
		return this;
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public boolean isDebug() {
		return debug;
	}
	
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	public GuiElement getContainer() {
		return container;
	}
	
	public void setContainer(GuiElement container) {
		this.container = container;
	}
	
	public List<GuiElement> getElements() {
		if (elements == null) {
			elements = new ArrayList<GuiElement>();
			add(defaultElements());
		}
		return elements;
	}
	
	public float[] getPadding() {
		return padding;
	}
	
	public void setPadding(float[] padding) {
		this.padding = padding;
	}
	
	public float getAlpha() {
		return alpha;
	}
	
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	
	public float getTransitionAlpha() {
		return transitionAlpha;
	}
	
	public void setTransitionAlpha(float transitionAlpha) {
		this.transitionAlpha = transitionAlpha;
	}
	
	public boolean isVisible() {
		if (!getSuperContainer().getMenu().isVisible())
			return false;
		if (getContainer() == null) {
			return visible;
		}
		return visible && getContainer().isVisible();
	}
	
	public GuiMenu getMenu() {
		return menu;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public GuiElement getSuperContainer() {
		if (superContainer == null && container == null) {
			superContainer = this;
		}
		if (superContainer == null) {
			superContainer = container.getSuperContainer();
		}
		return superContainer;
	}
	
	public void updateElements() {
		updateSuper();
		if (isVisible())
			update();
		if (onUpdate != null) {
			onUpdate.run();
		}
		for (GuiElement e : getElements()) {
			if (isVisible())
				e.updateElements();
		}
		if (getSuperContainer() == this) {
			if (mouseAttention != null) {
				if (!mouseAttention.wantsAttention) {
					mouseAttention = null;
				}
			}
			if (mouseAttention == null) {
				mouseAttention = giveAttention;
			}
			
		}
		updatePosition();
		
	}
	
	public boolean hasMouseAttention() {
		return getSuperContainer().getMouseAttention() == this;
	}
	
	public GuiElement getMouseAttention() {
		return mouseAttention;
	}
	
	private void updatePosition() {
		boolean can = isHovering() || hasMouse;
		if (isMoveable() && isVisible() && getSuperContainer().mouseAttention == null && getSuperContainer().giveAttention == null && can) {
			if (!isClicked()) {
				mouseGrabbed = false;
			}
			if (mouseGrabbed) {
				if (hasMouse) {
					setX(Mouse.getMouseX() - mouseGrabPoint.x);
					setY(Mouse.getMouseY() - mouseGrabPoint.y);
				}
			} else {
				hasMouse = false;
				if (isHovering()) {
					if (isClicked()) {
						mouseGrabbed = true;
						hasMouse = true;
						mouseGrabPoint = new Point((int) (Mouse.getMouseX() - getX()), (int) (Mouse.getMouseY() - getY()));
					}
				}
			}
		}
	}
	
	public float getRelativeY() {
		return relativeY;
	}
	
	public void setRelativeY(float relativeY) {
		this.relativeY = relativeY;
	}
	
	public float getRelativeX() {
		return relativeX;
	}
	
	public void setRelativeX(float relativeX) {
		this.relativeX = relativeX;
	}
	
	public void mouseAttention() {
		getSuperContainer().giveAttention = this;
		wantsAttention = true;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public Runnable getOnUpdate() {
		return onUpdate;
	}
	
	public void setOnUpdate(Runnable onUpdate) {
		this.onUpdate = onUpdate;
	}
	
	public GuiElement getGiveAttention() {
		return giveAttention;
	}
	
	public void setGiveAttention(GuiElement giveAttention) {
		this.giveAttention = giveAttention;
	}
	
	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}
	
	public Runnable getOnDraw() {
		return onDraw;
	}
	
	public void setOnDraw(Runnable onDraw) {
		this.onDraw = onDraw;
	}
	
	public Graphics2D getOnDrawGraphics() {
		return onDrawGraphics;
	}
	
	public void setMenu(GuiMenu menu) {
		this.menu = menu;
	}
	
}
