package com.hit.services;

import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.algorithm.RandomAlgoCacheImpl;
import com.hit.algorithm.SecondChance;
import com.hit.dao.DaoFileImpl;
import com.hit.dm.DataModel;
import com.hit.memory.CacheUnit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CacheUnitService<T> {

	private CacheUnit<T> cacheUnit;
	private int swaps, requests;

	public CacheUnitService() {// constructor
		DaoFileImpl<T> daoFile = new DaoFileImpl<>("src/main/resources/datasource.txt");
		LRUAlgoCacheImpl<T, DataModel<T>> lru = new LRUAlgoCacheImpl<>(5);
		this.cacheUnit = new CacheUnit(lru, daoFile);
		swaps = 0;
		requests = 0;
	}

	public boolean delete(DataModel<T>[] dataModels) {
		boolean isDelete = false;

		DataModel[] returnModels = null;
		Long[] ids = new Long[dataModels.length];

		for (int i = 0; i < dataModels.length; i++) {
			ids[i] = dataModels[i].getDataModelId();
		}

		returnModels = cacheUnit.getDataModels(ids);

		for (DataModel model : returnModels) {
			model.setContent(null);
		}

		if (returnModels.length > 0) {
			isDelete = true;
		}

		requests = cacheUnit.getNumberOfRequests();
		swaps = cacheUnit.getNumberOfSwaps();
		return isDelete;
	}

	public DataModel<T>[] get(DataModel<T>[] dataModels) {
		Long[] ids = new Long[dataModels.length];
		DataModel[] models = null;

		for (int i = 0; i < dataModels.length; i++) {
			ids[i] = dataModels[i].getDataModelId();
		}

		models = cacheUnit.getDataModels(ids);

		requests = cacheUnit.getNumberOfRequests();
		swaps = cacheUnit.getNumberOfSwaps();
		return models;
	}

	public boolean update(DataModel<T>[] dataModels) {

		boolean isUpdate = false;

		DataModel[] returnModels = null;
		Long[] ids = new Long[dataModels.length];

		for (int i = 0; i < dataModels.length; i++) {
			ids[i] = dataModels[i].getDataModelId();
		}

		returnModels = cacheUnit.getDataModels(ids);

		for (int i = 0; i < dataModels.length; i++) {
			for (int j = 0; j < returnModels.length; j++) {
				if (dataModels[i].getDataModelId().equals(returnModels[j].getDataModelId())) {
					returnModels[j].setContent(dataModels[i].getContent());
					j = returnModels.length + 1;
				}
			}
		}
		for (DataModel model : dataModels) {
			cacheUnit.updateFile(model);
			swaps++;
		}

		if (dataModels.length > 0) {
			isUpdate = true;
		}
		requests= cacheUnit.getNumberOfRequests();
		swaps= cacheUnit.getNumberOfSwaps();
		return isUpdate;
	}

	public int getNumberOfSwaps() {
		return swaps;
	}

	public int getNumberOfRequests() {
		return requests;
	}
}