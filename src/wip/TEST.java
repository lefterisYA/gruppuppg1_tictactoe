package wip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TEST {
	static int noOfChoices = 0;
	static int[][] ticTac = new int[3][3];
	static ArrayList<Integer> playersChoices = new ArrayList<>();    // Array for all players choices 

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		GUI.drawArrayPositions();
		GUI.drawEmptyArray();

		Player t1 = new Player("player1", 'X', 1);
		Player t2 = new Player("player2", 'O', 2);
		Player[] players = { t1, t2 };
		int playTurn = 0;
		
		do {
			Player currentPlayer = players[playTurn];

			int playerChoice = currentPlayer.playerChoose(input);
			currentPlayer.playerPosition(playerChoice, ticTac);
			GUI.drawBoard(ticTac);
			System.out.println(currentPlayer.name + " this is your choices "+ currentPlayer.playerList);
			if (currentPlayer.checkToWin(currentPlayer.playerList))
				break;

			if ( playTurn == 0 )
				playTurn=1;
			else 
				playTurn=0;

		} while (noOfChoices <= 9);
		System.out.println("game over.");

		input.close();
	}
}

class Player {
	String name;
	int playerNumber;
	char ch;
	int choice;
	ArrayList<Integer> playerList = new ArrayList<Integer>();        // every object has own Array for his choices
	ArrayList<Integer> Choices = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));    // players have to choose from  this numbers limit 
	
	public Player( String name, char symbol, int playerNumber ) {
		this.ch = symbol;
		this.name = name;
		this.playerNumber = playerNumber;
	}

	// method to take a choice from every player but must be within range 1...9 and just numbers otherwise 
	// it throws exceptions
	public int playerChoose(Scanner input) {
		boolean flag = false;

		do {
			try {
				System.out.print(this.name + " choose your position: ");

				choice = input.nextInt();
				if ((choice < 1 || choice > 9) || (TEST.playersChoices.contains(choice)))
					throw new Exception();

				TEST.playersChoices.add(choice);
				playerList.add(choice);

				TEST.noOfChoices++;
				flag = true;

			} catch (Exception e) {

				input.nextLine(); // without this code here will be infinite loop
				System.out.println();
				System.out.println("your choice out of limit, choose again from this list:");
				Choices.removeAll(TEST.playersChoices);
				System.out.print(Choices);
				System.out.println();
				System.out.println();
			}
		} while (!flag);
		return choice;
	}

	// method show the position after every player's choice 
	public int[][] playerPosition(int choice, int[][] ticTac) {
		switch (choice) {
		case 1:
			ticTac[0][0] = playerNumber;
			break;
		case 2:
			ticTac[0][1] = playerNumber;
			break;
		case 3:
			ticTac[0][2] = playerNumber;
			break;
		case 4:
			ticTac[1][0] = playerNumber;
			break;
		case 5:
			ticTac[1][1] = playerNumber;
			break;
		case 6:
			ticTac[1][2] = playerNumber;
			break;
		case 7:
			ticTac[2][0] = playerNumber;
			break;
		case 8:
			ticTac[2][1] = playerNumber;
			break;
		case 9:
			ticTac[2][2] = playerNumber;
			break;
		}
		return ticTac;
	}

	// method to check if player's choices have this standard win numbers so it will be break and this player wins 
	public  boolean checkToWin(ArrayList<Integer> playerList) {

		ArrayList<Integer> topRaw = new ArrayList<>(Arrays.asList(1, 2, 3));
		ArrayList<Integer> midRaw = new ArrayList<>(Arrays.asList(4, 5, 6));
		ArrayList<Integer> botRaw = new ArrayList<>(Arrays.asList(7, 8, 9));
		ArrayList<Integer> leftColomn = new ArrayList<>(Arrays.asList(1, 4, 7));
		ArrayList<Integer> midColomn = new ArrayList<>(Arrays.asList(2, 5, 8));
		ArrayList<Integer> rightColomn = new ArrayList<>(Arrays.asList(3, 6, 9));
		ArrayList<Integer> cross1 = new ArrayList<>(Arrays.asList(1, 5, 9));
		ArrayList<Integer> cross2 = new ArrayList<>(Arrays.asList(3, 5, 7));

		if (playerList.containsAll(topRaw) || playerList.containsAll(midRaw) || playerList.containsAll(botRaw)
				|| playerList.containsAll(leftColomn) || playerList.containsAll(midColomn)
				|| playerList.containsAll(rightColomn) || playerList.containsAll(cross1)
				|| playerList.containsAll(cross2)) {
			System.out.println(" you win ");
			return true;
		}
		return false;

	}
}

class GUI {
	private static String convertZeroToBlankAnd12ToXO(int in) {
		String printed = "";
		if ( in == 0 )
			printed = " ";
		else if ( in == 1 )
			printed = "X";
		else if ( in == 1 )
			printed = "0";
		return printed;
	}

	private static String convertZeroToBlank(int in) {
		String printed = "";
		if ( in == 0 )
			printed = " ";
		else
			printed = in+"";
		return printed;
	}

	private static void printRow(int[] data) {
		for (int j = 0; j < 3; j++) {
			String printed = convertZeroToBlank(data[j]);
			System.out.print(printed);
			if ( j < 2 )
				System.out.print(" | ");
		}
		System.out.println();
	}
	
	public static void drawBoard(int[][] data) {
		System.out.println("\n");
		printRow(data[0]);
		System.out.println("--+---+--");
		printRow(data[1]);
		System.out.println("--+---+--");
		printRow(data[2]);
		System.out.println("\n");
	}

	// method to show players the game positions 
	public static void drawArrayPositions() {
		System.out.println("these are the position no. of your choice:");
		System.out.println();
		int[][] positions = { { 1,2,3 }, {4,5,6}, {7,8,9} };
		drawBoard(positions);
		System.out.println("\n\nlets started!\n");
	}
	
	// method to draw just the empty game (without positions)
	public static void drawEmptyArray() {
		int[][] emptyArray = new int[3][3];
		drawBoard(emptyArray);
		System.out.println("\n");
	}

}