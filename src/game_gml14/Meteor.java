package game_gml14;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Meteor extends Circle {
	
	private double speed;
	
	public Meteor(boolean isBig) {

		super.setCenterX(820);
		super.setCenterY(Math.random()*500);
		
		if (isBig){

			super.setRadius(Math.random()*50+30);
			super.setFill(Color.BLUE);
			setSpeed(Math.random()*2);
		}
		else{
			super.setRadius(Math.random()*40+20);
			setSpeed(Math.random()*4);
			super.setFill(Color.SIENNA);

		}
		
	}
	
	public void setSpeed(double i) {
		speed = i;
	}
	public double getSpeed() {
		return speed;
	}
}
