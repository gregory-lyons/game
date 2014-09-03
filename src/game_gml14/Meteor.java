package game_gml14;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Meteor extends Circle {
	
	private double speed;
	
	public Meteor(boolean isBig) {

		this.setCenterX(820);
		this.setCenterY(Math.random()*500);
		
		if (isBig){

			this.setRadius(Math.random()*50+30);
			this.setFill(Color.BROWN);
			setSpeed(4);
		}
		else{
			this.setRadius(Math.random()*40+20);
			setSpeed(7);
			this.setFill(Color.BROWN);
		}

	}
	
	public void setSpeed(double i) {
		speed = i;
	}
	public double getSpeed() {
		return speed;
	}
	
}
