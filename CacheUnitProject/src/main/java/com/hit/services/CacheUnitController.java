package com.hit.services;

import com.hit.dm.DataModel;

public class CacheUnitController<T> extends java.lang.Object {
	public CacheUnitService<T> temp;

	public CacheUnitController() {
		temp = new CacheUnitService<T>();
	}

	public boolean update(DataModel<T>[] dataModels) {
		return temp.update(dataModels);
	}

	public boolean delete(DataModel<T>[] dataModels) {
		return temp.delete(dataModels);
	}

	public DataModel<T>[] get(DataModel<T>[] dataModels) {
		return temp.get(dataModels);
	}
}