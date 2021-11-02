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
	// 0 betyder: ledig ruta
	// 1 betyder: rutan till spelare  1
	// 2 betyder: rutan till spelare  2
    int[][] board;
    boolean[] occupiedTiles = new boolean[9];

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

            int tile = playerChoose(sc, Integer.toString(currPlayer));
            occupiedTiles[tile-1] = true;
            int[] coord = sequentialToCoordinates(tile);

            board[coord[0]][coord[1]] = currPlayer;

            UI.draw(board);

            if ( WinCheckerSimple.check(board) ) {
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

    ArrayList<Integer> Choices = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
    static ArrayList<Integer> playersChoices = new ArrayList<>();
    static int noOfChoices = 0;
    
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
					 
				noOfChoices++;
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

class WinCheckerSimple {
    public static boolean check(int[][] board) {
		int[][] allPossibleLines = { 
				{1,2,3}, {4,5,6}, {7,8,9}, {1,4,7}, {2,5,8}, {3,6,9}, {1,5,9}, {3,5,7},
		};
		if ( checkIfTilesAreFree(allPossibleLines, board) ) {
			return true;
		}
		return false;
    }

    private static boolean checkIfTilesAreFree(int[][] allLines, int[][] boardIn) {
    	for ( int[] line : allLines ) {
    		if ( isWinningLine(line, boardIn) ) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    private static boolean isWinningLine(int[] line, int[][] boardIn) {
    	for ( int elem : line ) {
    		int x = (elem-1) % boardIn.length;
    		int y = (elem-1) / boardIn.length;
    		if ( boardIn[y][x] == 0 )
    			return false;
    	}
    	return true;
    }
}
