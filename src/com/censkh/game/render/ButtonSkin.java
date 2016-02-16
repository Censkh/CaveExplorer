package com.censkh.game.render;

import java.awt.Graphics2D;

public class ButtonSkin {
	
	private final Sprite sprite;
	
	public static ButtonSkin defaultSkin = new ButtonSkin("res/gui/buttons/default.png");
	public static ButtonSkin hoverSkin = new ButtonSkin("res/gui/buttons/hover.png");
	public static ButtonSkin clickingSkin = new ButtonSkin("res/gui/buttons/clicked.png");
	
	public ButtonSkin(String res) {
		sprite = new Sprite(res, 12, 12);
		sprite.addState(
				"default",
				new FrameInfo[] {
						new FrameInfo(0, 0, 0),
						new FrameInfo(1, 0, 0),
						new FrameInfo(2, 0, 0),
						new FrameInfo(0, 1, 0),
						new FrameInfo(1, 1, 0),
						new FrameInfo(2, 1, 0),
						new FrameInfo(0, 2, 0),
						new FrameInfo(1, 2, 0),
						new FrameInfo(2, 2, 0),
				}).bake();
	}
	
	public void render(Graphics2D g, float x, float y, float width, float height, float a) {
		for (int tx = 1; tx < (width / 12) - 1; tx++) {
			for (int ty = 1; ty < (height / 12) - 1; ty++) {
				RenderImage.draw(g, getSprite().getFrames("default").get(4).getImage(), x + (tx * 12) - 6, y + (ty * 12) - 6, a);
			}
		}
		for (int ty = 1; ty < (height / 12) - 1; ty++) {
			RenderImage.draw(g, getSprite().getFrames("default").get(3).getImage(), x, y + (ty * 12) - 6, a);
			RenderImage.draw(g, getSprite().getFrames("default").get(5).getImage(), x + width - 12, y + (ty * 12) - 6, a);
		}
		for (int tx = 1; tx < (width / 12) - 1; tx++) {
			RenderImage.draw(g, getSprite().getFrames("default").get(1).getImage(), x + (tx * 12) - 6, y, a);
			RenderImage.draw(g, getSprite().getFrames("default").get(7).getImage(), x + (tx * 12) - 6, y + height - 12, a);
		}
		
		RenderImage.draw(g, getSprite().getFrames("default").get(0).getImage(), x, y, a);
		RenderImage.draw(g, getSprite().getFrames("default").get(2).getImage(), x + width - 12, y, a);
		RenderImage.draw(g, getSprite().getFrames("default").get(6).getImage(), x, y + height - 12, a);
		RenderImage.draw(g, getSprite().getFrames("default").get(8).getImage(), x + width - 12, y + height - 12, a);
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
}
