package main;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Main {
	   public static void main(String[] args) {
	        JFrame frame = new JFrame("Crossy Chicken");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setResizable(false);
	        frame.setLayout(new BorderLayout());
	        GamePanel gamePanel = new GamePanel();
	        frame.add(gamePanel);
	        frame.pack();
	        frame.setLocationRelativeTo(null);
	        frame.setVisible(true);
	        
	    }
}
