package game_gml14;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PlayerShip extends Rectangle{

	public PlayerShip(int i){
		this.setX(-355*i+385); //30 for level 1, 740 for level 2?
		this.setY(250);
		this.setHeight(30);
		this.setWidth(30);
		this.setFill(Color.RED);
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
	
}
