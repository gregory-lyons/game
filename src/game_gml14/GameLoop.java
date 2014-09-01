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
		// Create a scene graph to organize the scene
		list = new ArrayList<Circle>();
		root = new Group();
		// Create a place to see the shapes
		scene = new Scene(root, width, height, Color.WHITE);
		timer = 0;
		// Make some shapes and set their properties
       /// Image image = new Image(getClass().getResourceAsStream("images/duke.gif"));
       // imageView = new ImageView();
       // imageView.setImage(image);
	//	myBall = new Circle(scene.getWidth() / 2, scene.getHeight() / 2, 60);
		//myBall.setFill(Color.RED);
		
		// remember shapes for viewing later
		//root.getChildren().add(myBall);
		//root.getChildren().add(imageView);
		/*newMeteor = new Meteor();
		newMeteor.setFill(Color.BROWN);
		newMeteor.setCenterX(scene.getWidth());
		newMeteor.setCenterY(scene.getHeight()/2);
		newMeteor.setRadius(30);
		root.getChildren().add(newMeteor);*/
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
		if (isBig) {
			Circle newMeteor = new Circle();
			newMeteor.setFill(Color.BLUE);
			newMeteor.setCenterX(scene.getWidth()+30);
			newMeteor.setCenterY(Math.random()*500);
			newMeteor.setRadius(Math.random()*80);
			root.getChildren().add(newMeteor);
			list.add(newMeteor);
		}
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
