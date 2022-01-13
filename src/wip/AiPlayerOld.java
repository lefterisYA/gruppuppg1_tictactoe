package wip;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import AiPlayer.Moves;
import main.Board;

class AiPlayerOl {
//	LinkedList<int[]> bestPath; // = new LinkedList<int[]>();
//	HashMap<Integer, Moves[]> paths = new HashMap<Integer, Moves[]>();
//	LinkedList<UpstreamData> moves = new LinkedList<UpstreamData>();
//	int[][] initialBoard;
//	int[][] scoreBoard;

	public static void main(String[] args) {
		int[][] testBoard = new int[][] {
				{1,0,2},
				{2,0,0},
				{2,1,1}
		};
	}

	private int getOppo(int currPlayer) {
		return currPlayer == 1 ? 2 : 1;
	}

	public int[] miniMax(Board board, int currPlayer){
		int[][] scoreBoard = new int[board.sideLen][board.sideLen];

		boolean ugly = true;
		UpstreamData temp = miniMax(board, scoreBoard, 0, board.getFreeTilesQnt(), currPlayer, currPlayer, -1, -1);
		System.out.println("bestmoveres: ["+temp.bestMove[0]+","+temp.bestMove[1]+"]: "+temp.score);
		if (ugly)
			return temp.bestMove;
		
//		Utils.printPaths(paths);

		Utils.printScoreBoard(scoreBoard);
		
		int maxScore=Integer.MIN_VALUE;
		LinkedList<int[]> bestMoves = new LinkedList<int[]>();
		for ( int y=0; y<scoreBoard.length; y++) {
			for ( int x=0; x<scoreBoard[0].length; x++) {
				if ( ! board.isFree(y, x) || maxScore > scoreBoard[y][x] )
					continue; 

				if ( maxScore < scoreBoard[y][x] ) {
					bestMoves.clear();
				}

				bestMoves.add(new int[] { y, x } );
				maxScore = scoreBoard[y][x];
			}
		}

		if ( bestMoves.size() > 1 ) {
			Random rd = new Random();
			int randomBest = rd.nextInt(bestMoves.size());
			int[] bestMove = bestMoves.get(randomBest);
			return bestMove;
		}
		int[] bestMove = bestMoves.getFirst();
		
//		System.out.println("Best move:" + (bestMove[0]*3+bestMove[1]+1));

		return bestMove;
//				Utils.printBestMoves(bestMoves, board);
	}

	private UpstreamData miniMax( Board board, int[][] scoreBoard, int depth, int freeTiles, int strtPlayer, int currPlayer, int frstY, int frstX ){
		
		if ( board.hasWinner() ) {
			if ( currPlayer == strtPlayer ) {
				if ( depth > 0 ) 
					scoreBoard[frstY][frstX] += 1 *(10-depth);
//				if ( depth == 2 )
//					return new int[] {10000, frstY, frstX};
//				else
//				return new int[] {1, frstY, frstX};
//				return -1.0;
//				return new int[] {-1, 1};
				return new UpstreamData(-1.0, null);
			} else {
				if ( depth > 0 ) 
					scoreBoard[frstY][frstX] += -1 *(10-depth);
//				return new int[] {-1, frstY, frstX};
//				return 1.0;
//				return new int[] {1, 1};
				return new UpstreamData( 1.0, null);
			}
		}

		if ( board.isFull() ) {
			if ( depth > 0 ) 
				scoreBoard[frstY][frstX] += 1 *(10-depth);
//			return new int[] {0, frstY, frstX};
//			return 0.0;
//			return new int[] {0, 1};
			return new UpstreamData( 0.0, null);
		}

		double totScore=0;
		double bestScore=-Double.MAX_VALUE; //Integer.MIN_VALUE;
		int bestOppScore=Integer.MIN_VALUE;
		int[] bstOwnMov=new int[] {-1,-1,-1};
		int[] bstOppMov=new int[] {-99,-99,-99};
		int valCnt = 0;

		for ( int y=0; y<board.sideLen; y++ ) {
			for ( int x=0; x<board.sideLen; x++ ) {
				if ( board.isFree(y, x) ) {
					Board newBoard = board.deepCopy();
					newBoard.placeTile(y, x, currPlayer);
					
//					int[] temp = 
//					int[] temp =
					UpstreamData temp =
							miniMax(newBoard, scoreBoard, depth+1, freeTiles-1, strtPlayer, getOppo(currPlayer), y, x);
					double avr = temp.score;
					
//					int[][] oppScrBrd = new int[3][3];
//					Board oppBoard = newBoard.deepCopy();
//					int[] oppoBest = 
//							miniMax(newBoard.deepCopy(), oppScrBrd, 0, freeTiles-1, strtPlayer, currPlayer, -2, -2);

//					int scoreForMove = temp[0] - oppoBest[0];

//					if ( temp[0] > bestScore ) {
					if ( avr > bestScore ) {
						bestScore = avr;
						bstOwnMov = new int[] { y, x };
//						bestMove = new int[] { y, x , bestScore, oppoBest[1], oppoBest[2], oppoBest[0]};
					}
					valCnt++;
					totScore+=avr;
//					if ( oppoBest[0] > bestOppScore) {
//						bestOppScore = oppoBest[0];
//						bstOppMov = new int[] { oppoBest[1], oppoBest[2], bestOppScore };
//					}

//					if ( depth == 0 ) {
//						totScore=temp[0];
//						System.out.println("["+y+","+x+"]: "+temp);
//					}
				}
			}
		}

//		if ( depth == 0 && frstY == -1) {
//			System.out.print("TotScore:      ["+totScore+"\n");
//			System.out.print("Best own move: ["+bstOwnMov[0]+","+bstOwnMov[1]+"]:"+bstOwnMov[2]+"\n");
//			System.out.print("Best opp move: ["+bstOppMov[0]+","+bstOppMov[1]+"]:"+bstOppMov[2]+"\n");
//			System.out.print("Best own move: ["+bstOwnMov[0]+","+bstOwnMov[1]+"]: "+bestScore+"\n");
//		}

//		return new int[] {totScore, bstOwnMov[0], bstOwnMov[1]};
//		return totScore/valCnt;
		return new UpstreamData(totScore/valCnt, bstOwnMov);
	}
}

class Utils {
    static public int[][] deepCopy(int[][] in) {
    	int[][] newBoard = new int[in.length][in.length];
		for ( int y=0; y<in.length; y++ ) {
			for ( int x=0; x<in.length; x++ ) {
				newBoard[y][x] = in[y][x];
			}
		}
    	return newBoard;
    }
    
    static public boolean isFull(int[][] in) {
    	for ( int y=0; y<in.length; y++ ) {
    		for ( int x=0; x<in.length; x++ ) {
    			if ( in[y][x] == 0 )
    				return false;
    		}
    	}
    	return true;
    }

	static int[] printBestMoves( LinkedList<int[]> bestMoves, int[][] board ) {
		int lowestStepsToWin = board.length*board.length+1;
		int lowestStepsToDrw = board.length*board.length+1;
		int[] fastestWinMove = new int[2];
		int[] fastestDrwMove = new int[2];

		while ( ! bestMoves.isEmpty() ) {
			int[] elem = bestMoves.pop();
			System.out.println(elem[0] + ", " + elem[1] + ", " + elem[2] + ", " + elem[3] + ", ");
			if ( elem[1] == 1  && elem[0] < lowestStepsToWin ) {
				lowestStepsToWin=elem[0];
				fastestWinMove[0]=elem[2];
				fastestWinMove[1]=elem[3];
			} else if ( elem[1] == 0 && elem[0] < lowestStepsToDrw ) {
				lowestStepsToDrw=elem[0];
				fastestDrwMove[0]=elem[2];
				fastestDrwMove[1]=elem[3];
			}
		}

		int[] ret= new int[] {-1,-1};
		if ( lowestStepsToDrw < board.length*board.length ) {
			System.out.print(lowestStepsToDrw+" to ");
			System.out.println("Drw:" + (fastestDrwMove[0]+1) + "," + (fastestDrwMove[1]+1) );
			ret = new int[] { fastestDrwMove[0], fastestDrwMove[1] };
		}
		if ( lowestStepsToWin < board.length*board.length ) {
			System.out.print(lowestStepsToWin+" to ");
			System.out.println("Win:" + (fastestWinMove[0]+1) + "," + (fastestWinMove[1]+1) );
			ret = new int[] { fastestWinMove[0], fastestWinMove[1] };
		}
		return ret;
	}

	static void printPaths(HashMap<Integer, Moves[]> paths) {
		int depth=0;
		while ( paths.containsKey(depth++) ) {
			System.out.println(depth-1);
			Moves[] branch = paths.get(depth);
			if ( branch == null )
				continue;
			for ( Moves br : branch )
				System.out.println(br.size() + " " + br);
		}
	}

	static void printList(String title, LinkedList<int[]> movesHere) {
		if ( movesHere.isEmpty() ) {
			System.out.println(title);
			return;
		}
		int playerWon=movesHere.getLast()[2];
		String print="Pl "+playerWon+" "+title+" GAME: ";
		for ( int[] move : movesHere ) {
			int player = move[2];
			print+=player+":["+move[0]+", "+move[1]+"], ";
		}
		System.out.println(print);
	}
	
	static void printScoreBoard(int[][] board) {
		String print="";
		for ( int y=0; y<board.length; y++) {
			for ( int x=0; x<board.length-1; x++) {
				print+=" "+scorePretty(board[y][x])+"|";
			}
			print+=" "+scorePretty(board[y][board.length-1])+"\n";
		}
		System.out.println(print);
	}
	
	static String scorePretty(int in) {
		String inStr = Integer.toString(in);
		return inStr + " ".repeat(10-inStr.length());
	}
}


class UpstreamData {
	final public double score;
	final public int[] bestMove;

	public UpstreamData(double score, int[] bestMove) {
        this.score = score;
        this.bestMove = bestMove;
	}
}

//					Moves mvsBranch = moves.branch();
//					mvsBranch.addMove(y, x, currPlayer);
//		int currPlayer = playerCalled;
//			currPlayer = moves.getCurrentPlayer();
//			int maxiPlayer = getOppo(currPlayer); // Player we are maximizing for!
//		int lowestSteps = board.length*board.length;
//		int scoreForMove = -2;
//		String bestMoves = "";
//		String bestMove  = "";
//		currPlayer = getOppo(currPlayer);
//					if ( depth==0 )
//						bestMoves.add(new int[] { fromDep, totScore, y, x });
//				    bestMoves.add(new int[] { depth, scoreForMove, y, x });
//					if ( temp != -99999 )
//						scoreForMove -= temp;

//					if (scoreForMove > score) {
//						int[] move=mvsBranch.getFrstMove();
//						int moveLen = mvsBranch.size();
//						score = scoreForMove;
//						bestMove="["+(move[0]+1)+","+(move[1]+1)+"]: " + moveLen+";"+score+" d:"+depth;
//						bestMoves+=bestMove+" | ";
//						lowestSteps=moveLen;
//							System.out.println(bestMove);
//					}

//					Moves[] temp = paths.get(depth);
//					if ( temp == null ) {
//						temp = new Moves[1];
//						temp[0] = mvsBranch;
//						paths.put(depth, temp);
//					} else {
//						int newSize = temp.length+1;
//						Moves[] temp2 = new Moves[newSize];
//						for ( int i=0; i<temp.length; i++) {
//							temp2[i]=temp[i];
//						}
//						temp2[temp2.length-1]=mvsBranch;
////						temp=temp2;
//						paths.put(depth, temp2);
//					}
//		System.out.println(bestMoves);
//		if ( depth == 0 ) {
//			System.out.println(bestMoves);
//		}
	/*
[1,2]: 2;-1 d:1
[1,2]: 3;1 d:2
[1,2]: 2;-1 d:1
[1,2]: 1;1 d:0
[2,2]: 1;1 d:0
[2,3]: 3;1 d:2
[2,3]: 2;-1 d:1
[2,3]: 2;-1 d:1
[2,3]: 1;1 d:0

[1,2]: 2;-1 d:1
[1,2]: 3;1 d:2
[1,2]: 2;0 d:1
[1,2]: 1;0 d:0
[2,2]: 1;1 d:0
[2,3]: 3;1 d:2
[2,3]: 2;0 d:1
		 */
//					//
//
//					int[] newMove = {y,x,currPlayer};
//					@SuppressWarnings("unchecked")
//					LinkedList<int[]> mvsHereBranch = (LinkedList<int[]>) movesHere.clone();
//					mvsHereBranch.add(newMove);
					//

//					int scoreForMove = mvsHereBranch.removeFirst()[0];
//		bestPath = new LinkedList<int[]>();
//		System.out.println(WinChecker.check(board));
//		boolean retNow = true;
//		if (retNow)
//			return 0;
//		shortestWinPath = null;
//		shortestDrawPath = null;
//		shortestLoosePath = null;

//		if ( shortestDrawPath != null ) {
//			int[] step = shortestDrawPath.getFirst();
//			System.out.println("DRAW! "+shortestDrawPath.size()+": "+(step[0]*3+step[1]+1));
//		}
//		if ( shortestWinPath != null && shortestWinPath.size()>0 ) {
//			int[] step = shortestWinPath.getFirst();
//			System.out.println("WIN! "+shortestWinPath.size()+": "+(step[0]*3+step[1]+1));
//		}
//		if ( bestPath != null && bestPath.size() > 0 ) {
//			int[] step = bestPath.getFirst();
//			System.out.println("BEST! "+bestPath.size()+": "+(step[0]*3+step[1]+1));
//		}
//				if ( currPlayer == 2 ) {
//					if ( shortestWinPath == null || movesHere.size() <= shortestWinPath.size() ) {
//						shortestWinPath=(LinkedList<int[]>) movesHere.clone();
//					}
					//				return 1;
//				} else {
//					if ( shortestLoosePath == null || movesHere.size() <= shortestLoosePath.size() ) {
//						shortestLoosePath=(LinkedList<int[]>) movesHere.clone();
//					}
//				}
//					printList("WIN ", movesHere);
//					movesHere.addFirst(new int[] {1});
//					printList("LOO ", movesHere);
//					movesHere.addFirst(new int[] {-1});
//				return movesHere;
				//			movesHere.clear();

//				return currPlayer == 2 ? 1 : -1;

				//				shortestDrawPath=(LinkedList<int[]>) movesHere.clone();
//				printList("DRW ", movesHere);
				//			movesHere.clear();
				//			}

//				movesHere.addFirst(new int[] {0});
////		System.out.println(depth);
//


		/*
 X |   | O
---+---+---
 O |   |
---+---+---
 O | X | X
 =========
 O |   | X
---+---+---
 X |   |
---+---+---
 X | O | O
		 */
//		if ( WinChecker.check(board, nextPlayer(currPlayer)) ) {
//			if ( shortestLoosePath == null || movesHere.size() <= shortestLoosePath.size() ) {
//				shortestLoosePath=(LinkedList<int[]>) movesHere.clone();
//				movesHere.clear();
//			}
//			return -1;
//		}

			//shortestPath = new LinkedList<int[]>();
			//int[] step = movesHere.getFirst();
			//System.out.println("WIN! "+step[0]+","+step[1]);
			//System.out.println(movesHere.size());

//		int[] move = {-1,-1, currPlayer};
//		int score = -2;
//					System.out.println("Dep:"+depth);
//					if ( depth < 10 ) {
//					}
//					movesHere.add(new int[] { y, x, currPlayer} );
//					int scoreForMove = - miniMax( boardWithNewMove, movesHere, newMove);
//					if (scoreForMove > score) {
//						score = scoreForMove;
//						move = new int[] { y, x, currPlayer };
//						bestPath.clear();
//						bestPath.add(move);
//					}
//		if (move[0] == -1 && move[1] == -1) {
//		}

//		return score;
