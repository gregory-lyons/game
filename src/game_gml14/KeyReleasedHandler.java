package game_gml14;


import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class KeyReleasedHandler implements EventHandler<KeyEvent> {
    private final GameLoop game;

    public KeyReleasedHandler(GameLoop game) {
        this.game = game;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        /*if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.DOWN) {
            game.player1Paddle.stopMovement();
        }*/
    }
}