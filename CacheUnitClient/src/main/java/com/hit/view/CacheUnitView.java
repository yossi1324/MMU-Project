package com.hit.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileSystemView;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CacheUnitView extends java.util.Observable implements View {

	private String numOfreq = "0", state;
	private int numOfDataMod = 0;
	String capacity="";
	private String numOfSwap = "0";
	private JFrame frame, getModels;
	private String algo, command = "", getmodels;
	HashMap<String, String> ans;
	private FileInputStream in;

	@Override
	public <T> void updateUIData(T t) { // get ans
		ans = (HashMap<String, String>) t;
		state = ans.get("state");
		command = ans.get("command");
		capacity=ans.get("capacity");
		if (state.equals("true")) { // update the date only if the request succeed
			getmodels = ans.get("dataModels");
			numOfDataMod++;
			numOfSwap = ans.get("swaps");
			numOfreq = ans.get("requests");
			algo = ans.get("algo");
		}

	}

	@Override
	public void start() {

		
		frame = new JFrame("Cache Unit");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setPreferredSize(new Dimension(1000, 800));
		frame.setLocation(400, 100);
		try {
			frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("src\\main\\resources\\ground.png")))));
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		JLabel capacityLabel = new JLabel("");
		JLabel algorithmLabel = new JLabel("");
		JLabel numOfReqLabel = new JLabel(" ");
		JLabel numOfDataModelLabel = new JLabel("");
		JLabel numOfSwapLabel = new JLabel("");

		frame.add(capacityLabel);
		frame.add(algorithmLabel);
		frame.add(numOfReqLabel);
		frame.add(numOfDataModelLabel);
		frame.add(numOfSwapLabel);

		capacityLabel.setLocation(10, 100);
		algorithmLabel.setLocation(10, 150);
		numOfReqLabel.setLocation(10, 200);
		numOfDataModelLabel.setLocation(10, 250);
		numOfSwapLabel.setLocation(10, 300);

		capacityLabel.setFont(new Font("Serif", Font.BOLD, 25));
		algorithmLabel.setFont(new Font("Serif", Font.BOLD, 25));
		numOfReqLabel.setFont(new Font("Serif", Font.BOLD, 25));
		numOfDataModelLabel.setFont(new Font("Serif", Font.BOLD, 25));
		numOfSwapLabel.setFont(new Font("Serif", Font.BOLD, 25));

		numOfSwapLabel.setForeground(Color.WHITE);
		numOfDataModelLabel.setForeground(Color.WHITE);
		numOfReqLabel.setForeground(Color.WHITE);
		algorithmLabel.setForeground(Color.WHITE);
		capacityLabel.setForeground(Color.WHITE);

		capacityLabel.setSize(800, 50);
		algorithmLabel.setSize(800, 50);
		numOfReqLabel.setSize(800, 50);
		numOfDataModelLabel.setSize(800, 50);
		numOfSwapLabel.setSize(800, 50);

		JButton statisticButton = new JButton("Show Statistics");
		statisticButton.setBounds(10, 10, 130, 30);

		statisticButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				stats(capacityLabel, algorithmLabel, numOfReqLabel, numOfDataModelLabel, numOfSwapLabel);
			}
		});
		frame.add(statisticButton);
		JButton loadReq = new JButton("Load a Request");
		loadReq.setBounds(150, 10, 130, 30);

		loadReq.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				capacityLabel.setVisible(false);
				algorithmLabel.setVisible(false);
				numOfReqLabel.setVisible(false);
				numOfDataModelLabel.setVisible(false);
				numOfSwapLabel.setVisible(false);

				final JFileChooser load = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				load.setDialogTitle("Load a request");
				load.setPreferredSize(new Dimension(500, 300));
				int returnValue = load.showOpenDialog(null);
				JsonObject jsonObject = new JsonObject();
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File file = load.getSelectedFile();

					try {
						JsonParser parser = new JsonParser();
						JsonElement jsonElement = parser.parse(new FileReader(file));
						jsonObject = jsonElement.getAsJsonObject();
						setChanged(); // update will only get called if this method is called
						notifyObservers(jsonObject.toString());

					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
				if (command.equals("unknown")) {
					JFrame error = new JFrame("Error- Bad Request, Command unknown");

					error.setLayout(null);
					error.setPreferredSize(new Dimension(500, 20));
					error.setLocation(500, 250);
					error.pack();
					error.setVisible(true);
				}
			}
		});

		frame.add(loadReq);

		JButton getButton = new JButton("Show Data Models");
		getButton.setBounds(290, 10, 150, 30);

		getButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (command.equals("GET")) {
					getModels = new JFrame("Get Data Models");
					getModels.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					getModels.setLayout(null);
					getModels.setPreferredSize(new Dimension(500, 400));
					getModels.setLocation(400, 100);
					JLabel title = new JLabel("");
					getModels.add(title);
					title.setText("Data Models you asked for : ");
					title.setLocation(10, 50);
					title.setSize(400, 14); // need to change

					JLabel modelslabel = new JLabel(getmodels.toString());
					getModels.add(modelslabel);
					modelslabel.setLocation(10, 10);
					modelslabel.setSize(500, 300);

					getModels.pack();
					getModels.setVisible(true);
				} else {
					JFrame error = new JFrame("Error- You Have To Send GET Request");

					error.setLayout(null);
					error.setPreferredSize(new Dimension(500, 20));
					error.setLocation(500, 250);
					error.pack();
					error.setVisible(true);
					
				}
			}

		});

		frame.add(getButton);

		frame.pack();
		frame.setVisible(true);

	}

	private void stats(JLabel capacityLabel, JLabel algorithmLabel, JLabel numOfReqLabel, JLabel numOfDataModelLabel,
			JLabel numOfSwapLabel) {

		capacityLabel.setText("Capacity: "+capacity);
		algorithmLabel.setText("Algorithm: " + algo);
		numOfReqLabel.setText("Total number of requests from cache: " + numOfreq);
		numOfDataModelLabel.setText("Total number of DataModels (GET/DELETE/UPDATE requests: " + numOfDataMod);
		numOfSwapLabel.setText("Total number of DataModel swaps (from cache to Disk): " + numOfSwap);

		capacityLabel.setVisible(true);
		algorithmLabel.setVisible(true);
		numOfReqLabel.setVisible(true);
		numOfDataModelLabel.setVisible(true);
		numOfSwapLabel.setVisible(true);
	}

}
