/*
 * Copyright © 2015 by <name>
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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;



/**
 * @author <name>
 *
 */
public class Main implements ActionListener, KeyListener{
	private TimerFrame frame;
	public Timer t = new Timer();
	
	public static void main(String[] args){
		Main m = new Main();
		m.showFrame();
	}
	
	public void showFrame(){
		frame = new TimerFrame(this, true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		switch(s){
			case("2"):
				frame.three.setSelected(false);
				frame.two.setSelected(true);
				frame.custom.setSelected(false);
				frame.field.setEnabled(false);
				frame.field.setText("00:00:00");
				frame.time.setText("02:00:00");
				break;
			case("3"):
				frame.two.setSelected(false);
				frame.three.setSelected(true);
				frame.custom.setSelected(false);
				frame.field.setEnabled(false);
				frame.field.setText("00:00:00");
				frame.time.setText("03:00:00");
				break;
			case("custom"):
				frame.three.setSelected(false);
				frame.two.setSelected(false);
				frame.custom.setSelected(true);
				frame.field.setEnabled(true);
				frame.time.setText("00:00:00");
				break;
			case("start"):
				 if(frame.two.isSelected())
					 t.setHours(2);
				 else if(frame.three.isSelected())
					 t.setHours(3);
				 else if(frame.custom.isSelected()){
					 String t = frame.field.getText();
					 try{
						 this.t.setHoursByString(t);
					 }catch(NumberFormatException ex){
						 JOptionPane.showMessageDialog((Component) frame.panel, ex.getMessage(), "Format Error", JOptionPane.ERROR_MESSAGE);
						 return;
					 }
				 }
				t.start();
				buildNewWindow(frame.fancyMode);
				break;
			
		}
		
	}
	
	private void buildNewWindow(boolean fancymode){
		if(fancymode){
			frame.setVisible(false);
			frame.dispose();
			
			if(!t.started)
				frame = new TimerFrame(this, frame.getLocation(), true);
			else{
				frame = new TimerFrame(this, frame.getLocation(), false);
				Thread thread = new Thread(frame);
				thread.setName("TickThread");
				thread.setDaemon(true);
				thread.start();
			}
		}else{
			frame.setVisible(false);
			frame.dispose();
			if(!t.started)
				frame = new TimerFrame(this, true);
			else{
				frame = new TimerFrame(this, false);
				Thread thread = new Thread(frame);
				thread.setName("TickThread");
				thread.setDaemon(true);
				thread.start();
			}
			
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_F1){
			if(frame.fancyMode){
				buildNewWindow(false);
			}
			else{
				buildNewWindow(true);
			}
		}else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			System.exit(0);
		}else if(e.getKeyCode() == KeyEvent.VK_SPACE){
			frame.stop();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}
		
}
