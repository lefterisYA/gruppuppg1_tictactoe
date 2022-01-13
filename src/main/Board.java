package main;

import AiPlayer.WinChecker;

public class Board {
    public final int[][] board; // Betydelser: 0:ledig, 1:Spelare ETT, 2: Spelare TVÅ
    public final int sideLen;
    public final int maxTurns;
    private int occupiedTiles = 0;

    public Board(int gridSize) {
        board=new int[gridSize][gridSize];
        sideLen=gridSize;
        maxTurns = gridSize*gridSize;
    }

    public Board(int[][] board) { // for testing purposes.
        this.board=board;
        sideLen=board.length;
        maxTurns = sideLen*sideLen;
    }
    
    public Board deepCopy() {
    	Board ret = new Board(sideLen);
    	ret.occupiedTiles = occupiedTiles;
		for ( int y=0; y<sideLen; y++ ) {
			for ( int x=0; x<sideLen; x++ ) {
				int val = board[y][x];
				ret.board[y][x] = val;
			}
		}
    	return ret;
    }
    
    public int[][] getBoard(){
    	return board;
    }
    
    public boolean placeTile(int n, int pNum) {
    	int[] yx = sequentialToYX(n);
    	return placeTile(yx[0], yx[1], pNum);
    }

    public boolean placeTile(int y, int x, int pNum) {
    	if ( board[y][x] != 0 )
    		return false;
    	board[y][x] = pNum;
    	occupiedTiles++;
    	return true;
    }
    
    public boolean isFree(int n) {
    	int[] yx = sequentialToYX(n);
    	return isFree(yx[0], yx[1]);
    }

    public boolean isFree(int y, int x) {
    	return board[y][x] == 0;
    }
    
    public int getFreeTilesQnt() {
    	return maxTurns-occupiedTiles;
    }
    
    public String getFreeTilesSeq() {
    	String ret="";
    	int[][] freeTiles = getFreeTilesYX();
    	for ( int i=0; i<freeTiles.length-1; i++)
    		ret += YXToseq(freeTiles[i][0], freeTiles[i][1]) + ", ";
    	return ret + YXToseq(freeTiles[freeTiles.length-1][0], freeTiles[freeTiles.length-1][1]);
    }

    public int[][] getFreeTilesYX() {
    	int[][] ret = new int[getFreeTilesQnt()][2];
    	int i=0;
    	for (int y=0; y<sideLen; y++) {
    		for (int x=0; x<sideLen; x++) {
    			if ( board[y][x] == 0 ) {
    				ret[i++] = new int[] { y, x };
    				if ( i >= (getFreeTilesQnt()) )
    					break;
    			}
    		}
    	}
    	return ret;
    }
    
    private int YXToseq(int y, int x) {
    	return y*sideLen+x+1;
    }
    
    // Byter från en int 1-9, som användaren matar in, till y och x, alltså ex: [0,0], [1,0]
    private int[] sequentialToYX(int tile) {
		 int[] ret = new int[2];
		 ret[0] = (tile-1) / 3; // y
		 ret[1] = (tile-1) % 3; // x
		 return ret;
    }
    
    public boolean isFull() {
    	return occupiedTiles == maxTurns;
    }
    
    public boolean hasWinner() {
    	return WinChecker.check(board);
    }
}