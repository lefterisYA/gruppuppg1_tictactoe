package main;

import java.util.Scanner;
import AiPlayer.*;

public class TicTacToe {
    public static void main(String[] args) {
    	System.out.println(System.getProperty("java.version"));
        Scanner sc = new Scanner(System.in);

		int[][] testBoard = new int[][] {
				{1,0,2},
				{2,0,0},
				{2,1,1}
		};

		int[][] testBoard2 = new int[][] {
				{1,0,2},
				{2,0,0},
				{0,1,0}
		};

		Board board = new Board(3);
        Logic logic = new Logic();
        logic.play(sc, board);
        sc.close();
    }
}

class Logic {
    public void play(Scanner sc, Board board) {
        UI.drawLegent(); 
        
        Player.addPlayer("1", false);
        Player.addPlayer("2", true);

    	AiPlayer ai2 = new AiPlayer();
//        Player.addPlayer("1", true);
//        Player.addPlayer("2", false);

        Player currPlayer = Player.getCurrPlayer();
   
        while (true) {
            UI.drawBoard(board);

            if ( currPlayer.isAi() ) {
            	ai2.miniMax(board, currPlayer.getOppo());
            	System.out.println( "Computer playing!" );
            	int[] aiChoice = currPlayer.getAi().miniMax(board, currPlayer);
            	board.placeTile(aiChoice[0], aiChoice[1], currPlayer.getPlayerNum());
            } else {
            	System.out.println( "Player " + currPlayer.getPlayerName() + " it's your turn.");
//            	ai2.miniMax(board, currPlayer);
            	int userChoice = currPlayer.playerChoose(sc, board);
            	board.placeTile(userChoice, currPlayer.getPlayerNum());
            }

            if ( board.hasWinner() ) {
            	break;
            } else if ( board.isFull() ) {
            	currPlayer = null;
            	break;
            }
        	currPlayer = Player.nextPlayer();
        }

        UI.drawBoard(board);
        
        if ( currPlayer == null ) {
        	System.out.println("Ah, game ended in draw!");
        } else {
        	System.out.println("Congratulations player " + currPlayer.getPlayerName() + "! You won!!");
        }
    }
}

/*
 * Should draw the board given a 2D array.
 */
class UI {
	// Draws the board with all the positions as the uer sees them.
	public static void drawLegent() {
		System.out.println("Positions:");
		int[][] nums = {{1,2,3},{4,5,6},{7,8,9}};
		drawBoard(nums, true);
	}
	
	public static void drawBoard(Board board) {
		drawBoard(board.getBoard(), false);
	}
	
	private static void drawBoard(int[][] ticTac, boolean isLegent) {
		System.out.print(" "+"\n");
		for (int i = 0; i < 2; i++) {
			System.out.print(" ");
			for (int j = 0; j < 2; j++) {
				System.out.print(getPrintSymbol(ticTac[i][j], isLegent) + " | ");
			}

			System.out.print(getPrintSymbol(ticTac[i][2], isLegent));
			System.out.println();
			System.out.println("---+---+---");
		}
		System.out.print(" ");
		for (int j = 0; j < 2; j++) {
			System.out.print(getPrintSymbol(ticTac[2][j], isLegent) + " | ");
		}
		System.out.print(getPrintSymbol(ticTac[2][2], isLegent));
		System.out.println("\n\n");
	}
	
	// Change Ints to what the user sees. 
	// If isLegent, don't change anything so we can still print 1,2,3,4 and not X,O,3,4..
	private static String getPrintSymbol(int val, boolean isLegent) {
		if (isLegent)
			return Integer.toString(val);
		else if ( val == 0 )
			return " ";
		else if ( val == 1 )
			return "X";
		else
			return "O";
	}
}
