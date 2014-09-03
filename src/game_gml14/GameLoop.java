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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameLoop {
	
	private PlayerShip myShip;
	private Group root;
	private ArrayList<Meteor> meteors;
	private ArrayList<Circle> bullets;
	private ArrayList<AlienShip> aliens;
	private ArrayList<Circle> alienBullets;
	private Scene scene;
	private Scene scene2;
	private int timer;
	private int score;
	private ArrayList<Rectangle> ammo;
	private Text scoreText;
	private boolean paused;
	private boolean newGame;
	private boolean level2;


    private EventHandler<ActionEvent> oneFrame = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent evt) {
			if (!newGame && !level2) {
			timer++;
			updateMeteors();
			updateBullets();
			updateAliens();
			updateAlienBullets();
			//if (meteors.size()<=10 && timer%30==0)
			//	randomMeteor(false);
			
			if (timer%400==0) {
				newAlienShip();
			}
	
			if (timer%40==0) {
				score++;
				if (scoreText != null) {
					root.getChildren().remove(scoreText);
				}
				scoreText = new Text(30,40, String.valueOf(score));
				scoreText.setFill(Color.WHITE);
				root.getChildren().add(scoreText);
			}
			checkShipCollisions();
			checkBulletCollisions();
			/*if (score >=100) {
				level2 = true;
				newGame = true;
				startGame();
			}*/
			}
			if (level2) {
				timer++;
				updateMeteors();
				updateBullets();
				updateAliens();
				if (meteors.size()<=10 && timer%30==0)
					randomMeteor(true);
		
				if (timer%40==0) {
					score++;
					if (scoreText != null) {
						root.getChildren().remove(scoreText);
					}
					scoreText = new Text(30,40, String.valueOf(score));
					scoreText.setFill(Color.WHITE);
					root.getChildren().add(scoreText);
				}
				checkShipCollisions();
				checkBulletCollisions();
			}
		
		}
	};

	public Scene init (Stage s, int width, int height) {
		root = new Group();
		scene = new Scene(root, width, height, Color.BLACK);
		scene.addEventHandler(KeyEvent.KEY_PRESSED, new KeyPressedHandler(this));
		/*Rectangle startGameBox = new Rectangle(150, 250, 500, 100);
		startGameBox.setFill(Color.YELLOW);
		root.getChildren().add(startGameBox);*/
		Text title = new Text(160, 100, "VOYAGE TO VENUS");
		title.setFont(Font.font("Arial Rounded MT Bold", 50));
		title.setFill(Color.YELLOW);
		root.getChildren().add(title);
		Text line1 = new Text(120, 300, "You must navigate to Venus (LEVEL 1), acquire a sample, and return home");
		line1.setFont(Font.font("Arial Rounded MT Bold", 12));
		line1.setFill(Color.YELLOW);
		root.getChildren().add(line1);
		newGame = true;
		level2 = false;
		return scene;
	}
	
	public void startGame() {
		meteors = new ArrayList<Meteor>();
		bullets = new ArrayList<Circle>();
		ammo = new ArrayList<Rectangle>();
		aliens = new ArrayList<AlienShip>();
		alienBullets = new ArrayList<Circle>();
		root.getChildren().clear();
		//scene.addEventHandler(KeyEvent.KEY_PRESSED, new KeyPressedHandler(this));
        //scene.addEventHandler(KeyEvent.KEY_RELEASED, new KeyReleasedHandler(this));
		timer = 0;
		//paused = false;
		myShip = new PlayerShip();
		root.getChildren().add(myShip);
		resetAmmo();
		newGame = false;
	}
	
	public void level2(int w, int h) {
		
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
	
	private void newAlienShip(){
		AlienShip newAlien = new AlienShip();
		root.getChildren().add(newAlien);
		aliens.add(newAlien);
	}
	
	public void newBullet() {
		if (ammo.size()>0) {
			Rectangle last = ammo.get(ammo.size()-1);
			root.getChildren().remove(last);
			ammo.remove(last);
			Circle newBullet = new Circle(myShip.getX()+30, myShip.getY()+15, 10, Color.YELLOW);
			bullets.add(newBullet);
			root.getChildren().add(newBullet);
		}
	}
	
	public void newAlienBullet(AlienShip a){
		Circle alienBullet = new Circle(a.getX()+30, a.getY()+30, 5, Color.ORANGE);
		root.getChildren().add(alienBullet);
		alienBullets.add(alienBullet);
	}
	
	private void updateMeteors () {
		for (Meteor m: meteors) {
			m.setCenterX(m.getCenterX()-m.getSpeed());
			if (m.getCenterX() <= -90 || m.getRadius()<=5) {
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
			if (b.getCenterX() >= 800) {
				root.getChildren().remove(b);
				bullets.remove(b);
				return;
			}
		}
	}
	
	private void updateAlienBullets() {
		for (AlienShip a : aliens) {
			if (timer%50 == 0) newAlienBullet(a);
		}
		for (Circle b : alienBullets) {
			b.setCenterX(b.getCenterX()-3);
			if (b.getCenterX() <= -15) {
				root.getChildren().remove(b);
				alienBullets.remove(b);
				return;
			}
		}
	}
	
	private void updateAliens() {
		for (AlienShip a : aliens) {
			a.setX(a.getX()-1);
			if (a.getX() <= 0) {
				root.getChildren().remove(a);
				aliens.remove(a);
				return;
			}
			if (myShip.getY()>a.getY()) {
				a.setY(a.getY()+0.5);
				continue;
			}
			else if (myShip.getY()<a.getY())
				a.setY(a.getY()-0.5);
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
				resetAmmo();
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
		for (Rectangle r : ammo) {
			root.getChildren().remove(r);
		}
		ammo.clear();
		for (int i=0;i<5;i++) {
			Rectangle newRect = new Rectangle(70+i*20, 30, 10, 20);
			newRect.setFill(Color.YELLOW);
			ammo.add(newRect);
			root.getChildren().add(newRect);
		}
	}

}
