package com.censkh.game.input;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import com.censkh.game.Game;
import com.censkh.game.GameFrame;
import com.censkh.game.engine.util.IUpdate;

public class Mouse implements MouseListener,MouseMotionListener, IUpdate {
	
	private static Mouse instance;
	private final List<Integer> mouseDown = new ArrayList<Integer>();
	private final List<Integer> mousePressed = new ArrayList<Integer>();
	private final List<Integer> mouseReleased = new ArrayList<Integer>();
	private final List<Integer> cacheMousePressed = new ArrayList<Integer>();
	private final List<Integer> cacheMouseReleased = new ArrayList<Integer>();
	private static float x;
	private static float y;
	
	public Mouse(GameFrame frame) {
		instance = this;
		frame.addMouseMotionListener(this);
		frame.addMouseListener(this);
	}
	
	public static float getMouseX() {
		return x -3;
	}
	
	public static float getMouseY() {
		return y - 25;
	}
	
	public static float getMouseScaledX() {
		float p = getMouseX() / (float) Game.getGame().getScale();
		return p;
	}
	
	public static float getMouseScaledY() {
		float p = getMouseY() / (float)Game.getGame().getScale();
		return p;
	}
	
	public static boolean hovering(Rectangle rect, boolean scale) {
		return rect.contains(scale ? getMouseScaledX() : getMouseX(), scale ? getMouseScaledY() : getMouseY());
	}
	
	public static boolean hovering(int x, int y, int width, int height, boolean scale) {
		return hovering(new Rectangle(x, y, width, height), scale);
	}
	
	public static boolean buttonDown(int button) {
		return instance.mouseDown.contains(button);
	}
	
	public static boolean buttonPressed(int button) {
		return instance.mousePressed.contains(button)|| instance.cacheMousePressed.contains(button);
	}
	
	public static boolean buttonReleased(int button) {
		return instance.mouseReleased.contains(button)||instance.cacheMouseReleased.contains(button);
	}
	
	public static boolean hovering(float x, float y, float width, float height, boolean scale) {
		return hovering(new Rectangle((int) x, (int) y, (int) width, (int) height), scale);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (!mouseDown.contains(e.getButton())) {
			mouseDown.add(e.getButton());
			mousePressed.add(e.getButton());
		}
		mouseReleased.remove(Integer.valueOf(e.getButton()));
		cacheMouseReleased.remove(Integer.valueOf(e.getButton()));
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDown.remove(Integer.valueOf(e.getButton()));
		mousePressed.remove(Integer.valueOf(e.getButton()));
		cacheMousePressed.remove(Integer.valueOf(e.getButton()));
		mouseReleased.add(e.getButton());
	}
	
	@Override
	public void update() {
		cacheMousePressed.clear();
		cacheMouseReleased.clear();
		cacheMousePressed.addAll(mousePressed);
		cacheMouseReleased.addAll(mouseReleased);
		mousePressed.clear();
		mouseReleased.clear();
	}
	
	public static Mouse getInstance() {
		return instance;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}
	
}
