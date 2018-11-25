import java.util.ArrayList;

public class Board {
	Square[][] board;
	ArrayList<String> log;
	int turn;
	
	/**
	 * Initialize the board with col and row labels with the rest '-'
	 */
	public Board() {
		log = new ArrayList<>();
		board = new Square[9][9];
		board[0][0] = new Square(' ');
		board[1][0] = new Square('A');
		board[2][0] = new Square('B');
		board[3][0] = new Square('C');
		board[4][0] = new Square('D');
		board[5][0] = new Square('E');
		board[6][0] = new Square('F');
		board[7][0] = new Square('G');
		board[8][0] = new Square('H');
		
		board[0][1] = new Square('1');
		board[0][2] = new Square('2');
		board[0][3] = new Square('3');
		board[0][4] = new Square('4');
		board[0][5] = new Square('5');
		board[0][6] = new Square('6');
		board[0][7] = new Square('7');
		board[0][8] = new Square('8');
		
		turn = 1;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (j > 0 && i != 0) {
					board[i][j] = new Square();
				}
			}
		}
		
//		//Test of killermove successful.
//		board [2][1] = new Square('X');
//		board [3][1] = new Square('X');
//		Square test = new Square('X', 4, 1);
//		scoreMove(test);
	}
	/**
	 * Copy constructor
	 * @param b original board b
	 */
	Board(Board b){
		board = new Square[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				board[i][j] = new Square(b.board[i][j]);
			}
		}
	}
	
	
	/**
	 * places one square onto the board. Made to take in
	 * a square passed by abPruning
	 * @param move
	 */
	public void placeSquare(Square move) {
		board[move.posX][move.posY] = move;
		
	}
	
	/**
	 * Takes in the coordinates of a move and determines the score
	 * given by that square. The higher the score the better the move.
	 * @return
	 */
	
	public int scoreMove(Square move) {
		int score = 0;
		if (checkKillerMove(move)) {
			score += 100000;
		}
		System.out.println(score);
		
		
		return score;
	}
	
	/**
	 * checks the squares next to the move and checks if this
	 * will result in a move that will always lead to a win.
	 * Uses the char value in move. Returns true if there is a killer move.
	 * A killer move looks like -CCC-. Where - is an available move to win.
	 * @param move
	 */
	public boolean checkKillerMove(Square move) {
		int x = move.posX;
		int y = move.posY;
		
		//checks the row for a 3 in a row
		for (int i = 1; i < 9; i++) {
			//checks for the first character of move, or until the move is reached.
			// and if there is enough room for there to be two move of the same 
			//move followed by a space -(current)CC-
			if ((board[x][i].getDisplay() == move.getDisplay() || i == y) && i < 6) {
				//checks if there is a blank space before the square
				if(board[x][i-1].getDisplay() == '-' 
						//continues Checking for 3C in a row. if i == y then that is the move.
						&& (board[x][i+1].getDisplay() == move.getDisplay() || i+1 == y)
						&& (board[x][i+2].getDisplay() == move.getDisplay() || i+2 == y)
						//checks for space at the end.
						&& board[x][i+3].getDisplay() == '-') {
					return true;
				}
			}
		}
		//check col
		for (int i = 1; i < 9; i++) {
			//checks for the first character of move, or until the move is reached.
			// and if there is enough room for there to be two move of the same 
			//move followed by a space -(current)CC-
			if ((board[i][y].getDisplay() == move.getDisplay() || i == x) && i < 6) {
				//checks if there is a blank space before the square
				if(board[i-1][x].getDisplay() == '-' 
						//continues Checking for 3C in a row. if i == x then that is the move.
						&& (board[i+1][y].getDisplay() == move.getDisplay() || i+1 == x)
						&& (board[i+2][y].getDisplay() == move.getDisplay() || i+2 == x)
						//checks for space at the end.
						&& board[i+3][y].getDisplay() == '-') {
					return true;
				}
			}
		}
		
		return false;
	}
	
	
	
	/**
	 * prints just the board. Used for testing, as it does not print the
	 * move log.
	 */
	public void printBoard() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(board[i][j].getDisplay() + " ");
			}
			System.out.println();
		}
	}
	
	/**
	 * Prints display of board with move log adjacent to the right.
	 */
	public void printDisplay() {
		
		for (int i = 0; i < 9; i++) {
			System.out.print(board[0][i].getDisplay() + " ");
		}
		
		System.out.println("\t Player vs Opponent");
		
		for (int i = 0; i < 9; i++) {
			System.out.print(board[1][i].getDisplay() + " ");
		}
		
		
		//Checks if a move has been made, and if so prints it
		if(log.size() >= 1) {
			System.out.print("\t\t" + log.get(0));
		}
		
		//checks if both moves of the first turn have been made
		if(log.size() >= 2) {
			System.out.print("\t\t" + log.get(1));
		}
		
		System.out.println("\t \t");
		
		for (int i = 0; i < 9; i++) {
			System.out.print(board[2][i].getDisplay() + " ");
		}
		
		System.out.println("\t \t ");
		
		for (int i = 0; i < 9; i++) {
			System.out.print(board[3][i].getDisplay() + " ");
		}
		
		System.out.println("\t \t ");
		
		for (int i = 0; i < 9; i++) {
			System.out.print(board[4][i].getDisplay() + " ");
		}
		
		System.out.println("\t \t ");
		
		for (int i = 0; i < 9; i++) {
			System.out.print(board[5][i].getDisplay() + " ");
		}
		
		System.out.println("\t \t ");
		
		for (int i = 0; i < 9; i++) {
			System.out.print(board[6][i].getDisplay() + " ");
		}
		
		System.out.println("\t \t ");
		
		for (int i = 0; i < 9; i++) {
			System.out.print(board[7][i].getDisplay() + " ");
		}
		
		System.out.println("\t \t ");
		
		for (int i = 0; i < 9; i++) {
			System.out.print(board[8][i].getDisplay() + " ");
		}
		
		System.out.println("\t \t ");
		
		
		
	}

}
