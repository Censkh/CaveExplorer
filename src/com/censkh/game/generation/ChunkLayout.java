package com.censkh.game.generation;

import java.util.ArrayList;
import java.util.List;

import com.censkh.game.Game;
import com.censkh.game.tile.Tile;
import com.censkh.game.tile.TileGroup;

public class ChunkLayout {
	
	private static final List<ChunkLayout> layouts = new ArrayList<ChunkLayout>();
	private final String[][] tiles;
	private final ChunkType type;
	
	public ChunkLayout(ChunkType type, String[][] tiles) {
		this.tiles = tiles;
		this.type = type;
		layouts.add(this);
	}
	
	public static ChunkLayout random(ChunkType type) {
		List<ChunkLayout> chunks = new ArrayList<ChunkLayout>();
		for (ChunkLayout layout : getLayouts()) {
			if (layout.getType() == type) {
				chunks.add(layout);
			}
		}
		return chunks.get(Game.getRandom().nextInt(chunks.size()));
	}
	
	public byte[][][] generate(int cx, int cy,MapTheme theme) {
		byte[][][] rTiles = new byte[3][10][10];
		for (int layer = 0; layer < 3; layer++) {
			for (int x = 0; x < 10; x++) {
				for (int y = 0; y < 10; y++) {
					byte id = getTile(theme, cx, cy, tiles[layer][y].charAt(x));
					boolean set = true;
					if (layer == Tile.LAYER_FOREGROUND && Tile.getById(rTiles[layer][x][y]).getGroup() == TileGroup.ORE && id == Tile.air.getId()) {
						set = false;
					}
					if (set)
						rTiles[layer][x][y] = id;
					if (id != Tile.air.getId() && layer == Tile.LAYER_MAIN && Tile.getById(id).isSolid()) {
						if (theme.createOre())
							rTiles[Tile.LAYER_FOREGROUND][x][y] = theme.getOreTile().getId();
					}
				}
			}
		}
		return rTiles;
	}
	
	private byte getTile(MapTheme theme, int x, int y, char c) {
		byte tile = 0;
		if (c == 'b') {
			tile = theme.getBackgroundTile().getId();
		} else if (c == '0') {
			tile = Tile.air.getId();
		} else if (c == '1') {
			tile = theme.getMainTile().getId();
		} else if (c == '2') {
			tile = Game.getRandom().nextBoolean() ? Tile.air.getId() : theme.getMainTile().getId();
		} else if (c == 'T') {
			tile = Tile.torch.getId();
		} else if (c == 't') {
			tile = Game.getRandom().nextBoolean() ? Tile.air.getId() : Tile.torch.getId();
		} else if (c == 'p') {
			tile = theme.getPillarTile().getId();
		} else if (c == '*') {
			if (x == 0 && y == 0)
				tile = Tile.doorway.getId();
			else if (x == 4 && y == 4)
				tile = Tile.doorway.getId();
			else
				tile = Tile.air.getId();
		}
		return tile;
	}
	
	public static List<ChunkLayout> getLayouts() {
		return layouts;
	}
	
	public ChunkType getType() {
		return type;
	}
	
	public String[][] getTiles() {
		return tiles;
	}
	
}
