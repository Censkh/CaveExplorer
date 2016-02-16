package com.censkh.game.engine;

import com.censkh.game.Game;
import com.censkh.game.GamePanel;

public class Time {
	
	private static final boolean debug = true;
	private static final int second = 1000;
	private static Time instance;
	private static double delta;
	private long oldSysTime = System.nanoTime();
	private int updatesThisSecond = 0;
	private int framesThisSecond = 0;
	private int framesLastSecond = 0;
	private float milsSinceUpdate = 0;
	private int cacheSecond = 0;
	private long lastLoopTime = 0;
	
	public Time() {
		instance = this;
	}
	
	public void update() {
		lastLoopTime = System.nanoTime();
		delta = (lastLoopTime - oldSysTime) / 1000000d;
		oldSysTime = lastLoopTime;
		milsSinceUpdate += delta;
		framesThisSecond++;
		if (milsSinceUpdate >= Time.second / Game.getRunSpeed()) {
			for (int i = 0; i < milsSinceUpdate / (Time.second / Game.getRunSpeed()); i++) {
				gameUpdate();
			}
		}
		GamePanel.getInstance().repaint();		
		if (cacheSecond < getSecond()) {
			cacheSecond = getSecond();
			if (debug)
				System.out.println("FPS: " + framesThisSecond + " Updates: " + updatesThisSecond + " Delta: " + delta);
			updatesThisSecond = 0;
			framesLastSecond = framesThisSecond;
			framesThisSecond = 0;
		}
		double wait = (double) (Math.floor((double) second / (double) Game.getTargetFps()) - Math.floor((double) (System.nanoTime() - lastLoopTime) / 1000000f));
		try {
			//Thread.yield();
			//Runtime.getRuntime().freeMemory();
				
			Thread.sleep((long) (wait < 0 ? 0 : wait));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void gameUpdate() {
		if (!Game.initialized)
			return;
		updatesThisSecond++;
		Game.getGame().update();
		milsSinceUpdate -= Time.second / (double) Game.getRunSpeed();
	}
	
	public static Time getInstance() {
		return instance;
	}
	
	public static double getDelta() {
		return delta;
	}
	
	public int getSecond() {
		return (int) (System.nanoTime() / 1000000000f);
	}
	
	public int getFramesThisSecond() {
		return framesThisSecond;
	}
	
	public int getFramesLastSecond() {
		return framesLastSecond;
	}
	
	public int getUpdatesThisSecond() {
		return updatesThisSecond;
	}
	
}
