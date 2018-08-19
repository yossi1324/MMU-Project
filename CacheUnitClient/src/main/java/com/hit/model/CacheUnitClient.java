package com.hit.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

public class CacheUnitClient extends java.lang.Object {

	public static final int PORT_NUMBER = 12345;
	public static final String HOST_NUMBER = "127.0.0.1";
	HashMap<String, String> ans;

	public CacheUnitClient() {

	}

	@SuppressWarnings("finally")
	public java.lang.String send(java.lang.String request) {

		Socket socket;
		String reqAns = "";
		try {
			socket = new Socket("127.0.0.1", PORT_NUMBER);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

			out.writeObject(request);
			out.flush();
			ans = (HashMap<String, String>) in.readObject();
			reqAns = ans.get("state");

			socket.close();
			in.close();
			out.close();

		} catch (SocketException ex) {

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return reqAns;
		}

	}

}
