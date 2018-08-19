package com.hit.server;

import com.hit.util.CLI;

public class CacheUnitServerDriver {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		CLI cli = new CLI(System.in, System.out);
		Server server = new Server();
		cli.addObserver(server);
		new Thread(cli).start();
	}
}