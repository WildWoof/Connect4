import java.util.Scanner;

public class Main
{

	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		
		System.out.println("4-In-a-Line");
		System.out.println();
		System.out.println("What would you like to do?");
		printOptions();
		
		

		//Board b = new Board();
		//b.printDisplay();

	}
	
	private static void printOptions()
	{
		System.out.println("1.) Start a New Game");
		System.out.println("2.) Recreate a game and Resume");
		
		
	}

}
