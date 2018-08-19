package com.hit.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import com.hit.dm.DataModel;

public class DaoFileImpl<T> extends java.lang.Object implements IDao<java.lang.Long, DataModel<T>> {

	public static final int size = 1000;
	private String filepath;
	private ObjectInputStream input;
	private ObjectOutputStream output;

	private java.util.Map<Long, DataModel<T>> hdData;

	@SuppressWarnings("unchecked")
	public DaoFileImpl(java.lang.String filePath) {
		this.filepath = filePath;

		java.io.File file = new java.io.File(filePath);
		try {
			if (file.exists()) {
				input = new ObjectInputStream(new FileInputStream(filePath));
				hdData = (HashMap<Long, DataModel<T>>) input.readObject();
			} else {
				try {
					file.createNewFile();
					hdData = new HashMap<Long, DataModel<T>>(size);
					for (int i = 1; i <= size; i++)
						hdData.put((long) i, null);
					output = new ObjectOutputStream(new FileOutputStream(filepath));
					output.writeObject((HashMap<Long, DataModel<T>>) hdData);
					output.flush();

				} catch (IOException e) {
					System.out.println("IO Exception");
					e.printStackTrace();
				} finally {
					try {
						output.close();
					} catch (IOException e) {
						e.printStackTrace();

					}
				}
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void save(DataModel<T> entity) {

		try {
			hdData.put(entity.getDataModelId(), entity);
			output = new ObjectOutputStream(new FileOutputStream(filepath));
			output.writeObject((HashMap<Long, DataModel<T>>) hdData);
			output.flush();
			output.close();
		} catch (IOException e) {
			System.out.println("IO Exception");
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void delete(DataModel<T> entity) {

		try {
			hdData.remove(entity.getDataModelId());
			output = new ObjectOutputStream(new FileOutputStream(filepath));
			output.writeObject((HashMap<Long, DataModel<T>>) hdData);
			output.flush();
			output.close();
		} catch (IOException e) {
			System.out.println("IO Exception");
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataModel<T> find(Long id) {

		DataModel<T> page = null;

		try {
			input = new java.io.ObjectInputStream(new java.io.FileInputStream(filepath));
			hdData = (java.util.HashMap<Long, DataModel<T>>) input.readObject();
			if (hdData.containsKey(id))
				page = hdData.get(id);
		} catch (java.lang.ClassNotFoundException | IOException e1) {
			e1.printStackTrace();

		} finally {
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return page;
	}

}
