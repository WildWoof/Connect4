
public class Square {
	boolean isFilled;
	//display is X, O, or - in the graph. The
	//0 indexed row and columns are auto filled with custom
	//labels to each row or col.
	char display;
	//enemy is the opposite of X or O, whatever this square is not.
	//only initialized if X or O is assigned.
	char enemy;
	int posX;
	int posY;
	String boardPosition;
	int score;

	public Square() {
		display = '-';
		isFilled = false;
		score = -1000;
	}

	//To input a square without initializing coordinatews
	public Square(char c) {
		display = c;
		if (c == '-') {
			isFilled = false;
		} else {
			isFilled = true;
		}
		if ( c == 'X' || c == 'x') {
			enemy = 'o';
		} else if (c == 'O' || c == 'o') {
			enemy = 'x';
		}
	}

	// to input a square of move c and pos x, y
	public Square(char c, int x, int y) {
		display = c;
		if (c == '-') {
			isFilled = false;
		} else {
			isFilled = true;
		}
		posX = x;
		posY = y;
		if ( c == 'X' || c== 'x') {
			enemy = 'o';
		} else if (c == 'O' || c == 'o') {
			enemy = 'x';
		}
	}
	
	/**
	 * Copy constructor
	 * @param s original square s
	 */
	public Square(Square s) {
		isFilled = s.isFilled;
		display = s.display;
		enemy = s.enemy;
		posX = s.posX;
		posY = s.posY;
		score = s.score;
		findBoardPosition();
		
	}
	//display is the X, O, or - on the screen
	public char getDisplay() {
		return display;
	}
	
	//enemy is the opposite of X or O. 
	public char getEnemy() {
		return enemy;
	}
	
	
	public boolean getIsFilled()
	{
		if (display == '-') {
			return false;
		} else {
			return true;
		}
		//return isFilled;
	}
	
	public void setDisplay(char temp)
	{
		display = temp;
		if (display == '-') {
			isFilled = false;
		}else {
			isFilled = true;
		}
	}
	
	public int getScore() {
		return score;
	}
	public void setScore(int newScore) {
		score = newScore;
	}

	/**
	 * finds the position of this square on the board and turns it into a
	 * [letter][number] layout instead of x y coordinates.
	 */
	public void findBoardPosition() {
		switch (posX) {

		case 1:
			boardPosition = "a" + posY;
			System.out.println(boardPosition);
			break;

		case 2:
			boardPosition = "b" + posY;
			System.out.println(boardPosition);
			
			break;
		case 3:
			boardPosition = "c" + posY;
			System.out.println(boardPosition);
			break;

		case 4:
			boardPosition = "d" + posY;
			System.out.println(boardPosition);
			break;
		case 5:
			boardPosition = "e" + posY;
			System.out.println(boardPosition);
			break;

		case 6:
			boardPosition = "f" + posY;
			System.out.println(boardPosition);
			break;
		case 7:
			boardPosition = "g" + posY;
			System.out.println(boardPosition);
			break;

		case 8:
			boardPosition = "h" + posY;
			System.out.println(boardPosition);
			break;

		default:
			break;

		}
	}
}
