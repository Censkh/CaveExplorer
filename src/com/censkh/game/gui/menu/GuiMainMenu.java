package com.censkh.game.gui.menu;

import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.censkh.game.Game;
import com.censkh.game.GameFrame;
import com.censkh.game.engine.GameState;
import com.censkh.game.gui.GuiElement;
import com.censkh.game.gui.GuiMenu;
import com.censkh.game.gui.element.GuiArea;
import com.censkh.game.gui.element.GuiButton;
import com.censkh.game.gui.element.GuiImage;
import com.censkh.game.gui.element.GuiText;
import com.censkh.game.render.ChatColor;

public class GuiMainMenu extends GuiMenu {
	
	private final static Font titleFont = new Font("arial", Font.BOLD, 28);
	private final static Font buttonFont = new Font("arial", Font.PLAIN, 18);
	
	@Override
	public GuiElement[] createElements() {
		return new GuiElement[] {
				new GuiImage() {
					{
						try {
							setImage(ImageIO.read(new File("res/background.png")));
						} catch (IOException e) {
						}
						
					}
				}, new GuiArea() {
					{
						setX(64);
						setY(64);
					}
				}.add(new GuiElement[] {
						new GuiText() {
							{
								setFont(titleFont);
								setText(ChatColor.GOLD + GameFrame.getInstance().getTitle());
							}
						}, new GuiArea() {
							{
								setHeight(200);
							}
						}, new GuiButton() {
							{
								setWidth(240);
								setOnClick(new Runnable() {
									
									@Override
									public void run() {
										Game.getGame().switchGameState(GameState.GAME);
									}
								});
							}
						}.add(new GuiElement[] {
							new GuiText() {
								{
									setFont(buttonFont);
									setText(ChatColor.WHITE + "Start");
								}
							}
						}), new GuiButton() {
							{
								setWidth(240);
								setOnClick(new Runnable() {
									
									@Override
									public void run() {
										Game.getGame().switchGameState(GameState.OPTIONS);
									}
								});
							}
						}.add(new GuiElement[] {
							new GuiText() {
								{
									setFont(buttonFont);
									setText(ChatColor.WHITE + "Options");
								}
							}
						}), new GuiButton() {
							{
								setWidth(240);
								setOnClick(new Runnable() {
									
									@Override
									public void run() {
										Game.exit();
									}
								});
							}
						}.add(new GuiElement[] {
							new GuiText() {
								{
									setFont(buttonFont);
									setText(ChatColor.WHITE + "Exit");
								}
							}
						})
				}),
		
		};
	}
	
}
