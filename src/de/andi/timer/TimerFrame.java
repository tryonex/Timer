/*
 * Copyright © 2015 by Tryonex
 * This file is part of Timer
 * Timer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Timer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Timer.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package de.andi.timer;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author <name>
 *
 */
public class TimerFrame extends JFrame implements Runnable{

	private static final long serialVersionUID = 1L;
	private Main main = null;
	public TimeLabel time = new TimeLabel();
	public JRadioButton two = new JRadioButton("2 h"), three = new JRadioButton("3 h"), custom = new JRadioButton();
	public JButton start = new JButton("start");
	public JTextField field = new JTextField();
	public JPanel panel = new JPanel(), top = new JPanel();
	public boolean fancyMode = false;
	public boolean stop = false;
	public boolean pause = false;
	public Color Red = new Color(255, 50, 50);
	public Color Gray = new Color(50, 50, 50);
	
	/**
	 * 
	 * @param main the Main instance with the Timer object
	 * 
	 * This constructor creates a normal window in the right corner at the bottom
	 */
	public TimerFrame(Main main, boolean topP) {
		super("Timer");
		this.main = main;
		int width = 280, height = 200;
		if(!topP)
			height = (int) (height / 1.8);
		setLocation(1920 - width - 5, 1080 - height - 45);
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
//		setUndecorated(true);
		getPanel(topP);
		setFocusable(true);
		addKeyListener(main);
		this.setContentPane(panel);
		try {
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel(UIManager.getInstalledLookAndFeels()[1].getClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(this);
		setVisible(true);
	}
	
	/**
	 * 
	 * @param main the Main instance with the Timer object
	 * 
	 * This constructor creates a fancy window at the last position
	 */
	public TimerFrame(Main main, Point loc, boolean topP) {
		super("Timer");
		fancyMode = true;
		this.main = main;
		int width = 280, height = 200;
		if(!topP)
			height = (int) (height / 2.0);
		setLocation(new Point((int)loc.getX(), (int)loc.getY()));
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setUndecorated(true);
		setOpacity(0.6f);
		getPanel(topP);
		setFocusable(true);
		addKeyListener(main);
		this.setContentPane(panel);
		try {
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel(UIManager.getInstalledLookAndFeels()[1].getClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(this);
		setVisible(true);
		
	}
	
	public void getPanel(boolean topP){
		if(topP){
			two.setFocusable(false);
			three.setFocusable(false);
			custom.setFocusable(false);
			start.setFocusable(false);
//			field.setFocusable(false);
			top.setFocusable(false);
		}
		panel.setFocusable(false);
		time.setFocusable(false);
		if(topP){
			two.setToolTipText("sets the timer on 2 hours");
			three.setToolTipText("sets the timer on 3 hours");
			start.setToolTipText("starts the timer");
			custom.setToolTipText("sets the timer on a custom amount of time");
			field.setToolTipText("sets the timer on a custom amount of time");
			field.setEnabled(false);
		}
		Font f = new Font("Arial", Font.PLAIN, 65);
		time.setFont(f);
		time.setForeground(Gray);
		time.setText("02:00:00");
//		time.setSize(new Dimension(WIDTH- 15, HEIGHT - 10));
		if(topP){
			two.setSelected(true);
			top.setLayout(new FlowLayout());
			two.setActionCommand("2");
			three.setActionCommand("3");
			custom.setActionCommand("custom");
			field.setActionCommand("field");
			start.setActionCommand("start");
			two.addActionListener(main);
			three.addActionListener(main);
			custom.addActionListener(main);
			field.addActionListener(main);
			start.addActionListener(main);
			field.setText("00:00:00");
			field.setEditable(true);
			top.add(two);
			top.add(three);
			top.add(custom);
			top.add(field);
			top.add(start);
			top.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			panel.add(top);
		}
		panel.add(time);
	}
	
	long pausetime = 0;
	@Override
	public void run() {
		while(true){
			if(stop){
				return;
			}else if(!pause){
				time.setText(main.t.calcRemainingTime());
				boolean blink = false;
				if(main.t.hours == 0 && main.t.min < 20)
					blink = true;
				blockForNextSecond();
				if(blink && time.getForeground() == Red)
					time.setForeground(Gray);
				else if(blink && time.getForeground() == Gray)
					time.setForeground(Red);
			} else{
				try {
					Thread.sleep(667);
				} catch (InterruptedException e) {
				}
				if(pause){
					if(time.getForeground() == Gray)
						time.setForeground(panel.getBackground());
					else{
						time.setForeground(Gray);
					}
				}else{
					time.setForeground(Gray);
				}
			}

		}
		
	}
	
	public void blockForNextSecond(){
		double start = main.t.getRemainingSeconds();
		while(true){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {}
			if(main.t.getRemainingSeconds() < start)
				return; 
		}
	}

	/**
	 * @param b
	 */
	public void stop() {
		pause = !pause;
		if(pause){
			if(pausetime == 0){
				pausetime = main.t.getRemainingTime();
			}
			this.panel.setBorder(BorderFactory.createLineBorder(Color.red));
		}else{
			this.panel.setBorder(BorderFactory.createEmptyBorder());
			main.t.restart(pausetime);
			pausetime = 0;
		}
	}

}
