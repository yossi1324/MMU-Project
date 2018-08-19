package com.hit.model;

public class CacheUnitModel extends java.util.Observable implements Model {

	private CacheUnitClient client;

	public CacheUnitModel() {
		client = new CacheUnitClient();

	}

	@Override
	public void updateModelData(Object t) {
		// TODO Auto-generated method stub
		client.send((String) t);
		setChanged();
		notifyObservers(client.ans);
	}
}
