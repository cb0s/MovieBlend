package com.github.cb0s.movieblend;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Simple implementation of a blend for e.g. movie-nights for a second screen.<br>
 * <br>
 * As I currently only own 1 additional monitor, I don't need to code a blend for more than one monitor. If I get a 3rd monitor
 * one day I might update this simple script, until then feel free to implement and commit the changes yourself or execute the
 * script more than once.
 * 
 * @author Cedric Boes
 * @version 1.0
 */
public class MovieScreen {

	/**
	 * Main-Method to start the program.
	 * 
	 * @param args are not supported
	 */
	public static void main(String[] args) {
		var currentDevice = new AtomicInteger(-1);
		var devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		createNewFrame(currentDevice, devices);
	}
	
	/**
	 * To ensure no {@link ArrayIndexOutOfBoundsException} when requesting a new monitor, the index might need to be resetted.
	 * 
	 * @param count		currentCount
	 * @param arraySize	is the length of the array
	 * @return incremented or resetted {@link AtomicInteger}
	 */
	private static AtomicInteger getNewCount(AtomicInteger count, int arraySize) {
		if (count.incrementAndGet() >= arraySize) {
			count.set(0);
		}
		return count;
	}
	
	/**
	 * Creates new movie blends which when clicked will change the monitor.<br>
	 * To close them, you will need to close the Java-Application from the task-bar or via the Task-Manager.
	 * 
	 * @param currentDevice	is the id of the current monitor being blended
	 * @param devices		array of all local screen devices
	 */
	private static void createNewFrame(AtomicInteger currentDevice, GraphicsDevice[] devices) {		
		var frame = new JFrame(devices[getNewCount(currentDevice, devices.length).get()].getDefaultConfiguration());
		
		System.out.println("Setting up new blend on monitor " + frame.getGraphicsConfiguration().toString());
		
		var button = new JButton();
		button.addActionListener(event -> {
			createNewFrame(currentDevice, devices);
			frame.dispose();
		});
		button.setBorder(null);
		button.setBackground(Color.BLACK);
		frame.setLayout(new BorderLayout());
		frame.add(button, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setBackground(Color.BLACK);
		frame.setVisible(true);
	}
}
