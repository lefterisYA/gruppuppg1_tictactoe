package AiPlayer;

/*
 * Should check whether any player has won by checking if there are three of the same boxes in the 2D array.
 */
public class WinChecker {
	public static void main(String[] args) {
		int[][] testBoard = new int[][] {
				{1,0,0},
				{0,1,0},
				{0,0,0}
		};
		System.out.println(check(testBoard));
	}
//	static int method(TwoArgIntOperator operator) {
//		return operator.op(5, 10);
//	}
//    TwoArgIntOperator addTwoInts = (a, b) -> a + b;

	private static boolean recCheckLine(int[] line, int staInd) {
		if ( staInd == line.length-1)
			return true;
		else if ( line[staInd] == line[staInd+1] )
			return recCheckLine(line, staInd+1);
		return false;
	}
	
	private static int head(int[] list) {
		return list[0];
	}
	
	private static int[] tail(int[] list) {
		int[] retVal = new int[list.length-1];
		for ( int i=1; i<list.length; i++ )
			retVal[i-1] = list[i];
		return retVal;
	}

	//n               0,0   0,1   1,1
	// {0,1}, 2 => { {0,0},{0,1},{1,0},{1,1} }
	
	// {0,1,2}, 2 { {0,0}, {0,1}, {0,2}, {1,0}, {1,1}, {1,2}, {2,0}, {2,1}, {2,2} } 
	// [9][2]

	// {0,1}, 2 => { {0,0},{1,0},{0,1},{1,1} }
	private static int[][] gnrtPrms(int[] bag, int len) { // {0,1,2}, 2
		int permSize=(int) Math.pow(bag.length, len); // 9
		int[][] retVals = new int[permSize][len]; // [9][2]

		for ( int i=0; i<permSize; i++ ) { // 0,1,2,3
			int dgitIndx = i + (i / bag.length);
			for ( int u=0; u<bag.length; u++ ) {
				retVals[i][u] = bag[u];
			}
		}
		return null;
	}

	public static boolean vectorCheck(int[][] board) {
		int e = board.length;
		//                      Horizontal:     Vertical:       Diagon 1:       Diagon 2:
		Point[] staPnts     = { new Point(0,0), new Point(0,0), new Point(0,0), new Point( 0,e-1)};
		Point[] nxtItemVcts = { new Point(1,0), new Point(0,1), new Point(1,1), new Point(-1,-1 )}; 
		Point[] nxtLineVcts = { new Point(0,1), new Point(1,0), new Point(e,e), new Point( e,e  )}; 
		
		Point currPoint;
		Point currLine; // Straigt line through matrix, not necessarily a row!
		for ( int i=0; i<staPnts.length; i++ ) {
			currPoint = staPnts[i].clone();
			currLine = staPnts[i].clone();
			while ( currLine.x < e && currLine.y < e && currLine.x >= 0 && currLine.y >= 0) {
//				currPoint = staPnts[i].clone();
				while ( currPoint.x < e && currPoint.y < e && currPoint.x >= 0 && currPoint.y >= 0) {
					currPoint.addVector(nxtItemVcts[i]);
					System.out.print(currPoint.x + ", " + currPoint.y + " ");
				}
				System.out.println();
				currLine = staPnts[i].clone();
				currLine.addVector(nxtLineVcts[i]);
//				currPoint = staPnts[i].clone();
			}
		}
		return false;
	}
	
//	private static int recCheck(int[][] board, int y)
	
	 // Takes a 2D-array and returns true if there exists a straight line with all values equal and not zero.
    public static boolean check(int[][] board) {
    	int[][] horizoLines = new int[board.length][board.length];
    	int[][] verticLines = new int[board.length][board.length];
    	int[][] diagonLines = new int[2][board.length]; 
    	int[][][] linesArray = {horizoLines, verticLines, diagonLines};
    	
    	for ( int i=0; i<linesArray.length; i++ ) {
    		int[][] lines = LineGenerator.genLinesAtIndx(board, i);
    		for ( int u=0; u<lines.length; u++) {
    			if ( allElemsEqualAndNotZero(lines[u]) )
    				return true;
    		}
    	}

    	return false;
    }

	 // Takes a 2D-array and returns true if there exists a straight line with all values equal and not zero.
    public static boolean check(int[][] board, int playerNum) {
    	int[][] horizoLines = new int[board.length][board.length];
    	int[][] verticLines = new int[board.length][board.length];
    	int[][] diagonLines = new int[2][board.length]; 
    	int[][][] linesArray = {horizoLines, verticLines, diagonLines};
    	
    	for ( int i=0; i<linesArray.length; i++ ) {
    		int[][] lines = LineGenerator.genLinesAtIndx(board, i);
    		for ( int u=0; u<lines.length; u++) {
    			if ( allElemsEqual(lines[u], playerNum) )
    				return true;
    		}
    	}

    	return false;
    }
 
    // Given a 1D-array, return whether all elements are identical and not zero
    private static boolean allElemsEqualAndNotZero(int[] line) {
    	int frsNum = line[0];
    	for ( int i=1; i<line.length; i++) {
    		int currElem = line[i];
    		if ( currElem != frsNum || currElem == 0 )
    			return false;
    	}
        
    	return true;
    }

    // Given a 1D-array, return whether all elements are identical and not zero
    private static boolean allElemsEqual(int[] line, int value) {
//    	int frsNum = line[0];
//    	for ( int i=1; i<line.length; i++) {
//    		int currElem = line[i];
//    		if ( currElem != frsNum || currElem == 0 )
    	for ( int elem : line ) {
    		if ( elem != value )
    			return false;
    	}
        
    	return true;
    }
    
}

class Point {
	int y;
	int x;
	public Point(int x, int y) {
		this.y=y;
		this.x=x;
	}

	void addVector(Point vector){
		x+=vector.x;
		y+=vector.y;
	}
	
	public Point clone() {
		return new Point(x, y);
	}
}

class LineGenerator {
	public static int[][] genLinesAtIndx(int[][] board, int indx){
		switch (indx) {
		case 0:
			return genHorizonLines(board);
		case 1:
			return genVerticLines(board);
		case 2:
			return genDiagonLines(board);
		}
		return null;
	}

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

