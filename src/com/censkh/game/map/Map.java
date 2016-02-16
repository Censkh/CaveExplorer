package com.censkh.game.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.censkh.game.Game;
import com.censkh.game.engine.Camera;
import com.censkh.game.engine.util.CollisionType;
import com.censkh.game.engine.util.IDraw;
import com.censkh.game.entity.Doorway;
import com.censkh.game.entity.Entity;
import com.censkh.game.entity.EntityManager;
import com.censkh.game.entity.Gold;
import com.censkh.game.entity.Player;
import com.censkh.game.entity.enemy.Bat;
import com.censkh.game.generation.MapGenerator;
import com.censkh.game.generation.MapLayout;
import com.censkh.game.generation.MapTheme;
import com.censkh.game.input.Mouse;
import com.censkh.game.render.RenderImage;
import com.censkh.game.tile.Tile;
import com.censkh.game.tile.TileConstructure;
import com.censkh.game.tile.TileRenderType;

public class Map extends EntityManager implements IDraw {
	
	private final int width = 50 * Tile.size;
	private final int height = 50 * Tile.size;
	private final byte[][][] tiles;
	private final TileRenderType[][][] tileRenderMap = new TileRenderType[3][getWidth() / Tile.size][getHeight() / Tile.size];
	public final static Color TINT_BACKGROUND = new Color(0, 0, 0, 120);
	public final static Color TINT_MAIN = new Color(0, 0, 0, 30);
	private final BufferedImage mapImage;
	private final List<Entity> lights = new ArrayList<Entity>();
	private final Camera camera;
	private BufferedImage lightMap = null;
	private final HashMap<Integer, List<Entity>> entityRenderLayers = new HashMap<Integer, List<Entity>>();
	private final String name;
	private Player player;
	
	public Map(String name) {
		super();
		this.name = name;
		for (int i = 0; i < 3; i++) {
			entityRenderLayers.put(i, new ArrayList<Entity>());
		}
		camera = new Camera(this, 0, 0);
		tiles = MapGenerator.getInstance().generateTiles(this, MapTheme.grassland, MapLayout.random());
		generateTileRenderMap();
		mapImage = new BufferedImage((getWidth() / Tile.size) * 2, (getHeight() / Tile.size) * 2, BufferedImage.TYPE_INT_ARGB);
		generateMiniMap();
		createEntity(100, 100, Bat.class);
	}
	
	public void setEntityRenderLayer(Entity entity, int layer) {
		for (List<Entity> entityList : getEntityRenderLayers().values()) {
			if (entityList.contains(entity)) {
				entityList.remove(entity);
			}
		}
		getEntityRenderLayers().get(layer).add(entity);
	}
	
	private void generateMiniMap() {
		for (int layer = 0; layer < 3; layer++) {
			for (int x = 0; x < getWidth() / Tile.size; x++) {
				for (int y = 0; y < getHeight() / Tile.size; y++) {
					updateMiniMapTile(layer, x, y);
				}
			}
		}
	}
	
	private void updateMiniMapTile(int layer, int x, int y) {
		Tile tile = getTile(layer, x, y);
		int tint = 0;
		if (layer == Tile.LAYER_BACKGROUND) {
			tint = 40;
		}
		int color = -1;
		if (tile.isOpaque()) {
			int r = tile.getTileColor().getRed() - tint;
			int g = tile.getTileColor().getGreen() - tint;
			int b = tile.getTileColor().getBlue() - tint;
			if (r < 0)
				r = 0;
			if (g < 0)
				g = 0;
			if (b < 0)
				b = 0;
			color = getColor(r, g, b, 128);
		} else {
			if (layer == Tile.LAYER_MAIN) {
				updateMiniMapTile(Tile.LAYER_BACKGROUND, x, y);
			}
		}
		if (color != -1) {
			mapImage.setRGB(x * 2, y * 2, color);
			mapImage.setRGB((x * 2) + 1, (y * 2) + 1, color);
			mapImage.setRGB((x * 2), (y * 2) + 1, color);
			mapImage.setRGB((x * 2) + 1, y * 2, color);
		}
		
	}
	
	private int getColor(int r, int g, int b, int a) {
		int argb = a;
		argb = (argb << 8) + r;
		argb = (argb << 8) + g;
		argb = (argb << 8) + b;
		return argb;
	}
	
	public void load() {
		FileInputStream fis;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream("sav/test.map");
			ois = new ObjectInputStream(fis);
			String type = null;
			TileConstructure tc = TileConstructure.getCurrent();
			while (true) {
				Object obj = ois.readObject();
				if (type == null) {
					type = (String) obj;
				} else if (type.equals(type)) {
					int n = tc.completed();
					int value = (Integer) obj;
					if (n == 0) {
						tc.setX(value);
					} else if (n == 1) {
						tc.setY(value);
					} else if (n == 2) {
						tc.setLayer(value);
					} else if (n == 3) {
						tc.setTile(value);
					}
					if (tc.finished()) {
						int[] ti = tc.bake();
						tiles[ti[2]][ti[0]][ti[1]] = (byte) ti[3];
						type = null;
					}
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			ois.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		generateTileRenderMap();
	}
	
	public void save() {
		try {
			FileOutputStream fos = new FileOutputStream("sav/test.map");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			for (int layer = 0; layer < 3; layer++) {
				for (int x = 0; x < getWidth() / Tile.size; x++) {
					for (int y = 0; y < getHeight() / Tile.size; y++) {
						oos.writeObject("tile");
						oos.writeObject(x);
						oos.writeObject(y);
						oos.writeObject(layer);
						oos.writeObject(getTile(layer, x, y).getId());
					}
				}
			}
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void generateTileRenderMap() {
		for (int layer = 0; layer < 3; layer++) {
			for (int x = 0; x < getWidth() / Tile.size; x++) {
				for (int y = 0; y < getHeight() / Tile.size; y++) {
					updateTileRenderType(layer, x, y);
				}
			}
		}
	}
	
	public TileRenderType getTileRenderType(int layer, int x, int y) {
		if (x < 0 || y < 0 || x >= tiles[layer].length || y >= tiles[layer][x].length)
			return TileRenderType.AIR_ALL;
		return tileRenderMap[layer][x][y];
	}
	
	private void updateTileRenderType(int layer, int x, int y) {
		if (x < 0 || y < 0 || x >= tiles[layer].length || y >= tiles[layer][x].length)
			return;
		TileRenderType trt = TileRenderType.AIR_NONE;
		Tile tile = getTile(layer, x, y);
		boolean up = false, down = false, left = false, right = false, tright = false, tleft = false, bright = false, bleft = false;
		
		if (getTile(layer, x, y - 1) != tile && getTile(layer, x, y - 1).getGroup() != tile.getGroup())
			up = true;
		if (getTile(layer, x, y + 1) != tile && getTile(layer, x, y + 1).getGroup() != tile.getGroup())
			down = true;
		if (getTile(layer, x - 1, y) != tile && getTile(layer, x - 1, y).getGroup() != tile.getGroup())
			left = true;
		if (getTile(layer, x + 1, y) != tile && getTile(layer, x + 1, y).getGroup() != tile.getGroup())
			right = true;
		
		if (getTile(layer, x - 1, y - 1) != tile && getTile(layer, x - 1, y - 1).getGroup() != tile.getGroup())
			tleft = true;
		if (getTile(layer, x + 1, y - 1) != tile && getTile(layer, x + 1, y - 1).getGroup() != tile.getGroup())
			tright = true;
		if (getTile(layer, x - 1, y + 1) != tile && getTile(layer, x - 1, y + 1).getGroup() != tile.getGroup())
			bleft = true;
		if (getTile(layer, x + 1, y + 1) != tile && getTile(layer, x + 1, y + 1).getGroup() != tile.getGroup())
			bright = true;
		
		if (up == false && down == false && left == false && right == false && tright == true && tleft == true && bright == true && bleft == true)
			trt = TileRenderType.AIR_CORNERS_ALL;
		else if (up == false && down == false && left == false && right == false && bleft == true && bright == true && tleft == true)
			trt = TileRenderType.AIR_NONE_CORNER_TLEFT_BRIGHT_BLEFT;
		else if (up == false && down == false && left == false && right == false && tright == true && bleft == true && tleft == true)
			trt = TileRenderType.AIR_NONE_CORNER_TLEFT_BLEFT_TRIGHT;
		else if (up == false && down == false && left == false && right == false && tright == true && bright == true && tleft == true)
			trt = TileRenderType.AIR_NONE_CORNER_TLEFT_BRIGHT_TRIGHT;
		else if (up == false && down == false && left == false && right == false && tright == true && bright == true && bleft == true)
			trt = TileRenderType.AIR_NONE_CORNER_BRIGHT_TRIGHT_BLEFT;
		else if (up == false && down == false && left == false && right == false && tright == true && bleft == true)
			trt = TileRenderType.AIR_NONE_CORNER_TRIGHT_BLEFT;
		else if (up == false && down == false && left == false && right == false && tleft == true && bright == true)
			trt = TileRenderType.AIR_NONE_CORNER_TLEFT_BRIGHT;
		else if (up == false && down == false && left == false && right == false && tleft == true && tright == true)
			trt = TileRenderType.AIR_NONE_CORNER_TLEFT_TRIGHT;
		else if (up == false && down == false && left == false && right == false && bleft == true && bright == true)
			trt = TileRenderType.AIR_NONE_CORNER_BLEFT_BRIGHT;
		else if (up == false && down == false && left == false && right == false && bright == true && tright == true)
			trt = TileRenderType.AIR_NONE_CORNER_TRIGHT_BRIGHT;
		else if (up == false && down == false && left == false && right == false && tleft == true && bleft == true)
			trt = TileRenderType.AIR_NONE_CORNER_BLEFT_TLEFT;
		else if (up == false && down == false && left == true && right == false && tright == true && bright == true)
			trt = TileRenderType.AIR_LEFT_CORNER_TRIGHT_BRIGHT;
		else if (up == false && down == false && left == false && right == true && tleft == true && bleft == true)
			trt = TileRenderType.AIR_RIGHT_CORNER_TLEFT_BLEFT;
		else if (up == true && down == false && left == false && right == false && bright == true && bleft == true)
			trt = TileRenderType.AIR_UP_CORNER_BRIGHT_BLEFT;
		else if (up == false && down == true && left == false && right == false && tright == true && tleft == true)
			trt = TileRenderType.AIR_DOWN_CORNER_TLEFT_TRIGHT;
		else if (up == false && down == true && left == false && right == true && tleft == true)
			trt = TileRenderType.AIR_DOWN_RIGHT_CORNER_TLEFT;
		else if (up == true && down == false && left == false && right == true && bleft == true)
			trt = TileRenderType.AIR_UP_RIGHT_CORNER_BLEFT;
		else if (up == true && down == false && left == true && right == false && bright == true)
			trt = TileRenderType.AIR_LEFT_UP_CORNER_BRIGHT;
		else if (up == false && down == true && left == true && right == false && tright == true)
			trt = TileRenderType.AIR_DOWN_LEFT_CORNER_TRIGHT;
		else if (up == false && down == true && left == false && right == false && tleft == true)
			trt = TileRenderType.AIR_DOWN_CORNER_TLEFT;
		else if (up == false && down == true && left == false && right == false && tright == true)
			trt = TileRenderType.AIR_DOWN_CORNER_TRIGHT;
		else if (up == true && down == false && left == false && right == false && bleft == true)
			trt = TileRenderType.AIR_UP_CORNER_BLEFT;
		else if (up == true && down == false && left == false && right == false && bright == true)
			trt = TileRenderType.AIR_UP_CORNER_BRIGHT;
		else if (up == false && down == false && left == true && right == false && bright == true)
			trt = TileRenderType.AIR_LEFT_CORNER_BRIGHT;
		else if (up == false && down == false && left == true && right == false && tright == true)
			trt = TileRenderType.AIR_LEFT_CORNER_TRIGHT;
		else if (up == false && down == false && left == false && right == true && tleft == true)
			trt = TileRenderType.AIR_RIGHT_CORNER_TLEFT;
		else if (up == false && down == false && left == false && right == true && bleft == true)
			trt = TileRenderType.AIR_RIGHT_CORNER_BLEFT;
		else if (up == false && down == false && left == false && right == false && bleft == true)
			trt = TileRenderType.AIR_NONE_CORNER_BLEFT;
		else if (up == false && down == false && left == false && right == false && bright == true)
			trt = TileRenderType.AIR_NONE_CORNER_BRIGHT;
		else if (up == false && down == false && left == false && right == false && tleft == true)
			trt = TileRenderType.AIR_NONE_CORNER_TLEFT;
		else if (up == false && down == false && left == false && right == false && tright == true)
			trt = TileRenderType.AIR_NONE_CORNER_TRIGHT;
		else if (up == true && down == true && left == true && right == true)
			trt = TileRenderType.AIR_ALL;
		else if (up == false && down == true && left == false && right == false)
			trt = TileRenderType.AIR_DOWN;
		else if (up == false && down == true && left == true && right == false)
			trt = TileRenderType.AIR_DOWN_LEFT;
		else if (up == false && down == true && left == false && right == true)
			trt = TileRenderType.AIR_DOWN_RIGHT;
		else if (up == false && down == false && left == true && right == false)
			trt = TileRenderType.AIR_LEFT;
		else if (up == false && down == false && left == true && right == true)
			trt = TileRenderType.AIR_LEFT_RIGHT;
		else if (up == false && down == false && left == false && right == false)
			trt = TileRenderType.AIR_NONE;
		else if (up == false && down == false && left == false && right == true)
			trt = TileRenderType.AIR_RIGHT;
		else if (up == true && down == false && left == false && right == false)
			trt = TileRenderType.AIR_UP;
		else if (up == true && down == true && left == false && right == false)
			trt = TileRenderType.AIR_UP_DOWN;
		else if (up == true && down == false && left == true && right == false)
			trt = TileRenderType.AIR_UP_LEFT;
		else if (up == true && down == false && left == false && right == true)
			trt = TileRenderType.AIR_UP_RIGHT;
		else if (up == false && down == false && left == false && right == false)
			trt = TileRenderType.AIR_NONE;
		else if (up == false && down == true && left == true && right == true)
			trt = TileRenderType.AIR_DOWN_LEFT_RIGHT;
		else if (up == true && down == true && left == true && right == false)
			trt = TileRenderType.AIR_UP_LEFT_DOWN;
		else if (up == true && down == false && left == true && right == true)
			trt = TileRenderType.AIR_UP_LEFT_RIGHT;
		else if (up == true && down == true && left == false && right == true)
			trt = TileRenderType.AIR_UP_RIGHT_DOWN;
		tileRenderMap[layer][x][y] = trt;
	}
	
	@Override
	public void update() {
		super.update();
		lightMap = MapManager.getInstance().getLightThread().getLightMap();
		if (Mouse.buttonPressed(MouseEvent.BUTTON1)) {
			int x = (int) ((Mouse.getMouseScaledX() + camera.getX()) / Tile.size);
			int y = (int) ((Mouse.getMouseScaledY() + camera.getY()) / Tile.size);
			setTile(Tile.getById((byte) (getTile(Tile.LAYER_MAIN, x, y).getId() + 1)), Tile.LAYER_MAIN, x, y);
		}
		camera.update();
	}
	
	public void setTile(Tile tile, int layer, int x, int y) {
		if (x < 0 || y < 0 || x >= tiles[layer].length || y >= tiles[layer][x].length)
			return;
		Tile oldTile = Tile.getById(tiles[layer][x][y]);
		tiles[layer][x][y] = tile.getId();
		if (oldTile.getValue() > 0) {
			Gold gold = (Gold) createEntity((int) x * Tile.size - 4, (int) y * Tile.size - 4, Gold.class);
			gold.setValue(Game.getRandom().nextInt(150));
		}
		if (layer == Tile.LAYER_MAIN) {
			if (!tile.isSolid() && Tile.getById(tiles[Tile.LAYER_FOREGROUND][x][y]).isSupported()) {
				setTile(Tile.air, Tile.LAYER_FOREGROUND, x, y);
			}
		}
		for (int cx = x - 1; cx < x + 2; cx++) {
			for (int cy = y - 1; cy < y + 2; cy++) {
				updateTileRenderType(layer, cx, cy);
			}
		}
		updateMiniMapTile(layer, x, y);
	}
	
	public Tile getTile(int layer, int x, int y) {
		if (x < 0 || y < 0 || x >= getWidth() / Tile.size || y >= getHeight() / Tile.size) {
			return Tile.air;
		}
		return Tile.getById(tiles[layer][x][y]);
	}
	
	@Override
	public void addEntity(Entity e) {
		super.addEntity(e);
		e.setMap(this);
		if (e.isLightSource()) {
			lights.add(e);
		}
		if (e instanceof Player) {
			setPlayer((Player) e);
			Doorway entrance = (Doorway) getEntities(Doorway.class).get(0);
			e.setX(entrance.getX());
			e.setY(entrance.getY());
		}
		setEntityRenderLayer(e, e.getRenderLayer());
	}
	
	@Override
	public void removeEntity(Entity e) {
		super.removeEntity(e);
		for (List<Entity> el : entityRenderLayers.values()) {
			if (el.contains(e))
				el.remove(e);
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		
		renderLayers(g);
		if (lightMap != null) {
			RenderImage.draw(g, lightMap, getCamera().getX(), getCamera().getY());
		}
		
	}
	
	private void renderEntityLayer(Graphics2D g, int layer) {
		for (Entity e : getEntityRenderLayers().get(Integer.valueOf(layer))) {
			if (e.getX() > getCamera().getX() - e.getWidth() && e.getY() > getCamera().getY() - e.getHeight() && e.getX() < getCamera().getX() + Game.getViewWidth() + e.getWidth()
					&& e.getY() < getCamera().getY() + Game.getViewHeight() + e.getHeight()) {
				e.draw(g);
			}
		}
	}
	
	public void renderLayers(Graphics2D g) {
		for (int layer = 0; layer < 3; layer++) {
			for (int x = (int) (camera.getX() / Tile.size) - 1; x < (int) ((camera.getX() + Game.getViewWidth()) / Tile.size) +1 ; x++) {
				for (int y = (int) (camera.getY() / Tile.size) - 1; y < (int) ((camera.getY() + Game.getViewHeight()) / Tile.size) + 1; y++) {
					Tile tile = getTile(layer, x, y);
					if (tile != Tile.air) {
						boolean d = true;
						if (layer == Tile.LAYER_BACKGROUND) {
							if (getTile(Tile.LAYER_MAIN, x, y).isOpaque()) {
								d = false;
							}
						} else if (layer == Tile.LAYER_MAIN) {
							if (getTile(Tile.LAYER_FOREGROUND, x, y).isOpaque()) {
								d = false;
							}
						}
						if (d) {
							boolean shadow = layer != Tile.LAYER_BACKGROUND && tile.isOpaque();
							if (shadow) {
								if (getTile(layer, x + 1, y).isOpaque() && getTile(layer, x, y + 1).isOpaque())
									shadow = false;
							}
							tile.draw(g, getTileRenderType(layer, x, y), shadow, x * Tile.size, y * Tile.size);
						}
						
					}
					
				}
			}
			renderEntityLayer(g, layer);
			if (layer == Tile.LAYER_BACKGROUND) {
				g.setColor(TINT_BACKGROUND);
				g.fillRect((int) getCamera().getX(), (int) getCamera().getY(), (int) Game.getViewWidth(), (int) Game.getViewHeight());
			} else if (layer == Tile.LAYER_MAIN) {
				g.setColor(TINT_MAIN);
				g.fillRect((int) getCamera().getX(), (int) getCamera().getY(), (int) Game.getViewWidth(), (int) Game.getViewHeight());
			}
		}
	}
	
	public boolean isCollision(float x, float y, CollisionType type) {
		boolean c = false;
		if (x >= 0 && y >= 0 && x < getWidth() && y < getHeight()) {
			if (type == CollisionType.TILE_SOLID) {
				c = getTile(Tile.LAYER_MAIN, (int) Math.ceil(x / Tile.size), (int) Math.ceil(y / Tile.size)).isSolid();
			}
		}
		return c;
	}
	
	public TileRenderType[][][] getTileRenderMap() {
		return tileRenderMap;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public List<Entity> getLights() {
		return lights;
	}
	
	public boolean isCollision(Entity entity, CollisionType type) {
		return isCollision(entity, entity.getX(), entity.getY(), type);
	}
	
	public List<Entity> getEntities(Class<? extends Entity> clazz) {
		List<Entity> el = new ArrayList<Entity>();
		for (Entity e : getEntityList()) {
			if (clazz.isInstance(e)) {
				el.add(e);
			}
		}
		return el;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public boolean isCollision(Entity entity, float ex, float ey, CollisionType type) {
		boolean collision = false;
		if (type == CollisionType.TILE_SOLID) {
			for (int x = (int) Math.floor((float) (ex + entity.getHitbox().getX()) / Tile.size); x < (int) Math.ceil((float) (ex + entity.getHitbox().getWidth()) / Tile.size); x++) {
				for (int y = (int) Math.floor((float) (ey + entity.getHitbox().getY()) / Tile.size); y < (int) Math.ceil((float) (ey + entity.getHitbox().getHeight()) / Tile.size); y++) {
					if (getTile(Tile.LAYER_MAIN, x, y).isSolid()) {
						collision = true;
						break;
					}
				}
			}
		}
		return collision;
	}
	
	public BufferedImage getMapImage() {
		return mapImage;
	}
	
	public HashMap<Integer, List<Entity>> getEntityRenderLayers() {
		return entityRenderLayers;
	}
	
	public List<Entity> getCollisions(Entity entity, Class<? extends Entity> clazz) {
		return getCollisions(entity, entity.getX(), entity.getY(), clazz);
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void destroy() {
		int n = getEntityList().size();
		for (int i = 0; i < n; i++) {
			getEntityList().get(0).remove();
		}
		try {
			finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public String getName() {
		return name;
	}
	
	public List<Entity> getCollisions(Entity entity, float x, float y, Class<? extends Entity> clazz) {
		List<Entity> list = new ArrayList<Entity>();
		for (Entity e : getEntityList()) {
			if (clazz.isInstance(e)) {
				if (isCollision(entity, x, y, e)) {
					list.add(e);
				}
			}
		}
		return list;
	}
	
	public boolean isCollision(Entity entity, float x, float y, Entity e) {
		return new Rectangle((int) (e.getX() + e.getHitbox().getX()), (int) (e.getY() + e.getHitbox().getY()), (int) e.getHitbox().getWidth(), (int) e.getHitbox().getHeight()).intersects(x
				+ entity.getHitbox().getX(), y + entity.getHitbox().getY(), entity.getHitbox().getWidth(), entity.getHitbox().getHeight());
	}
	
	public boolean isCollision(Entity entity, Entity e) {
		return isCollision(entity, entity.getX(), entity.getY(), e);
	}
	
	@Override
	public boolean doSetMapToNull() {
		return true;
	}
	
}
