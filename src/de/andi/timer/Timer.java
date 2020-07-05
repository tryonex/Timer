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

/**
 * @author <name>
 *
 */
public class Timer {

	private int h = 0;
	private long finish = 0;
	public int hours = 0;
	public int min = 0;
	public int sec = 0;
	public boolean started = false;
	public Timer(){};
	public Timer(int hours) {
		h = hours;
	}
	public void setHours(int h){
		this.h = h;
	}
	public void start(){
		if(finish == 0)
			finish = hoursToMillis(h) + System.currentTimeMillis();
		started = true;
	}
	public void restart(long time){
		finish = time + System.currentTimeMillis();
		started = true;
	}
	
	private long hoursToMillis(int hours){
		//minutes
		long i = hours * 60L;
		//seconds
		i *= 60L;
		//milliseconds
		i *= 1000L;
		return i;
	}
	
	private long minutesToMillis(int minutes){
		//seconds
		long i = minutes *= 60L;
		//milliseconds
		i *= 1000L;
		return i;
	}
	
	private long secondsToMillis(int seconds){
		//milliseconds
		long i = seconds *= 1000L;
		return i;
	}
	public void setHoursByString(String time) throws NumberFormatException{
		String[] s = time.split(":");
		String err = "Time in the wrong format: " + time + "\nTry this format: <hours>:<minutes>:<seconds>";
		switch(s.length){
			case(0):
				throw new NumberFormatException(err);
			case(1):
				throw new NumberFormatException(err);
			case(2):
				throw new NumberFormatException(err);
			case(3):
				int h = Integer.parseInt(s[0]);
				int min = Integer.parseInt(s[1]);
				int sec = Integer.parseInt(s[2]);
				finish = System.currentTimeMillis() + hoursToMillis(h) + minutesToMillis(min) + secondsToMillis(sec);
		}
	}
	public String calcRemainingTime(){
		long timeDif = finish - System.currentTimeMillis();
		if(timeDif <= 0){
			return "00:00:00";
		}
		getTime(timeDif);
		String ho = (""+hours).length() == 1 ? "0"+hours : ""+hours;
		String mi = (""+min).length() == 1 ? "0"+min : ""+min;
		String se = (""+sec).length() == 1 ? "0"+sec : ""+sec;
		return ho +":" +mi +":" + se;
	}
	public int getRemainingSeconds(){
		int timeDif = (int) (finish - System.currentTimeMillis());
		return (int) (timeDif / 1000.0);
	}
	public long getRemainingTime(){
		return (finish - System.currentTimeMillis());
		
	}
	private void getTime(long millis){
		//seconds
		double m = millis / 1000.0;
		//minutes
		m /= 60;
		//hours
		hours = (int) m / 60;
		double minutes = m % 60;
		min = (int) minutes;
		sec = (int ) ((minutes - min) * 60);
	}
	
	
	
}
