package game_gml14;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class AlienShip extends Rectangle{

	public AlienShip(){
		this.setX(820);
		this.setY(Math.random()*500);
		this.setHeight(60);
		this.setWidth(60);
		this.setFill(Color.GREEN);
	}
	
}