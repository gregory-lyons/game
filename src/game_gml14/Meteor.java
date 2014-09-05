package game_gml14;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Meteor extends Circle {
	
	private double speed;
	
	public Meteor(int i) {

		this.setCenterX(420*i+400);  //820 for level 1, -20 for level 2
		this.setCenterY(Math.random()*500);
		
		if (i == 1){

			this.setRadius(Math.random()*50+30);
			this.setFill(Color.BROWN);
			setSpeed(4);
		}
		else{
			this.setRadius(Math.random()*40+20);
			setSpeed(3);
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
