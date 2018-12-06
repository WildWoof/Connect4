import java.util.ArrayList;
import java.util.Random;

public class Board
{
	Square[][] board;
	ArrayList<Square> log;
	int turn, timeLimit, turnCounter;
	boolean gameContinue, isFirst;
	final int maxDepth;

	/**
	 * Initialize the board with col and row labels with the rest '-'
	 * 
	 * @param Time Limit when Calculating Each Move
	 */
	public Board(int time, boolean isFirst)
	{
		maxDepth = 3;
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
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				if (j > 0 && i != 0)
				{
					board[i][j] = new Square('-', i, j);
				}
			}
		}

		timeLimit = time;
		gameContinue = true;
		turnCounter = 1;
		this.isFirst = isFirst;

	}

	/**
	 * Copy constructor
	 * 
	 * @param b original board b
	 */
	Board(Board b)
	{
		maxDepth = 3;
		board = new Square[9][9];
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				board[i][j] = new Square(b.board[i][j]);
			}
		}

		gameContinue = true;

		timeLimit = 25;
	}

	/**
	 * Set the Time Limit to Calculate Each Move
	 * 
	 * @param time
	 */
	public void setTimeLimit(int time)
	{
		timeLimit = time;
	}

	/**
	 * is called to determine whether or not the game is over.
	 * 
	 * @return gameContinue
	 */
	public boolean isGameContinue()
	{
		return gameContinue;
	}

	/**
	 * checks if the string has the opponent's move, then inputs the opponents move
	 * and updates the board
	 * 
	 * @param move
	 * @return true if successful, false if error
	 */
	public boolean opponentMove(String move)
	{
		int temp, row, col;

		temp = move.charAt(0);
		temp = temp - 96;

		if (temp >= 1 && temp <= 8 && move.length() == 2)
		{
			row = temp;
			temp = move.charAt(1);
			temp = temp - 48;

			if (temp >= 1 && temp <= 8)
			{
				col = temp;
				if (board[row][col].getIsFilled() == false)
				{
					board[row][col].setDisplay('o');
					turnCounter++;
					log.add(board[row][col]);
					return true; // successful input of opponent move

				}

			}
		}
		return false; // error when inputting opponent move
	}

	/**
	 * checks if the string has the computer's move, then inputs the computers move
	 * and updates the board
	 * 
	 * @param move
	 * @return true if successful, false if error
	 */
	public boolean computerMove(String move)
	{
		int temp = 0, row, col;

		if (move.length() > 0)
			temp = move.charAt(0);
		temp = temp - 96;

		if (temp >= 1 && temp <= 8 && move.length() == 2)
		{
			row = temp;
			temp = move.charAt(1);
			temp = temp - 48;

			if (temp >= 1 && temp <= 8)
			{
				col = temp;
				if (board[row][col].getIsFilled() == false)
				{
					board[row][col].setDisplay('x');
					turnCounter++;
					log.add(board[row][col]);
					return true; // successful input of opponent move

				}

			}
		}
		return false; // error when inputting opponent move
	}

	/**
	 * places one square onto the board. Made to take in a square passed by
	 * abPruning
	 * 
	 * @param move
	 */
	public void placeSquare(Square move)
	{
		board[move.posX][move.posY] = move;
	}

	/**
	 * Takes in the coordinates of a move and determines the score given by that
	 * square. The higher the score the better the move.
	 * 
	 * @return
	 */

	public int scoreMove(Square move)
	{
		int score = 0;

		// Rank 3: 3rd highest points given, if made you win next turn.
		if (checkKillerMoveRow(move))
		{
			// adding 100,000
			score += 100000;
		}

		if (checkKillerMoveCol(move))
		{
			// adding 100,000
			score += 100000;
		}

		// Rank2: 2nd highest points given. Can't win if you lose.
		if (stopWinningMove(move))
		{
			// adding 1,000,000
			score += 1000000;
		}

		// Rank1: greatest points given. You win, game over.
		if (makeWinningMove(move))
		{
			// adding 100,000,000
			score += 100000000;
		}

		// Rank 4: less than make killer move since making a killer move leads to a win,
		// and this stops a killer row from being made, but does not lead to a win.
		if (stopKillerRow(move))
		{
			// adding 10,000
			score += 10000;
		}
		// Rank 4:
		if (stopKillerCol(move))
		{
			// adding 10,000
			score += 10000;
		}

		// Rank 5: Always try to set up a killer move as its the only way to win
		if (setupKillerRow(move))
		{
			// adding 1,000
			score += 1000;
		}

		// Rank 5:
		if (setupKillerCol(move))
		{
			// adding 1,000
			score += 1000;
		}

		// Rank 6: Force Block when not setting up to win.
		// Better to be on the offensive than to be the reactionary player stopping wins
		if (forceBlock(move))
		{
			score += 100;
		}

		// Rank 7: Bias towards center squares, primarily relied on in early turns
		// The rows D and E will always have at least one of them in a vertical answer.
		// The cols e and 5 will always have at least one of them in a horizontal
		// answer.
		// Because of this the center 4 squares are the best starting moves.
		if (centralRows(move))
		{
			score += 10;
		}

		if (centralCols(move))
		{
			score += 10;
		}

		if (score > 0)
		{
			// test to check if score is actually doing anything
			System.out.print(' ');
			if (score > 1)
			{
				System.out.print(' ');
			}
		}
		return score;
	}

	/**
	 * checks the squares next to the move and checks if this will result in a move
	 * that will always lead to a win. Uses the char value in move. Returns true if
	 * there is a killer move. A killer move looks like -CCC-. Where - is an
	 * available move to win.
	 * 
	 * @param move
	 */
	public boolean checkKillerMoveRow(Square move)
	{
		int x = move.posX;
		int y = move.posY;

		// checks the row for a 3 in a row
		for (int i = 1; i < 6; i++)
		{
			// checks for the first character of move, or until the move is reached.
			// and if there is enough room for there to be two move of the same
			// move followed by a space -(current)CC-
			if (board[x][i].getDisplay() == move.getDisplay() || i == y)
			{
				// checks if there is a blank space before the square
				if (board[x][i - 1].getDisplay() == '-'
						// continues Checking for 3C in a row. if i == y then that is the move.
						&& (board[x][i + 1].getDisplay() == move.getDisplay() || i + 1 == y)
						&& (board[x][i + 2].getDisplay() == move.getDisplay() || i + 2 == y)
						// checks for space at the end.
						&& board[x][i + 3].getDisplay() == '-')
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks to see if this leads to a killer move on a column
	 * 
	 * @param move the move being scored
	 * @return true if this results in a killer more, false otherwise
	 */
	public boolean checkKillerMoveCol(Square move)
	{
		int x = move.posX;
		int y = move.posY;

		// checks the col for a 3 in a row
		for (int i = 1; i < 6; i++)
		{
			// checks for the first character of move, or until the move is reached.
			// and if there is enough room for there to be two move of the same
			// move followed by a space -(current)CC-
			if (board[i][y].getDisplay() == move.getDisplay() || i == x)
			{
				// checks if there is a blank space before the square
				if (board[i - 1][x].getDisplay() == '-'
						// continues Checking for 3C in a row. if i == x then that is the move.
						&& (board[i + 1][y].getDisplay() == move.getDisplay() || i + 1 == x)
						&& (board[i + 2][y].getDisplay() == move.getDisplay() || i + 2 == x)
						// checks for space at the end.
						&& board[i + 3][y].getDisplay() == '-')
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Second highest score awarded to stopping an opponent's winning move. Checks
	 * each row and column and checks if the move would stop a win.
	 * 
	 * @param move the tile that will be placed by this move
	 * @return true if a killer move is found and blocked by this move. false
	 *         otherwise
	 */
	public boolean stopWinningMove(Square move)
	{
		int x = move.posX;
		int y = move.posY;

		// checks the row for an enemy winning move in a row. i<5 makes sure there is
		// room.
		for (int i = 1; i < 6; i++)
		{
			// checks for the first character and checks to see if
			// there is a winning move in that range. If there is a spot
			// where there is a winning move, and it can make it, will return true.
			if (board[x][i].getDisplay() == move.getEnemy() || (i == y && board[x][i].getDisplay() == '-'))
			{

				// Checking for a winning enemy move. if i == y then that is the move.
				if ((board[x][i + 1].getDisplay() == move.getEnemy() || (i + 1 == y && board[x][i + 1].getDisplay() == '-'))
						&& (board[x][i + 2].getDisplay() == move.getEnemy() || (i + 2 == y && board[x][i + 2].getDisplay() == '-'))
						&& (board[x][i + 3].getDisplay() == move.getEnemy() || (i + 3 == y && board[x][i + 3].getDisplay() == '-')))
				{
					return true;
				}
			}
			// checks col
			if (board[i][y].getDisplay() == move.getEnemy() || (i == x && board[i][y].getDisplay() == '-'))
			{

				// Checking for a winning enemy move. if i == x then that is the move.
				if ((board[i + 1][y].getDisplay() == move.getEnemy() || (i + 1 == x && board[i + 1][y].getDisplay() == '-'))
						&& (board[i + 2][y].getDisplay() == move.getEnemy() || (i + 2 == x && board[i + 2][y].getDisplay() == '-'))
						&& (board[i + 3][y].getDisplay() == move.getEnemy() || (i + 3 == x && board[i + 3][y].getDisplay() == '-')))
				{
					return true;
				}
			}

		}

		return false;
	}

	/**
	 * Checks both vertically and horizontally for a winning move to be made
	 * 
	 * @param move the move being made
	 * @return true if there is a winning move, false otherwise
	 */
	public boolean makeWinningMove(Square move)
	{
		int x = move.posX;
		int y = move.posY;

		// checks the row for an enemy winning move in a row
		for (int i = 1; i < 6; i++)
		{
			// checks for the first character and checks to see if
			// there is a winning move in that range. If there is a spot
			// where there is a winning move, and it can take it, will return true.
			if (board[x][i].getDisplay() == move.getDisplay() || (i == y && board[x][i].getDisplay() == '-'))
			{

				// Checking for a winning enemy move. if i == y then that is the move.
				if ((board[x][i + 1].getDisplay() == move.getDisplay() || (i + 1 == y && board[x][i + 1].getDisplay() == '-'))
						&& (board[x][i + 2].getDisplay() == move.getDisplay() || (i + 2 == y && board[x][i + 2].getDisplay() == '-'))
						&& (board[x][i + 3].getDisplay() == move.getDisplay() || (i + 3 == y && board[x][i + 3].getDisplay() == '-')))
				{
					return true;
				}
			}
			// checks col
			if (board[i][y].getDisplay() == move.getDisplay() || (i == x && board[i][y].getDisplay() == '-'))
			{

				// Checking for a winning move. if i == x then that is the move.
				if ((board[i + 1][y].getDisplay() == move.getDisplay() || (i + 1 == x && board[i + 1][y].getDisplay() == '-'))
						&& (board[i + 2][y].getDisplay() == move.getDisplay() || (i + 2 == x && board[i + 2][y].getDisplay() == '-'))
						&& (board[i + 3][y].getDisplay() == move.getDisplay() || (i + 3 == x && board[i + 3][y].getDisplay() == '-')))
				{
					return true;
				}
			}

		}

		return false;
	}

	/**
	 * This checks a row for two patterns that are 1 move away from being a killer
	 * move Those patterns are -CC- and -C-C-. This checks if the move blocks it.
	 * 
	 * @param move the move being scored
	 * @return true if it stops a killer row, false otherwise
	 */
	public boolean stopKillerRow(Square move)
	{
		int x = move.posX;
		int y = move.posY;
		for (int i = 2; i < 7; i++)
		{
			// Checks for the -C which is the start of a killer row
			if (board[x][i].getDisplay() == move.getEnemy() && board[x][i - 1].getDisplay() == '-')
			{
				// checks for -C C-, which sets up a killer Row. Returns true if blocks an empty
				// space
				if (board[x][i + 1].getDisplay() == move.getEnemy() && board[x][i + 2].getDisplay() == '-' && (y == i - 1 || y == i + 2))
				{
					return true;
					// checks for -C -C-, another killer row setup
				} else if (i < 6 && board[x][i + 1].getDisplay() == '-' && board[x][i + 2].getDisplay() == move.getEnemy()
						&& board[x][i + 3].getDisplay() == '-' && (y == i - 1 || y == i + 1 || y == i + 3))
				{
					return true;
				}
			}

		}

		return false;
	}

	/**
	 * This checks a col for two patterns that are 1 move away from being a killer
	 * move Those patterns are -CC- and -C-C-. This checks if the move blocks it.
	 * 
	 * @param move the move being scored
	 * @return true if it stops a killer row, false otherwise
	 */
	public boolean stopKillerCol(Square move)
	{
		int x = move.posX;
		int y = move.posY;
		for (int i = 2; i < 7; i++)
		{
			// Checks for the -C which is the start of a killer row
			if (board[i][y].getDisplay() == move.getEnemy() && board[i - 1][y].getDisplay() == '-')
			{
				// checks for -C C-, which sets up a killer Row. Returns true if blocks an empty
				// space
				if (board[i + 1][y].getDisplay() == move.getEnemy() && board[i + 2][y].getDisplay() == '-' && (x == i - 1 || x == i + 2))
				{
					return true;
					// checks for -C -C-, another killer row setup
				} else if (i < 6 && board[x][i + 1].getDisplay() == '-' && board[i + 2][y].getDisplay() == move.getEnemy()
						&& board[i + 3][y].getDisplay() == '-' && (x == i - 1 || x == i + 1 || x == i + 3))
				{
					return true;
				}
			}

		}

		return false;
	}

	/**
	 * Tries to set up to make a killer move in the next turn by having a square
	 * with both sides empty Is of three forms, --CC-, -CC--, or -C-C-
	 * 
	 * @param move the being tested
	 * @return true if it does set up, false otherwise
	 */
	public boolean setupKillerRow(Square move)
	{
		int x = move.posX;
		int y = move.posY;
		for (int i = 2; i < 7; i++)
		{
			// checks for -C or -M. Where C is a made character and M is the move, with -
			// being a blank space.
			if ((board[x][i].getDisplay() == move.getDisplay() || i == y) && board[x][i - 1].getDisplay() == '-')
			{
				// checks for -CM- or -MC-
				if ((board[x][i + 1].getDisplay() == move.getDisplay() || i + 1 == y) && board[x][i + 2].getDisplay() == '-')
				{
					// checks for --C
					if (board[x][i - 2].getDisplay() == '-')
					{
						return true;
					}
					// checks for out of bound and then for form -CC--
					if (i + 3 < 9 && board[x][i + 3].getDisplay() == '-')
					{
						return true;
					}
				}
				// checks for form -C-C-
				if (i + 3 < 9 && board[x][i + 1].getDisplay() == '-' && board[x][i + 3].getDisplay() == '-'
						&& (board[x][i + 2].getDisplay() == move.getDisplay() || i + 2 == y))
				{
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Tries to set up to make a killer move in the next turn by having a square
	 * with both sides empty Is of three forms, --CC-, -CC--, or -C-C-
	 * 
	 * @param move the being tested
	 * @return true if it does set up, false otherwise
	 */
	public boolean setupKillerCol(Square move)
	{
		int x = move.posX;
		int y = move.posY;
		for (int i = 2; i < 7; i++)
		{
			// checks for -C or -M. Where C is a made character and M is the move, with -
			// being a blank space.
			if ((board[i][y].getDisplay() == move.getDisplay() || i == x) && board[i - 1][y].getDisplay() == '-')
			{
				// checks for -CM- or -MC-
				if ((board[i + 1][y].getDisplay() == move.getDisplay() || i + 1 == x) && board[i + 2][y].getDisplay() == '-')
				{
					// checks for --C
					if (board[i - 2][y].getDisplay() == '-')
					{
						return true;
					}
					// checks for out of bound and then for form -CC--
					if (i + 3 < 9 && board[i + 3][y].getDisplay() == '-')
					{
						return true;
					}
				}
				// checks for form -C-C-
				if (i + 3 < 9 && board[i + 1][y].getDisplay() == '-' && board[i + 3][y].getDisplay() == '-'
						&& (board[i + 2][y].getDisplay() == move.getDisplay() || i + 2 == x))
				{
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Checks if the move forces a block. In form -CCC, CCC-, C-CC or CC-C
	 * 
	 * @param move the move being checked if it forces a block
	 * @return true if it does force a block false otherwise.
	 */
	public boolean forceBlock(Square move)
	{
		int x = move.posX;
		int y = move.posY;
		for (int i = 1; i < 6; i++)
		{
			// check for -CCC
			// row
			if (board[x][i].getDisplay() == '-' && (board[x][i + 1].getDisplay() == move.getDisplay() || y == i + 1)
					&& (board[x][i + 2].getDisplay() == move.getDisplay() || y == i + 2)
					&& (board[x][i + 3].getDisplay() == move.getDisplay() || y == i + 3))
			{
				return true;
			}
			// col
			if (board[i][y].getDisplay() == '-' && (board[i + 1][y].getDisplay() == move.getDisplay() || x == i + 1)
					&& (board[i + 2][y].getDisplay() == move.getDisplay() || x == i + 2)
					&& (board[i + 3][y].getDisplay() == move.getDisplay() || x == i + 3))
			{
				return true;
			}
			// check for CCC-
			// row
			if (board[x][i + 3].getDisplay() == '-' && (board[x][i].getDisplay() == move.getDisplay() || y == i)
					&& (board[x][i + 1].getDisplay() == move.getDisplay() || y == i + 1)
					&& (board[x][i + 2].getDisplay() == move.getDisplay() || y == i + 2))
			{
				return true;
			}
			// col
			if (board[i + 3][y].getDisplay() == '-' && (board[i][y].getDisplay() == move.getDisplay() || x == i)
					&& (board[i + 1][y].getDisplay() == move.getDisplay() || x == i + 1)
					&& (board[i + 2][y].getDisplay() == move.getDisplay() || x == i + 2))
			{
				return true;
			}

			// check for C-CC
			if (board[x][i + 1].getDisplay() == '-' && (board[x][i].getDisplay() == move.getDisplay() || y == i)
					&& (board[x][i + 2].getDisplay() == move.getDisplay() || y == i + 2)
					&& (board[x][i + 3].getDisplay() == move.getDisplay() || y == i + 3))
			{
				return true;
			}

			if (board[i + 1][y].getDisplay() == '-' && (board[i][y].getDisplay() == move.getDisplay() || x == i)
					&& (board[i + 2][y].getDisplay() == move.getDisplay() || x == i + 2)
					&& (board[i + 3][y].getDisplay() == move.getDisplay() || x == i + 3))
			{
				return true;
			}

			// check for CC-C
			if (board[x][i + 2].getDisplay() == '-' && (board[x][i].getDisplay() == move.getDisplay() || y == i)
					&& (board[x][i + 1].getDisplay() == move.getDisplay() || y == i + 1)
					&& (board[x][i + 3].getDisplay() == move.getDisplay() || y == i + 3))
			{
				return true;
			}

			if (board[i + 2][y].getDisplay() == '-' && (board[i][y].getDisplay() == move.getDisplay() || x == i)
					&& (board[i + 1][y].getDisplay() == move.getDisplay() || x == i + 1)
					&& (board[i + 3][y].getDisplay() == move.getDisplay() || x == i + 3))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Tests to see if the move is in row D or E
	 * 
	 * @param move the move being tested
	 * @return true if the move is in the center rows
	 */
	public boolean centralRows(Square move)
	{
		if (move.posX == 4 || move.posX == 5)
		{
			return true;
		}
		return false;
	}

	/**
	 * Test to see if the move is in column 4 or 5
	 * 
	 * @param move the move being tested
	 * @return true if te move is in the center cols
	 */
	public boolean centralCols(Square move)
	{
		if (move.posY == 4 || move.posY == 5)
		{
			return true;
		}
		return false;
	}

	/**
	 * Scores each empty space where a move can go. Avoids the 0 index because those
	 * are headers.
	 */
	public void scoreBoard()
	{
		for (int i = 1; i < 9; i++)
		{
			for (int j = 1; j < 9; j++)
			{
				if (!board[i][j].getIsFilled())
				{
					board[i][j].setScore(scoreMove(board[i][j]));
				}
			}
		}
	}

	/**
	 * returns if there is room for more moves on the board.
	 * 
	 * @return
	 */
	public boolean moreMoves()
	{
		for (int i = 1; i < 9; i++)
		{
			for (int j = 1; j < 9; j++)
			{
				if (!board[i][j].getIsFilled())
				{
					return true;
				}
			}

		}
		return false;
	}

	/**
	 * prints just the board. Used for testing, as it does not print the move log.
	 */
	public void printBoard()
	{
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				System.out.print(board[i][j].getDisplay() + " ");
			}
			System.out.println();
		}
	}

	/**
	 * Prints display of board.
	 */
	public void printDisplay()
	{

		System.out.println();
		for (int i = 0; i < 9; i++)
		{
			System.out.print(board[0][i].getDisplay() + " ");
		}

		if (isFirst)
			System.out.print("\t\t Computer vs Opponent");
		else
			System.out.print("\t\t Opponent vs Computer");
		System.out.println();

		int roundCounter = 1;

		// Board
		for (int i = 0; i < 9; i++)
		{
			System.out.print(board[1][i].getDisplay() + " ");
		}

		// History
		if (log.size() > 0)
		{
			System.out.print("\t\t" + roundCounter + ". " + log.get(0).findBoardPosition());
		}

		if (log.size() > 1)
		{
			System.out.print("\t\t" + log.get(1).findBoardPosition());
		}

		roundCounter++;

		System.out.println("\t \t");

		// Board
		for (int i = 0; i < 9; i++)
		{
			System.out.print(board[2][i].getDisplay() + " ");
		}
		// History
		if (log.size() > 2)
		{
			System.out.print("\t\t" + roundCounter + ". " + log.get(2).findBoardPosition());
		}

		if (log.size() > 3)
		{
			System.out.print("\t\t" + log.get(3).findBoardPosition());

		}

		roundCounter++;

		System.out.println("\t \t ");

		// Board
		for (int i = 0; i < 9; i++)
		{
			System.out.print(board[3][i].getDisplay() + " ");
		}
		// History
		if (log.size() > 4)
		{
			System.out.print("\t\t" + roundCounter + ". " + log.get(4).findBoardPosition());
		}

		if (log.size() > 5)
		{
			System.out.print("\t\t" + log.get(5).findBoardPosition());
		}

		roundCounter++;

		System.out.println("\t \t ");

		// Board
		for (int i = 0; i < 9; i++)
		{
			System.out.print(board[4][i].getDisplay() + " ");
		}
		// History
		if (log.size() > 6)
		{
			System.out.print("\t\t" + roundCounter + ". " + log.get(6).findBoardPosition());
		}

		if (log.size() > 7)
		{
			System.out.print("\t\t" + log.get(7).findBoardPosition());
		}

		roundCounter++;

		System.out.println("\t \t ");

		// Board
		for (int i = 0; i < 9; i++)
		{
			System.out.print(board[5][i].getDisplay() + " ");
		}
		// History
		if (log.size() > 8)
		{
			System.out.print("\t\t" + roundCounter + ". " + log.get(8).findBoardPosition());
		}

		if (log.size() > 9)
		{
			System.out.print("\t\t" + log.get(9).findBoardPosition());
		}

		roundCounter++;

		System.out.println("\t \t ");

		// Board
		for (int i = 0; i < 9; i++)
		{
			System.out.print(board[6][i].getDisplay() + " ");
		}
		// History
		if (log.size() > 10)
		{
			System.out.print("\t\t" + roundCounter + ". " + log.get(10).findBoardPosition());
		}

		if (log.size() > 11)
		{
			System.out.print("\t\t" + log.get(11).findBoardPosition());
		}

		roundCounter++;

		System.out.println("\t \t ");

		// Board
		for (int i = 0; i < 9; i++)
		{
			System.out.print(board[7][i].getDisplay() + " ");
		}
		// History
		if (log.size() > 12)
		{
			System.out.print("\t\t" + roundCounter + ". " + log.get(12).findBoardPosition());
		}

		if (log.size() > 13)
		{
			System.out.print("\t\t" + log.get(13).findBoardPosition());
		}

		roundCounter++;

		System.out.println("\t \t ");

		// Board
		for (int i = 0; i < 9; i++)
		{
			System.out.print(board[8][i].getDisplay() + " ");
		}
		// History
		if (log.size() > 14)
		{
			System.out.print("\t\t" + roundCounter + ". " + log.get(14).findBoardPosition());
		}

		if (log.size() > 15)
		{
			System.out.println("\t\t" + log.get(15).findBoardPosition());
		}

		roundCounter++;
		// History
		if (log.size() > 16)
		{
			boolean first = true;

			for (int i = 16; i < log.size(); i++)
			{

				if (first)
					System.out.print("\t\t\t\t" + roundCounter + ". " + log.get(i).findBoardPosition());
				else
				{
					System.out.println("\t\t" + log.get(i).findBoardPosition());
					roundCounter++;
				}
				first = !first;
			}

		}

		System.out.println("\t \t ");

	}

	//Overloaded test algorithm to make sure the scoring is working.
//	public Square findBestMove(int time)
//	{
//		Square bestMove = board[1][1];
//
//		for (int i = 1; i < 9; i++)
//		{
//			for (int j = 1; j < 9; j++)
//			{
//				// score the tile
//				if (!board[i][j].getIsFilled())
//				{
//					// set char for enemy checking in evaluation
//					board[i][j].setDisplay('x');
//					// score move takes in a move with a character.
//					board[i][j].setScore(scoreMove(board[i][j]));
//					// set display back
//					board[i][j].setDisplay('-');
//				}
//				// if the square is not filled then if its score is better than the best move
//				// replace best move.
//				if (!board[i][j].isFilled && board[i][j].getScore() > bestMove.getScore())
//				{
//					bestMove = board[i][j];
//				}
//			}
//		}
//
//		bestMove.setDisplay('x');
//		placeSquare(bestMove);
//		log.add(bestMove);
//		turnCounter++;
//		return bestMove;
//	}

	/**
	 * MiniMax recursive algorithm. Starts of as max, iterating through the entire board
	 * recursively to find the best move. 
	 * 
	 * @return true if game is over. false otherwise
	 */
	public Boolean findBestMove(Boolean goesFirst)
	{
		int depth;
		//if goes first looks further ahead
		if(goesFirst) {
			depth = 1;
		//looks less ahead due to scoring mechanism not stopping wins if going second.
		} else {
			depth = 1;
		}
		
		Square bestMove = findFirstAvailableSpace();

		// traverse through the board
		for (int i = 1; i < 9; i++)
		{
			for (int j = 1; j < 9; j++)
			{
				// if available square, make the move.
				if (!board[i][j].getIsFilled())
				{
					board[i][j].setDisplay('x');
					board[i][j].setScore(scoreMove(board[i][j]));

					// Recursively call. Currently we are max, so this move will call min
					Square move = minValue(board[i][j], depth, -2000000000,2000000000);
					
//					//if the move has an equal score, randomly replace the move with this move
					Random random = new Random();
					int r = random.nextInt(100) + 1;
					if(r > 50 && !move.getIsFilled() && move.getScore() == bestMove.getScore()) {
						bestMove = move;
					}

					// if the move has a better score than best move, replace best move
					if (!move.getIsFilled() && move.getScore() > bestMove.getScore())
					{
						bestMove = move;
					}
					// undo the move continue iterating and scoring.
					board[i][j].setDisplay('-');

				}
			}

		}
		bestMove.setDisplay('x');
		// make the best move
		
		placeSquare(bestMove);
		if (makeWinningMove(bestMove)) {
			return true;
		}
		turnCounter++;
		log.add(bestMove);
		return false;
	}

	public Square maxValue(Square move, int depth, long alpha, long beta)
	{
		// terminal condition, max depth reached, or no more moves available
		if (depth == maxDepth || !moreMoves())
		{
			move.setScore(scoreMove(move));
			return move;
		}
		Square bestMove = move;
		Square nextMove;

		// traverse through the board
		// in psuedocode this is iterating through successor states
		for (int i = 1; i < 9; i++)
		{
			for (int j = 1; j < 9; j++)
			{
				// if available square, make the move.

				if (!board[i][j].getIsFilled())
				{
					board[i][j].setDisplay('x');
					// Recursively call. Currently we are max, so this move will call min
					nextMove = minValue(board[i][j], depth + 1, alpha, beta);

					// if the move has less points than the best move, replace best.
					if (!move.getIsFilled() && nextMove.getScore() < bestMove.getScore())
					{
						bestMove = nextMove;
					}

					// undo the move continue iterating and scoring.
					board[i][j].setDisplay('-');

					// pruning
					if (move.getScore() > beta)
					{
						break;
					}
					// update alpha.
					if (move.getScore() > alpha)
					{
						alpha = move.getScore();
					}

				}
			}

		}

		return bestMove;
	}

	public Square minValue(Square move, int depth, long alpha, long beta)
	{
		// terminal condition, max depth reached, or no more moves available
		if (depth == maxDepth || !moreMoves())
		{

			move.setScore(scoreMove(move));
			return move;
		}
		Square bestMove = move;
		Square nextMove;
		bestMove.setScore(scoreMove(bestMove));
		// traverse through the board
		// in psuedocode this is iterating through all successor states
		for (int i = 1; i < 9; i++)
		{
			for (int j = 1; j < 9; j++)
			{
				// if available square, make the move.
				if (!board[i][j].getIsFilled())
				{
					board[i][j].setDisplay('o');
					// Recursively call. Currently we are max, so this move will call max

					nextMove = maxValue(board[i][j], depth + 1, alpha, beta);

					// if the move has more points than the best move, replace best.
					// even though we are in min, the scoring algorithm is scoring the next move
					// based on
					// min, so we want as high a score as possible, meaning min will choose
					// optimally
					if (nextMove.getScore() > bestMove.getScore())
					{
						bestMove = nextMove;
					}

					// undo the move continue iterating and scoring.
					board[i][j].setDisplay('-');

					// pruning
					if (move.getScore() < alpha)
					{
						break;
					}
					// update beta.
					if (move.getScore() < beta)
					{
						beta = move.getScore();
					}

				}
			}

		}

		return bestMove;
	}

	/**
	 * used to
	 * 
	 * @return
	 */
	public Square findFirstAvailableSpace()
	{

		for (int i = 1; i < 9; i++)
		{
			for (int j = 1; j < 9; j++)
			{
				if (!board[i][j].getIsFilled())
				{
					return board[i][j];
				}
			}
		}

		System.out.println("Game Over, No available spaces");
		return null;
	}

}
