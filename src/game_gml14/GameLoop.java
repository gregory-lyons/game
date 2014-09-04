package game_gml14;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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
	private ImageView imageView;
	private Scene scene;
	private int timer;
	private int score;
	private ArrayList<Rectangle> ammo;
	private Text scoreText;
	private Text pauseText1;
	private Text pauseText2;
	private boolean paused;
	private boolean newGame;
	private boolean level2;
	private KeyPressedHandler keyHandler;
	private KeyCode code;
	private int state;   //0 = start, 1 = level 1, 2 = level 2, pause = 3


    private EventHandler<ActionEvent> oneFrame = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent evt) {
			if(state == 0) {
				if (code == KeyCode.ENTER) {
					state++;
					startGame();
				}
			}
			if (state == 1) {
				updateLevel1(code);
				if (score==30) {
					state++;
					resetGame();
				}
			}
			if (state == 2) {
				updateLevel2(code);
				if (score==162){
					state++;
				}
			}
			
			if (state == 3) {
				
			}
			
			if (state == 4) {
				if (code == KeyCode.ENTER) {
					resume();
				}
			}
			
			code = null;		
		}
	};

	public Scene init (Stage s, int width, int height) {
		state = 0;
		root = new Group();
		scene = new Scene(root, width, height, Color.BLACK);
		keyHandler = new KeyPressedHandler(this);
		scene.addEventHandler(KeyEvent.KEY_PRESSED, keyHandler);
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
	
	public void getKeyCode(KeyCode k) {
		code = k;
	}
	
	public void pause() {
		paused = true;
		pauseText1 = new Text(230, 150, "PAUSED");
		pauseText1.setFont(Font.font("Arial Rounded MT Bold", 80));
		pauseText1.setFill(Color.WHITE);
		root.getChildren().add(pauseText1);
		pauseText2 = new Text(275, 200, "Press [ENTER] to resume");
		pauseText2.setFont(Font.font("Arial Rounded MT Bold", 20));
		pauseText2.setFill(Color.WHITE);
		root.getChildren().add(pauseText2);
	}
	
	public void resume() {
		paused = false;
		if (root.getChildren().contains(pauseText1)&&root.getChildren().contains(pauseText2)){
			root.getChildren().remove(pauseText1);
			root.getChildren().remove(pauseText2);
		}
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
	
	private void updateLevel1(KeyCode kc){
		timer++;
		updateMeteors();
		updateBullets();
		updateAliens();
		updateAlienBullets();
		if (timer%120==0)
			newAlienShip();
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
		
		if (kc == KeyCode.UP)
			myShip.moveUp();
		if (kc == KeyCode.DOWN)
			myShip.moveDown();
		if (kc == KeyCode.SPACE)
			newBullet();
		if (kc == KeyCode.R)
			resetAmmo();
	}
	
	private void updateLevel2(KeyCode kc){
		timer++;
		updateMeteors();
		updateBullets();
		updateAliens();
		updateAlienBullets();
		if (timer%120==0)
			newAlienShip();
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
		
		if (kc == KeyCode.UP)
			myShip.moveUp();
		if (kc == KeyCode.DOWN)
			myShip.moveDown();
		if (kc == KeyCode.SPACE)
			newBullet();
		if (kc == KeyCode.R)
			resetAmmo();
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
			b.setCenterX(b.getCenterX()-5);
			if (b.getCenterX() <= -15) {
				root.getChildren().remove(b);
				alienBullets.remove(b);
				return;
			}
		}
	}
	
	private void updateAliens() {
		for (AlienShip a : aliens) {
			a.setX(a.getX()-2);
			if (a.getX() <= 0) {
				root.getChildren().remove(a);
				aliens.remove(a);
				return;
			}
			if (myShip.getY()>a.getY()) {
				a.setY(a.getY()+0.8);
				continue;
			}
			else if (myShip.getY()<a.getY())
				a.setY(a.getY()-0.8);
		}
	}
	
	
	private void resetGame() {
		root.getChildren().clear();
		meteors.clear();
		bullets.clear();
		alienBullets.clear();
		aliens.clear();
		myShip = new PlayerShip();
		root.getChildren().add(myShip);
		timer = 0;
		score = 0;
		resetAmmo();
	}
	private void checkShipCollisions() {
		for (Meteor m : meteors) {
			double distance = Math.sqrt(
								Math.pow(m.getCenterX()-myShip.getX()+15,2)
								+Math.pow(m.getCenterY()-myShip.getY()-15,2));
			if (distance < m.getRadius()+15) {
				resetGame();
				return;
			}
		}
		for (Circle b : alienBullets) {
			double distance = Math.sqrt(
					Math.pow(b.getCenterX()-myShip.getX()+15,2)
					+Math.pow(b.getCenterY()-myShip.getY()-15,2));
			if (distance < b.getRadius()+20) {
				resetGame();
				return;
			}
		}
		
		for (AlienShip a : aliens) {
			double distance = Math.sqrt(
					Math.pow(a.getX()+30-myShip.getX()+15,2)
					+Math.pow(a.getY()+30-myShip.getY()-15,2));
			if (distance < 50) {
				resetGame();
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
			for (AlienShip a : aliens) {
				double distance = Math.sqrt(
					Math.pow(a.getX()+30-b.getCenterX(),2)
					+Math.pow(a.getY()+30-b.getCenterY(),2));
				if (distance < 30) {
					root.getChildren().remove(a);
					aliens.remove(a);
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
