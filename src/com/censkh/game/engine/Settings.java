package com.censkh.game.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {
	
	private static Settings instance;
	private final Properties props;
	
	private final int keyMoveLeft;
	private final int keyMoveRight;
	private final int keyJump;
	private final int keyAttack;
	
	public Settings() {
		instance = this;
		props = new Properties();
		try {
			getProps().load(new FileInputStream(new File("options.properties")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		keyMoveLeft = Integer.parseInt(instance.props.getProperty("keyMoveLeft"));
		keyMoveRight = Integer.parseInt(instance.props.getProperty("keyMoveRight"));
		keyJump = Integer.parseInt(instance.props.getProperty("keyJump"));
		keyAttack = Integer.parseInt(instance.props.getProperty("keyAttack"));
	}
	
	public Settings getInstance() {
		return instance;
	}
	
	public static int keyMoveLeft() {
		return instance.keyMoveLeft;
	}
	
	public static int keyMoveRight() {
		return instance.keyMoveRight;
	}
	
	public static int keyAttack() {
		return instance.keyAttack;
	}
	
	public static int keyJump() {
		return instance.keyJump;
	}
	
	public Properties getProps() {
		return props;
	}
	
}
