package com.censkh.game.entity;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.censkh.game.engine.util.IUpdate;

public abstract class EntityManager implements IUpdate {
	
	private final List<Entity> entityList = new ArrayList<Entity>();
	private final List<Entity> entityAddList = new ArrayList<Entity>();
	private final List<Entity> entityRemoveList = new ArrayList<Entity>();
	private boolean init=false;
	
	public List<Entity> getEntityList() {
		return entityList;
	}
	
	public void addEntity(Entity e) {
		if (init) entityAddList.add(e); else entityList.add(e);
	}
	
	public void removeEntity(Entity e) {
		entityRemoveList.add(e);
	}
	
	public void clearEntityList() {
		entityList.clear();
	}
	
	public Entity createEntity(float x, float y, Class<? extends Entity> clazz) {
		Entity e = null;
		try {
			e = (Entity) clazz.getConstructors()[0].newInstance(x, y);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e1) {
			e1.printStackTrace();
		}
		if (e != null)
			addEntity(e);
		return e;
	}
	
	public abstract boolean doSetMapToNull();
	
	@Override
	public void update() {
		init = true;
		popEntities();
		for (Entity e : entityList) {
			e.update();
		}
	}
	
	public void popEntities() {
		for (Entity e : entityAddList) {
			entityList.add(e);
		}
		entityAddList.clear();
		for (Entity e : entityRemoveList) {
			entityList.remove(e);
			if (doSetMapToNull()) e.setMap(null);
		}
		entityRemoveList.clear();
	}
	
}
