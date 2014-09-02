package game_gml14;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameLoop {
	
	private PlayerShip myShip;
	private int numMeteors;
	//private Circle newMeteor;
	Group root;
	ArrayList<Circle> list;
	Scene scene;
	int timer;

	/**
	 *  Function to do each game frame
	 */
    private EventHandler<ActionEvent> oneFrame = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent evt) {
			timer++;
			updateSprites();
			if (list.size()<=10 && timer%30==0)
				randomMeteor(true);
		}
	};

	
	/**
	 * Create the game's scene
	 */
	public Scene init (Stage s, int width, int height) {
		list = new ArrayList<Circle>();
		root = new Group();
		scene = new Scene(root, width, height, Color.WHITE);
		timer = 0;
		myShip = new PlayerShip();
		root.getChildren().add(myShip);
		return scene;

	}

	/**
	 * Create the game's frame
	 */
	public KeyFrame start () {
		return new KeyFrame(Duration.millis(1000/60), oneFrame);
	}
	
	//create random meteors
	//boolean isBig refers to meteor type: big or small
	private void randomMeteor(boolean isBig) {
		Meteor newMeteor = new Meteor(isBig);
		root.getChildren().add(newMeteor);
		list.add(newMeteor);
			
	}
	private void updateSprites () {
		for (int i = 0; i<list.size(); i++) {
			list.get(i).setCenterX(list.get(i).getCenterX()-5);
		}
		for (int i = 0; i<list.size(); i++) {
			if (list.get(i).getCenterX() <= -90) {
				root.getChildren().remove(list.get(i));
				list.remove(list.get(i));
			}
		}
	}

}
