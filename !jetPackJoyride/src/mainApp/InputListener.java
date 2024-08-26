package mainApp;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
/**
 * Class: InputListener
 * @author W24_A304
 * <br>Purpose: Listens for player input and takes actions based on that input
 * <br>Restrictions: requires frame to be attached to
 */
public class InputListener implements KeyListener {

	Hero hero;
	MainApp app;
	
	public InputListener(Hero hero, MainApp app) {
		this.hero = hero;
		this.app = app;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	/**
	 * triggers when a key is pressed. calls movement changes and level changes.
	 * @param e - the KeyEvent that contains information about the key press
	 */
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			hero.isFlying = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_U) {
			hero.setY(550);
			app.switchLevels(1);
		}
		if(e.getKeyCode() == KeyEvent.VK_D) {
			hero.setY(550);
			app.switchLevels(-1);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			hero.isFlying = false;
		}
	}

}
