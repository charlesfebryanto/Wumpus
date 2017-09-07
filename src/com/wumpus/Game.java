package com.wumpus;

import java.util.Random;
import java.util.Scanner;

public class Game {
	private char[][] board;
	
	private char  player = '*';
	private int playerX;
	private int playerY;
	private Wumpus wumpus;
	private Pit[] pits;
	private Gold[] golds;
	private ClearGround[] grounds;
	private int score;
	private int maxGold;
	
	private Random random = new Random();

	public void runGame() {
		boolean isPlaying = true;
		int option;
		Scanner sc = new Scanner(System.in);
		setBoard(4);
		display();
		do {
			menu();
			option = sc.nextInt();
			switch(option) {
			case 1:
				isPlaying = moveLeft();
				break;
			case 2:
				isPlaying = moveRight();
				break;
			case 3:
				isPlaying = moveUp();
				break;
			case 4:
				isPlaying = moveDown();
				break;
			case 5:
				System.out.println("quiting");
				break;
			default:
				System.out.println("invalid");
			}
			
		} while((option != 5 && isPlaying) && maxGold > 0);
		System.out.println("No More Gold to Find, You Win");
	}

	private char[][] setBoard(int square) {
		maxGold = 0;
		int pitNumber = 3;
		
		board = new char[square][square];

		// create board
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board.length; j++) {
				ClearGround clearGround = new ClearGround('.', i, j);
				board[i][j] = clearGround.display();
			}
		}
		
		// create pit
		int pitCount = 0;
		
		while(pitCount < pitNumber) {
			int randomRow = random.nextInt(square);
			int randomCol = random.nextInt(square);
			
//			System.out.println(randomRow + " " + randomCol + " for pit");
			if(board[randomRow][randomCol] == '.') {
				//					System.out.println("hit save");
				Pit pit = new Pit('p', randomRow, randomCol);
				board[randomRow][randomCol] = pit.display();
				pitCount++;
			}
		}
		
		do {
			maxGold = random.nextInt(4);
		} while(maxGold <= 0);
		
		int goldCount = 0;
		
		while(goldCount < maxGold) {
			int randomRow = random.nextInt(square);
			int randomCol = random.nextInt(square);
			
			if(board[randomRow][randomCol] == '.') {
				Gold gold = new Gold('g', randomRow, randomCol);
				board[randomRow][randomCol] = gold.display();
				goldCount++;
			}
		}
		
		int wumpusCount = 0;
		do {
			int randomRow = random.nextInt(square);
			int randomCol = random.nextInt(square);
			if(board[randomRow][randomCol] == '.') {
				wumpus = new Wumpus('W', randomRow, randomCol);
				board[randomRow][randomCol] = wumpus.display();
//				System.out.println(randomRow + " " + randomCol + " for wumpus");
				wumpusCount++;
			}
		} while (wumpusCount < 1);
		
		int playerCount = 0;
		do {
			int randomRow = random.nextInt(square);
			int randomCol = random.nextInt(square);
			if(board[randomRow][randomCol] == '.') {
				board[randomRow][randomCol] = player; 
				playerX = randomRow;
				playerY = randomCol;
//				System.out.println(randomRow + " " + randomCol + " for player");
				playerCount++;
			}
		} while(playerCount < 1);
		
		// count
//		System.out.println(pitCount);
//		System.out.println(goldCount);
//		System.out.println(wumpusCount);
//		System.out.println(playerCount);

		return board;
	}





	//	

	private void display() {
//		char[][] gameBoard = setBoard(4, 4);
		System.out.println("player On " + playerX + " " + playerY);
		for(int i= 0; i<board.length; i++) {
			for(int j = 0; j<board.length; j++) {
				if(j >= board.length-1) {
					System.out.println("[ " + board[i][j] + " ][" + i + "][" + j + "] ");
				} else {
					System.out.print("[ " + board[i][j] + " ][" + i +"][" + j + "] ");
				}
			}
		}
	}
	
	private void menu() {
			System.out.println("=== Wumpus ===");
			System.out.println("There is : " + maxGold + " Gold Left");
			System.out.println("Gold : " + score);
			System.out.println("1. Move Player Left");
			System.out.println("2. Move Player Right");
			System.out.println("3. Move Player Up");
			System.out.println("4. Move Player Down");
			System.out.println("5. Quit");
	}
	
	
	private void senseNearby() {
		
	}
	
	private boolean moveLeft() {
		// set previous field to be clear ground
				board[playerX][playerY] = '.';
				
				
//				System.out.println(playerX);
				// checking stage
				if(playerY == 0) {
					if(board[playerX][board.length-1] == 'p') {
						System.out.println("You fall to the pit");
						return false;
					} else if(board[playerX][board.length-1] == 'W'){
						System.out.println("Killed by wumpus");
						return false;
					} else if(board[playerX][board.length-1] == 'g') {
						System.out.println("Found 1 Gold !");
						score++;
						maxGold--;
					}
				} else {
					if(board[playerX][playerY-1] == 'p') {
						System.out.println("You fall to the pit");
						return false;
					} else if(board[playerX][playerY-1] == 'W'){
						System.out.println("Killed by wumpus");
						return false;
					} else if(board[playerX][playerY-1] == 'g') {
						System.out.println("Found 1 Gold !");
						score++;
						maxGold--;
					}
				}
				
				//decrease and move
				if(playerY == 0) {
					playerY = board.length-1;
				} else {
					playerY--;
				}
				board[playerX][playerY] = player;
				display();
				return true;
	}
	
	private boolean moveRight() {
		// set previous field to be clear ground
		board[playerX][playerY] = '.';


		//				System.out.println(playerX);
		// checking stage
		if(playerY == 3) {
			if(board[playerX][0] == 'p') {
				System.out.println("You fall to the pit");
				return false;
			} else if(board[playerX][0] == 'W'){
				System.out.println("Killed by wumpus");
				return false;
			} else if(board[playerX][0] == 'g') {
				System.out.println("Found 1 Gold !");
				score++;
				maxGold--;
			}
		} else {
			if(board[playerX][playerY+1] == 'p') {
				System.out.println("You fall to the pit");
				return false;
			} else if(board[playerX][playerY+1] == 'W'){
				System.out.println("Killed by wumpus");
				return false;
			} else if(board[playerX][playerY+1] == 'g') {
				System.out.println("Found 1 Gold !");
				score++;
				maxGold--;
			}
		}

		//decrease and move
		if(playerY == 3) {
			playerY = 0;
		} else {
			playerY++;
		}
		board[playerX][playerY] = player;
		display();
		return true;
	}
	
	private boolean moveUp() {
		
		// set previous field to be clear ground
		board[playerX][playerY] = '.';
		
		
//		System.out.println(playerX);
		// checking stage
		if(playerX == 0) {
			if(board[board.length-1][playerY] == 'p') {
				System.out.println("You fall to the pit");
				return false;
			} else if(board[board.length-1][playerY] == 'W'){
				System.out.println("Killed by wumpus");
				return false;
			} else if(board[board.length-1][playerY] == 'g') {
				System.out.println("Found 1 Gold !");
				score++;
				maxGold--;
			}
		} else {
			if(board[playerX-1][playerY] == 'p') {
				System.out.println("You fall to the pit");
				return false;
			} else if(board[playerX-1][playerY] == 'W'){
				System.out.println("Killed by wumpus");
				return false;
			} else if(board[playerX-1][playerY] == 'g') {
				System.out.println("Found 1 Gold !");
				score++;
				maxGold--;
			}
		}
		
		//decrease and move
		if(playerX == 0) {
			playerX = board.length-1;
		} else {
			playerX--;
		}
		board[playerX][playerY] = player;
		display();
		return true;
	}
	
	private boolean moveDown() {
		// set previous field to be clear ground
		board[playerX][playerY] = '.';


		//				System.out.println(playerX);
		// checking stage
		if(playerX == 3) {
			if(board[0][playerY] == 'p') {
				System.out.println("You fall to the pit");
				return false;
			} else if(board[0][playerY] == 'W'){
				System.out.println("Killed by wumpus");
				return false;
			} else if(board[0][playerY] == 'g') {
				System.out.println("Found 1 Gold !");
				score++;
				maxGold--;
			}
		} else {
			if(board[playerX+1][playerY] == 'p') {
				System.out.println("You fall to the pit");
				return false;
			} else if(board[playerX+1][playerY] == 'W'){
				System.out.println("Killed by wumpus");
				return false;
			} else if(board[playerX+1][playerY] == 'g') {
				System.out.println("Found 1 Gold !");
				score++;
				maxGold--;
			}
		}

		//decrease and move
		if(playerX == 3) {
			playerX = 0;
		} else {
			playerX++;
		}
		board[playerX][playerY] = player;
		display();
		return true;
	}
}
