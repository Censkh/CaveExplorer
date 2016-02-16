package com.censkh.game.map;

import java.util.ArrayList;
import java.util.List;

import com.censkh.game.Game;
import com.censkh.game.engine.thread.LightThread;
import com.censkh.game.entity.Player;

public class MapManager {
	
	private static MapManager instance;
	private final List<Map> maps = new ArrayList<Map>();
	private final LightThread lightThread;
	private Map current;
	private GameRun run;
	
	public MapManager() {
		instance = this;
		lightThread = new LightThread();
		lightThread.start();
		run = new GameRun();
		Game.getGame().setPlayer(new Player(100, 100));
		current = getRun().getFirstMap();
		current.addEntity(Game.getGame().getPlayer());
	}
	
	public static MapManager getInstance() {
		return instance;
	}
	
	public List<Map> getMaps() {
		return maps;
	}
	
	public static Map getCurrentMap() {
		return getInstance().current;
	}
	
	public Map getCurrent() {
		return current;
	}
	
	public void setCurrent(Map map) {
		current = map;
	}
	
	public LightThread getLightThread() {
		return lightThread;
	}
	
	public Map createMap(String name) {
		Map map = new Map(name);
		maps.add(map);
		return map;
	}

	public void removeMap(Map m) {
		maps.remove(m);
	}

	public void destroyMap(Map m) {
		removeMap(m);
		m.destroy();
	}

	public void switchMap(Map map) {
		Player player = getCurrent().getPlayer();
		getCurrent().removeEntity(player);
		map.addEntity(player);
		setCurrent(map);
	}

	public GameRun getRun() {
		return run;
	}
	
}
