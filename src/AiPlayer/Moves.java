package AiPlayer;

public class Moves {
	private Move frstMove;
	private Move lastMove;
	private int size;

	// First move
	public Moves(int y, int x, int pl) {
		frstMove = new Move(y, x, pl);
		lastMove = frstMove;
		size = 1;
	}
	
	public Moves() {
		frstMove = null;
		lastMove = null;
		size = 0;
	}
	
	private Moves(Move move) {
		setFrst(move);
	}

	private void setFrst(Move move) {
		frstMove = move;
		lastMove = move;
		size = 1;
	}

	// ####

	public int getStrtPlayer() {
		return frstMove.player;
	}
	
	public int[] getFrstMove() {
		return new int[] { frstMove.y, frstMove.x };
	}
	
	public int[] getLastMove() {
		return new int[] { lastMove.y, lastMove.x };
	}
	
	public int getCurrentPlayer() {
		return lastMove.player;
	}
	
	public int size() {
		return size;
	}
	
	private void addMove(Move newMove) {
		lastMove.nextMove = newMove;
		lastMove = newMove;
		size++;
	}

	public void addMove(int y, int x, int pl) {
		Move newMove = new Move(y, x, pl);
		if ( frstMove != null )
			addMove(newMove);
		else {
			setFrst(newMove);
		}
	}
	
	public Moves branch() {
		Moves newBranch = new Moves(frstMove);
		newBranch.size = size;
		return newBranch;
	}
	
	public Moves deepCopy() { // caled on frstMove
		Move newFrstMove =  new Move(frstMove.y, frstMove.x, frstMove.player);
		newFrstMove.nextMove = frstMove.nextMove; // Address of source object.
		Moves movesCopy = new Moves(newFrstMove);
		deepCopyRec(movesCopy, newFrstMove.nextMove); // Address of source object.
		return movesCopy;
	}
	
	// Crawls through each object recursively and adds every member to dest.
	protected Move deepCopyRec(Moves dest, Move moveCand) {
		dest.addMove( new Move(moveCand.y, moveCand.x, moveCand.player) ); // Actual copy created here.
		if ( moveCand.nextMove == null )
			return new Move(moveCand.y, moveCand.x, moveCand.player); // and here
		else
			return deepCopyRec(dest, moveCand.nextMove); // Still adress of original object
	}

	public String toString() {
		Move currMove=frstMove;
		String rtrnValu = frstMove.toString2();
		do {
			currMove=currMove.nextMove;
			rtrnValu+=", "+currMove.toString2();
		} while ( currMove.nextMove != null );
		return rtrnValu;
	}

	public String toString3() {
		Move currMove=frstMove;
		String rtrnValu = frstMove.toString();
		do {
			currMove=currMove.nextMove;
			rtrnValu+=", "+currMove.toString();
		} while ( currMove.nextMove != null );
		return rtrnValu;
	}
}

class Move {
	protected Move nextMove;

	protected final int y;
	protected final int x;
	protected final int player;
	
	public Move(int y, int x, int pl) {
		this.y=y;
		this.x=x;
		this.player=pl;
	}
	
	public String toString2() {
		return "(p"+player+":"+(y+1)+","+(x+1)+")";
	}
}
