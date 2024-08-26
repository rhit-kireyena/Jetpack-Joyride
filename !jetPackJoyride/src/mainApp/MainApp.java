package mainApp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class: MainApp
 * @author W24_A304
 * <br>Purpose: Top level class for CSSE220 Project containing main method 
 * <br>Restrictions: None
 */
public class MainApp {
	public static final Dimension SIZE = new Dimension(750,600);
	private static final int FPS = 100;
	private static final int DELAY = 1000/FPS;
	final private int DEFAULT_HEALTH = 3;
	private int heroHealth;
	private int currentLevel;


	JFrame mainFrame;
	JLabel loseText;
	JPanel centerPanel;
	Timer gameTimer;

	Level level;
	Hero barry;
	private final String[] files = new String[] {
			"Levels/level1.txt",
			"Levels/level2.txt",
			"Levels/level3.txt",
			"Levels/level4.txt"
	};
	
	private void runApp() {
		heroHealth = DEFAULT_HEALTH;
		mainFrame = new JFrame("Jetpack Joyride!");
		mainFrame.setSize(SIZE);
		
		barry = new Hero(heroHealth);
		currentLevel = 0;
		level = new Level(barry, files[currentLevel]);
		
		InputListener keyIn = new InputListener(barry, this);
		mainFrame.addKeyListener(keyIn);
		
		mainFrame.add(level);
		
		
		JPanel topPanel = new JPanel();
		
		mainFrame.add(topPanel, BorderLayout.NORTH);
		
		JLabel scoreText = new JLabel("Score ");
		JLabel lifeText = new JLabel("Lives ");
		topPanel.add(lifeText);
		topPanel.add(scoreText);
		
		gameTimer = new Timer(DELAY, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				level.update();
				scoreText.setText("Score: " + barry.getScore()+" ");
				lifeText.setText("Health: " + barry.getHealth() +" ");
				if (barry.getHealth()==0) {
					//frameMain.setVisible(false);
					
					//frameLose.setVisible(true);
					//frameLose.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					endGame(false);
		
				}else if (heroHealth > barry.getHealth()) {
					 heroHealth = barry.getHealth();
					 restartLevel(barry);
				} else if(barry.endReached()) {
					if(currentLevel == files.length-1) {
						endGame(true);
					}
					else {
					restartLevel(barry);
					switchLevels(1);
					}
				}
				
				loseText = new JLabel("GAME OVER");
				centerPanel = new JPanel();
				centerPanel.add(loseText);
				
				JButton restartButton = new JButton("Play Again!");
				restartButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
						restartGame();
					}
					
				});
				centerPanel.add(restartButton);
				
				JButton quitButton = new JButton("Quit Game :(");
				quitButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						mainFrame.dispose();
					}
					
				});
				centerPanel.add(quitButton);
				
			}
			private void endGame(boolean won) {
				//TODO implement
				mainFrame.remove(level);
				if(gameTimer != null) gameTimer.stop();
				mainFrame.add(centerPanel, BorderLayout.CENTER);
				if(won) loseText.setText("we're so back | SCORE: " + barry.getScore());
				else loseText.setText("its so joever | SCORE: " + barry.getScore());
				
			}


						
		});
		
		gameTimer.start();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		
	} // runApp

	public void restartGame() {
		mainFrame.dispose();
		this.runApp();
	}
	
	
	/**
	 * switches between levels (called by the input listener or when level is finished)
	 * @param the amount to change levels by (usually +/- 1)
	 */
	public void switchLevels(int changeAmount) {
		currentLevel+= changeAmount;
		if(currentLevel > files.length-1) currentLevel = 0; //loops back around on levels
		if(currentLevel < 0) currentLevel = files.length-1;
		level.loadFile(files[currentLevel]);
	}

	/**
	 * @return void
	 */
	public void restartLevel(Hero hero) {
		level.loadFile(files[currentLevel]);
		hero.resetPosition();
	} //restartLevel
	
	/**
	 * ensures: runs the application
	 * @param args unused
	 */
	public static void main(String[] args) {
		MainApp mainApp = new MainApp();
		mainApp.runApp();		
	} // main

}