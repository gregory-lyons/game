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
			this.setFill(Color.BLUE);
			setSpeed(Math.random()*2);
		}
		else{
			this.setRadius(Math.random()*40+20);
			setSpeed(Math.random()*4);
			this.setFill(Color.SIENNA);
		}

	}
	
	public void setSpeed(double i) {
		speed = i;
	}
	public double getSpeed() {
		return speed;
	}
	
}
