package com.censkh.game.gui.element;

import java.awt.Font;
import java.awt.Graphics2D;

import com.censkh.game.gui.GuiElement;
import com.censkh.game.render.ChatColor;
import com.censkh.game.render.RenderText;

public class GuiText extends GuiElement {
	
	public static final Font defaultFont = new Font("Arial", Font.PLAIN, 18);
	private Font font = defaultFont;
	private String text = "null";
	private boolean shadow = true;
	
	@Override
	public String getName() {
		return "text.static";
	}
	
	@Override
	public float[] createPadding() {
		return new float[] {
				0f, 0f, 0f, 2f,
		};
	}
	
	@Override
	public void draw(Graphics2D g) {
		RenderText.draw(g, getFont(), getText(), getRelativeX(), getRelativeY(), getDisplayAlpha(), shadow);
	}
	
	@Override
	public boolean isContainer() {
		return false;
	}
	
	public String getText() {
		return text;
	}
	
	public Font getFont() {
		return font;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setFont(Font font) {
		this.font = font;
	}
	
	@Override
	public float getWidth() {
		float longest = 0f;
		int indent = 0;
		for (int i = 0; i < text.length(); i++) {
			if (i != text.length() - 1) {
				String ss = text.substring(i, i + 2);
				
				if (ss.equals(ChatColor.NEW_LINE.toString())) {
					
					float w = ChatColor.stripColor(text).substring(indent, i).length() * (font.getSize() / 2);
					i++;
					indent = i;
					if (w > longest)
						longest = w;
				} else {
					
				}
				
			}
		}
		float w = ChatColor.stripColor(text.substring(indent, text.length())).length() * (font.getSize() / 2);
		if (w > longest)
			longest = w;
		return longest + 4f;
	}
	
	@Override
	public float getHeight() {
		return (font.getSize() + 1) * (1 + stringCount(text, ChatColor.NEW_LINE + "")) + getPadding()[1] + getPadding()[3];
	}
	
	public int stringCount(String text, String subString) {
		int lastIndex = 0;
		int count = 0;
		
		while (lastIndex != -1) {
			
			lastIndex = text.indexOf(subString, lastIndex);
			
			if (lastIndex != -1) {
				count++;
				lastIndex += subString.length();
			}
		}
		return count;
	}
	
	@Override
	public void update() {
		
	}
	
}
