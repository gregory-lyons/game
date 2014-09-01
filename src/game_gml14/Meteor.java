package game_gml14;

import javafx.scene.shape.Circle;

public class Meteor extends Circle {
	
	private int x;  //x coordinate
	private int y;  //y coordinate
	private int r;  //z coordinate
	private int s;  //speed
	
	public Meteor(int x_coord, int y_coord, int rad, int speed) {
		x = x_coord;
		y = y_coord;
		r = rad;
		s = speed;
	}
	
	public Meteor() {
		
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getRad() {
		return r;
	}
	public int getSpeed() {
		return s;
	}

	public void setX(int x1) {
		x=x1;
	}
	public void setY(int y1) {
		y=y1;
	}
	public void setRad(int r1) {
		r=r1;
	}
	public void setSpeed(int s1) {
		s=s1;
	}
	
}
