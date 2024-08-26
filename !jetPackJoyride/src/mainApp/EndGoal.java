package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Class: EndGoal
 * @author W24_A304
 * <br>Purpose: represents the level winning end zone
 * <br>Restrictions: Requires level/game to be displayed in, requires hero to collide with to end the level
 */
public class EndGoal extends NonPlayerObject {
	private static int GOAL_WIDTH = 60;
	private Color color;
	private int width;
	
	public EndGoal(int[] point) {
		super(point);
		this.color = Color.magenta;
		this.width = GOAL_WIDTH;
	}
	public EndGoal(int x) {
		this(new int[] {x, 0});
	}

	@Override
	protected boolean collidesWith(Hero hero) {
		getBounds();
		if(this.shapeIntersectsHero(hero)) hero.collideWithEnd();
		return false;
	}

	@Override
	protected void drawOn(Graphics2D g2) {
		getBounds();
		g2.setColor(color);
		g2.fill(boundingShape);
		g2.draw(boundingShape);
		
	}
	
	protected void getBounds() {
		this.boundingShape = new Rectangle(point[0], point[1], width, MainApp.SIZE.height);
	}

}
