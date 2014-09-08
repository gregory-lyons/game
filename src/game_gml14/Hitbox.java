// This entire file is part of my masterpiece.
// GREG LYONS

package game_gml14;

import javafx.scene.shape.Circle;

public class Hitbox extends Circle {
	
	//Create the Hitbox using the Circle constructor
	public Hitbox(double x, double y, double r){
		super(x, y, r);
	}
	
	//Check for a collision with another Hitbox
	//Parameters: Another Hitbox object h
	//Return: true if collision, false if no collision
	public boolean getCollision(Hitbox h) {
		double d = Math.sqrt(Math.pow(this.getCenterX()-h.getCenterX(),2)+Math.pow(this.getCenterY()-h.getCenterY(),2));
		return (d < (this.getRadius() + h.getRadius()));
	}
}

