package game_gml14;


import game_gml14.GameLoop;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * 
 * @author Gregory Lyons (netID: gml14)
 * 
 * This is the main class for the game.  The GameLoop class has most of the game details.
 *
 */
public class Voyage extends Application {

	private GameLoop myGame;
	
	@Override
	public void start(Stage s) throws Exception {
		s.setTitle("Voyage to Venus");
		myGame = new GameLoop();
		Scene scene = myGame.init(s, 800, 500);
		s.setScene(scene);
		s.show();

		KeyFrame frame = myGame.start();
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	

}
