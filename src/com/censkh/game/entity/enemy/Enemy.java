package com.censkh.game.entity.enemy;

import java.awt.Graphics2D;

import com.censkh.game.engine.util.Direction;
import com.censkh.game.entity.LivingEntity;
import com.censkh.game.gui.element.GuiText;
import com.censkh.game.render.ChatColor;
import com.censkh.game.render.RenderText;

public abstract class Enemy extends LivingEntity {
	
	public Enemy(float x, float y) {
		super(x, y);
	}
	
	@Override
	public void update() {
		super.update();
		if (getMap().isCollision(this, getMap().getPlayer())) {
			getMap().getPlayer().damage(1f);
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		RenderText.draw(g, GuiText.defaultFont, "" + ChatColor.DARK_RED + getHealth(), (int)getX(), (int)getY()-12, 1f, true);
		getSprite().draw(g, "idle", getX()+(getDirection() == Direction.RIGHT ? 0 : getWidth()), getY(), getDirection() == Direction.RIGHT ? 1 : -1,1);
	}
	
}
