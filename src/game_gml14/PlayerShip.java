// This entire file is part of my masterpiece.
// GREG LYONS

package game_gml14;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PlayerShip extends Rectangle{

	//All of the +15 values come from the fact that the Rectangle (30x30) class uses the top left corner as its X and Y coordinates,
	//rather than the center (as the Circle class does).  To use the center coordinates, add 15 each to the top left X and Y coordinates

	private Hitbox hitBox;

	//Takes state as a parameter
	public PlayerShip(int i){
		//x-coordinate value gives 30 for level 1, 740 for level 2
		super(-355*i+385,250,30,30);
		this.setFill(Color.CORNFLOWERBLUE);
		hitBox = new Hitbox(this.getX()+15, this.getY()+15, 15);
	}
	
	public void moveUp(){
		if (this.getY()<10) {
			this.setY(470);
		}
		else this.setY(this.getY()-20);
	}
	
	public void moveDown(){
		if (this.getY()-15>450)
			this.setY(0);
		else this.setY(this.getY()+20);
	}
	
	//Set the Hitbox to the current location of the ship
	public void updateHitbox(){
		hitBox.setCenterX(this.getX()+15);
		hitBox.setCenterY(this.getY()+15);
	}
	
	public Hitbox getHitbox(){
		return hitBox;
	}
	
}
