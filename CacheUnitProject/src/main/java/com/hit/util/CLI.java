package com.hit.util;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Scanner;

public class CLI extends Observable implements Runnable {

	private Scanner reader;
	private PrintWriter writer;
	private Map<String, String> commands;

	public CLI(java.io.InputStream in, java.io.OutputStream out) {
		reader = new Scanner(in);
		writer = new PrintWriter(out);
		commands = new HashMap<>();
		commands.put("start", "starting server");
		commands.put("stop", "shutdown server");

	}

	public void run() {
		while (true) {
			write("Please enter START to begin or STOP to exit:\n");

			String line = reader.nextLine();
			if (commands.containsKey(line)) {
				// write(commands.get(line));
				setChanged();
				notifyObservers(line);
				clearChanged();
			} else {
				write("Not a valid command");
			}
		}
	}

	public void write(java.lang.String string) {
		writer.println(string);
		writer.flush();
	}
}