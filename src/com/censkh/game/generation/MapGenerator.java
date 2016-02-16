package com.censkh.game.generation;

import com.censkh.game.entity.Doorway;
import com.censkh.game.entity.Torch;
import com.censkh.game.map.Map;
import com.censkh.game.tile.Tile;

public class MapGenerator {
	
	private static MapGenerator instance;
	
	public MapGenerator() {
		instance = this;
		new LayoutFactory();
	}
	
	public byte[][][] generateTiles(Map map, MapTheme theme, MapLayout layout) {
		byte[][][] tiles = new byte[3][50][50];
		for (int cx = 0; cx < 5; cx++) {
			for (int cy = 0; cy < 5; cy++) {
				ChunkLayout chunk = layout.getChunk(cy, cx);
				byte[][][] cTiles = new byte[3][10][10];
				cTiles = chunk.generate(cx,cy,theme);
				for (int layer = 0; layer < 3; layer++)
					for (int x = 0; x < 10; x++) {
						for (int y = 0; y < 10; y++) {
							Tile tile = Tile.getById(cTiles[layer][x][y]);
							int ex = (10*cx) + x;
							int ey = (10*cy) + y;
							if (tile == Tile.torch) {
								map.createEntity((Tile.size / 2) + (Tile.size * ex), (Tile.size / 2) + (Tile.size * ey), Torch.class);
							} else if (tile == Tile.doorway) {
								Doorway doorway = (Doorway) map.createEntity((ex*Tile.size)+(Tile.size/2)-(54/2),(ey*Tile.size)-Tile.size-16,Doorway.class);
								if (cx==4&&cy==4) {
									doorway.setUseable(true);
								}
							}else {
								tiles[layer][(10 * cx) + x][(10 * cy) + y] = tile.getId();
							}
							
						}
					}
			}
		}
		return tiles;
	}
	
	public static MapGenerator getInstance() {
		return instance;
	}
	
}
