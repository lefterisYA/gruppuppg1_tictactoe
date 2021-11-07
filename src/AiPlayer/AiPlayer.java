package AiPlayer;

import java.util.HashMap;
import java.util.LinkedList;

public class AiPlayer {
//	LinkedList<int[]> shortestWinPath; // = new LinkedList<int[]>();
//	LinkedList<int[]> shortestLoosePath; // = new LinkedList<int[]>();
//	LinkedList<int[]> shortestDrawPath; // = new LinkedList<int[]>();
	LinkedList<int[]> bestPath; // = new LinkedList<int[]>();
	HashMap<Integer, Moves[]> paths = new HashMap<Integer, Moves[]>();
	LinkedList<int[]> bestMoves = new LinkedList<int[]>();
	int[][] initialBoard;
	
	public static void main(String[] args) {
		int[][] testBoard = new int[][] {
				{1,0,2},
				{2,0,0},
				{2,1,1}
		};
		System.out.println(isFull(testBoard));
	}

	private void printList(String title, LinkedList<int[]> movesHere) {
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

	private static boolean isFull(int[][] board) {
		for ( int y=0; y<board.length; y++ ) {
			for ( int x=0; x<board.length; x++ ) {
				if ( board[y][x] == 0 )
					return false;
			}
		}
		return true;
	}

	public int[] miniMax(int[][] board, int currPlayer){
//		LinkedList<int[]> movesHere = new LinkedList<int[]>();
//		initialBoard = deepCopy(board);
//		int[][] newBoard = board.clone();
		bestMoves.clear();
		Moves moves = new Moves();
		miniMax(board, 0, moves, currPlayer+1, 0);
		
		int lowestStepsToWin = board.length*board.length+1;
		int lowestStepsToDrw = board.length*board.length+1;
		int[] fastestWinMove = new int[2];
		int[] fastestDrwMove = new int[2];

		//int[] { depth, score, y, x });
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
		if ( lowestStepsToDrw < board.length*board.length+1 ) {
			System.out.print(lowestStepsToDrw+" to ");
			System.out.println("Drw:" + (fastestDrwMove[0]+1) + "," + (fastestDrwMove[1]+1) );
			ret = new int[] { fastestDrwMove[0], fastestDrwMove[1] };
		}
		if ( lowestStepsToWin < board.length*board.length+1 ) {
			System.out.print(lowestStepsToWin+" to ");
			System.out.println("Win:" + (fastestWinMove[0]+1) + "," + (fastestWinMove[1]+1) );
			ret = new int[] { fastestWinMove[0], fastestWinMove[1] };
		}
		
		int depth=0;
		while ( paths.containsKey(depth++) ) {
			System.out.println(depth-1);
			Moves[] branch = paths.get(depth); 
			if ( branch == null )
				continue;
			for ( Moves br : branch )
				System.out.println(br.size() + " " + br);
		}
		return ret;
	}

	private int[] miniMax(int[][] board, int depth, Moves moves, int currPlayer, int scoreForMove){
//		int currPlayer = playerCalled; 
		if ( moves.size() > 0 ) {
//			currPlayer = moves.getCurrentPlayer();
			int maxiPlayer = getOppo(currPlayer); // Player we are maximizing for!

			int[] lastMove = moves.getFrstMove();
			int y=lastMove[0];
			int x=lastMove[1];
			if ( WinChecker.check(board) ) {
				if ( currPlayer == maxiPlayer ) {
//				    bestMoves.add(new int[] { depth, 1, y, x });
					return new int[] {1,depth, scoreForMove+1};
				} else {
//				    bestMoves.add(new int[] { depth, -1, y, x });
					return new int[] {-1,depth, scoreForMove-1};
				}
			} 
			else if ( isFull(board) ) {
//				bestMoves.add(new int[] { depth, 0, y, x });
				return new int[] {0,depth, 0};
			} 
		}
		int score = -2;
//		int lowestSteps = board.length*board.length;
//		int scoreForMove = -2;
//		String bestMoves = "";
//		String bestMove  = "";
//		currPlayer = getOppo(currPlayer);
		for ( int y=0; y<board.length; y++ ) {
			for ( int x=0; x<board.length; x++ ) {
				if ( board[y][x] == 0 ) {
					Moves mvsBranch = moves.branch();
					mvsBranch.addMove(y, x, currPlayer);
					int[][] newBoard = deepCopy(board);
					newBoard[y][x] = currPlayer;
					int[] temp=miniMax( newBoard, depth+1, mvsBranch, getOppo(currPlayer), scoreForMove);
					int totScore=temp[2];
					int fromDep=temp[1];
					if ( depth==0 )
						bestMoves.add(new int[] { fromDep, totScore, y, x });
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
				}
			}
		}

//		System.out.println(bestMoves);
//		if ( depth == 0 ) {
//			System.out.println(bestMoves);
//		}
		return new int[] {0,depth,0};
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
	}
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
	
	private int[][] deepCopy(int[][] in){
		int[][] ret = new int[in.length][in[0].length];
		for ( int y=0; y<in.length; y++ ) {
			for ( int x=0; x<in.length; x++ ) {
				ret[y][x] = in[y][x];
			}
		}
		return ret;
	}
	
	private int getOppo(int currPlayer) {
		return currPlayer == 1 ? 2 : 1;
	}
}