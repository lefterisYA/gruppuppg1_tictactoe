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
	// 1 betyder: rutan till spelare 1
	// 2 betyder: rutan till spelare  2
    int[][] board;

    public Logic(int gridSize) {
        board=new int[gridSize][gridSize];
    }

    public void play(Scanner sc) {
        UI.draw(board);

        int currPlayer = 2;
        int playCnt = 0;
        while ( ! WinChecker.check(board, currPlayer, playCnt) ) {
        	playCnt++;
            currPlayer = nextPlayer(currPlayer);

            System.out.println("Player " + 
            		currPlayer + " it's your turn. Input 1-9:");

            int tile = sc.nextInt();
            int[] coord = sequentialToCoordinates(tile);

            board[coord[0]][coord[1]] = currPlayer;

            UI.draw(board);
        }

        System.out.println("Congratulations player " + currPlayer + "! You won!!");
    }
    
    private int[] sequentialToCoordinates(int tile) {
    	int[] ret = new int[2];
    	ret[0] = (tile-1) / 3;
    	ret[1] = (tile-1) % 3;
    	
    	return ret;
    }

    // Just return the opposite:
    private int nextPlayer(int player) {
		return player == 1 ? 2 : 1;
    }
}

/*
 * Should draw the board given a 2D array.
 */
class UI {
	public static void draw(int[][] board) {
		drawUi(board);
		drawDebug(board);
	}
	
    public static void drawDebug(int[][] board) {
		for ( int y = 0; y<board.length; y++ ) {
			for ( int x = 0; x<board.length; x++ ) {
				System.out.print(board[y][x]);
			}
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
	 * line with all values equal and not zero.
	 */
    public static boolean check(int[][] board, int currPlayer, int playCnt) {
      // TODO
    	return false;
    }

    /*
     * By transforming the board we get can easier iterate over the elements and so check the vertical lines too.
     */
    private static int[][] rotateBoard(int[][] boardIn) {
      // TODO
    	return null;
    }

    // Given a 1D-array, return whether all tiles are identical.
    private static boolean chkLineWins(int[] line) {
        //TODO
    	return false;
    }
    
	private static int[][] genDiaLines(int[][] boardIn){

		return null;
	}
}
