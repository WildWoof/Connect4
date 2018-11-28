
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
	}

	public Square(char c) {
		display = c;
		if (c == '-') {
			isFilled = false;
		} else {
			isFilled = true;
		}
		if ( c == 'X') {
			enemy = 'O';
		} else if (c == 'O') {
			enemy = 'X';
		}
	}

	public Square(char c, int x, int y) {
		display = c;
		if (c == '-') {
			isFilled = false;
		} else {
			isFilled = true;
		}
		posX = x;
		posY = y;
		if ( c == 'X') {
			enemy = 'O';
		} else if (c == 'O') {
			enemy = 'X';
		}
	}
	
	/**
	 * Copy constructor
	 * @param s original square s
	 */
	public Square(Square s) {
		display = s.display;
		posX = s.posX;
		posY = s.posY;
		isFilled = s.isFilled;
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
		return isFilled;
	}
	
	public void setDisplay(char temp)
	{
		display = temp;
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
