package com.censkh.game.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	
	public static Sound jump = new Sound("res/sound/jump.wav");
	public static Sound click = new Sound("res/sound/click.wav");
	
	public static Sound dirtStep = new Sound("res/sound/step/gravel1.wav");
	
	private Clip clip;
	
	public Sound(String fileName) {
		try {
			clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(fileName));
			clip.open(inputStream);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		clip.stop();
		clip.setFramePosition(0);
		clip.start();
	}
	
}
