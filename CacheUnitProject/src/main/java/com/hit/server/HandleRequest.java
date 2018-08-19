package com.hit.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hit.dm.DataModel;
import com.hit.services.CacheUnitController;

public class HandleRequest<T> extends java.lang.Object implements java.lang.Runnable {
	ObjectInputStream in;
	ObjectOutputStream out;
	Socket socket;
	CacheUnitController<T> controller;
	Request<DataModel<T>[]> socketRequest;
	Gson gson;
	HashMap<String, String> ans = new HashMap<String, String>();
	Type ref;

	public HandleRequest(java.net.Socket s, CacheUnitController<T> controller) {
		this.socket = s;
		this.controller = controller;
	}

	public void run() {
		gson = new GsonBuilder().create();
		String inputString, command;
		DataModel<T>[] body;

		try {
			in = new ObjectInputStream(socket.getInputStream()); // in stream from socket
			out = new ObjectOutputStream(socket.getOutputStream()); // out stream to socket
			Type ref = new TypeToken<Request<DataModel<T>[]>>() {
			}.getType(); // from instructions in PDF
			inputString = (String) in.readObject(); // reading the "object" from client- came as GSON
			socketRequest = new Gson().fromJson(inputString, ref);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Map<String, String> headers = socketRequest.getHeaders();
		body = socketRequest.getBody();
		command = (String) headers.get("action");
		ans.put("algo", "LRU");
		ans.put("capacity", "5");
		if (command.equals("GET")) {
			DataModel<T>[] datamodels = controller.get(body);
			String gsonString = gson.toJson(datamodels);
			ans.put("dataModels", gsonString);
			ans.put("state", "true");
			ans.put("command", command);
			ans.put("swaps", "" + controller.temp.getNumberOfSwaps());
			ans.put("requests", "" + controller.temp.getNumberOfRequests());
			try {
				out.writeObject(ans);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else if (command.equals("DELETE")) {
			boolean del = this.controller.delete(body);
			try {
				if (del)
					ans.put("state", "true");
				else
					ans.put("state", "false");
				ans.put("command", command);
				ans.put("swaps", "" + controller.temp.getNumberOfSwaps());
				ans.put("requests", "" + controller.temp.getNumberOfRequests());
				out.writeObject(ans);
				out.flush();

			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (command.equals("UPDATE")) {
			boolean update = this.controller.update(body);
			try {
				if (update)
					ans.put("state", "true");
				else
					ans.put("state", "false");
				ans.put("command", command);
				ans.put("swaps", "" + controller.temp.getNumberOfSwaps());
				ans.put("requests", "" + controller.temp.getNumberOfRequests());
				out.writeObject(ans);
				out.flush();

			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				ans.put("command", "unknown");
				ans.put("state", "false");
				out.writeObject(ans);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {

			out.close();
			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
