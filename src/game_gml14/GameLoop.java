package game_gml14;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
	private ArrayList<Circle> starList;
	private ImageView imageView;
	private Scene scene;
	private int timer;
	private int score;
	private ArrayList<Rectangle> ammo;
	private Text scoreText;
	private Text pauseText2;

	private KeyPressedHandler keyHandler;
	private KeyCode code;
	private boolean godMode;
	private int state;   //0 = start, 1 = level 1, -1 = level 2, mid-level = 2, pause = 3, endgame = 4
	private int prevState; //save the previous state
	private int imageState;

    private EventHandler<ActionEvent> oneFrame = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent evt) {
			if(state == 0) {
				if (code == KeyCode.SPACE) {
					imageState++;
					if (imageState == 1) {
						nextImage("images/voyage2.png");
						code = null;
					}
					if (imageState == 2)
						nextImage("images/voyage3.png");
				}
				if (code == KeyCode.ENTER) {
					state = 1;
					startGame();
				}
			}
			if (state == 1) {
				update(code);
				if (score==40) {
					state = 2;
					nextImage("images/venus.png");
				}
			}
			if (state == -1) {
				update(code);
				if (score==40){
					state = 4;
					nextImage("images/earth.png");
				}
			}
			
			if (state == 2) {
				if (code == KeyCode.ENTER) {
					state = -1;
					resetGame();
				}
			}
			
			if (state == 3) {
				if (code == KeyCode.ENTER) {
					resume();
				}
				else pause();
			}
			if (state == 4) {
				
			}
			code = null;
		}
	};

	public Scene init (Stage s, int width, int height) {
	
		root = new Group();
		scene = new Scene(root, width, height, Color.BLACK);
		keyHandler = new KeyPressedHandler(this);
		scene.addEventHandler(KeyEvent.KEY_PRESSED, keyHandler);
		state = 0;
		imageState = 0;
		nextImage("images/voyage1.png");
		return scene;
	}
	
	public void getKeyCode(KeyCode k) {
		code = k;
	}
	
	private void nextImage(String s) {
		root.getChildren().clear();
		Image image = new Image(getClass().getResourceAsStream(s));
		imageView = new ImageView();
		imageView.setImage(image);
		imageView.setFitWidth(800);
		imageView.setFitHeight(500);
		imageView.setPreserveRatio(true);
		root.getChildren().add(imageView);
		
	}
	
	private void pause() {
		if (!root.getChildren().contains(pauseText2))
			root.getChildren().add(pauseText2);
	}
	
	private void resume() {
			root.getChildren().remove(pauseText2);
			state = prevState;
	}
	public void startGame() {
		meteors = new ArrayList<Meteor>();
		bullets = new ArrayList<Circle>();
		ammo = new ArrayList<Rectangle>();
		aliens = new ArrayList<AlienShip>();
		alienBullets = new ArrayList<Circle>();
		starList = new ArrayList<Circle>();
		root.getChildren().clear();
		timer = 0;
		myShip = new PlayerShip(state);
		root.getChildren().add(myShip);
		resetAmmo();
		pauseText2 = new Text(275, 200, "Press [ENTER] to resume");
		pauseText2.setFont(Font.font("Arial Rounded MT Bold", 20));
		pauseText2.setFill(Color.WHITE);
	}
	
	private void update(KeyCode kc) {
		timer++;
		updateBullets();
		updateScore();
		checkShipCollisions();
		checkBulletCollisions();
		checkKeys(kc);
		if (state == 1) {
			stars();
			updateMeteors();
			if (meteors.size()<=10 && timer%30==0)
				newMeteor();
		}
		if (state == -1) {
			updateAliensandAlienBullets();
			if (timer%120==0)
				newAlienShip();
		}
	}
	
	private void updateScore(){
		if (timer%20==0) {
			score++;
			if (scoreText != null) {
				root.getChildren().remove(scoreText);
			}
			scoreText = new Text(30,40, String.valueOf(score));
			scoreText.setFill(Color.WHITE);
			root.getChildren().add(scoreText);
		}
	}
	
	private void checkKeys(KeyCode k) {
		if (k == KeyCode.UP)
			myShip.moveUp();
		if (k == KeyCode.DOWN)
			myShip.moveDown();
		if (k == KeyCode.SPACE)
			newBullet();
		if (k == KeyCode.R)
			resetAmmo();
		if (k == KeyCode.ESCAPE) {
			prevState = state;
			state = 3;
		}
	}

	public KeyFrame start () {
		return new KeyFrame(Duration.millis(1000/60), oneFrame);
	}
	
	
	private void newMeteor() {
		Meteor newMeteor = new Meteor(state);
		root.getChildren().add(newMeteor);
		meteors.add(newMeteor);
	}
	
	private void newAlienShip(){
		AlienShip newAlien = new AlienShip();
		root.getChildren().add(newAlien);
		aliens.add(newAlien);
	}
	
	private void newBullet() {
		if (ammo.size()>0) {
			Rectangle last = ammo.get(ammo.size()-1);
			root.getChildren().remove(last);
			ammo.remove(last);
			Circle newBullet = new Circle(myShip.getX()+30, myShip.getY()+15, 10, Color.YELLOW);
			bullets.add(newBullet);
			root.getChildren().add(newBullet);
		}
	}
	
	private void newAlienBullet(AlienShip a){
		Circle alienBullet = new Circle(a.getX()+30, a.getY()+30, 5, Color.ORANGE);
		root.getChildren().add(alienBullet);
		alienBullets.add(alienBullet);
	}
	
	private void updateMeteors() {
		for (Meteor m: meteors) {
			m.setCenterX(m.getCenterX()-state*m.getSpeed());
			if (m.getCenterX() <= -90 || m.getCenterX() >= 830 || m.getRadius()<=5) {
				root.getChildren().remove(m);
				meteors.remove(m);
				return;
			}
		}
	}
	
	private void updateBullets() {
		for (Circle b : bullets) {
			b.setCenterX(b.getCenterX()+state*10);
			if (b.getCenterX() >= 800 || b.getCenterX() <= 0) {
				root.getChildren().remove(b);
				bullets.remove(b);
				return;
			}
		}
	}
	
	private void updateAliensandAlienBullets() {
		for (AlienShip a : aliens) {
			if (timer%80 == 0) 
				newAlienBullet(a);
			a.setX(a.getX()+2);
			if (a.getX() >=830) {
				root.getChildren().remove(a);
				aliens.remove(a);
				return;
			}
			if (myShip.getY()>a.getY()) {
				a.setY(a.getY()+0.8);
			}
			else if (myShip.getY()<a.getY())
				a.setY(a.getY()-0.8);
		}
		for (Circle b : alienBullets) {
			b.setCenterX(b.getCenterX()+5);
			if (b.getCenterX() >= 800) {
				root.getChildren().remove(b);
				alienBullets.remove(b);
				return;
			}
		}
	}
	
	private void stars() {
		if (timer%15==0){
			Circle newStar = new Circle(420+state*400, Math.random()*500, 2, Color.WHITE);
			newStar.setOpacity(0.5);
			starList.add(newStar);
			root.getChildren().add(newStar);
		}
		for (Circle s : starList) {
			s.setCenterX(s.getCenterX()-8);
			if (s.getCenterX()<0) {
				starList.remove(s);
				root.getChildren().remove(s);
				return;
			}
		}
	}
	
	private void resetGame() {
		root.getChildren().clear();
		meteors.clear();
		bullets.clear();
		alienBullets.clear();
		aliens.clear();
		myShip = new PlayerShip(state);
		root.getChildren().add(myShip);
		timer = 0;
		score = 0;
		resetAmmo();
	}
	private void checkShipCollisions() {
		for (Meteor m : meteors) {
			double distance = Math.sqrt(
								Math.pow(m.getCenterX()-(myShip.getX()+15),2)
								+Math.pow(m.getCenterY()-(myShip.getY()+15),2));
			if (distance < m.getRadius()+15) {
				resetGame();
				return;
			}
		}
		for (Circle b : alienBullets) {
			double distance = Math.sqrt(
					Math.pow(b.getCenterX()-(myShip.getX()+15),2)
					+Math.pow(b.getCenterY()-(myShip.getY()+15),2));
			if (distance < b.getRadius()+20) {
				resetGame();
				return;
			}
		}
		for (AlienShip a : aliens) {
			double distance = Math.sqrt(
					Math.pow(a.getX()+30-(myShip.getX()+15),2)
					+Math.pow(a.getY()+30-(myShip.getY()+15),2));
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
