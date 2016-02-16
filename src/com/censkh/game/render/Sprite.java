package com.censkh.game.render;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

public class Sprite {
	
	private final BufferedImage sheet;
	private final HashMap<String, List<Frame>> frames = new HashMap<String, List<Frame>>();
	private final HashMap<String, Integer> frameNumber = new HashMap<String, Integer>();
	private final HashMap<String, Integer> frameTicks = new HashMap<String, Integer>();
	private final int tileWidth;
	private final int tileHeight;
	private final HashMap<String, FrameInfo[]> frameInfo = new HashMap<String, FrameInfo[]>();
	private final List<String> statesDrawn = new ArrayList<String>();
	
	public Sprite(BufferedImage image, int tileWidth, int tileHeight) {
		this.sheet = image;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		SpriteManager.getInstance().add(this);
	}
	
	public Sprite bake() {
		generateFrames();
		return this;
	}
	
	public Sprite(String string, int tileWidth, int tileHeight) {
		this(loadSheet(string), tileWidth, tileHeight);
	}
	
	public static BufferedImage loadSheet(String string) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(string));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	private void generateFrames() {
		for (String state : frameInfo.keySet()) {
			List<Frame> stateFrames = new ArrayList<Frame>();
			for (FrameInfo info : frameInfo.get(state)) {
				stateFrames.add(new Frame(sheet.getSubimage(info.getPosition().x * tileWidth, info.getPosition().y * tileHeight, tileWidth, tileHeight), info.getLength()));
			}
			frames.put(state, stateFrames);
			frameNumber.put(state, 0);
			frameTicks.put(state, 0);
		}
	}
	
	public void draw(Graphics2D g, String state, float x, float y,float alpha) {
		draw(g, state, x, y, 1, 1,alpha);
	}
	
	public void draw(Graphics2D g, String state, float x, float y) {
		draw(g, state, x, y,1f);
	}
	
	public void draw(Graphics2D g, String state, float x, float y, int xs, int ys) {
		draw(g, state, x, y, xs, ys, 1f);
	}
	
	public void draw(Graphics2D g, String state, float x, float y, int xs, int ys,float alpha) {
		if (statesDrawn.contains(state) == false)
			statesDrawn.add(state);
		RenderImage.draw(g, getCurrentImage(state).getImage(), x, y, xs, ys,alpha);
	}
	
	private void incrementState(String state) {
		int ticks = frameTicks.get(state);
		ticks++;
		if (ticks >= getCurrentImage(state).getLength()) {
			int n = getFrameCount(state);
			int i = frameNumber.get(state);
			i++;
			if (i >= n)
				i = 0;
			frameNumber.put(state, i);
			ticks = 0;
		}
		frameTicks.put(state, ticks);
		
	}
	
	public int getFrameCount(String state) {
		return getFrames(state).size();
	}
	
	public List<Frame> getFrames(String state) {
		return frames.get(state);
	}
	
	private Frame getCurrentImage(String state) {
		return frames.get(state).get(frameNumber.get(state));
	}
	
	public BufferedImage getSheet() {
		return sheet;
	}
	
	public int getTileWidth() {
		return tileWidth;
	}
	
	public int getTileHeight() {
		return tileHeight;
	}
	
	public Sprite addState(String name, FrameInfo[] info) {
		frameInfo.put(name, info);
		return this;
	}
	
	public void update() {
		for (String s : statesDrawn) {
			incrementState(s);
		}
		statesDrawn.clear();
	}

	
	
}
