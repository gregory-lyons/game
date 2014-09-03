package game_gml14;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameLoop {
	
	private PlayerShip myShip;
	private Group root;
	private ArrayList<Meteor> meteors;
	private ArrayList<Circle> bullets;
	private Scene scene;
	private int timer;
	private int score;
	private ArrayList<Rectangle> ammo;
	private Text scoreText;


    private EventHandler<ActionEvent> oneFrame = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent evt) {
			timer++;
			updateMeteors();
			updateBullets();
			if (meteors.size()<=10 && timer%30==0)
				randomMeteor(true);
	
			if (timer%40==0) {
				score++;
				if (scoreText != null) {
					root.getChildren().remove(scoreText);
				}
				scoreText = new Text(30,40, String.valueOf(score));
				root.getChildren().add(scoreText);
			}
			checkShipCollisions();
			checkBulletCollisions();
		}
	};

	public Scene init (Stage s, int width, int height) {
		meteors = new ArrayList<Meteor>();
		bullets = new ArrayList<Circle>();
		ammo = new ArrayList<Rectangle>();
		root = new Group();
		scene = new Scene(root, width, height, Color.WHITE);
		scene.addEventHandler(KeyEvent.KEY_PRESSED, new KeyPressedHandler(this));
        scene.addEventHandler(KeyEvent.KEY_RELEASED, new KeyReleasedHandler(this));
		timer = 0;
		myShip = new PlayerShip();
		root.getChildren().add(myShip);
		resetAmmo();
		return scene;
	}

	public KeyFrame start () {
		return new KeyFrame(Duration.millis(1000/60), oneFrame);
	}
	
	//create random meteors
	//boolean isBig refers to meteor type: big or small
	private void randomMeteor(boolean isBig) {
		Meteor newMeteor = new Meteor(isBig);
		root.getChildren().add(newMeteor);
		meteors.add(newMeteor);
	}
	
	public void newBullet() {
		if (ammo.size()>0) {
			Rectangle last = ammo.get(ammo.size()-1);
			root.getChildren().remove(last);
			ammo.remove(last);
			Circle newBullet = new Circle(myShip.getX()+30, myShip.getY()+15, 10, Color.BLACK);
			bullets.add(newBullet);
			root.getChildren().add(newBullet);
		}
	}
	
	private void updateMeteors () {
		for (Meteor m: meteors) {
			m.setCenterX(m.getCenterX()-5);
			if (m.getCenterX() <= -90) {
				root.getChildren().remove(m);
				meteors.remove(m);
				return;
			}
		}
	}
	
	public void moveUp() {
		if (myShip.getY()<10) {
			myShip.setY(470);
		}
		else myShip.setY(myShip.getY()-20);
	}
	
	public void moveDown() {
		if (myShip.getY()-15>450)
			myShip.setY(0);
		else myShip.setY(myShip.getY()+20);

	}
	
	private void updateBullets() {
		for (Circle b : bullets) {
			b.setCenterX(b.getCenterX()+10);
			if (b.getCenterX() <= -90) {
				root.getChildren().remove(b);
				bullets.remove(b);
				return;
			}
		}
	}
	
	private void checkShipCollisions() {
		for (Meteor m : meteors) {
			double distance = Math.sqrt(
								Math.pow(m.getCenterX()-myShip.getX()+15,2)
								+Math.pow(m.getCenterY()-myShip.getY()-15,2));
			if (distance < m.getRadius()+15) {
				root.getChildren().clear();
				meteors.clear();
				myShip = new PlayerShip();
				root.getChildren().add(myShip);
				timer = 0;
				score = 0;
				return;
			}
		}
	}
	
	private void checkBulletCollisions() {
		for (Circle b : bullets) {
			for (Meteor m : meteors) {
				double distance = Math.sqrt(
					Math.pow(m.getCenterX()-b.getCenterX(),2)
					+Math.pow(m.getCenterY()-b.getCenterY(),2));
				if (distance < m.getRadius()+3) {
					if (m.getRadius()<=40) {
						root.getChildren().remove(m);
						meteors.remove(m);
					}
					else {
						m.setRadius(m.getRadius()-40);
					}
					root.getChildren().remove(b);
					bullets.remove(b);
				return;
			}
			}
		}
	}
	
	public void resetAmmo() {
		for (int i=0;i<5;i++) {
			Rectangle r = new Rectangle(70+i*20, 30, 10, 20);
			r.setFill(Color.BLACK);
			ammo.add(r);
			root.getChildren().add(r);
		}
	}

}
