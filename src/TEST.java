
public class TEST {
	import java.util.ArrayList;
	import java.util.Arrays;
	import java.util.Scanner;

	public class TEST {
		String name ;
		char ch ;
		
		static int noOfChoices = 0;
		static char[][]ticTac = new char [3][3];
		static ArrayList<Integer> playersChoices = new ArrayList<>();
		 ArrayList<Integer> playerList = new ArrayList<>();
		 ArrayList<Integer> Choices = new ArrayList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
		
		
		


		public static void main(String[] args) {
			Scanner input = new Scanner(System.in);
			do {
			TEST t1 = new TEST("player1", 'X');
			t1.playerPosition(t1.playerChoose( input));
			


			if(noOfChoices == 9)
				break;
			
			TEST t2 = new TEST("player2", '0');
			t2.playerPosition(t2.playerChoose( input));
			}while (noOfChoices <= 9);
			

			input.close();
		}
		public static char[][] drawEmptyArray() {

			   ticTac = new char [3][3];

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
		public TEST (String name , char ch) {
			this.name = name ;
			this.ch = ch ;
//			this.playerList = playerList ;
		}
		
		public int playerChoose( Scanner input){	
			
			boolean flag = false;
			int choice=0;
			
			do {
			try {
				System.out.print(this.name +" choose your position: ");

				choice = input.nextInt();
				if ((choice < 1 || choice > 9) || (playersChoices.contains(choice))) 
					throw new Exception();
				
				playersChoices.add(choice);
//				playerList(choice);	 
				noOfChoices++;
				flag = true;
				
			} catch (Exception e) {
				
				input.nextLine();             // without this code here will be infinite loop
				System.out.println();
				System.out.println("your choice out of limit, choose again from this list:");
				Choices.removeAll(playersChoices);
				System.out.print(Choices);
				System.out.println();
				System.out.println();
			}
			} while (!flag);
			return choice ;

		}
		
//		public ArrayList<Integer> playerNumbers(int choice){
//			 ArrayList<Integer> playerList = new ArrayList<>();
//			 playerList.add(choice);
//			 return playerList ;
//		}
		
		public char [][] playerPosition(int choice ){
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

	public static boolean checkToWin(ArrayList<Integer> playerList ) {
			
			ArrayList <Integer>topRaw =      new ArrayList<>(Arrays.asList(1,2,3));
			ArrayList <Integer>midRaw =      new ArrayList<>(Arrays.asList(4,5,6));
			ArrayList <Integer>botRaw =      new ArrayList<>(Arrays.asList(7,8,9));
			ArrayList <Integer>leftColomn =  new ArrayList<>(Arrays.asList(1,4,7));
			ArrayList <Integer>midColomn =   new ArrayList<>(Arrays.asList(2,5,8));
			ArrayList <Integer>rightColomn = new ArrayList<>(Arrays.asList(3,6,9));
			ArrayList <Integer>cross1 =      new ArrayList<>(Arrays.asList(1,5,9));
			ArrayList <Integer>cross2 =      new ArrayList<>(Arrays.asList(3,5,7));
			
			if (playerList.containsAll(topRaw)     ||  playerList.containsAll(midRaw)   || playerList.containsAll(botRaw) ||
				playerList.containsAll(leftColomn) || playerList.containsAll(midColomn) || playerList.containsAll(rightColomn) ||
				playerList.containsAll(cross1)     || playerList.containsAll(cross2)) {
					System.out.println(" you win ");
			return true ;
			}
			return false;
			
		}	

}
