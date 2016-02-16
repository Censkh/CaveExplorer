package com.censkh.game.entity;

import com.censkh.game.engine.util.IDraw;
import com.censkh.game.engine.util.IUpdate;

public interface IEntity extends IUpdate, IDraw {
	
	public float getWidth();
	
	public float getHeight();
	
	public int getRenderLayer();
	
}
