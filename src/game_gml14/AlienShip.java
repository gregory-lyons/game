package game_gml14;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class AlienShip extends Rectangle{

	private Hitbox hitBox;
	
	public AlienShip(){
		super(-20, Math.random()*500,60,60);
		this.setFill(Color.GREEN);
		hitBox = new Hitbox(this.getX()+30, this.getY()+30, 30);
	}
	
	public void updateHitbox(){
		hitBox.setCenterX(this.getX()+30);
		hitBox.setCenterY(this.getY()+30);
	}
	
	public Hitbox getHitbox(){
		return hitBox;
	}
	
	
}