

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TEST {
	String name;
	char ch;
	int choice;

	static int noOfChoices = 0;
	static char[][] ticTac = new char[3][3];
	static ArrayList<Integer> playersChoices = new ArrayList<>();    // Array for all players choices 
	ArrayList<Integer> playerList = new ArrayList<Integer>();        // every object has own Array for his choices
	ArrayList<Integer> Choices = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));    // players have to choose from  this numbers limit 

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		drawArrayPositions();
		drawEmptyArray();

		TEST t1 = new TEST("player1", 'X');
		TEST t2 = new TEST("player2", '0');
		do {
			t1.playerPosition(t1.playerChoose(input));
			System.out.println(t1.name + " this is your choices "+ t1.playerList);
			if (t1.checkToWin(t1.playerList))
				break;

			if (noOfChoices == 9)
				break;

			t2.playerPosition(t2.playerChoose(input));
			System.out.println(t2.name + " this is your choices "+ t2.playerList);
			if (t2.checkToWin(t2.playerList))
				break;

		} while (noOfChoices <= 9);
		System.out.println("game over.");

		input.close();
	}
	// method to show players the game positions 
	public static int[][] drawArrayPositions() {

		System.out.println("these are the position no. of your choice:");
		System.out.println();
		int[][] positions = new int[3][3];
		positions[0][0] = 1;
		positions[0][1] = 2;
		positions[0][2] = 3;
		positions[1][0] = 4;
		positions[1][1] = 5;
		positions[1][2] = 6;
		positions[2][0] = 7;
		positions[2][1] = 8;
		positions[2][2] = 9;

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				System.out.print(positions[i][j] + " | ");
			}

			System.out.print(positions[i][2]);
			System.out.println();
			System.out.println("--+---+--");

		}
		for (int j = 0; j < 2; j++) {
			System.out.print(positions[2][j] + " | ");
		}
		System.out.print(positions[2][2]);
		System.out.println();
		System.out.println();
		System.out.println("lets started!");
		System.out.println();
		return positions;
	}
	// method to draw just the empty game (without positions)
	public static char[][] drawEmptyArray() {

		ticTac = new char[3][3];

		ticTac[0][0] = ' ';
		ticTac[0][1] = ' ';
		ticTac[0][2] = ' ';
		ticTac[1][0] = ' ';
		ticTac[1][1] = ' ';
		ticTac[1][2] = ' ';
		ticTac[2][0] = ' ';
		ticTac[2][1] = ' ';
		ticTac[2][2] = ' ';
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				System.out.print(ticTac[i][j] + " | ");
			}

			System.out.print(ticTac[i][2]);
			System.out.println();
			System.out.println("--+---+--");

		}
		for (int j = 0; j < 2; j++) {
			System.out.print(ticTac[2][j] + " | ");
		}
		System.out.print(ticTac[2][2]);
		System.out.println();
		System.out.println();
		return ticTac;

	}

	public TEST(String name, char ch) {
		this.name = name;
		this.ch = ch;

	}	 
	// method to take a choice from every player but must be within range 1...9 and just numbers otherwise it throws exceptions
	public int playerChoose(Scanner input) {

		boolean flag = false;

		do {
			try {
				System.out.print(this.name + " choose your position: ");

				choice = input.nextInt();
				if ((choice < 1 || choice > 9) || (playersChoices.contains(choice)))
					throw new Exception();

				playersChoices.add(choice);
				playerList.add(choice);
//			playerNumbers(choice);

				noOfChoices++;
				flag = true;

			} catch (Exception e) {

				input.nextLine(); // without this code here will be infinite loop
				System.out.println();
				System.out.println("your choice out of limit, choose again from this list:");
				Choices.removeAll(playersChoices);
				System.out.print(Choices);
				System.out.println();
				System.out.println();
			}
		} while (!flag);
		return choice;

	}
	// method show the position after every player's choice 

	public char[][] playerPosition(int choice) {
		switch (choice) {
		case 1:
			ticTac[0][0] = this.ch;
			break;
		case 2:
			ticTac[0][1] = this.ch;
			break;
		case 3:
			ticTac[0][2] = this.ch;
			break;
		case 4:
			ticTac[1][0] = this.ch;
			break;
		case 5:
			ticTac[1][1] = this.ch;
			break;
		case 6:
			ticTac[1][2] = this.ch;
			break;
		case 7:
			ticTac[2][0] = this.ch;
			break;
		case 8:
			ticTac[2][1] = this.ch;
			break;
		case 9:
			ticTac[2][2] = this.ch;
			break;

		}
		System.out.println();
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				System.out.print(ticTac[i][j] + " | ");
			}

			System.out.print(ticTac[i][2]);
			System.out.println();
			System.out.println("--+---+--");

		}
		for (int j = 0; j < 2; j++) {
			System.out.print(ticTac[2][j] + " | ");
		}
		System.out.print(ticTac[2][2]);
		System.out.println();
		System.out.println();
		System.out.println();

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