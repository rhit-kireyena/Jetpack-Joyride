package mainApp;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;


/**
 * Class: NonPlayerObject
 * @author W24_A304
 * <br>Purpose: Abstract class that is inherited by objects that are not players, in order to handle things such as collision and horizontal translation
 * <br>Restrictions: a level to display in and a frame to hold that level
 */
public abstract class NonPlayerObject {
	int[] point;
	Shape boundingShape;
	
	public NonPlayerObject(int[] point) {
		this.point = point;
	}

	/**
	 * Updates the parameters of the object
	 * -moves the object forward in order to create sidescrolling
	 * @param horizontalSpeed
	 */
	public void update(int horizontalSpeed) {
		point[0] -= horizontalSpeed;
		
	} //update
	
	
	//TODO add method that collides with Hero to all inherited classes.
		/*
		it decides whether the hero has collided with it, and takes any necessary actions because of it.
		it should be overridden in all the inherited classes
		any shared functionality should be in the NonPlayerObject function
		*/
		protected abstract boolean collidesWith(Hero hero);
		protected abstract void drawOn(Graphics2D g2);

		protected boolean shapeIntersectsHero(Hero hero) {
			Rectangle heroBox = hero.getBoundingBox();
			return boundingShape.intersects(heroBox);
		}

} //NonPlayerObject
