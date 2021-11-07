package main;

/*
 * Meddelande till läraren:
 * Anas skrev det mesta i metoderna som du finner i klassen Player och UI.
 * Lefteris ändrade en hel del på strukturen men behöll Anas abstraktion för spelet, vilket förklarar varför
 * koden är lite bastardiserad.
 * 
 * Sen har lefteris fortsatt lite med att försöka skapa ett AI, vilket var varför klassen Player skapades. Det är lite
 * work in progress med de delarna.
 * 
 * Hela projektet är bifogad med gammal kod i seperata paket. Det relevanta för uppgiften ligger under main.
 * 
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import AiPlayer.*;

public class TicTacToe {
    public static void main(String[] args) {
    	System.out.println(System.getProperty("java.version"));
        Scanner sc = new Scanner(System.in);

        Logic logic = new Logic(3);
        logic.play(sc);
        sc.close();
    }
}

class Logic {
    int[][] board; // Betydelser: 0:ledig, 1:Spelare ETT, 2: Spelare TVÅ

    public Logic(int gridSize) {
        board=new int[gridSize][gridSize];
    }

    public Logic(int[][] board) { // for testing purposes.
        this.board=board;
    }

    public void play(Scanner sc) {
        UI.drawLegent(); 
        
        Player.addPlayer("1", false);
        Player.addPlayer("2", false);

        int playCnt = 0;
        int maxTurns = board.length * board.length;
        Player currPlayer = Player.getCurrPlayer();
   
        while (true) {
        	playCnt++;
            UI.drawBoard(board);

            if ( currPlayer.isAi() ) {
            	System.out.println( "Computer playing!" );
            	int[] aiChoice = currPlayer.getAi().miniMax(board, currPlayer.getPlayerNum());
            	currPlayer.addUsrChoice(XYToseq(aiChoice[0], aiChoice[1]));
            	try {
            	board[aiChoice[0]][aiChoice[1]]  = currPlayer.getPlayerNum()+1;
            	} catch (Exception e) {
            		System.out.println("error. Manual input.\ny:");
            		board[sc.nextInt()][sc.nextInt()] = currPlayer.getPlayerNum();
            	}
            } else {
            	// Ta in data från användare som får välja mellan 1-9.
            	System.out.println( "Player " + currPlayer.getPlayerName() + " it's your turn.");
            	int userChoice = currPlayer.playerChoose(sc);
            	currPlayer.addUsrChoice(userChoice);

            	// Gör om användardanat till X och Y koordinater och spara även i board.
            	int[] coord = sequentialToXY(userChoice);
            	int y=coord[0];
            	int x=coord[1];
            	board[y][x] = currPlayer.getPlayerNum()+1;
            }

            if ( currPlayer.hasWon() ) {
            	break;
            } else if ( playCnt >= maxTurns ) {
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
    
    private int XYToseq(int y, int x) {
    	return y*board.length+x+1;
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
}

class Player {
	static Player[] players; // = { new Players(), new Players() };
	static int activePlayer = 0;
    static ArrayList<Integer> Choices = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
    static ArrayList<Integer> playersChoices = new ArrayList<Integer>();
	final ArrayList<Integer> playerList; // Lista som innehåller alla de upptagna rutorna sekventiellt.
    final String name;
    final int playerNum;
    AiPlayer ai;
    
    public Player(String name, boolean isAi) {
    	this.name=name;
    	playerNum = players == null ? 0 : players.length;
    	
    	playerList = new ArrayList<Integer>();
    	if ( isAi == true )
    		ai = new AiPlayer();
    }
    
    public static void addPlayer(String name, boolean isAi) {
    	int oldLen = players != null ? players.length : 0;

    	Player[] newPlayers = new Player[oldLen+1];
    	Player newPlayer = new Player(name, isAi);
    	newPlayers[newPlayers.length-1] = newPlayer;

    	for ( int i=0; i<oldLen; i++ ) {
    		newPlayers[i] = players[i]; 
    	}
    	players = newPlayers;
    }
    
    public static Player getCurrPlayer() {
    	return players[activePlayer];
    }

    public int getPlayerNum() {
    	return playerNum;
    }
    
    public String getPlayerName() {
    	return name;
    }

    public boolean isAi() {
    	return ai != null;
    }
    
    public AiPlayer getAi() {
    	return ai;
    }

    public static Player nextPlayer() {
    	activePlayer = activePlayer == 0 ? 1 : 0;
    	return getCurrPlayer();
    }

	public int playerChoose(Scanner input){	
		while (true) {
			try {
				Choices.removeAll(playersChoices); // Get valid choices.
				System.out.println( "Input one of the following " + Choices + ":");

				int choice = input.nextInt();
				if ((choice < 1 || choice > 9) || (playersChoices.contains(choice))) 
					throw new Exception();

				return choice;

			} catch (Exception e) {
				System.out.println("Your choice out of limit!");
			}
		}
	}
	
	public void addUsrChoice(int choice) {
		playerList.add(choice);
		playersChoices.add(choice);
	}

	// method to check if player's choices have this standard win numbers so it will be break and this player wins 
	public boolean hasWon() {
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
			return true;
		}
		return false;
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
	
	public static void drawBoard(int[][] ticTac) {
		drawBoard(ticTac, false);
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
