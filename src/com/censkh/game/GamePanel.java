package com.censkh.game;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private static GamePanel instance;
	private static final long serialVersionUID = -4332987116190639732L;
	
	public GamePanel() {
		super();
		instance = this;
		setBackground(Color.black);
		setVisible(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (Game.initialized)
			Game.getGame().draw(g);
	}
	
	public static GamePanel getInstance() {
		return instance;
	}
	
}
