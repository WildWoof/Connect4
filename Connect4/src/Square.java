
public class Square {
	boolean isFilled;
	char display;
	int posX;
	int posY;

	public Square() {
		display = '-';
	}

	public Square(char c) {
		display = c;
	}

	public char getDisplay() {
		return display;
	}
}
