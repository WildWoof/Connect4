
public class Square {
	boolean isFilled;
	char display;
	int posX;
	int posY;
	String boardPosition;

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
