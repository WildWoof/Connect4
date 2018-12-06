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

		// testAlgorithm();

		// this handles the initial menu
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
			case 4:
				// testAlgorithm();
				break;

			default:
				System.out.println("Invalid Input, Try Again");
				System.out.println("----------------------------------------------");
				break;
			}

		}
		System.out.println("Thank You for Playing!");
		scan.close();

	}

	/*
	 * private static void testAlgorithm() { Board board = new Board(30);
	 * 
	 * // TEST OF all scoring heuristics. Depth 0. No miniMax.
	 * 
	 * Square move = new Square ('o' , 5, 4); board.placeSquare(move);
	 * board.findBestMove(4); move = new Square ('o' , 5, 5); board.findBestMove(4);
	 * move = new Square ('o' , 4, 3); board.placeSquare(move);
	 * board.printDisplay(); board.findBestMove(4); board.printDisplay();
	 * 
	 * // Test succeeds, optimal move chosen each time. // END TEST
	 * 
	 * board.findBestMove(); Square move = new Square('o', 5, 4);
	 * board.placeSquare(move); board.printBoard(); board.findBestMove();
	 * board.printBoard(); move = new Square('o', 4, 3); board.placeSquare(move);
	 * board.findBestMove(); board.printBoard();
	 * 
	 * }
	 */

	/**
	 * This prints the avaliable options of the initial menu
	 */
	private static void printOptions()
	{
		System.out.println("1.) Start a New Game");
		System.out.println("2.) Recreate a game and Resume");
		System.out.println("3.) Exit");

	}

	/**
	 * This begins a new game, asking who goes first and then creating the game to
	 * begin playing
	 */
	private static void startNewGame()
	{
		scan.nextLine();
		String goesFirst = null, move = null;
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
		scan.nextLine();

		Board board = new Board(timeLimit, computerFirst);

		if (computerFirst)
		{

			do
			{

				// computer moves
				System.out.println();
				board.findBestMove(computerFirst);
				board.printDisplay();

				// enter opponent move
				boolean first = true;
				do
				{
					System.out.println();
					if (!first)
					{
						System.out.println("Error With Move, Try Again");
					}

					System.out.println("Enter Opponent Move (Ex: e5)");

					move = scan.nextLine().trim().toLowerCase();
					first = false;
				} while (!board.opponentMove(move));

			} while (board.isGameContinue());
			System.out.println("GAME OVER");
			board.printDisplay();

		} else
		{

			do
			{

				// enter opponent move
				boolean first = true;
				do
				{
					System.out.println();
					if (!first)
					{
						System.out.println("Error With Move, Try Again");
					}

					System.out.println("Enter Opponent Move (Ex: e5)");

					move = scan.nextLine().trim().toLowerCase();
					first = false;
				} while (!board.opponentMove(move));

				// computer moves
				System.out.println();
				board.findBestMove(computerFirst);
				board.printDisplay();

			} while (board.isGameContinue());

			System.out.println("GAME OVER");
			board.printDisplay();
		}

	}

	/**
	 * This recreates a game and inputs the info of the previous game. then
	 * recreates the board state and resumes from where the game left off.
	 */
	private static void recreateGame()
	{
		scan.nextLine();
		String goesFirst = null, move = null;
		boolean invalidInput = true, computerFirst = false;
		int timeLimit = 0;

		System.out.println("----------------------------------------------");

		while (invalidInput)
		{
			System.out.println();
			System.out.println("Whose turn is it currently? C for computer, O for opponent");
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
		scan.nextLine();

		Board board = new Board(timeLimit, computerFirst);

		boolean first = true;

		System.out.println("Enter all of this computer's moves (The x's). Enter 'continue' when done.");
		move = "";
		while (!move.equals("continue"))
		{
			first = true;
			do
			{
				System.out.println();
				if (!first)
				{
					System.out.println("Error With Move, Try Again");
				}

				System.out.println("Enter Next Move (Ex: e5)");

				move = scan.nextLine().trim().toLowerCase();
				first = false;

			} while (!move.equals("continue") && !board.computerMove(move));
		}

		System.out.println("Enter all of the opponents's moves (The o's). Enter 'continue' when done.");

		move = "";
		while (!move.equals("continue"))
		{
			first = true;
			do
			{
				System.out.println();
				if (!first)
				{
					System.out.println("Error With Move, Try Again");
				}

				System.out.println("Enter Next Move (Ex: e5)");

				move = scan.nextLine().trim().toLowerCase();
				first = false;

			} while (!move.equals("continue") && !board.opponentMove(move));
		}

		// This is a complete copy of code from Start New Game
		if (computerFirst)
		{
			do
			{
				// computer moves
				System.out.println();
				board.findBestMove(computerFirst);
				board.printDisplay();

				// enter opponent move
				first = true;
				do
				{
					System.out.println();
					if (!first)
					{
						System.out.println("Error With Move, Try Again");
					}

					System.out.println("Enter Opponent Move (Ex: e5)");

					move = scan.nextLine().trim().toLowerCase();
					first = false;
				} while (!move.equals("continue") && !board.opponentMove(move));

			} while (board.isGameContinue());
			System.out.println("GAME OVER");
			board.printDisplay();

		} else
		{

			do
			{
				// enter opponent move
				first = true;
				do
				{
					System.out.println();
					if (!first)
					{
						System.out.println("Error With Move, Try Again");
					}

					System.out.println("Enter Opponent Move (Ex: e5)");

					move = scan.nextLine().trim().toLowerCase();
					first = false;
				} while (!board.opponentMove(move));

				// computer moves
				System.out.println();
				board.findBestMove(computerFirst);
				board.printDisplay();
				

			} while (board.isGameContinue());

			System.out.println("GAME OVER");
			board.printDisplay();
		}

	}

}
