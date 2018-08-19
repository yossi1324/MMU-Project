package com.hit.memory;

import java.util.ArrayList;

import com.hit.algorithm.IAlgoCache;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;

public class CacheUnit<T> {
	private IAlgoCache<Long, DataModel<T>> algo;
	private IDao<java.io.Serializable, DataModel<T>> dao;
	private int swaps, requests;

	public CacheUnit(com.hit.algorithm.IAlgoCache<java.lang.Long, DataModel<T>> algo,
			IDao<java.io.Serializable, DataModel<T>> dao) {
		this.algo = algo;
		this.dao = dao;
		swaps = 0;
		requests = 0;
	}

	public DataModel<T>[] getDataModels(java.lang.Long[] ids) {

		ArrayList<DataModel<T>> listOfEntitys = new ArrayList<>();
		DataModel<T> entity;
		ArrayList<Long> arrayIds = new ArrayList<>();
		ArrayList<Long> removeId = new ArrayList<>();

		for (Long l : ids) {
			arrayIds.add(l);
			requests++;
		}

		for (int i = 0; i < arrayIds.size(); i++) {
			entity = (DataModel<T>) algo.getElement(arrayIds.get(i));

			if (entity != null) {
				removeId.add(arrayIds.get(i));
				listOfEntitys.add(entity);
			}
		}

		for (Long id : removeId) {
			arrayIds.remove(id);
		}

		for (int i = 0; i < arrayIds.size(); i++) {
			Long id = arrayIds.get(i);
			DataModel<T> tempData;
			tempData = (DataModel<T>) dao.find(id);

			if (tempData != null) {
				listOfEntitys.add(tempData);
				removeId.add(id);
			}

			DataModel resultObject = null;

			if (tempData != null) {
				resultObject = (DataModel) algo.putElement(tempData.getDataModelId(), tempData);
			}
			if (resultObject != null) {
				dao.save(resultObject);
				swaps++; // disk swap
			}

		}

		DataModel<T>[] arrOfDataModels = new DataModel[listOfEntitys.size()];

		for (int i = 0; i < listOfEntitys.size(); i++) {
			arrOfDataModels[i] = listOfEntitys.get(i);
		}

		return arrOfDataModels;

	}

	public int getNumberOfSwaps() {
		return swaps;
	}

	public int getNumberOfRequests() {
		return requests;
	}

	public void updateFile(DataModel model) {
		dao.save(model);
	}

}
