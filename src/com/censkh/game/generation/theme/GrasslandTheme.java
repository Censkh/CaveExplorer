package com.censkh.game.generation.theme;

import com.censkh.game.Game;
import com.censkh.game.generation.MapTheme;
import com.censkh.game.tile.Tile;

public class GrasslandTheme extends MapTheme {
	
	@Override
	public Tile getMainTile() {
		return Game.getRandom().nextInt(3) == 0 ? Tile.dirt : Game.getRandom().nextInt(5) == 0 ? Tile.gravel : Tile.grass;
	}
	
	@Override
	public Tile getBackgroundTile() {
		return Game.getRandom().nextInt(8) == 0 ? Tile.gravel : Tile.dirt;
	}
	
	@Override
	public Tile getPillarTile() {
		return Tile.woodpillar;
	}
	
	@Override
	public Tile getOreTile() {
		return Game.getRandom().nextBoolean() ? Tile.diamondore : Tile.goldore;
	}
	
	@Override
	public boolean createOre() {
		return Game.getRandom().nextInt(25) == 0;
	}
	
}
