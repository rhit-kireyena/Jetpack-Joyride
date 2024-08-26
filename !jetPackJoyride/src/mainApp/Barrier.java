package mainApp;

import java.awt.Color;
import java.awt.Shape;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Class: Barrier
 * @author W24_A304
 * <br>Purpose: Creates a template for inheritance for the creation of barriers in the game
 * <br>Restrictions: Cannot be used instantiated
 */
public abstract class Barrier extends NonPlayerObject{
	//instance variables
	protected double rotation;
	protected Color barrierColor;
	protected int width;
	protected int height;


	//Constants
	public final static Color BORDER_COLOR = Color.black;
	
	public Barrier(int[] point, int rotation) {
		super(point);
		this.rotation = Math.toRadians(rotation);
		this.width = 25;
	} //Barrier
	public Barrier(int x, int y, int rotation) {
		this(new int[] {x,y}, rotation);
	} //Barrier
	public Barrier(int x, int y, int height, int rotation) {
		this(x,y, rotation);
		this.height = height;
	} //Barrier
	/**
	 * Draw the barrier
	 * @param g2 - the 2d Graphics object
	 */
	public void drawOn(Graphics2D g2) {
		g2.translate(point[0], point[1]);
		g2.rotate(rotation);
		Rectangle barrier = new Rectangle(-width/2, 0, width, height);
		g2.setColor(barrierColor);
		g2.fill(barrier);
		g2.setColor(BORDER_COLOR);
		g2.draw(barrier);
		g2.rotate(-rotation);
		g2.translate(-point[0],-point[1]);
		
	} //drawOn
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	
	
	
	/**
	 * Sets the amount the barrier is rotated from the horizontal
	 * @param angle - the amount to rotate in degrees
	 */
	protected void setRotation(double angle) {
		this.rotation  = Math.toRadians(angle);
	} //setRotation
	
	public boolean collidesWith(Hero hero) {
		//TODO checks if the object is colliding with hero
		//sub classes can call this superclass since the collision detection should be the same for all sub classes

		getTransformedRectangle();
		return this.shapeIntersectsHero(hero);
	} //collidesWith
	
	public void update(int horizontalSpeed) {
		super.update(horizontalSpeed);
	}
	
	public double getRotation() {
		return rotation;
	}

    public Shape getTransformedRectangle() {
        
    	AffineTransform transform = new AffineTransform();
        transform.translate(point[0], point[1]);
        transform.rotate(rotation);

        double[] rectPoints = new double[] {
            0, 0,
            0, height,
            width, height,
            width, 0
        };
        transform.transform(rectPoints, 0, rectPoints, 0, 4);

        Polygon polygon = new Polygon();
        for (int i = 0; i < rectPoints.length; i += 2) {
            polygon.addPoint((int) rectPoints[i], (int) rectPoints[i + 1]);
        }
        
        boundingShape = polygon;
        return boundingShape;
    }
	
}