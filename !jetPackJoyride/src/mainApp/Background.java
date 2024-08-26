package mainApp;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Class: Background
 * @author W24_A304
 * <br>Purpose: displays part of a parallax scrolling background
 * <br>Restrictions: Requires level/game to be displayed in, requires image files
 */
public class Background {

	double speed;
	Hero hero;
	Image image;
	double x;
	double y;
	int width;
	int height;
	
	/**
	 * @param x - the initial x location of the image
	 * @param speed - the ratio to the hero's speed that the scrolling should occur
	 * @param hero - the hero object
	 * @param imageLocation - the location of the file for the image
	 */
	public Background(double x, double speed, Hero hero, String imageLocation) {
		this.speed = speed;
		this.hero = hero;
		this.x = x;
		
		this.width = MainApp.SIZE.width;
		this.height = MainApp.SIZE.height;
		try {
			this.image = ImageIO.read(new File(imageLocation));
		} catch (IOException e) {
			System.err.println("Image not found");
			e.printStackTrace();
		}
		this.y =0;
	}
	
	public void update(int horizontalSpeed) {
		x-=horizontalSpeed*speed;
		
		if(x+width <= 0) x = width;
		
	}
	
	public void drawOn(Graphics g) {
		g.drawImage(this.image, (int)x,(int) y, width, height, null);
	}

}
