package com.wumpus;

public class GameItem {
	private char character;
	private int posX;
	private int posY;
	
	public GameItem(char c, int posX, int posY) {
		character = c;
		this.posX = posX;
		this.posY = posY;
	}
	
	public char display() {
		return character;
	}
	
	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}
}
