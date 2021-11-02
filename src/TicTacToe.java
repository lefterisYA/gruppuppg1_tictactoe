import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) {
//    	AnasTic anasTic = new AnasTic();
//    	AnasTic.anasRun(null);
    	//TEST.main(null);
    	//System.exit(0);
    	
        Scanner sc = new Scanner(System.in);

        Logic logic = new Logic(3);
        logic.play(sc);
        sc.close();
    }
}

class Logic {
	// 0 betyder: ledig ruta
	// 1 betyder: rutan till spelare  1
	// 2 betyder: rutan till spelare  2
    int[][] board;

    public Logic(int gridSize) {
        board=new int[gridSize][gridSize];
    }

    public void play(Scanner sc) {
        UI.draw(board);

        int currPlayer = 1;
        int playCnt = 0;
        int maxTurns = board.length * board.length;
   
        while ( ! WinChecker.check(board) && playCnt < maxTurns ) {
        	playCnt++;

            System.out.println( "Player " + currPlayer + 
            		" it's your turn. Input 1-9:");

            int tile = playerChoose(sc, Integer.toString(currPlayer));
            int[] coord = sequentialToCoordinates(tile);

            board[coord[0]][coord[1]] = currPlayer;

            UI.draw(board);

            currPlayer = nextPlayer(currPlayer);
         
        }

        System.out.println("Congratulations player " + currPlayer + "! You won!!");
    }
    
    // [0, 0] [0, 1] [0, 2]
    // [1, 0] [1, 1] [1, 2]
    // [2, 0] [2, 1] [2, 2]

    // Byter från en int 1-9, som användaren matar in, till y och x, 
    // alltså ex: [0,0], [1,0]
    private int[] sequentialToCoordinates(int box) {
    	int[] ret = new int[2];
    	ret[0] = (box-1) / 3; // y
    	ret[1] = (box-1) % 3; // x
    	
    	return ret;
    }

    // Just return the opposite:
    private int nextPlayer(int player) {
    	if ( player == 1 )
    		return 2;
    	else
    		return 1;
//		return player == 1 ? 2 : 1;
    }
    ArrayList<Integer> Choices = new ArrayList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
    static ArrayList<Integer> playersChoices = new ArrayList<>();
    static int noOfChoices = 0;
    //String name ;
    
	public int playerChoose( Scanner input, String name){	

		boolean flag = false;
		int choice=0;

		do {
			try {
				System.out.println(name +" choose your position: ");

				choice = input.nextInt();
				if ((choice < 1 || choice > 9) || (playersChoices.contains(choice))) 
					throw new Exception();

				playersChoices.add(choice);
				//				playerList(choice);	 
				noOfChoices++;
				flag = true;

			} catch (Exception e) {
				printTakenBox(input);
			}
		} while (!flag);
		return choice ;
	}
	
	void printTakenBox(Scanner input) {
		input.nextLine();             // without this code here will be infinite loop
		System.out.println();
		System.out.println("your choice out of limit, choose again from this list:");
		Choices.removeAll(playersChoices);
		System.out.print(Choices);
		System.out.println();
		System.out.println();
	}
}

/*
 * Should draw the board given a 2D array.
 */
class UI {
	public static void drawBoard(int[][] ticTac) {
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
	}
	
	public static void draw(int[][] board) {
		drawBoard(board);
		//drawUi(board);
		//drawDebug(board, "Board[][]:");
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
 * Should check whether any player has won by checking if there are three of the same boxes in the 2D array.
 */
class WinChecker {
	/* Check takes a 2D-array and returns true if there exists a straight
	 * line with all values equal and not zero. hej igen då!
	 */
    public static boolean check(int[][] board) {
    	/*
    	ArrayList <Integer>topRaw =      new ArrayList<>(Arrays.asList(1,2,3));
		ArrayList <Integer>midRaw =      new ArrayList<>(Arrays.asList(4,5,6));
		ArrayList <Integer>botRaw =      new ArrayList<>(Arrays.asList(7,8,9));
		ArrayList <Integer>leftColomn =  new ArrayList<>(Arrays.asList(1,4,7));
		ArrayList <Integer>midColomn =   new ArrayList<>(Arrays.asList(2,5,8));
		ArrayList <Integer>rightColomn = new ArrayList<>(Arrays.asList(3,6,9));
		ArrayList <Integer>cross1 =      new ArrayList<>(Arrays.asList(1,5,9));
		ArrayList <Integer>cross2 =      new ArrayList<>(Arrays.asList(3,5,7));
		
		horizoLines[0] == topRow
		
		
    	 */
    	int[][] horizoLines = new int[board.length][board.length];
    	int[][] verticLines = new int[board.length][board.length];
    	int[][] diagonLines = new int[2][board.length]; 
    	
    	for ( int i=0; i<board.length; i++) {
    		horizoLines[i] = board[i];
    	}

    	if ( checkListOfLines(horizoLines) )
    		return true;

    	verticLines = swapCoordinates(board);

    	if ( checkListOfLines(verticLines) )
    		return true;

    	diagonLines = genDiaLines(board);

    	//UI.drawDebug(horizoLines, "HorizLines");
    	//UI.drawDebug(verticLines, "VertiLines:");
    	//UI.drawDebug(diagonLines, "DiagoLines:");
    	
    	if ( checkListOfLines(diagonLines) )
    		return true;

    	return false;
    }
    
    private static boolean checkListOfLines(int[][] lines) {
    	for ( int i=0; i<lines.length; i++) {
    		if ( chkLineWins(lines[i]) )
    			return true;
    	}
    	return false;
    }

    /*
     * By transforming the board we get can easier iterate over the elements and so check the vertical lines too.
     * 123          147
     * 456          258
     * 789          369
     */
    private static int[][] swapCoordinates(int[][] boardIn) {
    	int[][] ret = new int[boardIn.length][boardIn.length];
    	for ( int i=0; i<boardIn.length; i++) {
    		for ( int u=0; u<boardIn.length; u++) {
    			ret[i][u] = boardIn[u][i];
    		}
    	}
    	return ret;
    }

    // Given a 1D-array, return whether all elements are identical and not zero
    private static boolean chkLineWins(int[] line) {
    	int frsNum = line[0];

    	for ( int i=1; i<line.length; i++) {
    		int currElem = line[i];

    		if ( currElem != frsNum || currElem == 0 )
    			return false;
    	}
        
    	return true;
    }
    
	private static int[][] genDiaLines(int[][] boardIn){
    	int[][] ret = new int[2][boardIn.length];
    	for ( int i=0; i<boardIn.length; i++) {
    		boardIn[0][i] = boardIn[i][i];
    		boardIn[1][i] = boardIn[i][boardIn.length-i-1];
    	}
		return ret;
	}
}
