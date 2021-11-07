package wip;
// WIP:
class Wip {
	
}
class WinCheckerSimple2 {
	/*
	 * Takes a 2D-array of length 3 and checks every single possible straight line.
	 */
    public static boolean check(int[][] board) {
		int[][] allPossibleLines = { 
				{1,2,3}, {4,5,6}, {7,8,9}, {1,4,7}, {2,5,8}, {3,6,9}, {1,5,9}, {3,5,7},
		};
		int[][] allPossibleLinesCoord = { 
				{1,2,3}, {4,5,6}, {7,8,9}, {1,4,7}, {2,5,8}, {3,6,9}, {1,5,9}, {3,5,7},
		};
		if ( containsWinningLine(allPossibleLines, board) ) {
			return true;
		}
		return false;
    }

    /* 
     * Returns true if all elements of int[] line are are the same and not 0.
     */
    private static boolean containsWinningLine(int[][] allLines, int[][] boardIn) {
    	for ( int[] line : allLines ) {
    		if ( isWinningLine(line, boardIn) ) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    /* 
     * Returns true if all elements of int[] are non-zero and equal.
     */
    private static boolean isWinningLine(int[] line, int[][] boardIn) {
//    	int[] fstElemPos = seqTo2D(line[0], boardIn.length);
//
//    	for ( int i=1; i<line.length; i++) {
//    		int x = (elem-1) % boardIn.length;
//    		int y = (elem-1) / boardIn.length;
//    		if ( boardIn[y][x] == 0 )
//    			return false;
//    	}
//    	return true;
    	return false;
    }
    

}
//class Board {
//	private static int[][] board;
//	boolean[] occupiedTiles;
//	
//	public Board(int boardSize) {
//		board = new int[boardSize][boardSize];
//		occupiedTiles = new boolean[boardSize];
//	}
//
//	public static int getSize() {
//		return board.length;
//	}
//	
//	public int[][] getBoard(){
//		return board;
//	}
//	
//	public int[] getAllVals() {
//		int i=0;
//		int[] ret = new int[getSize()];
//		for ( int y=0; y<board.length; y++) {
//			for ( int x=0; x<board.length; x++) {
//				ret[i] = board[y][x];
//				i++;
//			}
//		}
//		return ret;
//	}
//	
//	public int getValAt(int seq) {
//		return getValAt(getCoordX(seq), getCoordY(seq));
//	}
//
//	public int getValAt(int x, int y) {
//		return board[y][x];
//	}
//
//	public boolean placeToken(int seq, char player) {
//		return placeToken(getCoordX(seq), getCoordY(seq), player);
//	}
//	
//	public boolean placeToken(int x, int y, char player) {
//		if ( isNotOccupied(x, y) ) {
//			board[y][x] = player;
//			return true;
//		}
//		return false;
//	}
//	
//	public boolean isNotOccupied(int x, int y) {
//		return board[y][x] == 0;
//	}
//
//    //Takes a position in sequencial coordinates (ex 1-9), and return 2D-coordinates (ex {2,1})
//    private static int[] toYXCoords(int seqInt){
//    	int x = getCoordX(seqInt);
//    	int y = getCoordY(seqInt);
//    	return new int[] {x, y};
//    }
//
//    //Takes a position in 2D-coordinates and returns it in Sequencial coordinates
//    private static int toSeqCoords(int x, int y){
//    	return y * Board.getSize() + x;
//    }
//    
//    private static int getCoordX(int seqInt) {
//    	return (seqInt-1) % Board.getSize();
//    }
//
//    private static int getCoordY(int seqInt) {
//    	return (seqInt-1) / Board.getSize();
//    }
//}
//}
//
//class Player {
//	String name;
//	char symbol;
//	boolean[][] tiles;
//	
//	public Player(String name, char symbol, int boardSize) {
//		this.name = name;
//		this.symbol = symbol;
//		tiles = new boolean[boardSize][boardSize];
//	}
//	
//	public String getName() {
//		return name;
//	}
//	
//	public char getSymbol() {
//		return symbol;
//	}
//}
//
//class Board2 {
//	static Tile[][] board;
//	boolean[] occupiedTiles;
//	
//	public Board2(int boardSize) {
//		board = new Tile[boardSize][boardSize];
//		occupiedTiles = new boolean[boardSize];
//	}
//	
//	public static int getSize() {
//		return board.length;
//	}
//	
//	private Tile[] getBoardInSeq() {
//		int i=0;
//		Tile[] ret = new Tile[board.length*board.length];
//		for ( int y=0; y<board.length; y++) {
//			for ( int x=0; x<board.length; x++) {
//				ret[i] = board[y][x];
//			}
//		}
//		return ret;
//	}
//	
//	public int getValAt(int x, int y) {
//		return board[y][x].val;
//	}
//	
//	public int[][] getBoardVals() {
//		int[][] ret = new int[board.length][board.length];
//		for ( int y=0; y<board.length; y++) {
//			for ( int x=0; x<board.length; x++) {
//				ret[y][x] = board[y][x].val;
//			}
//		}
//		return ret;
//	}
//
//	public boolean[][] getBoardBools() {
//		boolean[][] ret = new boolean[board.length][board.length];
//		for ( int y=0; y<board.length; y++) {
//			for ( int x=0; x<board.length; x++) {
//				ret[y][x] = board[y][x].isOccupied();
//			}
//		}
//		return ret;
//	}
//	
//	public boolean[] getUnoccupiedTilesSeq() {
//		boolean ret[] = new boolean[board.length];
//		for ( int i=0; i<board.length; i++) {
//			ret[i] = !occupiedTiles[i];
//		}
//		return ret;
//	}
//
//	public boolean placeToken(int seqTile, char player) {
//		occupiedTiles[seqTile-1] = true;
//		int x = (seqTile-1) % board.length;
//		int y = (seqTile-1) / board.length;
//		
//		return placeToken(x, y, player);
//	}
//
//	private boolean placeToken(int x, int y, char player) {
//		return board[x][y].setVal(player);
//	}
//	
//	public void drawBoard() {
//		for ( int y=0; y<board.length; y++) {
//			for ( int x=0; x<board.length; x++) {
//				System.out.print(board[x][y]);
//			}
//		}
//	}
//}
//
//class Tile {
//	public int val;
//	public final Coordinate coordinate;
//	
//	public Tile(int val, Coordinate coordinate) {
//		this.val = val;
//		this.coordinate = coordinate;
//	}
//
//	public Tile(int val, int x, int y) {
//		this.val = val;
//		this.coordinate = new Coordinate(x, y);
//	}
//
//	public Tile(int val, int seq) {
//		this.val = val;
//		this.coordinate = new Coordinate(seq);
//	}
//	
//	public boolean isOccupied() {
//		return val == 0 ? false : true;
//	}
//	
//	/*
//	 * A tile can change value only once: from 0-1/2, i.e. when a token is placed.
//	 */
//	public boolean setVal(int newVal) {
//		if ( ! isOccupied() ) {
//			val = newVal;
//			return true;
//		}
//		return false;
//	}
//}
//
//class Coordinate {
//	public final int seq;
//	public final int x;
//	public final int y;
//	
//	public Coordinate(int seq) {
//		int[] coord = to2D(seq);
//		this.x = coord[0];
//		this.y = coord[1];
//		this.seq = toSeq(x, y);
//	}
//	
//	public Coordinate(int x, int y) {
//		this.x = x;
//		this.y = y;
//		this.seq = toSeq(x, y);
//	}
//	
//    /*
//     * Takes a position in sequencial coordinates (ex 1-9), and return 2D-coordinates [
//     */
//    private static int[] to2D(int seqInt){
//    	int x = (seqInt-1) % Board.getSize();
//    	int y = (seqInt-1) / Board.getSize();
//    	return new int[] {x, y};
//    }
//
//    /*
//     * Takes a position in 2D-coordinates and returns it in Sequencial coordinates
//     */
//    private static int toSeq(int x, int y){
//    	return y * Board.getSize() + x;
//    }
//}
