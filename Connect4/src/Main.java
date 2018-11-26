import java.util.InputMismatchException;
import java.util.Scanner;

public class Main
{
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args)
	{
		boolean exit = false;
		
		int choice = 0;

		System.out.println("4-In-a-Line");
		System.out.println("By Richard Matthew Musquiz and Cody Nguyen");
		System.out.println("----------------------------------------------");

		while (!exit)
		{
			System.out.println();
			System.out.println("What would you like to do?");
			printOptions();

			choice = scan.nextInt();

			switch (choice)
			{
			case 1:
				startNewGame();
				System.out.println("----------------------------------------------");
				break;

			case 2:
				recreateGame();
				System.out.println("----------------------------------------------");
				break;
			case 3:
				exit = true;
				System.out.println("----------------------------------------------");
				break;

			default:
				System.out.println("Invalid Input, Try Again");
				System.out.println("----------------------------------------------");
				break;
			}

		}
		System.out.println("Thank You for Playing!");
		scan.close();

		// Board b = new Board();
		// b.printDisplay();

	}

	private static void printOptions()
	{
		System.out.println("1.) Start a New Game");
		System.out.println("2.) Recreate a game and Resume");
		System.out.println("3.) Exit");

	}

	private static void startNewGame()
	{
		scan.nextLine();
		String goesFirst = null;
		boolean invalidInput = true, computerFirst = false;
		
		int timeLimit = 0;

		System.out.println("----------------------------------------------");

		while (invalidInput)
		{
			System.out.println();
			System.out.println("Who goes first, C for computer, O for opponent");
			goesFirst = scan.nextLine();

			if (goesFirst.toLowerCase().equals("c"))
			{
				invalidInput = false;
				computerFirst = true;
			} else if (goesFirst.toLowerCase().equals("o"))
			{
				invalidInput = false;
				computerFirst = false;
			} else
			{
				System.out.println("Invalid Input, Try Again");
			}
		}

		System.out.println();
		System.out.println("What is the Time Limit Per Move? (In Seconds)");
		timeLimit = scan.nextInt();

		Board board = new Board();
		board.printDisplay();
		int turnCounter = 0;

		System.out.println();
		System.out.println("Player vs. Opponent");
		turnCounter++;
		System.out.print(turnCounter + ".");


	}

	private static void recreateGame()
	{
		System.out.println("test2");
	}

}
