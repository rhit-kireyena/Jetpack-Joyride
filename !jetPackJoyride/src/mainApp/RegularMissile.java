package mainApp;

/**
 * Class: RegularMissile
 * @author W24_A304
 * <br>Purpose: represents a missile that travels across the screen but does not track the player in the game
 */
import java.awt.Color;
/**
 * ensures: the missile goes across the screen in a straight line and possibly hits the hero
 * @param point used to initialize the location of the parts of the missile
 * <br>requires: an x and y in an array
 * @param speed used to set the horizontal speed
 * <br>requires: speed is greater than 0
 */
public class RegularMissile extends Missile{
	
	public RegularMissile(int[] point, int speed, Hero hero) {
		super(point, speed, hero);
		ySpeed = 0;
		this.missileColor = Color.orange;
	} // RegularMissile
	public RegularMissile(int x, int y, int speed, Hero hero) {
		super(x, y, speed, hero);
		ySpeed = 0;
		this.missileColor = Color.orange;
	} // RegularMissile
	
	public boolean collidesWith(Hero hero) {
		return super.collidesWith(hero);
	}
} // RegularMissile
