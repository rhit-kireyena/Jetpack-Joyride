package mainApp;
import java.awt.Color;

/**
 * Class: RegularBarrier
 * @author W24_A304
 * <br>Purpose: functions as a barrier in a game that causes damage to the player
 */
public class ElectricBarrier extends Barrier {
	
	public ElectricBarrier(int x, int y, int height, int rotation) {
		super(x, y, height, rotation);
		this.barrierColor = Color.red;
	}


	public boolean collidesWith(Hero hero) {
		boolean collision = super.collidesWith(hero);
		if(collision) {
			hero.collideWithElectricBarrier(this);
		}
		return false;
	}
}
