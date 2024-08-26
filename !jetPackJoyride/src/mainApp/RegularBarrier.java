package mainApp;
import java.awt.Color;


/**
 * Class: RegularBarrier
 * @author W24_A304
 * <br>Purpose: functions as a barrier obstacle in the game
 */
public class RegularBarrier extends Barrier {

	public RegularBarrier(int x, int y, int height, int rotation) {
		super(x, y, height, rotation);
		this.barrierColor = Color.cyan;
	}

	public boolean collidesWith(Hero hero) {
		boolean collision = super.collidesWith(hero);
		if(collision) {
			hero.collideWithRegularBarrier(this);
		}
		return false;
	}
}
