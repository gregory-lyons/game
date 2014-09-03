package game_gml14;


import game_gml14.GameLoop;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Voyage extends Application {

	private GameLoop myGame;
	
	@Override
	public void start(Stage s) throws Exception {
		s.setTitle("Voyage to Venus");
		// create your own game here
		myGame = new GameLoop();
		// attach game to the stage and display it
		Scene scene = myGame.init(s, 800, 500);
		s.setScene(scene);
		s.show();

		// sets the game's loop 
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
