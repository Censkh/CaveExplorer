package com.censkh.game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class Keyboard implements KeyListener {
	
	private static Keyboard instance;
	private final List<Integer> keysDown = new ArrayList<Integer>();
	private final List<Integer> cacheKeysPressed = new ArrayList<Integer>();
	private final List<Integer> keysPressed = new ArrayList<Integer>();
	private final List<Integer> cacheKeysReleased = new ArrayList<Integer>();
	private final List<Integer> keysReleased = new ArrayList<Integer>();
	
	public Keyboard() {
		instance = this;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (!keysDown.contains(e.getKeyCode())) {
			keysDown.add(e.getKeyCode());
			keysPressed.add(e.getKeyCode());
		}
		keysReleased.remove(Integer.valueOf(e.getKeyCode()));
		cacheKeysReleased.remove(Integer.valueOf(e.getKeyCode()));
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		keysDown.remove(Integer.valueOf(e.getKeyCode()));
		keysPressed.remove(Integer.valueOf(e.getKeyCode()));
		cacheKeysPressed.remove(Integer.valueOf(e.getKeyCode()));
		keysReleased.add(e.getKeyCode());
	}
	
	public static boolean isKeyDown(int key) {
		return instance.keysDown.contains(Integer.valueOf(key));
	}
	
	public static boolean isKeyPressed(int key) {
		return instance.keysPressed.contains(Integer.valueOf(key)) || instance.cacheKeysPressed.contains(Integer.valueOf(key));
	}
	
	public static boolean isKeyReleased(int key) {
		return instance.keysReleased.contains(Integer.valueOf(key)) || instance.cacheKeysReleased.contains(Integer.valueOf(key));
	}
	
	public static Keyboard getInstance() {
		return instance;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public void update() {
		cacheKeysPressed.clear();
		cacheKeysReleased.clear();
		cacheKeysPressed.addAll(keysPressed);
		cacheKeysReleased.addAll(keysReleased);
		keysPressed.clear();
		keysReleased.clear();
	}
	
}
