package com.censkh.game.tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.censkh.game.render.RenderImage;

public class Tile {
	
	private static Image shadow;
	public final static int size = 32;
	public final static int LAYER_BACKGROUND = 0;
	public final static int LAYER_FOREGROUND = 2;
	public final static int LAYER_MAIN = 1;
	private static byte iid = 0;
	private final static List<Tile> tiles = new ArrayList<Tile>();
	
	public final static Tile air = new Tile("Air") {
		{
			setOpaque(false);
			setSolid(false);
		}
	};
	public final static Tile dirt = new Tile("Dirt") {
		{
			setTileColor(new Color(121, 85, 53));
			setGroup(TileGroup.SOIL);
		}
	};
	public final static Tile torch = new Tile("Torch") {
		{
			setOpaque(false);
			setSolid(false);
			setGroup(TileGroup.PROP);
		}
	};
	public final static Tile doorway = new Tile("Doorway") {
		{
			setOpaque(false);
			setSolid(false);
			setGroup(TileGroup.PROP);
		}
	};
	public final static Tile grass = new Tile("Grass") {
		{
			setTileColor(new Color(121, 85, 53));
			setGroup(TileGroup.SOIL);
		}
	};
	public final static Tile woodpillar = new Tile("Wood Pillar") {
		{
			setOpaque(false);
			setSolid(false);
			setGroup(TileGroup.PILLAR);
			setSupported(true);
		}
	};
	public final static Tile goldore = new Tile("Gold Ore") {
		{
			setOpaque(false);
			setSolid(false);
			setGroup(TileGroup.ORE);
			setSupported(true);
			setValue(100);
		}
	};
	public final static Tile diamondore = new Tile("Diamond Ore") {
		{
			setOpaque(false);
			setSolid(false);
			setGroup(TileGroup.ORE);
			setSupported(true);
			setValue(100);
		}
	};
	public final static Tile gravel = new Tile("Gravel") {
		{
			setTileColor(new Color(110, 110, 110));
			setGroup(TileGroup.SOIL);
		}
	};
	
	private final String name;
	private final byte id;
	private int value = 0;
	private Color tileColor = Color.white;
	private boolean solid = true;
	private boolean opaque = true;
	private boolean supported = false;
	private TileGroup group = TileGroup.AIR;
	private BufferedImage sheetImage;
	private final Image[] images;
	
	public Tile(String name) {
		this.name = name;
		this.id = iid;
		try {
			this.sheetImage = ImageIO.read(new File("res/tiles/" + getName().replaceAll(" ", "").toLowerCase() + ".png"));
		} catch (IOException e) {
			try {
				this.sheetImage = ImageIO.read(new File("res/tiles/template.png"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		iid++;
		tiles.add(this);
		images = generateImages();
	}
	
	public void draw(Graphics2D g, TileRenderType trt, boolean shadow, float x, float y) {
		if (shadow) {
			RenderImage.draw(g, getShadow(), x, y);
		}
		RenderImage.draw(g, getImage(trt), x, y);
	}
	
	private Image[] generateImages() {
		int col = 5;
		int row = 10;
		Image[] i = new Image[col * row];
		for (int y = 0; y < row; y++) {
			for (int x = 0; x < col; x++) {
				i[(y * col) + x] = sheetImage.getSubimage(x * 32, y * 32, 32, 32).getScaledInstance(Tile.size, Tile.size, Image.SCALE_DEFAULT);
			}
		}
		return i;
	}
	
	public String getName() {
		return name;
	}
	
	public byte getId() {
		return id;
	}
	
	public static Tile getById(byte id) {
		if (id >= getTiles().size())
			return Tile.air;
		return getTiles().get(id);
	}
	
	public static List<Tile> getTiles() {
		return tiles;
	}
	
	public Image getImage(TileRenderType trt) {
		return images[trt.getId()];
	}
	
	public Image getSheetImage() {
		return sheetImage;
	}
	
	public static Image getShadow() {
		if (shadow == null) {
			try {
				shadow = ImageIO.read(new File("res/shadow.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return shadow;
	}
	
	public Color getTileColor() {
		return tileColor;
	}
	
	public void setTileColor(Color tileColor) {
		this.tileColor = tileColor;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public void setSolid(boolean solid) {
		this.solid = solid;
	}
	
	public boolean isOpaque() {
		return opaque;
	}
	
	public void setOpaque(boolean opaque) {
		this.opaque = opaque;
	}
	
	public TileGroup getGroup() {
		return group;
	}
	
	public void setGroup(TileGroup group) {
		this.group = group;
	}
	
	public boolean isSupported() {
		return supported;
	}
	
	public void setSupported(boolean supported) {
		this.supported = supported;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
