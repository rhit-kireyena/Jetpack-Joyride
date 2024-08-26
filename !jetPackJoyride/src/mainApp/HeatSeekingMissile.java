package mainApp;

import java.awt.Color;
/**
 * Class: HeatSeekingMissile
 * @author W24_A304
 * <br>Purpose: represents a missile that travels across the screen that tracks the player in the game
 */
public class HeatSeekingMissile extends Missile{
	/**
	 * ensures: there is a missile created that is different from the regular missile
	 * @param point used to initialize the location of the parts of the missile
	 * <br>requires: an x and y in an array
	 * @param speed used to set the initial speeds
	 * <br>requires: speed is greater than 0
	 */
	public HeatSeekingMissile(int[] point, int speed, Hero hero) {
		super(point, speed, hero);
		this.missileColor = Color.magenta;
		this.rotation = 0;
	} // HeatSeekingMissile
	public HeatSeekingMissile(int x, int y, int speed, Hero hero) {
		super(x, y, speed, hero);
		this.missileColor = Color.magenta;
		this.rotation = 0;
	} // HeatSeekingMissile
	/**
	 * ensures: sets the rotation to the angle that is made with the hero
	 * @param hero to determine the angle
	 */
	
	
	/**
	 * sets the missile pointing at the hero
	 * @param Hero hero - the hero to point the missile at
	 * @return void
	 */
	@Override
	public void setRotation(Hero hero) {
		// for when rocket is above hero
		if(isAbove(hero)) {
			this.rotation = (-1) * Math.atan(((double)(hero.getY()) - (double)(this.point[1])) / ((double)(this.point[0]) - (double)(hero.getX())));
		}
		// for when rocket is below hero
		else if(!isAbove(hero)) {
			this.rotation = Math.atan((double)((this.point[1] - hero.getY())) / ((double)(this.point[0] - hero.getX())));
		}
	} // setRotation
	
	
	public boolean collidesWith(Hero hero) {
		return super.collidesWith(hero);
		
	} //collidesWith
	
	public void update(int horizontalSpeed) {
		super.update(horizontalSpeed);
		if(isAbove(hero)) {
			point[1] += this.ySpeed;
		} else {
			point[1] -= ySpeed;
		}
	}
	
} // HeatSeekingMissile