package com.censkh.game.generation;

import java.util.ArrayList;
import java.util.List;

import com.censkh.game.Game;

public class MapLayout {
	
	private static final List<MapLayout> layouts = new ArrayList<MapLayout>();
	private final ChunkType[][] chunks;
	
	public MapLayout(ChunkType[][] chunks) {
		this.chunks = chunks;
		layouts.add(this);
	}
	
	public static MapLayout random() {
		return layouts.get(Game.getRandom().nextInt(layouts.size()));
	}
	
	public ChunkLayout getChunk(int x, int y) {
		return ChunkLayout.random(chunks[x][y]);
	}
	
	public ChunkType[][] getChunks() {
		return chunks;
	}
	
}
