package com.censkh.game.generation;

public class LayoutFactory {
	
	public LayoutFactory() {
		
		new MapLayout(new ChunkType[][] {
				{
						ChunkType.ENTRANCE_RIGHT, ChunkType.LEFT_TO_RIGHT, ChunkType.WALL_TOP, ChunkType.LEFT_TO_RIGHT, ChunkType.ENTRANCE_LEFT
				}, {
						ChunkType.ENTRANCE_RIGHT_DOWN, ChunkType.LEFT_TO_RIGHT, ChunkType.WALL_DOWN, ChunkType.WALL_TOP, ChunkType.OBSTACLE
				}, {
						ChunkType.ENTRANCE_UP, ChunkType.OBSTACLE, ChunkType.OBSTACLE, ChunkType.TOP_TO_BOTTOM, ChunkType.OBSTACLE
				}, {
						ChunkType.OBSTACLE, ChunkType.ENTRANCE_RIGHT, ChunkType.WALL_TOP, ChunkType.ENTRANCE_UP_LEFT, ChunkType.ENTRANCE_DOWN
				}, {
						ChunkType.OBSTACLE, ChunkType.ENTRANCE_RIGHT, ChunkType.WALL_DOWN, ChunkType.LEFT_TO_RIGHT, ChunkType.ENTRANCE_UP_LEFT
				},
		});
		new ChunkLayout(ChunkType.ENTRANCE_UP, new String[][] {
				{
						"bbbbbbbbbb",
						"bbbbbbbbbb",
						"bbbbbbbbbb",
						"bbbbbbbbbb",
						"bbbbbbbbbb",
						"bbbbbbbbbb",
						"bbbbbbbbbb",
						"bbbbbbbbbb",
						"bbbbbbbbbb",
						"bbbbbbbbbb",
				}, {
						"1100000111",
						"1111000111",
						"11100t0111",
						"1100000021",
						"1000000001",
						"1000000001",
						"1000000021",
						"10*0000101",
						"1111002111",
						"1111111111"
				}, {
						"0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000",
				},
		});
		new ChunkLayout(ChunkType.TOP_TO_BOTTOM, new String[][] {
				{
						"bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb",
				}, {
						"1100002111",
						"1110000111",
						"1112000211",
						"1100000021",
						"1000000001",
						"10t0000001",
						"1000000001",
						"10*0000001",
						"1112000211",
						"1100000111"
				}, {
						"0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000",
				},
		});
		new ChunkLayout(ChunkType.ENTRANCE_DOWN, new String[][] {
				{
						"bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb",
				}, {
						"1111111111",
						"1111111111",
						"1112222111",
						"1100000021",
						"100t0t0001",
						"1000*00001",
						"1001110021",
						"1000000211",
						"1110000111",
						"1100000111"
				}, {
						"0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000",
				},
		});
		new ChunkLayout(ChunkType.ENTRANCE_UP_LEFT, new String[][] {
				{
						"bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb",
				}, {
						"1100000011",
						"1110000111",
						"1120000011",
						"2100000021",
						"0000000001",
						"0000000001",
						"0000000021",
						"0000t00201",
						"01100*0111",
						"1111111111"
				}, {
						"0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000",
				},
		});
		new ChunkLayout(ChunkType.WALL_DOWN, new String[][] {
				{
						"bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb",
				}, {
						"1100000011", "1110000111", "1120000011", "2100000021", "0000000000", "0001110000", "0000000t00", "0000000000", "0112222110", "1111111111"
				}, {
						"0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000",
				},
		});
		new ChunkLayout(ChunkType.WALL_TOP, new String[][] {
				{
						"bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb",
				}, {
						"1111111111", "1110000211", "1200000021", "0000000000", "0000000000", "0t00000000", "0000000000", "0000000222", "1120002111", "1100000011"
				}, {
						"0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000",
				},
		});
		new ChunkLayout(ChunkType.ENTRANCE_RIGHT_DOWN, new String[][] {
				{
						"bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb",
				}, {
						"1111111111", "1112002111", "1120002011", "1100000011", "1100000000", "1100000000", "1120000t00", "1112000000", "1110000111", "1100000011"
				}, {
						"0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000",
				},
		});
		new ChunkLayout(ChunkType.OBSTACLE, new String[][] {
				{
						"bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb",
				}, {
						"1111111111", "1110002111", "1100002011", "1100000011", "1100000021", "1100t00021", "1120000211", "1122000211", "1112221111", "1111111111"
				}, {
						"0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000", "0000000000",
				},
		});
		new ChunkLayout(ChunkType.LEFT_TO_RIGHT, new String[][] {
				{
						"bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb",
				}, {
						"1111111111", "1101000111", "1000000021", "00000t0001", "0000000002", "0000111000", "0000000000", "2000000001", "1110000211", "1111111111"
				}, {
						"0000000000", "000p000000", "000p000000", "000p000000", "000p000000", "000p000000", "000p000000", "000p000000", "000p000000", "000p000000",
				},
		});
		new ChunkLayout(ChunkType.ENTRANCE_RIGHT, new String[][] {
				{
						"bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb",
				}, {
						"1111111111", "1121000111", "1122000021", "1100000001", "1000000000", "10000t0000",
						"1000000000",
						"1200000000",
						"11110*0211",
						"1111111111"
				}, {
						"0000000000", "000p000000", "000p000000", "000p000000", "000p000000", "000p000000", "000p000000", "000p000000", "000p000000", "0000000000",
				},
		});
		new ChunkLayout(ChunkType.ENTRANCE_LEFT, new String[][] {
				{
						"bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbb",
				}, {
						"1111111111", "1121000111", "1122000211", "1200000021", "0000000001",
						"00000t0001",
						"0000000021",
						"00*0000021",
						"1111200211",
						"1111111111"
				}, {
						"0000000000", "0000000000", "000p000000", "000p000000", "000p000000", "000p000000", "000p000000", "000p000000", "000p000000", "0000000000",
				},
		});
	}
	
}
