package com.censkh.game;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GameFrame extends JFrame {
	
	private static final long serialVersionUID = 3924721254266365911L;
	private static GameFrame instance;
	private final GamePanel panel;
	private Cursor cursor;
	
	public GameFrame(int width, int height) {
		super();
		instance = this;
		panel = new GamePanel();
		Image cursorImage = null;
		try {
			cursorImage = ImageIO.read(new File("res/cursor.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "gamePointer");
		add(panel);
		setTitle(Game.getTitle());
		setBounds(-1, -1, width, height);
		setBackground(Color.black);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setAutoRequestFocus(true);
		setMinimumSize(new Dimension(320, 320));
		setVisible(true);
		setCursor(cursor);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Game.exit();
			}
		});
		try {
			setIconImage(ImageIO.read(new File("res/icon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static GameFrame getInstance() {
		return instance;
	}
	
}
