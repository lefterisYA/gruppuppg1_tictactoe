import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Logic logic = new Logic(3);
        logic.play(sc);
        sc.close();
    }
}

class Logic {
    int[][] board; // Betydelser: 0:ledig, 1:Spelare ETT, 2: Spelare TVÅ

    // Skapa en lista av två winchecker, en för varje spelare.
    CheckToWin[] winCheckers = { new CheckToWin(), new CheckToWin() };

    public Logic(int gridSize) {
        board=new int[gridSize][gridSize];
    }

    public void play(Scanner sc) {
        UI.draw(board);

        int currPlayer = 1;
        int playCnt = 0;
        int maxTurns = board.length * board.length;
   
        while (true) {
        	playCnt++;
        	// Växla mellan winchekers beroende på spelare från listan
        	CheckToWin winChecker = winCheckers[currPlayer-1];

        	// Ta in data från användare som får välja mellan 1-9.
            int userChoice = playerChoose(sc, Integer.toString(currPlayer));
            winChecker.addUsrChoice(userChoice);

            // Gör om användardanat till X och Y koordinater och spara även i board.
            int[] coord = sequentialToXY(userChoice);
            int y=coord[0];
            int x=coord[1];
            board[y][x] = currPlayer;

            UI.draw(board);

//            if ( WinChecker.check(board) ) {
            if ( winChecker.check() ) {
            	break;
            } else if ( playCnt >= maxTurns ) {
            	currPlayer = 0;
            	break;
            }

            currPlayer = nextPlayer(currPlayer);
        }

        if ( currPlayer == 0 ) {
        	System.out.println("Ah, game ended in draw!");
        } else {
        	System.out.println("Congratulations player " + currPlayer + "! You won!!");
        }
    }
    
    // Byter från en int 1-9, som användaren matar in, till y och x, alltså ex: [0,0], [1,0]
    private int[] sequentialToXY(int userChoice) {
		switch (userChoice) {
		case 1:
			return new int[] {0,0};
		case 2:
			return new int[] {0,1};
		case 3:
			return new int[] {0,2};
		case 4:
			return new int[] {1,0};
		case 5:
			return new int[] {1,1};
		case 6:
			return new int[] {1,2};
		case 7:
			return new int[] {2,0};
		case 8:
			return new int[] {2,1};
		case 9:
			return new int[] {2,2};
		}
		return new int[] {0,0};

		// SAMMA SOM:
		// int[] ret = new int[2];
		// ret[0] = (box-1) / 3; // y
		// ret[1] = (box-1) % 3; // x
		// return ret;
    }

    // Just return the opposite:
    private int nextPlayer(int player) {
    	if ( player == 1 )
    		return 2;
    	else
    		return 1;
    	// SAMMA SOM:
    	// return player == 1 ? 2 : 1;
    }

    static ArrayList<Integer> Choices = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
    static ArrayList<Integer> playersChoices = new ArrayList<Integer>();
    
	public int playerChoose( Scanner input, String name){	
		boolean flag = false;
		int choice=0;

		do {
			try {
				System.out.println( "Player " + name + 
						" it's your turn. Input on of " + getValidChoices() + ":");

				choice = input.nextInt();
				if ((choice < 1 || choice > 9) || (playersChoices.contains(choice))) 
					throw new Exception();

				playersChoices.add(choice);
					 
				flag = true;

			} catch (Exception e) {
				printBoxTakenError();
			}
		} while (!flag);
		return choice ;
	}
	
	void printBoxTakenError() {
		System.out.println();
		System.out.println();
		System.out.println("Your choice out of limit!\n");
		System.out.println(getValidChoices());
	}
	
	ArrayList<Integer> getValidChoices() {
		Choices.removeAll(playersChoices);
		return Choices;
	}
}

/*
 * Should draw the board given a 2D array.
 */
class UI {
	public static void drawBoard(int[][] ticTac) {
		System.out.print(" "+"\n");
		for (int i = 0; i < 2; i++) {
			System.out.print(" ");
			for (int j = 0; j < 2; j++) {
				System.out.print(ticTac[i][j] + " | ");
			}

			System.out.print(ticTac[i][2]);
			System.out.println();
			System.out.println("---+---+---");
		}
		System.out.print(" ");
		for (int j = 0; j < 2; j++) {
			System.out.print(ticTac[2][j] + " | ");
		}
		System.out.print(ticTac[2][2]);
		System.out.println();
		System.out.println();
		System.out.println();
	}
	
	public static void draw(int[][] board) {
//		drawDebug(board, "Board[][]:");
		drawBoard(board);
		//drawUi(board);
	}
	
    public static void drawDebug(int[][] board, String title) {
    	System.out.println(title);
//    	System.out.println("int[][] board;");
		for ( int y = 0; y<board.length; y++ ) {
//			System.out.print("int["+y+"]: int[]:");
			for ( int x = 0; x<board[y].length; x++ ) {
				System.out.print(board[y][x]);
			}
//			System.out.print("  ");
//			for ( int x = 0; x<board.length; x++ ) {
//				System.out.print(y+":"+board[y][x]+" ");
//			}
			System.out.println("");
		}
    }
	public static void drawUi(int[][]board) {
		System.out.println("");
	}
}

/*
 * Klassen skapar objekt som tillhör spelarna. Den innehåller data och metoder som möjliggör att man 
 * kan kontrollera ifall spelet har avslutats.
 */
class CheckToWin{
	// Lista som innehåller alla de upptagna rutorna sekventiellt.
	ArrayList<Integer> playerList;
	
	public CheckToWin() {
		playerList = new ArrayList<Integer>();
	}
	
	void addUsrChoice(int choice) {
		playerList.add(choice);
	}

	// method to check if player's choices have this standard win numbers so it will be break and this player wins 
	boolean check() {
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










/*
 * #################
 * ANVÄNDS INTE FÖR TILLFÄLLET:
 * #################
 * #################
 * #################
 * #################
 * 
 * 
 */






/*
 * Should check whether any player has won by checking if there are three of the same boxes in the 2D array.
 */
class WinChecker {
	
//	private ArrayList<Integer> flattenBoard(int[][] board){
//		for ( int y=0; y<board.length; y++) {
//			for ( int x=0; x<board.length; x++) {
//
//			}
//		}
//	}
//
	/* 
	 * Takes a 2D-array and returns true if there exists a straight
	 * line with all values equal and not zero.
	 */
    public static boolean check(int[][] board) {
    	int[][] horizoLines = new int[board.length][board.length];
    	int[][] verticLines = new int[board.length][board.length];
    	int[][] diagonLines = new int[2][board.length]; 
    	
    	horizoLines = LineGenerator.genHorizonLines(board);

    	if ( checkListOfLines(horizoLines) )
    		return true;

    	verticLines = LineGenerator.genVerticLines(board);

    	if ( checkListOfLines(verticLines) )
    		return true;

    	diagonLines = LineGenerator.genDiagonLines(board);

    	if ( checkListOfLines(diagonLines) )
    		return true;

    	return false;
    }
 
    private static boolean checkListOfLines(int[][] lines) {
    	for ( int i=0; i<lines.length; i++) {
    		if ( allElemsAreEqualAndNotZero(lines[i]) )
    			return true;
    	}
    	return false;
    }

    // Given a 1D-array, return whether all elements are identical and not zero
    private static boolean allElemsAreEqualAndNotZero(int[] line) {
    	int frsNum = line[0];

    	for ( int i=1; i<line.length; i++) {
    		int currElem = line[i];

    		if ( currElem != frsNum || currElem == 0 )
    			return false;
    	}
        
    	return true;
    }
    
}

class LineGenerator {
	public static int[][] genHorizonLines(int[][] boardIn){
		return boardIn;
	}

	public static int[][] genDiagonLines(int[][] boardIn){
    	int[][] ret = new int[2][boardIn.length];
    	for ( int i=0; i<boardIn.length; i++) {
    		ret[0][i] = boardIn[i][i];
    		ret[1][i] = boardIn[i][boardIn.length-i-1];
    	}
		return ret;
	}

    public static int[][] genVerticLines(int[][] boardIn) {
    	int[][] ret = new int[boardIn.length][boardIn.length];
    	/*
    	 * By swapping x & y in the board we get a list of vertical lines:
    	 * 123          147   [0,0] -> [0,0] | [0,1] -> [1,0] | [0,2] -> [2,0]
    	 * 456          258   [1,0] -> [0,1] | [1,1] -> [1,1] | [1,2] -> [2,1]
    	 * 789          369   [2,0] -> [0,2] | [2,1] -> [2,1] | [2,2] -> [2,2]
    	 */
    	for ( int i=0; i<boardIn.length; i++) {
    		for ( int u=0; u<boardIn.length; u++) {
    			ret[i][u] = boardIn[u][i];
    		}
    	}
    	return ret;
    }
}
