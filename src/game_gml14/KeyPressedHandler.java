package game_gml14;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyPressedHandler implements EventHandler<KeyEvent> {
    private final GameLoop game;

    public KeyPressedHandler(GameLoop game) {
        this.game = game;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
    	
    	if (keyEvent.getCode() == KeyCode.UP) {
    		game.moveUp();
    	}
    	
        if (keyEvent.getCode() == KeyCode.DOWN) {
            game.moveDown();
        }

        if (keyEvent.getCode() == KeyCode.SPACE) {
        	game.newBullet();
        }
        
        if (keyEvent.getCode() == KeyCode.R) {
        	game.resetAmmo();
        }
    }
}
