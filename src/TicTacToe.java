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
    int[][] board;

    public Logic(int gridSize) {
        board=new int[gridSize][gridSize];
    }

    public void play(Scanner sc) {
        UI.draw(board);

        int player = 2;
        int playCnt = 0;
        while ( ! WinChecker.check(board, player, playCnt) ) {
        	playCnt++;
            player = nextPlayer(player);

            System.out.println("Input 1-9:");
            int tile = sc.nextInt();

            int y = (tile-1) / 3;
            int x = (tile-1) % 3;

            board[y][x] = player;

            UI.draw(board);
        }

        System.out.println("Congratulations player " + player + "! You won!!");
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
    public static void draw(int[][]board) {
		System.out.println("-"+"--".repeat(board.length));
		for ( int y = 0; y<board.length; y++ ) {
			System.out.print("|");
			for ( int x = 0; x<board.length; x++ ) {
				String tile = board[y][x] == 1 ? "X" : board[y][x] == 2 ? "O" : " "; 
				System.out.print(tile+"|");
			}
			System.out.println("\n-"+"--".repeat(board.length));
		}
    }
}

/*
 * Should check whether any player has won by checking if there are three of the same boxes in the 2D array.
 */
class WinChecker {
    public static boolean check(int[][] board, int currPlayer, int playCnt) {
      // TODO
    	return false;
    }

    // By transforming the board we get can easier iterate over the elements and so check the vertical lines too.
    private static int[][] rotateBoard(int[][] boardIn) {
      // TODO
    	return null;
    }

    // Given a 1D-array, return whether all tiles are identical.
    private static boolean chkLineWins(int[] line) {
        //TODO
    	return false;
    }
}
