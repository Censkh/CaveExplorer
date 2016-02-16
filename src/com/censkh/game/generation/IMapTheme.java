package com.censkh.game.generation;

import com.censkh.game.tile.Tile;

public interface IMapTheme {
	public Tile getMainTile();
	
	public Tile getBackgroundTile();
	
	public Tile getPillarTile();
	
	public Tile getOreTile();
	
	public boolean createOre();
}
