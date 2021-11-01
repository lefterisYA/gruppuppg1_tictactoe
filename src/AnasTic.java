
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

public class AnasTic {

	static ArrayList<Integer> Choices = new ArrayList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
	static ArrayList<Integer> player1And2Choices = new ArrayList<>();
	static ArrayList<Integer> player1List = new ArrayList<>();
	static ArrayList<Integer> player2List = new ArrayList<>();
	static int noOfchoices = 0;
	static char[][] ticTac = new char[3][3];
	static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		
		
		drawArray();
		drawEmptyArray(ticTac);
		do {
			if(noOfchoices == 9 ) 
				break; 
				player1(ticTac);
				if (checkToWin(player1List))
				break;
				
			
			if(noOfchoices == 9)
				break; 
				player2(ticTac);
				if (checkToWin(player2List))
					break;
			
		}while ( noOfchoices <= 9);
		input.close();
		
	}

	// method to show the players the positions of their choices
	public static int[][] drawArray() {

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

	// method return empty char 2D Array
	public static char[][] drawEmptyArray(char[][] ticTac) {

//			   char[][] ticTac = new char [3][3];

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

	public static char[][] player1(char[][] ticTac) {

		int choice1 = 0;
		boolean flag = false;

		do {
			try {        // if player1 enter number out of range 1...9 or number which already taken from player 2 will throws exception

				System.out.print(" player1 choose your position: ");

				choice1 = input.nextInt();
				if ((choice1 < 1 || choice1 > 9) || (player1And2Choices.contains(choice1))) 
					throw new Exception();
				
				player1And2Choices.add(choice1);
				player1List.add(choice1);
				noOfchoices++;
				flag = true;
				
			} catch (Exception e) {
				input.nextLine();          // without this code here will be infinite loop
				System.out.println();
				System.out.println("your choice out of limit, choose again from this list:");
				Choices.removeAll(player1And2Choices);    // return ChoiceArray minus all players choices 
				System.out.print(Choices);
				System.out.println();
				System.out.println();
			}
		} while (!flag);

		switch (choice1) {
		case 1:
			ticTac[0][0] = 'X';
			break;
		case 2:
			ticTac[0][1] = 'X';
			break;
		case 3:
			ticTac[0][2] = 'X';
			break;
		case 4:
			ticTac[1][0] = 'X';
			break;
		case 5:
			ticTac[1][1] = 'X';
			break;
		case 6:
			ticTac[1][2] = 'X';
			break;
		case 7:
			ticTac[2][0] = 'X';
			break;
		case 8:
			ticTac[2][1] = 'X';
			break;
		case 9:
			ticTac[2][2] = 'X';
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

	public static char[][] player2(char[][] ticTac) {
		
		int choice2 = 0;
		boolean flag = false;

		do {
			try {
				System.out.print(" player2 choose your position: ");

				choice2 = input.nextInt();
				if ((choice2 < 1 || choice2 > 9) || (player1And2Choices.contains(choice2))) 
					throw new Exception();
				
				player1And2Choices.add(choice2);
				player2List.add(choice2);	 
				noOfchoices++;
				flag = true;
				
			} catch (Exception e) {
				
				input.nextLine();             // without this code here will be infinite loop
				System.out.println();
				System.out.println("your choice out of limit, choose again from this list:");
				Choices.removeAll(player1And2Choices);
				System.out.print(Choices);
				System.out.println();
				System.out.println();
			}
		} while (!flag);

		switch (choice2) {
		case 1:
			ticTac[0][0] = '0';
			break;
		case 2:
			ticTac[0][1] = '0';
			break;
		case 3:
			ticTac[0][2] = '0';
			break;
		case 4:
			ticTac[1][0] = '0';
			break;
		case 5:
			ticTac[1][1] = '0';
			break;
		case 6:
			ticTac[1][2] = '0';
			break;
		case 7:
			ticTac[2][0] = '0';
			break;
		case 8:
			ticTac[2][1] = '0';
			break;
		case 9:
			ticTac[2][2] = '0';
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
	
	public static boolean checkToWin(ArrayList<Integer> player ) {
		
		ArrayList <Integer>topRaw =      new ArrayList<>(Arrays.asList(1,2,3));
		ArrayList <Integer>midRaw =      new ArrayList<>(Arrays.asList(4,5,6));
		ArrayList <Integer>botRaw =      new ArrayList<>(Arrays.asList(7,8,9));
		ArrayList <Integer>leftColomn =  new ArrayList<>(Arrays.asList(1,4,7));
		ArrayList <Integer>midColomn =   new ArrayList<>(Arrays.asList(2,5,8));
		ArrayList <Integer>rightColomn = new ArrayList<>(Arrays.asList(3,6,9));
		ArrayList <Integer>cross1 =      new ArrayList<>(Arrays.asList(1,5,9));
		ArrayList <Integer>cross2 =      new ArrayList<>(Arrays.asList(3,5,7));
		
		if (player.containsAll(topRaw) ||  player.containsAll(midRaw) || player.containsAll(botRaw) ||
			player.containsAll(leftColomn) || player.containsAll(midColomn) || player.containsAll(rightColomn) ||
			player.containsAll(cross1) || player.containsAll(cross2)) {
				System.out.println(" you win ");
		return true ;
		}
		return false;
		
	}
}

