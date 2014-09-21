// This entire file is part of my masterpiece.
// GREG LYONS

package game_gml14;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bullet extends Circle {

	private Hitbox hitBox;
	
	public Bullet(double x, double y, double r, Color fill) {
		super(x, y, r, fill);
		hitBox = new Hitbox(this.getCenterX(), this.getCenterY(), this.getRadius());
	}
	
	//Set the hitbox to the current location of the bullet
	public void updateHitbox(){
		hitBox.setCenterX(this.getCenterX());
		hitBox.setCenterY(this.getCenterY());
	}
	
	public Hitbox getHitbox(){
		return hitBox;
	}
	
}
