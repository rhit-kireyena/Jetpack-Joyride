package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * Class: Coin
 * @author W24_A304
 * <br>Purpose: Represents a coin in a game
 * <br>Restrictions: a level to display in an a frame to hold that level
 */
public class Coin extends NonPlayerObject{
	private static int RADIUS = 10;
	private static Color COIN_COLOR = Color.yellow;
	
	public Coin(int[] point) {
		super(point);
	}
	public Coin(int x, int y) {
		this(new int[] {x,y});
	}
	
	/**
	 * Draws the coin
	 * @param g2 the 2D graphics object
	 */
	public void drawOn(Graphics2D g2) {
		Ellipse2D.Double coin = new Ellipse2D.Double(point[0], point[1], 2*RADIUS, 2*RADIUS);
		g2.setColor(COIN_COLOR);
		g2.fill(coin);
		g2.setColor(Color.black);
		g2.draw(coin);
	}
	
	
	
	public boolean collidesWith(Hero hero) {
		//TODO checks if the object is colliding with hero
		//TODO Tell hero that it has been collided with
		//TODO delete instance of coin
		double distanceX = Math.abs(this.point[0] - hero.getX());
		double distanceY = Math.abs(this.point[1] - hero.getY());

		if (distanceX <= 0+getRadius() && distanceY <= hero.getHeight()) {
			hero.collectCoin();
			return true;
		}else{
			return false;
		}
	}
	
	
	public static int getRadius() {
		return RADIUS;
	}
	
}
