import java.util.InputMismatchException;
import java.util.Scanner;

public class Main
{

	public static void main(String[] args)
	{
		boolean exit = false;
		Scanner scan = new Scanner(System.in);
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
		System.out.println("----------------------------------------------");
	}

	private static void recreateGame()
	{
		System.out.println("test2");
	}

}
