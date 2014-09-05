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


/**
 * 
 * 
 * @author Gregory Lyons (netID: gml14)
 *
 *
 *This game works by managing states and updating based on the states.
 *The ReadMe and the in-game instructions contain valuable data on how the gameplay works.
 */

public class GameLoop {

	private Scene scene;
	private Group root;
	private ImageView imageView;
	private PlayerShip myShip;

	private ArrayList<Meteor> meteors;
	private ArrayList<Circle> bullets;
	private ArrayList<AlienShip> aliens;
	private ArrayList<Circle> alienBullets;
	private ArrayList<Circle> starList;
	private ArrayList<Rectangle> ammo;

	private int timer;
	private int score;
	private int state;   //0 = start, 1 = level 1, -1 = level 2, 2 = mid-level, 3 = pause, 4 = endgame
	private int prevState; //save the previous state (used for pausing)
	private int imageState;  //used for switching images in beginning

	private Text scoreText;
	private Text pauseText;

	private KeyPressedHandler keyHandler;  //used for key presses
	private KeyReleasedHandler keyHandler2;  //used for key releases
	private KeyCode code;

	private boolean godMode;  //Holding SHIFT key freezes all enemies (but also freezes score)


	//handler based on state
	private EventHandler<ActionEvent> oneFrame = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent evt) {

			//STATE 0:  PRE-GAME SPLASH SCREENS
			if(state == 0) {

				if (code == KeyCode.SPACE) {
					imageState++;
					if (imageState == 1) {
						nextImage("images/voyage2.png");
					}
					if (imageState == 2)
						nextImage("images/voyage3.png");
				}
				if (code == KeyCode.ENTER) {
					state = 1;  //set state to Level 1
					startGame(); //begin game
				}
			}


			//STATE 1: LEVEL 1
			if (state == 1) {
				update(code);
				if (score==162) {
					state = 2;  //advance to mid-level splash screen
					nextImage("images/venus.png");
				}
			}

			//STATE 2: MID-LEVEL SPLASH SCREEN
			if (state == 2) {
				if (code == KeyCode.ENTER) {
					state = -1;  //advance to level 2
					startGame();
				}
			}

			//STATE -1: LEVEL 2
			/*NOTE: 
			 * "-1" is used for the state of Level 2 because it makes it easy to share methods between Levels 1 and 2.
			 * Using "-1" and "1" for the two game states makes it easy to switch directions
			 */
			if (state == -1) {
				update(code);
				if (score==162){
					state = 4; //advance to endgame stage
					nextImage("images/earth.png");
				}
			}


			//STATE 3: PAUSE SCREEN
			if (state == 3) {
				if (code == KeyCode.ENTER) {
					resume();
				}
				else pause();
			}

			//STATE 4: FINISHED GAME
			if (state == 4) {
			}

			code = null;  //reset keyCode to null to avoid looping the same key
		}
	};

//=========INITIAL METHODS=====================
	
	//Initialize the conditions necessary to run the game
	public Scene init (Stage s, int width, int height) {
		root = new Group();
		scene = new Scene(root, width, height, Color.BLACK);
		keyHandler = new KeyPressedHandler(this);
		keyHandler2 = new KeyReleasedHandler(this);
		scene.addEventHandler(KeyEvent.KEY_PRESSED, keyHandler);
		scene.addEventHandler(KeyEvent.KEY_RELEASED, keyHandler2);
		state = 0;
		imageState = 0;
		nextImage("images/voyage1.png");
		return scene;
	}

	//set up data structures and other necessary details
	public void startGame() {
		meteors = new ArrayList<Meteor>();
		bullets = new ArrayList<Circle>();
		ammo = new ArrayList<Rectangle>();
		aliens = new ArrayList<AlienShip>();
		alienBullets = new ArrayList<Circle>();
		starList = new ArrayList<Circle>();
		root.getChildren().clear();
		timer = 0;
		score = 0;
		godMode = false;
		myShip = new PlayerShip(state);
		root.getChildren().add(myShip);
		resetAmmo();
		pauseText = new Text(275, 200, "Press [ENTER] to resume");
		pauseText.setFont(Font.font("Arial Rounded MT Bold", 20));
		pauseText.setFill(Color.WHITE);
	}
	
	public KeyFrame start () {
		return new KeyFrame(Duration.millis(1000/60), oneFrame);
	}

	//Method is accessed by KeyPressedHandler
	public void getKeyCode(KeyCode k) {
		code = k;
	}

	//Set up the imageView based on a given filepath
	private void nextImage(String s) {
		root.getChildren().clear();
		Image image = new Image(getClass().getResourceAsStream(s));
		imageView = new ImageView();
		imageView.setImage(image);
		imageView.setFitWidth(600);
		imageView.setFitHeight(500);
		imageView.setPreserveRatio(true);
		imageView.setX(100);
		root.getChildren().add(imageView);

	}


//=========UPDATE METHODS==================================
	
	//main update method run by the handler
	private void update(KeyCode kc) {
		timer++;
		checkKeys(kc);
		updateBullets();
		checkShipCollisions();
		checkBulletCollisions();
		updateStars();

		if (!godMode) { //godMode freezes enemies and score
			updateScore();
			updateMeteors();
			if (state == 1) {
				if (meteors.size()<=10 && timer%30==0)
					newMeteor();
			}

			if (state == -1) {
				if (meteors.size()<=4 && timer%240==0)
						newMeteor();
				updateAliensandAlienBullets();
				if (timer%120==0)
					newAlienShip();
			}
		}
	}
	
	//updates meteor positions
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

	//updates bullet positions
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

	//updates aliens and alien bullet positions
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

	//updates the score
	private void updateScore(){
		if (timer%25==0) {
			score++;
			if (scoreText != null) {
				root.getChildren().remove(scoreText);
			}
			scoreText = new Text(30,40, String.valueOf(score));
			scoreText.setFill(Color.WHITE);
			root.getChildren().add(scoreText);
		}
	}
	
	//updates the background stars' movement
	private void updateStars() {
		if (timer%15==0){
			Circle newStar = new Circle(400*state+400, Math.random()*500, 2, Color.WHITE);
			newStar.setOpacity(0.5);
			starList.add(newStar);
			root.getChildren().add(newStar);
		}
		for (Circle s : starList) {
			s.setCenterX(s.getCenterX()-8*state);
			if (s.getCenterX()>820 || s.getCenterX()<-20) {
				starList.remove(s);
				root.getChildren().remove(s);
				return;
			}
		}
	}

	//works with key-handler to deal with key presses
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
			prevState = state;  //save current state before pausing
			state = 3;
		}
	}

//=========CHECK COLLISIONS===============================

	//check for objects colliding with the player ship
	private void checkShipCollisions() {
		boolean meteorCollision = false;
		for (Meteor m : meteors) {
			if (distance(m.getCenterX(), m.getCenterY(), myShip.getX()+15, myShip.getY()+15) < m.getRadius()+15) {
				meteorCollision = true;
			}
		}
		boolean alienBulletCollision = false;
		for (Circle b : alienBullets) {
			if (distance(b.getCenterX(), b.getCenterY(), myShip.getX()+15, myShip.getY()+15) < b.getRadius()+20) {
				alienBulletCollision = true;
			}
		}
		boolean alienCollision = false;
		for (AlienShip a : aliens) {
			if (distance(a.getX()+30, a.getY()+30, myShip.getX()+15, myShip.getY()+15) < 50){
				alienCollision = true;
			}
		}
		if (meteorCollision || alienBulletCollision || alienCollision) {
			startGame();
			return;
		}
	}
	
	//helper method used for checking collisions
	private double distance(double x1, double y1, double x2, double y2) {
		double d = Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
		return d;

	}
	
	//check for objects colliding with bullets
	private void checkBulletCollisions() {
		for (Circle b : bullets) {
			for (Meteor m : meteors) {
				if (distance(m.getCenterX(), m.getCenterY(), b.getCenterX(), b.getCenterY()) < m.getRadius()+3) {
					if (m.getRadius()<=45) {
						root.getChildren().remove(m);
						meteors.remove(m);
					}
					else {
						m.setRadius(m.getRadius()-40);  //make big meteors smaller
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
	
//=======CREATE NEW OBJECTS==================

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

	
	
//=========CONTROLS========================

	//Pause the game
	private void pause() {
		if (!root.getChildren().contains(pauseText))
			root.getChildren().add(pauseText);
	}
	
	//Resume the game
	private void resume() {
		root.getChildren().remove(pauseText);
		state = prevState;
	}	
	
	//reset ammo capacity
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
	
	//============CHEATS==========================
	
	//Direct skip to level 2
	public void skipLevel() {
		state = -1;
		startGame();
	}

	//Freezes enemies (but also freezes score)
	public void setGodMode(boolean b) {
		godMode = b;
	}



}
