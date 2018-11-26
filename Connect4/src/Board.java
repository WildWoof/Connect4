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
//		board [1][6] = new Square('X');
//		board [1][7] = new Square('X');
//		Square test = new Square('X', 1, 5);
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
		
		//Rank 3: 3rd highest points given, if made you win next turn.
		if (checkKillerMoveRow(move)) {
			//adding 100,000
			score += 100000;
		}
		
		if(checkKillerMoveCol(move)) {
			//adding 100,000
			score += 100000;
		}
		
		//Rank2: 2nd highest points given. Can't win if you lose.
		if (stopWinningMove(move)) {
			//adding 1,000,000
			score +=1000000;
		}
		
		//Rank1: greatest points given. You win, game over.
		if (makeWinningMove(move)) {
			//adding 100,000,000
			score += 100000000;
		}
		
		//Rank 4: less than make killer move since making a killer move leads to a win,
		//and this stops a killer row from being made, but does not lead to a win.
		if (stopKillerRow(move)) {
			//adding 10,000
			score += 10000;
		}
		//Rank 4:
		if (stopKillerCol(move)) {
			//adding 10,000
			score += 10000;
		}
		
		//Rank 5: If opponent is close to setting up a sure win,
		//then try to set up a win.
		if (setupKillerRow(move)) {
			//adding 1,000
			score += 1000;
		}
		
		//Rank 5:
		if (setupKillerCol(move)) {
			//adding 1,000
			score += 1000;
		}
		
		//test output. DELETE LATER
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
	public boolean checkKillerMoveRow(Square move) {
		int x = move.posX;
		int y = move.posY;
		
		//checks the row for a 3 in a row
		for (int i = 1; i < 6; i++) {
			//checks for the first character of move, or until the move is reached.
			// and if there is enough room for there to be two move of the same 
			//move followed by a space -(current)CC-
			if (board[x][i].getDisplay() == move.getDisplay() || i == y) {
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
		return false;
	}

	/**
	 * Checks to see if this leads to a killer move on a column
	 * @param move the move being scored
	 * @return true if this results in a killer more, false otherwise
	 */
	public boolean checkKillerMoveCol(Square move) {
		int x = move.posX;
		int y = move.posY;
		
		//checks the col for a 3 in a row
		for (int i = 1; i < 6; i++) {
			//checks for the first character of move, or until the move is reached.
			// and if there is enough room for there to be two move of the same 
			//move followed by a space -(current)CC-
			if (board[i][y].getDisplay() == move.getDisplay() || i == x) {
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
	 * Second highest score awarded to stopping an opponent's winning move.
	 * Checks each row and column and checks if the move would stop a win.
	 * 
	 * @param move the tile that will be placed by this move
	 * @return true if a killer move is found and blocked by this move. false otherwise
	 */
	public boolean stopWinningMove(Square move) {
		int x = move.posX;
		int y = move.posY;
		
		//checks the row for an enemy winning move in a row. i<5 makes sure there is room.
		for (int i = 1; i < 6; i++) {
			//checks for the first  character and checks to see if
			// there is a winning move in that range. If there is a spot
			//where there is a winning move, and it can make it, will return true.
			if (board[x][i].getDisplay() == move.getEnemy() || (i == y && board[x][i].getDisplay() == '-')) {
				
				// Checking for a winning enemy move. if i == y then that is the move.
				if((board[x][i+1].getDisplay() == move.getEnemy() || (i+1 == y && board[x][i+1].getDisplay() == '-'))
						&& (board[x][i+2].getDisplay() == move.getEnemy() || (i+2 == y && board[x][i+2].getDisplay() == '-'))
						&& (board[x][i+3].getDisplay() == move.getEnemy() || (i+3 ==y && board[x][i+3].getDisplay() == '-'))) {
					return true;
				}
			}
			//checks col
			if (board[i][y].getDisplay() == move.getEnemy() || (i == x && board[i][y].getDisplay() == '-')) {
				
				// Checking for a winning enemy move. if i == x then that is the move.
				if((board[i+1][y].getDisplay() == move.getEnemy() || (i+1 == x && board[i+1][y].getDisplay() == '-'))
						&& (board[i+2][y].getDisplay() == move.getEnemy() || (i+2 == x && board[i+2][y].getDisplay() == '-'))
						&& (board[i+3][y].getDisplay() == move.getEnemy() || (i+3 ==x && board[i+3][y].getDisplay() == '-'))) {
					return true;
				}
			}			
			
		}
		
		return false;
	}
	
	/**
	 * Checks both vertically and horizontally for a winning move to be made
	 * @param move the move being made
	 * @return true if there is a winning move, false otherwise
	 */
	public boolean makeWinningMove(Square move) {
		int x = move.posX;
		int y = move.posY;
		
		//checks the row for an enemy winning move in a row
		for (int i = 1; i < 6; i++) {
			//checks for the first  character and checks to see if
			// there is a winning move in that range. If there is a spot
			//where there is a winning move, and it can take it, will return true.
			if (board[x][i].getDisplay() == move.getDisplay() || (i == y && board[x][i].getDisplay() == '-')) {
				
				// Checking for a winning enemy move. if i == y then that is the move.
				if((board[x][i+1].getDisplay() == move.getDisplay() || (i+1 == y && board[x][i+1].getDisplay() == '-'))
						&& (board[x][i+2].getDisplay() == move.getDisplay() || (i+2 == y && board[x][i+2].getDisplay() == '-'))
						&& (board[x][i+3].getDisplay() == move.getDisplay() || (i+3 ==y && board[x][i+3].getDisplay() == '-'))) {
					return true;
				}
			}
			//checks col
			if (board[i][y].getDisplay() == move.getDisplay() || (i == x && board[i][y].getDisplay() == '-')) {
				
				// Checking for a winning  move. if i == x then that is the move.
				if((board[i+1][y].getDisplay() == move.getDisplay() || (i+1 == x && board[i+1][y].getDisplay() == '-'))
						&& (board[i+2][y].getDisplay() == move.getDisplay() || (i+2 == x && board[i+2][y].getDisplay() == '-'))
						&& (board[i+3][y].getDisplay() == move.getDisplay() || (i+3 ==x && board[i+3][y].getDisplay() == '-'))) {
					return true;
				}
			}			
			
		}
		
		return false;
	}
	
	/**
	 * This checks a row for two patterns that are 1 move away from being a killer move
	 * Those patterns are -CC- and -C-C-. This checks if the move blocks it.
	 * @param move the move being scored
	 * @return true if it stops a killer row, false otherwise
	 */
	public boolean stopKillerRow(Square move) {
		int x = move.posX;
		int y = move.posY;
		for (int i = 2; i < 7; i++) {
			//Checks for the -C which is the start of a killer row
			if(board[x][i].getDisplay() == move.getEnemy() && board[x][i-1].getDisplay() == '-') {
				//checks for -C C-, which sets up a killer Row. Returns true if blocks an empty space
				if (board[x][i+1].getDisplay() == move.getEnemy() && board[x][i+2].getDisplay() == '-' 
						&& (y == i-1  || y== i+2)) {
					return true;
				//checks for -C -C-, another killer row setup
				} else if ( i < 6 && board[x][i+1].getDisplay() == '-' 
						&& board[x][i+2].getDisplay() == move.getEnemy() 
						&& board[x][i+3].getDisplay() == '-'
						&& (y ==i-1 || y== i+1 || y== i+3)) {
					return true;
				}
			}
			
		}
		
		return false;
	}
	
	/**
	 * This checks a col for two patterns that are 1 move away from being a killer move
	 * Those patterns are -CC- and -C-C-. This checks if the move blocks it.
	 * @param move the move being scored
	 * @return true if it stops a killer row, false otherwise
	 */
	public boolean stopKillerCol(Square move) {
		int x = move.posX;
		int y = move.posY;
		for (int i = 2; i < 7; i++) {
			//Checks for the -C which is the start of a killer row
			if(board[i][y].getDisplay() == move.getEnemy() && board[i-1][y].getDisplay() == '-') {
				//checks for -C C-, which sets up a killer Row. Returns true if blocks an empty space
				if (board[i+1][y].getDisplay() == move.getEnemy() && board[i+2][y].getDisplay() == '-' 
						&& (x == i-1  || x== i+2)) {
					return true;
				//checks for -C -C-, another killer row setup
				} else if ( i < 6 && board[x][i+1].getDisplay() == '-' 
						&& board[i+2][y].getDisplay() == move.getEnemy() 
						&& board[i+3][y].getDisplay() == '-'
						&& (x ==i-1 || x== i+1 || x== i+3)) {
					return true;
				}
			}
			
		}
		
		return false;
	}	
	
	/**
	 * Tries to set up to make a killer move in the next turn by having a square with both sides empty
	 * Is of three forms, --CC-, -CC--, or -C-C-
	 * @param move the being tested
	 * @return true if it does set up, false otherwise
	 */
	public boolean setupKillerRow(Square move) {
		int x = move.posX;
		int y = move.posY;
		for(int i = 2; i < 7; i++) {
			//checks for -C or -M. Where C is a made character and M is the move, with - being a blank space.
			if ((board[x][i].getDisplay() == move.getDisplay() || i == y)
					&& board[x][i-1].getDisplay() == '-') {
				//checks for -CM- or -MC-
				if((board[x][i+1].getDisplay() == move.getDisplay() || i+1 == y)&& board[x][i+2].getDisplay() == '-') {
					//checks for --C
					if(board[x][i-2].getDisplay() == '-') {
						return true;
					}
					//checks for out of bound and then for form -CC--
					if(i + 3 < 9 && board[x][i+3].getDisplay() == '-') {
						return true;
					}
				}
				//checks for form -C-C- 
				if (i+ 3 < 9 && board[x][i+1].getDisplay() == '-' && board[x][i+3].getDisplay() == '-'
						&& (board[x][i+2].getDisplay() == move.getDisplay() || i+2 == y)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Tries to set up to make a killer move in the next turn by having a square with both sides empty
	 * Is of three forms, --CC-, -CC--, or -C-C-
	 * @param move the being tested
	 * @return true if it does set up, false otherwise
	 */
	public boolean setupKillerCol(Square move) {
		int x = move.posX;
		int y = move.posY;
		for(int i = 2; i < 7; i++) {
			//checks for -C or -M. Where C is a made character and M is the move, with - being a blank space.
			if ((board[i][y].getDisplay() == move.getDisplay() || i == x)
					&& board[i-1][y].getDisplay() == '-') {
				//checks for -CM- or -MC-
				if((board[i+1][y].getDisplay() == move.getDisplay() || i+1 == x)&& board[i+2][y].getDisplay() == '-') {
					//checks for --C
					if(board[i-2][y].getDisplay() == '-') {
						return true;
					}
					//checks for out of bound and then for form -CC--
					if(i + 3 < 9 && board[i+3][y].getDisplay() == '-') {
						return true;
					}
				}
				//checks for form -C-C- 
				if (i+ 3 < 9 && board[i+1][y].getDisplay() == '-' && board[i+3][y].getDisplay() == '-'
						&& (board[i+2][y].getDisplay() == move.getDisplay() || i+2 == x)) {
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
	 * Prints display of board.
	 */
	public void printDisplay() {
		
		for (int i = 0; i < 9; i++) {
			System.out.print(board[0][i].getDisplay() + " ");
		}
		
		//System.out.println("\t Player vs Opponent");
		
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
