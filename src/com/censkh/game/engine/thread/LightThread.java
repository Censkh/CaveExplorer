package com.censkh.game.engine.thread;

import java.awt.image.BufferedImage;
import java.util.List;

import com.censkh.game.Game;
import com.censkh.game.entity.Entity;
import com.censkh.game.map.Map;
import com.censkh.game.map.MapManager;

public class LightThread extends GameThread {
	
	private BufferedImage lightMap;
	private final int[] blankArray;
	private final int width;
	private final int height;
	private final int[] pixels;
	
	public LightThread() {
		super();
		setName("Lighting");
		width = (int) (Game.getViewWidth());
		height = (int) (Game.getViewHeight());
		lightMap = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		blankArray = new int[width * height];
		pixels = new int[blankArray.length];
		int black = getColor(0, 0, 0, 255);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				blankArray[(x * height) + y] = black;
			}
		}
		
	}
	
	@Override
	public void run() {
		while (true) {
			if (MapManager.getCurrentMap() != null) {
				
				System.arraycopy(blankArray, 0, pixels, 0, blankArray.length);
				List<Entity> l = getMap().getLights();
				for (Entity light : l) {
					float lx = light.getX() + light.getLightXOffset();
					float ly = light.getY() + light.getLightYOffset();
					float dr = light.getDisplayRadius();
					float cx = getMap().getCamera().getX();
					float cy = getMap().getCamera().getY();
					if (lx > cx - (0.5 * Game.getViewWidth()) && lx < cx + (1.5 * Game.getViewWidth()) && ly > cy - (0.5 * Game.getViewHeight()) && ly < cy + (1.5 * Game.getViewHeight())) {
						for (short x = (short) (lx - dr); x < lx + dr; x++) {
							for (short y = (short) (ly - dr); y < ly + dr; y++) {
								
								if (x > cx && x < cx + Game.getViewWidth() && y > cy && y < cy + Game.getViewHeight()) {
									double dist = distance((int) lx, (int) ly, x, y);
									if (dist > light.getDisplayRadius())
										dist = light.getDisplayRadius();
									int tx = (int) (x - cx);
									int ty = (int) (y - cy);
									float a = (float) (dist / light.getDisplayRadius()) * (float) (0xFF & (pixels[(ty * width) + tx] >> 24)) / 255;
									int r = 0, g = 0, b = 0;
									int argb = (int) (255*a);
									argb = (argb << 8) + r;
									argb = (argb<< 8) + g;
									argb = (argb << 8) + b;
									pixels[(ty * width) + tx] = argb;
									
								}
							}
						}
					}
				}
				lightMap.setRGB(0, 0, width, height, pixels, 0, width);
			}
			
			try {
				//Thread.yield();
				Thread.sleep(50);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private double distance(int x1, int y1, int x2, int y2) {
		int n = (int) ((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1));
		return Math.sqrt(n);
	}
	
	private int getColor(int r, int g, int b, int a) {
		a = (a << 8) + r;
		a = (a << 8) + g;
		a = (a << 8) + b;
		return a;
	}
	
	public Map getMap() {
		return MapManager.getInstance().getCurrent();
	}
	
	public BufferedImage getLightMap() {
		return lightMap;
	}
	
	public void setLightMap(BufferedImage lightMap) {
		this.lightMap = lightMap;
	}
	
}
