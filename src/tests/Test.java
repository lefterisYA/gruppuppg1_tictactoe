package tests;

import AiPlayer.*;

public class Test {
	public static void main(String args[]) {
		Moves moves = new Moves(0,0,1);
		moves.addMove(0,1,2);
		moves.addMove(0,2,1);
		moves.addMove(1,0,1);
		moves.addMove(1,1,1);
		Moves mvsCln = moves.deepCopy();
		
		System.out.println(moves);
//		System.out.println(moves.toString2());
		System.out.println(moves.toString3());
		System.out.println(moves.size());
		System.out.println(mvsCln);
//		System.out.println(mvsCln.toString2());
		System.out.println(mvsCln.toString3());
		System.out.println(mvsCln.size());
		
		Moves mvsFromNull = new Moves();
		mvsFromNull.addMove(0,0,1);
		mvsFromNull.addMove(0,1,2);
		mvsFromNull.addMove(0,2,1);
		mvsFromNull.addMove(1,0,1);
		mvsFromNull.addMove(1,1,1);
		System.out.println(mvsFromNull);
//		System.out.println(mvsFromNull.toString2());
		System.out.println(mvsFromNull.toString3());
		System.out.println(mvsFromNull.size());

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

//		int[][] testBoard = new int[][] {
//				{1,0,2},
//				{2,0,0},
//				{2,1,1}
//		};
//		board = testBoard;
}