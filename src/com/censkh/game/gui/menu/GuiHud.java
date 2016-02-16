package com.censkh.game.gui.menu;

import java.awt.Color;
import java.awt.Graphics2D;

import com.censkh.game.Game;
import com.censkh.game.entity.Player;
import com.censkh.game.gui.GuiElement;
import com.censkh.game.gui.GuiMenu;
import com.censkh.game.gui.element.GuiArea;
import com.censkh.game.gui.element.GuiImage;
import com.censkh.game.gui.element.GuiTextNonStatic;
import com.censkh.game.map.MapManager;
import com.censkh.game.render.ChatColor;
import com.censkh.game.render.RenderRectangle;
import com.censkh.game.tile.Tile;

public class GuiHud extends GuiMenu {
	
	@Override
	public GuiElement[] createElements() {
		return new GuiElement[] {
				new GuiTextNonStatic() {
					{
						setText("Health: ");
						setY(12);
						setX(12);
						setTextUpdate(new Runnable() {
							
							@Override
							public void run() {
								if (MapManager.getCurrentMap() == null) {
									return;
								}
								Player player = Game.getGame().getPlayer();
								setText("Health: " + player.getHealth() + "/" + player.getMaxHealth() + ChatColor.NEW_LINE + "Gold: " + player.getGold() + "g");
							}
						});
					}
				}, new GuiArea() {
					{
						setX(64);
						setY(Game.getFrameHeight() - 200);
						setMoveable(true);
					}
				}.add(new GuiElement[] {
						new GuiTextNonStatic() {
							{
								setTextUpdate(new Runnable() {
									
									@Override
									public void run() {
										setText(ChatColor.YELLOW + MapManager.getCurrentMap().getName());
									}
								});
							}
						}, new GuiImage() {
							{
								setImage(null);
								setOnUpdate(new Runnable() {
									@Override
									public void run() {
										if (MapManager.getCurrentMap() != null) {
											setImage(MapManager.getCurrentMap().getMapImage());
										}
									}
								});
								setOnDraw(new Runnable() {
									@Override
									public void run() {
										Graphics2D g = getOnDrawGraphics();
										RenderRectangle.draw(g, ((Game.getGame().getPlayer().getX() / Tile.size) * 2) + getRelativeX(), ((Game.getGame().getPlayer().getY() / Tile.size) * 2)
												+ getRelativeY(), 2f, 4f, Color.red, getDisplayAlpha());
									}
								});
							}
						},
				}),
		
		};
	}
	
}
