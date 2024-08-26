package mainApp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JComponent;

/**
 * Class: Level
 * @author W24_A304
 * <br>Purpose: Stores and displays the levels for a game
 * <br>Restrictions: File loaded must be valid
 */
public class Level extends JComponent {	
	//instance variables
	
	private ArrayList<NonPlayerObject> objects;
	private Hero hero;
	private boolean defaultFile;
	private Background[] backgrounds;
	
	private String[] backgroundFiles = new String[] {
			"Sprites/far-back.png",
			"Sprites/back.png",
			"Sprites/middle.png",
			"Sprites/front.png"
	};
	
	
	public Level(Hero hero, String file) {
		this.hero = hero;
		this.objects = new ArrayList<NonPlayerObject>();
		this.defaultFile = false;
		getLevelFromFile(file);
	} //Level
	public Level(Hero hero) {
		this.hero = hero;
		this.objects = new ArrayList<NonPlayerObject>();
		defaultLevel("[default level manually loaded]");
	} //Level
	
	/**
	 * 
	 * @param file - the file to load
	 */
	public void loadFile(String file) {
		this.objects = new ArrayList<NonPlayerObject>();
		this.defaultFile = false;
		getLevelFromFile(file);
	}
	/**
	 * generates a level from a file
	 * @param file - the path to the file location
	 */
	public void getLevelFromFile(String file){
		try {
			loadFileFromKeywords(file);
		} catch(InvalidFormatException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			
			defaultLevel(file);
		} catch (IOException e) {
			e.printStackTrace();
			defaultLevel(file);
			
		}
		backgrounds = new Background[2*backgroundFiles.length];
		double[] backgroundSpeeds = new double[] {
				0.25, .5, 1, 2
		};
		for(int i = 0; i < backgroundFiles.length; i++) {
			backgrounds[2*i] = new Background(0, backgroundSpeeds[i],hero, backgroundFiles[i]);
			backgrounds[2*i+1] = new Background(MainApp.SIZE.width, backgroundSpeeds[i], hero, backgroundFiles[i]);
		}
	} //getLevelFromFile

	/**
	 * Default level to load when loading from a file fails
	 * @param file - the name of the file that failed to load. used for output
	 */
	public void defaultLevel(String file) {
		this.objects.add(new Coin(100, 100));
		this.objects.add(new RegularBarrier(250, 200, 100, 45));
		this.objects.add(new ElectricBarrier(600, 200, 400, 0));
		System.err.println("Level load for " + file +" has failed. Default level loaded.");
		this.defaultFile = true;
	}
	
	/**
	 * @return returns true if the level is the default level
	 */
	public boolean isDefaultLevel() {
		return defaultFile;
	}
	/**
	 * loads a level from a file based on the keywords format
	 * @param file - the path to the file location
	 * @throws InvalidFormatException, IOException
	 */
	private void loadFileFromKeywords(String file) throws InvalidFormatException, IOException{
		final String[] KEYWORDS = new String[] {"bar", "eBar", "coinGrid", "coinSingle", "mis", "heatMis", "end"};
		
		File levelFile = new File(file);
		Scanner fileScan;
		try {
			fileScan = new Scanner(levelFile);
		} catch(IOException e) {
			System.err.println("Could not find/load file " + file + ". Please verify that the path is correct and the file is intact.");
			throw e;
		}
		
		for(int i = 0; fileScan.hasNextLine(); i++) {
			
			String line = fileScan.nextLine();
			String keywordFound = "";
			boolean invalidFlag = true;
			for(String s: KEYWORDS) {
			if(line.startsWith(s)) {
				invalidFlag = false;
				keywordFound = s;
			}
			}
			if(invalidFlag) throw new InvalidFormatException("Missing keyword on line " + (i+1) + " of " + file);
			parseKeyword(line, keywordFound, i+1);
		}
		
		fileScan.close();
	} //loadFileFromKeywords
	
	/**
	 * converts lines with a specified keyword into game objects
	 * @param line - the line that the keyword occurs in
	 * @param keyword - the keyword to be checked
	 * @param lineNumber - the number in the file the line occurs in (used for error output)
	 * @throws InvalidFormatException - if it can't understand the line and parse it
	 */
	private void parseKeyword(String line, String keyword, int lineNumber) throws InvalidFormatException {
		line = line.replaceFirst(keyword, "");
		ArrayList<String> params = getParamsFromString(line, lineNumber);
		int x;
		int y;
		int length;
		int rotation;
		int speed;
		String failIntMsg = "Expected integer in parameters on line " + lineNumber + " with parameters " + params.toString()
		+"\n ..." + line + " ...";
		switch(keyword) {
		case ("bar"): //regular barrier with form bar(int xPos, int yPos, int Length, int Rotation);
			
			try {
				x = Integer.parseInt(params.get(0));
				y = Integer.parseInt(params.get(1));
				length = Integer.parseInt(params.get(2));
				rotation = Integer.parseInt(params.get(3));
			} catch(NumberFormatException e) {
				throw new InvalidFormatException(failIntMsg);
				
			} catch(IndexOutOfBoundsException e) {
				throw new InvalidFormatException("Missing parameters for keyword " + keyword + " on line " + lineNumber +" with parameters " + params.toString()
						+"\n ..." + line + " ...");
			}
			objects.add(new RegularBarrier(x, y, length, rotation));
			break;
		case ("eBar"): //ElectricBarrier of form eBar(int xPos, int yPos, int Length, int Rotation);
		try {
			x = Integer.parseInt(params.get(0));
			y = Integer.parseInt(params.get(1));
			length = Integer.parseInt(params.get(2));
			rotation = Integer.parseInt(params.get(3));
		} catch(NumberFormatException e) {
			throw new InvalidFormatException(failIntMsg);
			
		} catch(IndexOutOfBoundsException e) {
			throw new InvalidFormatException("Missing parameters for keyword " + keyword + " on line " + lineNumber
					+"\n ..." + line + " ...");
		}
		objects.add(new ElectricBarrier(x, y, length, rotation));
			break;
		case ("coinGrid"): //coin grid preset of form coinGrid(int xPos, int yPos, int rows, int columns);
			int rows;
			int columns;
			try {
				x = Integer.parseInt(params.get(0));
				y = Integer.parseInt(params.get(1));
				rows = Integer.parseInt(params.get(2));
				columns = Integer.parseInt(params.get(3));
			} catch(NumberFormatException e) {
				throw new InvalidFormatException(failIntMsg);
				
			} catch(IndexOutOfBoundsException e) {
				throw new InvalidFormatException("Missing parameters for keyword " + keyword + " on line " + lineNumber
						+"\n ..." + line + " ...");
			}
			coinGrid(x, y, rows, columns);
			break;
		case ("coinSingle"): //single coin of form coinSingle(int xPos, int yPos);
			try {
				x = Integer.parseInt(params.get(0));
				y = Integer.parseInt(params.get(1));
			} catch(NumberFormatException e) {
				throw new InvalidFormatException(failIntMsg);
				
			} catch(IndexOutOfBoundsException e) {
				throw new InvalidFormatException("Missing parameters for keyword " + keyword + " on line " + lineNumber
						+"\n ..." + line + " ...");
			}
				objects.add(new Coin(x, y));
			break;
		case ("mis"): // missile of form RegularMissile(int x, int y, int speed);
			try {
				x = Integer.parseInt(params.get(0));
				y = Integer.parseInt(params.get(1));
				speed = Integer.parseInt(params.get(2));
			} catch(NumberFormatException e) {
				throw new InvalidFormatException(failIntMsg);
			} catch(IndexOutOfBoundsException e) {
				throw new InvalidFormatException("Missing parameters for keyword " + keyword + " on line " + lineNumber
						+"\n ..." + line + " ...");
			}
				objects.add(new RegularMissile(x, y, speed, hero));
			break;
		case ("heatMis"): // missile of form RegularMissile(int x, int y, int speed);
			try {
				x = Integer.parseInt(params.get(0));
				y = Integer.parseInt(params.get(1));
				speed = Integer.parseInt(params.get(2));
			} catch(NumberFormatException e) {
				throw new InvalidFormatException(failIntMsg);
			} catch(IndexOutOfBoundsException e) {
				throw new InvalidFormatException("Missing parameters for keyword " + keyword + " on line " + lineNumber
						+"\n ..." + line + " ...");
			}
				objects.add(new HeatSeekingMissile(x, y, speed, hero));
			break;
		case ("end"): //end barrier of form end(int x)
			try {
				x = Integer.parseInt(params.get(0));
			} catch(NumberFormatException e) {
				throw new InvalidFormatException(failIntMsg);
				
			} catch(IndexOutOfBoundsException e) {
				throw new InvalidFormatException("Missing parameters for keyword " + keyword + " on line " + lineNumber
						+"\n ..." + line + " ...");
			}
			objects.add(new EndGoal(x));
			break;
		default:
			throw new InvalidFormatException("Could not parse line based on string on line " + lineNumber + " for keyword " + keyword);
		}
	} //parseKeyword
	
	/**
	 * gets the parameters of in a string of the format
	 * ( param1, param2, param3, param4)
	 * and returns them in a array;
	 * @param string - string of format (param1, param2, param3, param4)
	 * @param lineNumber - the number in the file the line occurs in (used for error output)
	 * @return - the parameters as strings in an ArrayList
	 * @throws InvalidFormatException - if the parameters are empty or the string is of invalid format
	 */
	private ArrayList<String> getParamsFromString(String string, int lineNumber) throws InvalidFormatException{
		ArrayList<String> params = new ArrayList<String>();

		string = string.replaceAll(" ", ""); //remove all spaces
		if(string.charAt(0) != '(' || string.charAt(string.length()-1) != ')') //throws error if incorrect parenthesis
			throw new InvalidFormatException("Invalid parameter expression on line " + lineNumber + ". Missing parenthesis.");
		
		for(int i = 0;i< string.length(); i++) {
			String param;
			if(string.charAt(i) == ',' || i == 0) {
				int nextComma = string.indexOf(',', i+1);
				if(nextComma == -1) nextComma = string.length()-1;
				param = string.substring(i+1,nextComma);
				if(param.isEmpty()) //throws error for empty parameter
					throw new InvalidFormatException("Empty parameter on line " + lineNumber + " at index " + (i+1) + "." +
				"\n ..." + string.substring(i, nextComma+1) + "...");
				
				params.add(param);
			}
		}
		
		
		return params;
	} //getParamsFromString

	/**
	 * Creates a grid of coins spaced by 1 radius of the coin apart
	 * @param x - the x position of the center of the first coin (the top leftmost)
	 * @param y - the y position of the center of the first coin (the top leftmost)
	 * @param columns - the amount of columns of coins 
	 * @param rows - the amount of rows of coins
	 */
	private void coinGrid(int x, int y, int rows, int columns){
		int coinRadius = Coin.getRadius();
		int spacing = 3*coinRadius;
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				objects.add(new Coin(x + spacing*i, x + spacing*j));
			}
		}
	}
	
	
	@Override
	/**
	 * draws all the parts of the level
	 * @param g the graphics object
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		for(Background gnd : backgrounds) {
			gnd.drawOn(g);
		}		
		hero.drawOn(g2);
		//TODO update to only be NonPlayerObject (requires adding a drawOn function to NonPlayerObject)
		for(NonPlayerObject o: objects) {
			o.drawOn(g2);
		}
		
		
	} //paintComponent
	
	
	/**
	 * Updates all the parts of the level, then redraws them
	 * @param none
	 */
	public void update() {
		int horizontalSpeed = hero.getSpeedX();
		
		for(Background gnd : backgrounds) {
			gnd.update(horizontalSpeed);
		}
		for(int i = 0; i < objects.size(); i++) {
			objects.get(i).update(horizontalSpeed);
			if(objects.get(i).collidesWith(hero)) {
				objects.remove(i);
				i--;
			}
		}
		this.repaint();
	} //update
} //Level
