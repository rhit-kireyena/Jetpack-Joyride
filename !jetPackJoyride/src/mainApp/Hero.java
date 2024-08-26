package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 * Class: Hero
 * @author W24_A304
 * <br>Purpose: Creates the main player-character of the game
 * <br>Restrictions: Requires level/game to be displayed in, keyboard listener to get inputs
 */
public class Hero {	
	//class constants
	static final int HERO_HEIGHT = 50;
	static final int HERO_WIDTH = 20;
	static final Color HERO_COLOR = Color.blue;
	static final int DEFAULT_HEALTH = 3;
	
	//instance variables
	private int coinsCollected = 0;
	private int score = 0;
	private int health;
	private int[] point;
	private int speedY;
	private int speedX;
	private int gravity;
	public boolean isFlying;
	private boolean endReached;
	
	
	public Hero(int health) {
		this.coinsCollected = 0;
		this.score = 0;
		this.health = health;
		point = new int[] {50, 550};
		this.speedY = 6;
		this.gravity = 4;
		this.speedX = 1;
		this.endReached = false;
		}
	public Hero() {
		this(DEFAULT_HEALTH);
	}
	
	public void restartGame(int health) {
		this.coinsCollected = 0;
		this.score = 0;
		this.health = health;
		point = new int[] {50, 550};
		this.speedY = 6;
		this.gravity = 4;
		this.speedX = 1;
		this.endReached = false;
	}
	
	/**
	 * moves the hero up
	 * @param dy-  the amount to move the character up
	 */
//	public void moveVertical(int dy) {
//		point[1] += -dy;
//		
//	}
	private void moveVertical() {
		point[1] += -speedY;
	}
	
	/**
	 * ensures: that gravity will exist
	 */
	private void applyGravity() {
		point[1] += gravity;
	}
	
	/**
	 * updates data about the Hero, including ensuring values are within bounds
	 * @param g2 - 2D graphics object
	 */
	public void update(Graphics2D g2) {
		if(isFlying == true) {
			moveVertical();
		} else {
			applyGravity();
		}
		calculateScore(coinsCollected);
		if(point[1] < 0) point[1] = 0;
		int verticalBounds = g2.getClipBounds().height - HERO_HEIGHT;
		if(point[1] > verticalBounds) point [1] = verticalBounds;
		speedY=6;
		gravity=4;
		speedX=1;
	}
	
	/**
	 * draws the hero and calls any methods to update its values
	 * @param g2 - 2D graphics object
	 */
	public void drawOn(Graphics2D g2) {
		update(g2);
		Rectangle playerShape = new Rectangle(point[0], point[1], HERO_WIDTH, HERO_HEIGHT);
		g2.setColor(HERO_COLOR);
		g2.fill(playerShape);
	}
	
	
	// Getters & Setters
	public int getSpeedY() {
		return speedY;
	}
	public int getSpeedX() {
		return speedX;
	}
	public int getY() {
		return point[1] + HERO_HEIGHT / 2;
	}
	public int getX() {
		return point[0] + HERO_WIDTH / 2;
	}
	
	public Rectangle getBoundingBox() {
		return new Rectangle(this.point[0],this.point[1],HERO_WIDTH,HERO_HEIGHT);
	}
	
	public int getWidth() {
		return HERO_WIDTH;
	}
	
	public int getHeight() {
		return HERO_HEIGHT;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public void setY(int num) {
		point[1] = num;
	}
	
	public void collideWithElectricBarrier(ElectricBarrier ebar) {

		this.loseLife();
	}
	public void collideWithRegularBarrier(RegularBarrier bar) {
		Shape barrierBoundingBox = bar.getTransformedRectangle();
		if(barrierBoundingBox.intersects(getX()-HERO_WIDTH/2, getY()-HERO_HEIGHT/2, HERO_WIDTH, HERO_HEIGHT)){
			speedX=0;
		}
		if(barrierBoundingBox.intersects(getX()-HERO_WIDTH/2, getY()-HERO_HEIGHT/2, HERO_WIDTH-1, 1)) {
			speedY=0;
			double barRotation = bar.getRotation();
			if(Math.tan(barRotation) < 0) {
				speedY = (int) -Math.abs(Math.sin(barRotation)*10);
				moveVertical();
				
			}
		}else if(barrierBoundingBox.intersects(getX()-HERO_WIDTH/2, getY()+HERO_HEIGHT/2, HERO_WIDTH-1, 1)) {
			gravity = 0;
			double barRotation = bar.getRotation();
			if(Math.tan(barRotation) > 0) {
				speedY = (int) Math.abs(Math.sin(barRotation)*10);
				moveVertical();
				
			}
				
		}
		
	}
	public void collideWith(Missile missile) {
		this.loseLife();
	}
	public void collideWithEnd() {
		this.endReached = true;
	}
	
	
	private void loseLife() {
		this.health--;
	}
	
	public boolean endReached() {
		return endReached;
	}
	
	
	public void resetPosition() {
		setY(550);
		this.endReached =false;
	}
	
	private void calculateScore(int coins) {
		score = coins*10;
	}
	public void collectCoin() {
		coinsCollected++;
	}
	
}
