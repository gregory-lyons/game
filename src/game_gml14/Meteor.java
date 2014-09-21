// This entire file is part of my masterpiece.
// GREG LYONS

package game_gml14;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Meteor extends Circle {
	
	private double speed;
	private Hitbox hitBox;
	
	public Meteor(int i) {

		this.setCenterX(420*i+400);  //820 for level 1, -20 for level 2
		this.setCenterY(Math.random()*500);
		this.setFill(Color.BROWN);
		
		//Big, fast meteors for level 1
		if (i == 1){
			this.setRadius(Math.random()*50+30);
			this.setSpeed(4);
		}
		
		//Smaller, slower meteors for level 2
		else{
			this.setRadius(Math.random()*40+20);
			this.setSpeed(3);
		}
		hitBox = new Hitbox(this.getCenterX(), this.getCenterY(), this.getRadius());

	}
	
	public void setSpeed(double i) {
		speed = i;
	}
	public double getSpeed() {
		return speed;
	}
	
	//Set the Hitbox to the current location of the meteor
	public void updateHitbox(){
		hitBox.setCenterX(this.getCenterX());
		hitBox.setCenterY(this.getCenterY());
		hitBox.setRadius(this.getRadius());
	}
	
	public Hitbox getHitbox(){
		return hitBox;
	}
	
}
