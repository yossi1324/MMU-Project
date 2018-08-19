package com.hit.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.hit.services.CacheUnitController;

public class Server implements java.util.Observer {
	private static final int PORT_NUMBER = 12345;
	private ServerSocket server;
	private Socket socket;
	CacheUnitController<String> unitController;
	ExecutorService threadPool;

	Server() {
		unitController = new CacheUnitController<String>();
		threadPool = Executors.newFixedThreadPool(20);
	}

	public void start() {
		if (server == null) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						server = new ServerSocket(PORT_NUMBER);
						while (true) {
							System.out.println("Server waiting for requests.");
							socket = server.accept();

							Thread thread = new Thread(new HandleRequest<String>(socket, unitController)); // new client

							threadPool.submit(thread); // add the client to the pool as a new thread

							// /**
							// * create a new {@link SocketServer} object for each connection this will
							// allow
							// * multiple client connections
							// */
							// new SocketServer(server.accept());
						}

					} catch (SocketException ex) {
						System.out.println("Server was closed by CLI." + ex.getMessage());

					} catch (IOException ex) {
						System.out.println("Unable to start server." + ex.getMessage());
					} finally {
						try {
							if (server != null) {
								server.close();
								threadPool.shutdown();
							}
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
				}
			}).start();
		} else {
			System.out.println("Server is already running");// notify that the server already start
		}
	}

	public void stop() {
		if (server == null) {
			// notify that the server not initialized
			return;
		}
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		server = null;
	}

	public void update(Observable o, Object arg) {
		if ("start".equals(arg)) {
			start();
		} else if ("stop".equals(arg)) {
			stop();
		}

	}
}