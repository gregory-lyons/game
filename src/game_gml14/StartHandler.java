package game_gml14;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class StartHandler implements EventHandler<KeyEvent> {
    private final GameLoop game;
    
	public StartHandler(GameLoop game) {
        this.game = game;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
    	
    	if (keyEvent.getCode() == KeyCode.RIGHT) {
    		//game.nextScreen();
    	}
    	
    	if (keyEvent.getCode() == KeyCode.SPACE) {
    		game.startGame();
    	}

    }
}
