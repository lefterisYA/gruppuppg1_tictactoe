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
}