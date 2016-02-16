package com.censkh.game.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameRun {
	
	private final List<Map> maps = new ArrayList<Map>();
	private int progress = 0;
	
	public GameRun() {
		maps.addAll(Arrays.asList(new Map[] {
				MapManager.getInstance().createMap("Grassland 1-1"),
				MapManager.getInstance().createMap("Grassland 1-2"),
		}));
	}
	
	public Map getFirstMap() {
		return maps.get(0);
	}
	
	public void switchToNextMap() {
		progress++;
		MapManager.getInstance().switchMap(maps.get(progress));
	}
	
	public void destroy() {
		for (Map m : maps) {
			MapManager.getInstance().destroyMap(m);
		}
		try {
			finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}
	
}
