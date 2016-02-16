package com.censkh.game;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import com.censkh.game.engine.GameState;
import com.censkh.game.engine.NameGenerator;
import com.censkh.game.engine.Settings;
import com.censkh.game.engine.Time;
import com.censkh.game.entity.Player;
import com.censkh.game.generation.MapGenerator;
import com.censkh.game.gui.GuiManager;
import com.censkh.game.gui.GuiMenu;
import com.censkh.game.gui.menu.GuiIngameMenu;
import com.censkh.game.input.Keyboard;
import com.censkh.game.input.Mouse;
import com.censkh.game.map.Map;
import com.censkh.game.map.MapManager;
import com.censkh.game.render.RenderImage;
import com.censkh.game.render.SpriteManager;
import com.censkh.game.tile.TileConstructure;

public class Game {
	
	public static void main(String[] args) {
		Thread.currentThread().setName("Main");
		game = new Game();
		game.init();
		game.gameLoop();
	}
	
	private static Game game;
	private static final int frameWidth = 640;
	private static final int frameHeight = 640;
	private static final int viewWidth = 480;
	private static final int viewHeight = 480;
	private static final int targetFps = 120;
	private static final int runSpeed = 60;
	private static final String title = "FYW: " + NameGenerator.getTile();
	private static final SecureRandom random = new SecureRandom();
	public static boolean initialized = false;
	
	private static GameFrame frame;
	private static Time time;
	
	private final Settings settings = new Settings();
	private boolean close = false;
	private Robot robot;
	private GuiManager guiManager;
	private double scale;
	private BufferedImage noiseMap;
	private final List<BufferedImage> noiseMaps = new ArrayList<BufferedImage>();
	private Player player;
	private boolean bindMouse = false;
	private GameState gameState = GameState.MAIN_MENU;
	private GameState newGameState = null;
	private boolean guiToggle = true;
	
	public Game() {
		frame = new GameFrame(frameWidth, frameHeight);
		frame.addKeyListener(new Keyboard());
		new Mouse(frame);
	}
	
	public void init() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		new SpriteManager();
		new TileConstructure();
		noiseMaps.addAll(createNoiseMaps());
		new MapGenerator();
		new MapManager();
		
		guiManager = new GuiManager();
		initialized = true;
		time = new Time();
	}
	
	public BufferedImage generateVignette() {
		float thickness = 64;
		BufferedImage vignette = new BufferedImage(getFrameWidth(), getFrameHeight(), BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < getFrameWidth(); x++) {
			for (int y = 0; y < getFrameHeight(); y++) {
				float alpha = 0f;
				if (x < thickness + 1) {
					alpha = 1f - (x / thickness);
				} else if (x + 1 > getFrameWidth() - thickness) {
					alpha = 1f - ((getFrameWidth() - x) / thickness);
				} else {
					if (y < thickness + 1) {
						alpha = 1f - (y / thickness);
					} else if (y + 1 > getFrameHeight() - thickness) {
						alpha = 1f - ((getFrameHeight() - y) / thickness);
					}
				}
				vignette.setRGB(x, y, getColor(0, 0, 0, (int) (255 * alpha)));
			}
		}
		return vignette;
	}
	
	public void gameLoop() {
		System.out.println("Game loop starting.");
		while (!close) {
			time.update();
		}
		System.out.println("Closing, should exit instantly.");
		System.exit(0);
	}
	
	public synchronized void update() {
		mouseBind();
		SpriteManager.getInstance().update();
		Keyboard.getInstance().update();
		Mouse.getInstance().update();
		if (Keyboard.isKeyPressed(KeyEvent.VK_F1)) {
			setGuiToggle(!isGuiToggle());
		}
		if (newGameState != null) {
			gameState = newGameState;
			newGameState = null;
		}
		if (gameState == GameState.GAME) {
			if (Keyboard.isKeyPressed(KeyEvent.VK_ESCAPE)) {
				GuiMenu options = guiManager.getMenu(GuiIngameMenu.class);
				options.setVisible(!options.isVisible());
			}
			if (MapManager.getCurrentMap() != null)
				MapManager.getInstance().getCurrent().update();
		}
		if (time.getFramesThisSecond() % 5 == 0)
			noiseMap = noiseMaps.get(Game.getRandom().nextInt(noiseMaps.size()));
		guiManager.update();
		
		
	}
	
	private void mouseBind() {
		if (Keyboard.isKeyPressed(KeyEvent.VK_ALT)) {
			bindMouse = !bindMouse;
		}
		if (bindMouse) {
			robot.mouseMove(GameFrame.getInstance().getLocation().x + 32, GameFrame.getInstance().getLocation().y + 32);
		}
	}
	
	public synchronized void draw(Graphics g2) {
		Map map = MapManager.getCurrentMap();
		Graphics2D g = (Graphics2D) g2;
		scale = (double) getFrameHeight() / (double)getViewHeight();
		//g.translate((getFrameWidth()/2)-(getViewWidth()/2), 0);
		g.clearRect(0, 0, getFrameWidth(), getFrameHeight());
		g.setColor(Color.black);
		g.fillRect(0, 0, getFrameWidth(), getFrameHeight());
		setupDefaultRendering(g);
		if (gameState == GameState.GAME) {
			if (map != null) {
				
				double xOff = map.getCamera().getX();
				double yOff = map.getCamera().getY();
				g.scale(scale,scale);
				g.translate(-xOff, -yOff);
				g.setClip((int) map.getCamera().getX(), (int) map.getCamera().getY(), (int) getViewWidth(), (int) getViewHeight());
				map.draw(g);
				RenderImage.draw(g, noiseMap, map.getCamera().getX(), map.getCamera().getY());
				g.translate(xOff, yOff);
				g.scale(1 / scale, 1 / scale);
			}
		}
		g.setClip(0, 0, (int) getFrameWidth(), (int) getFrameHeight());
		if (isGuiToggle())
			getGuiManager().render(g);
	}
	
	public void setupDefaultRendering(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}
	
	public List<BufferedImage> createNoiseMaps() {
		List<BufferedImage> noiseMaps = new ArrayList<BufferedImage>();
		int width = (int) getViewWidth();
		int height = (int) getViewHeight();
		int length = width * height;
		for (int i = 0; i < 10; i++) {
			int[] pixels = new int[length];
			
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					pixels[(y * width) + x] = getColor(0, 0, 0, (int) (255 * Game.getRandom().nextFloat() * 0.1f));
				}
			}
			BufferedImage noiseMap = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			noiseMap.setRGB(0, 0, width, height, pixels, 0, width);
			noiseMaps.add(noiseMap);
		}
		return noiseMaps;
	}
	
	private int getColor(int r, int g, int b, int a) {
		int argb = a;
		argb = (argb << 8) + r;
		argb = (argb << 8) + g;
		argb = (argb << 8) + b;
		return argb;
	}
	
	public static String getTitle() {
		return title;
	}
	
	public static int getFrameHeight() {
		return frame.getHeight();
	}
	
	public static int getFrameWidth() {
		return frame.getWidth();
	}
	
	public static Game getGame() {
		return game;
	}
	
	public Time getTime() {
		return time;
	}
	
	public static int getTargetFps() {
		return targetFps;
	}
	
	public static int getRunSpeed() {
		return runSpeed;
	}
	
	public static float getViewHeight() {
		return viewHeight;
	}
	
	public static float getViewWidth() {
		return viewWidth;
	}
	
	public double getScale() {
		return scale;
	}
	
	public GuiManager getGuiManager() {
		return guiManager;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public static void exit() {
		getGame().close = true;
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	
	public void switchGameState(GameState state) {
		newGameState = state;
	}
	
	public boolean isGuiToggle() {
		return guiToggle;
	}
	
	public void setGuiToggle(boolean guiToggle) {
		this.guiToggle = guiToggle;
	}

	public static SecureRandom getRandom() {
		return random;
	}

	public Settings getSettings() {
		return settings;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
}
