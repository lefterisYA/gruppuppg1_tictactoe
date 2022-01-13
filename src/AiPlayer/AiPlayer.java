package AiPlayer;

import java.util.LinkedList;
import java.util.Random;

import main.*;

public class AiPlayer {
	private LinkedList<int[]> bestMoves = new LinkedList<int[]>();

	public int[] miniMax(Board board, Player currPlayer){
		bestMoves.clear();
		lowest=50;
		largest = -5;
 
		miniMaxAver(board, 0, board.getFreeTilesQnt(), currPlayer.getPlayerNum(), currPlayer.getPlayerNum());

		for ( int[] move : bestMoves ) {
			System.out.println("many"+move[0]+","+move[1]);
		}

		if ( bestMoves.size() > 1 ) {
			Random rd = new Random();
			int randomBest = rd.nextInt(bestMoves.size());
			int[] bestMove = bestMoves.get(randomBest);
			return bestMove;
		}

		int[] bestMove = bestMoves.getLast();
		return bestMove;
//		System.out.println("best move: ["+temp.bestMove[0]+","+temp.bestMove[1]+"]: "+temp.score);
	}

	private int miniMax(Board board, int depth, int initFreeTiles, int strtPlayer, int currPlayer){
		if ( board.hasWinner() ) {
			if ( currPlayer == strtPlayer )
				return 1;
			else 
				return -1;
//			int dpthGrav = initFreeTiles-depth+1;
//				return  1*dpthGrav;
//			return 1;
		} else if ( board.isFull() ) {
			return 0;
		}

		int bstScr = Integer.MIN_VALUE;
		int totScore = 0;
		int[] bstMov = new int[] {-1, -1};

		for ( int y=0; y<board.sideLen; y++ ) {
			for ( int x=0; x<board.sideLen; x++ ) {
				if ( board.isFree(y, x) ) {
					Board newBoard = board.deepCopy();
					newBoard.placeTile(y, x, currPlayer);
					
					int scr = miniMax(newBoard, depth+1, initFreeTiles, strtPlayer, getOppo(currPlayer));

					if ( currPlayer == strtPlayer )
						totScore+=scr;
					else
						totScore-=scr;
//						scr=scr*(-1);
					

					if (depth==0) {
						System.out.println(y+","+x+": "+scr);
					}
					if ( scr >= bstScr) {
						bstScr = scr;
						bstMov = new int[] { y, x };
					}
				}
			}
		}

		if (depth==0) {
			bestMoves.clear();
			bestMoves.add(bstMov);
		}

		return totScore;
	}

	static int lowest = 50;
	static int largest = -5;
	private double miniMaxAver(Board board, int depth, int initFreeTiles, int strtPlayer, int currPlayer){
		if ( board.hasWinner() ) {
			int dpthGrav = initFreeTiles-depth+1; // 9,8,7,6...
			if ( dpthGrav < 1 )
				System.out.println("ERROERROERROERROERROERROERROERROERROERROERROERRORRRRRRRRRRRRERROR");
			if ( dpthGrav < lowest ) {
				System.out.println(dpthGrav);
				lowest=dpthGrav;
			}
//			if ( depth > largest ) {
//				System.out.println(depth);
//				largest=depth;
//			}
//			double odds=(initFreeTiles-depth+1)/initFreeTiles;
			double odds=dpthGrav;
//			if ( odds > 1 )
//				System.out.println("BIGGER");
			if ( currPlayer == strtPlayer ) {
				return -1 * odds;
//				return -1*dpthGrav;
//				return -10;
			} else {
				return  odds;
//				return  1*dpthGrav;
//				return  10;
			}
		}

		if ( board.isFull() ) {
			return 0;
		}

		double totScore=0;
		double bestScore=-Double.MAX_VALUE; //Integer.MIN_VALUE;
		int valCnt = 0;

		for ( int y=0; y<board.sideLen; y++ ) {
			for ( int x=0; x<board.sideLen; x++ ) {
				if ( board.isFree(y, x) ) {
					Board newBoard = board.deepCopy();
					newBoard.placeTile(y, x, currPlayer);
					
					double scr = miniMaxAver(newBoard, depth+1, initFreeTiles, strtPlayer, getOppo(currPlayer));
					
					valCnt++;
					totScore+=scr;

					if (depth>0)
						continue;

					double scrRnd = Math.round(scr       * 10000000000d) / 10000000000d;
					double bstRnd = Math.round(bestScore * 10000000000d) / 10000000000d;

					System.out.print(y+","+x+":"+scrRnd);

					if ( scrRnd < bstRnd ) {
						System.out.println("");
						continue;
					}
					
					if ( scrRnd > bstRnd ) {
						System.out.print(" clearing");
						bestMoves.clear();
					}

					System.out.print(" adding("+scrRnd+">"+bstRnd+")");
					bestMoves.add(new int[] { y, x });

					bestScore=scr;
					System.out.println("");
				}
			}
		}

		return totScore/valCnt; // ???? average????:
//		return totScore;
//		return bestScore;
	}

	private int getOppo(int currPlayer) {
		return currPlayer == 1 ? 2 : 1;
	}
}

//class UpstreamData {
//	final public double score;
//	final public int[] bestMove;
//
//	public UpstreamData(double score, int[] bestMove) {
//        this.score = score;
//        this.bestMove = bestMove;
//	}
//}