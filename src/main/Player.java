package main;

import java.util.Scanner;

import AiPlayer.AiPlayer;

public class Player {
	static Player[] players; // = { new Players(), new Players() };
	static int activePlayer = 0;
    final String name;
    final int playerNum;
    AiPlayer ai;
    
    public Player(String name, boolean isAi) {
    	this.name=name;
    	playerNum = players == null ? 0 : players.length;
    	
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
    	return playerNum+1;
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
    
    public static Player getInactive() {
    	int oppo = activePlayer == 0 ? 1 : 0;
    	return players[oppo];
    }

    public static int getOppoNum(int currPlayNum) {
    	return currPlayNum == 1 ? 2 : 1;
    }
    
    public Player getOppo() {
    	int oppo = activePlayer == 0 ? 1 : 0;
    	return players[oppo];
    }

	public int playerChoose(Scanner input, Board board){	
		while (true) {
			System.out.println( "Input one of the following " + board.getFreeTilesSeq() + ":");
			int choice = input.nextInt();

			if ( board.isFree(choice) )
				return choice;
			else
				System.out.println("Your choice out of limit!");
		}
	}
}