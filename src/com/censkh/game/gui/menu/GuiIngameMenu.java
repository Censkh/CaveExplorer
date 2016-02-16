package com.censkh.game.gui.menu;

import java.awt.Color;

import com.censkh.game.Game;
import com.censkh.game.engine.GameState;
import com.censkh.game.gui.GuiElement;
import com.censkh.game.gui.GuiMenu;
import com.censkh.game.gui.element.GuiButton;
import com.censkh.game.gui.element.GuiPanel;
import com.censkh.game.gui.element.GuiText;

public class GuiIngameMenu extends GuiMenu {
	
	@Override
	public boolean isDefaultVisible() {
		return false;
	}
	
	@Override
	public GuiElement[] createElements() {
		return new GuiElement[] {
			new GuiPanel() {
				{
					setColor(new Color(128, 128, 128, 128));
					setMoveable(true);
					setX(100);
					setY(100);
				}
			}.add(new GuiElement[] {
					new GuiText() {
						{
							setText("Menu");
						}
					}, new GuiButton() {
						{
							setWidth(155);
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
								setText("Options");
							}
						}
					}), new GuiButton() {
						{
							setWidth(155);
							setOnClick(new Runnable() {
								
								@Override
								public void run() {
									Game.getGame().getGuiManager().getMenu(GuiIngameMenu.class).setVisible(false);
								}
							});
						}
					}.add(new GuiElement[] {
						new GuiText() {
							{
								setText("Close Menu");
							}
						}
					}), new GuiButton() {
						{
							setWidth(155);
							setOnClick(new Runnable() {
								
								@Override
								public void run() {
									Game.getGame().switchGameState(GameState.MAIN_MENU);
								}
							});
						}
					}.add(new GuiElement[] {
						new GuiText() {
							{
								setText("Exit");
							}
						}
					}),
			}),
		};
	}
	
}
