package game_gml14;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyPressedHandler implements EventHandler<KeyEvent> {
    private GameLoop game;

    public KeyPressedHandler(GameLoop game) {
        this.game = game;
    }

    @Override
    public void handle(KeyEvent keyEvent) {	
    	if (keyEvent.getCode() != null)
    		game.getKeyCode(keyEvent.getCode());
    }
}
