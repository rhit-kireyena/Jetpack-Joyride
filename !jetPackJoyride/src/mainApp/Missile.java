		package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
/**
 * Class: Missile
 * @author W24_A304
 * <br>Purpose: Abstract class that other missiles will inherit from as a base
 * <br>Restrictions: Every missile will have to have some sort of rotation
 */
public abstract class Missile extends NonPlayerObject {
	// Constants
	protected static final int WIDTH = 50;
	protected final static int HEIGHT = 25;
	protected final static Color BORDER_COLOR = Color.black;
	
	//Instance variables
	protected int xSpeed;
	protected int ySpeed;
	protected int speed;
	protected Color missileColor;
	protected int[] xArray = new int[3];
	protected int[] yArray = new int[3];
	protected double rotation;
	protected int[] originalPoint = new int[2];	
	Hero hero;
	/**
	 * ensures: initializes the location of the middle of the missile and three corners and the speed the missile will go at
	 * @param point used to initialize the location of the parts of the missile
	 * <br>requires: an x and y in an array
	 * @param speed used to set the different speeds
	 * <br>requires: speed is greater than 0
	 */
	public Missile(int[] point, int speed, Hero hero) {
		super(point);
		this.hero = hero;
		this.speed = speed;
		this.xSpeed = speed;
		this.ySpeed = speed;
		this.originalPoint[0]=point[0];
		this.originalPoint[1]=point[1];
		xArray[0] = (-WIDTH / 2);
		yArray[0] = 0;
		xArray[1] = (WIDTH / 2);
		yArray[1] = (-HEIGHT / 2);
		xArray[2] = (WIDTH / 2);
		yArray[2] = (HEIGHT / 2);
	} // Missiles
	public Missile(int x, int y, int speed, Hero hero) {
		this(new int[] {x, y}, speed, hero);
	} //Missiles
	/**
	 * ensures: draws the missile on the frame
	 * @param g2 2D graphic to out the missile on
	 */
	public void drawOn(Graphics2D g2) {
		g2.translate(point[0], point[1]);
		g2.rotate(this.rotation);
		Polygon missile = new Polygon(xArray, yArray, 3);
		g2.setColor(missileColor);
		g2.fillPolygon(missile);
		g2.setColor(BORDER_COLOR);
		g2.drawPolygon(missile);
		g2.rotate(-this.rotation);
		g2.translate(-point[0], -point[1]);
	} // drawOn
	/**
	 * ensures: checks if the hero is above or below the missile
	 * @param hero uses the x and y from the hero provided
	 * @return true if the hero is above the missile
	 */
	public boolean isAbove(Hero hero) {
		if(hero.getY() > this.point[1] + WIDTH / 2 && this.point[0] - hero.getX() > 0) return true;
		return false;
	}// isAbove
	
	// Getters and setters
	/**
	 * ensures: the current xSpeed is returned
	 * @return the horizontal speed
	 */
	public int getXSpeed() {
		return this.xSpeed;
	} // getXSpeed
	/**
	 * ensures: the current ySpeed is returned
	 * @return the vertical speed
	 */
	public int getYSpeed() {
		return this.ySpeed;
	} // getYSpeed
	/**
	 * ensures: the current x is returned
	 * @return the x position of missile
	 */
	public int getX() {
		return point[0]-WIDTH/2;
	} // getX
	/**
	 * ensures: the current y is returned
	 * @return the y position of missile
	 */
	public int getY() {
		return point[1];
	} // getY
	
	/**
	 * ensures: sets the rotation to the angle that is made with the hero
	 * @param hero to determine the angle
	 */
	public void setRotation(Hero hero) {
		this.rotation = 0;
	}
	
	
	public void resetPos() {
		point[0]=originalPoint[0];
		point[1]=originalPoint[1];
	}

	public boolean collidesWith(Hero hero) {
		//TODO checks if the object is colliding with hero
		//sub classes can call this superclass since the collision detection should be the same for all sub classes

		double distanceX = Math.abs(this.getX() - hero.getX());
		double distanceY = Math.abs(this.getY() - hero.getY());

		if (distanceX <= hero.getWidth()/2 && distanceY <= hero.getHeight()) {
			hero.collideWith(this);
		}

//		transformShape();
//		if(this.shapeIntersectsHero(hero)) hero.collideWith(this);;
		
		return false;
	}
	
	public void transformShape() {
		AffineTransform transform = new AffineTransform();
		transform.translate(point[0], point[1]);
        transform.rotate(rotation);
        
		double[] pointsArray = new double[xArray.length+yArray.length];
		for(int i = 0; i < xArray.length; i++) {
			pointsArray[i] = xArray[i];
			pointsArray[i+1] = yArray[i];
		}
		transform.transform(pointsArray, 0, pointsArray, 0, pointsArray.length/2);
		
		//int[] xPointsTransformed = new int[pointsArray.length/2];
		//int[] yPointsTransformed = new int[pointsArray.length/2];
		this.boundingShape = new Polygon();
		for(int i = 0; i < pointsArray.length-2; i+=2) {
			((Polygon) boundingShape).addPoint((int) pointsArray[i], (int) pointsArray[i + 1]);
		}
		//this.boundingShape = new Polygon(xPointsTransformed, yPointsTransformed, xPointsTransformed.length);
	}
	
	/**
	 * Updates the parameters of the object
	 * -moves the object forward in order to create sidescrolling
	 * -moves the object up or down if a vertical speed is given
	 * @param horizontalSpeed adds to the y so the object moves left
	 * @param verticalSpeed adds or subtracts to the x so the object will move up or down
	 * @param isAbove helps determine if the object should move up or down
	 */
	public void update(int horizontalSpeed) {
		super.update(horizontalSpeed + getXSpeed());
		if (point[0] <= 0) {
			this.resetPos();
		}
		setRotation(hero);
		
	} // update
	
} // Missiles
