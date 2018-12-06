
public class Square
{
	boolean isFilled;
	// display is X, O, or - in the graph. The
	// 0 indexed row and columns are auto filled with custom
	// labels to each row or col.
	char display;
	// enemy is the opposite of X or O, whatever this square is not.
	// only initialized if X or O is assigned.
	char enemy;
	int posX;
	int posY;
	String boardPosition;
	long score;
	long cumulativeScore;

	public Square()
	{
		display = '-';
		isFilled = false;
		score = -1000;
	}

	// To input a square without initializing coordinatews
	public Square(char c)
	{
		display = c;
		if (c == '-')
		{
			isFilled = false;
		} else
		{
			isFilled = true;
		}
		if (c == 'X' || c == 'x')
		{
			enemy = 'o';
		} else if (c == 'O' || c == 'o')
		{
			enemy = 'x';
		}
	}

	// to input a square of move c and pos x, y
	public Square(char c, int x, int y)
	{
		display = c;
		if (c == '-')
		{
			isFilled = false;
		} else
		{
			isFilled = true;
		}
		posX = x;
		posY = y;
		if (c == 'X' || c == 'x')
		{
			enemy = 'o';
		} else if (c == 'O' || c == 'o')
		{
			enemy = 'x';
		}
	}
	

	/**
	 * Copy constructor
	 * 
	 * @param s original square s
	 */
	public Square(Square s)
	{
		isFilled = s.isFilled;
		display = s.display;
		enemy = s.enemy;
		posX = s.posX;
		posY = s.posY;
		score = s.score;
		findBoardPosition();

	}

	// display is the X, O, or - on the screen
	public char getDisplay()
	{
		return display;
	}

	// enemy is the opposite of X or O.
	public char getEnemy()
	{
		return enemy;
	}

	public boolean getIsFilled()
	{
		if (display == '-')
		{
			return false;
		} else
		{
			return true;
		}
		// return isFilled;
	}

	public void setDisplay(char temp)
	{
		display = temp;
		if (display == '-')
		{
			isFilled = false;
		} else
		{
			isFilled = true;
		}
	}

	public long getScore()
	{
		return score;
	}

	public void setScore(long newScore)
	{
		score = newScore;
	}

	/**
	 * finds the position of this square on the board and turns it into a
	 * [letter][number] layout instead of x y coordinates.
	 */
	public String findBoardPosition()
	{
		switch (posX)
		{

		case 1:
			boardPosition = "A" + posY;
			return boardPosition;
		case 2:
			boardPosition = "B" + posY;
			return boardPosition;
		case 3:
			boardPosition = "C" + posY;
			return boardPosition;

		case 4:
			boardPosition = "D" + posY;
			return boardPosition;
		case 5:
			boardPosition = "E" + posY;
			return boardPosition;

		case 6:
			boardPosition = "F" + posY;
			return boardPosition;
		case 7:
			boardPosition = "G" + posY;
			return boardPosition;

		case 8:
			boardPosition = "H" + posY;
			return boardPosition;
		default:
			return null;

		}
	}
}
