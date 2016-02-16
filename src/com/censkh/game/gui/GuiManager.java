package com.censkh.game.gui;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.censkh.game.Game;
import com.censkh.game.engine.GameState;
import com.censkh.game.gui.menu.GuiHud;
import com.censkh.game.gui.menu.GuiIngameMenu;
import com.censkh.game.gui.menu.GuiMainMenu;
import com.censkh.game.gui.menu.GuiOptions;

public class GuiManager {
	
	private final HashMap<GameState, List<GuiMenu>> menus = new HashMap<GameState, List<GuiMenu>>();
	private GameState cacheState;
	
	public GuiManager() {
		cacheState = Game.getGame().getGameState();
		for (GameState s : GameState.values()) {
			menus.put(s, new ArrayList<GuiMenu>());
		}
		
		initMenu(GameState.MAIN_MENU, GuiMainMenu.class);
		initMenu(GameState.OPTIONS, GuiOptions.class);
		initMenu(GameState.GAME, GuiIngameMenu.class);
		initMenu(GameState.GAME, GuiHud.class);
	}
	
	private void initMenu(GameState state, Class<? extends GuiMenu> clazz) {
		try {
			GuiMenu menu = clazz.newInstance();
			menus.get(state).add(menu);
			if (state != Game.getGame().getGameState()) {
				menu.setVisible(false);
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public void render(Graphics2D g) {
		for (List<GuiMenu> m : menus.values()) {
			for (GuiMenu menu : m) {
				menu.render(g);
			}
		}
	}
	
	public void update() {
		int i = 0;
		for (List<GuiMenu> m : menus.values()) {
			GameState state = (GameState) menus.keySet().toArray()[i];
			i++;
			for (GuiMenu menu : m) {
				if (state != Game.getGame().getGameState()) {
					menu.setVisible(false);
				} else {
					if (cacheState != Game.getGame().getGameState()) {
						menu.setVisible(menu.isDefaultVisible());
					}
				}
				menu.update();
			}
		}
		cacheState = Game.getGame().getGameState();
	}
	
	public GuiMenu getMenu(Class<? extends GuiMenu> clazz) {
		for (List<GuiMenu> m : menus.values()) {
			for (GuiMenu menu : m) {
				if (clazz.isInstance(menu)) {
					return menu;
				}
			}
		}
		return null;
	}
}
