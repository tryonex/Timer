/*
 * Copyright © 2016 by Tryonex
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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

/**
 * @author <name>
 *
 */
public class TimeLabel extends JLabel{

	
	private static final long serialVersionUID = 1L;
	
	
	
	public TimeLabel(){
		super();
	}
	
	public TimeLabel(String s){
		super(s);
	}
	
	@Override
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D) (g);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//		g2.setBackground(new Color(0,0,0,0));
		g2.drawString(this.getText(), 0, this.getFont().getSize()-2);
	}

}
