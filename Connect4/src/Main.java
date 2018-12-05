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

		testAlgorithm();
		
//		while (!exit)
//		{
//			System.out.println();
//			System.out.println("What would you like to do?");
//			printOptions();
//
//			choice = scan.nextInt();
//
//			switch (choice)
//			{
//			case 1:
//				startNewGame();
//				System.out.println("----------------------------------------------");
//				break;
//
//			case 2:
//				recreateGame();
//				System.out.println("----------------------------------------------");
//				break;
//			case 3:
//				exit = true;
//				System.out.println("----------------------------------------------");
//				break;
//			case 4:
//				testAlgorithm();
//				break;
//
//			default:
//				System.out.println("Invalid Input, Try Again");
//				System.out.println("----------------------------------------------");
//				break;
//			}
//
//		}
//		System.out.println("Thank You for Playing!");
//		scan.close();

		

	}
	
	private static void testAlgorithm() {
		
		//TEST OF all scoring heuristics. Depth 0. No miniMax.
//		Board board = new Board(30);
//		Square move = new Square ('o' , 5, 4);
//		board.placeSquare(move);
//		board.findBestMove(4);
//		move = new Square ('o' , 5, 5);
//		board.findBestMove(4);
//		move = new Square ('o' , 4, 3);
//		board.placeSquare(move);
//		board.printDisplay();
//		board.findBestMove(4);
//		board.printDisplay();
		
		//Test succeeds, optimal move chosen each time.
		//END TEST
		
		
	
	
		
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

		Board board = new Board(timeLimit);
		//board.printDisplay();
		
		int turnCounter = 0;

		if(computerFirst)
		{
			
			//board.printDisplay();
			

			while(board.isGameContinue())
			{
				System.out.println("Enter Opponent Move (Ex: e5)");
				move = scan.nextLine().trim().toLowerCase();
				board.findBestMove();
				board.printDisplay();
				
				//pretty sure this is the error, since the opponent move never happens as its the ai turn
				while(!board.opponentMove(move))
				{
					System.out.println("Error With Move, Try Again");
					System.out.println();
					System.out.println("Enter Opponent Move (Ex: e5)");
					move = scan.nextLine().trim().toLowerCase();
				}
				
				if (board.isGameContinue())
				{
					System.out.println("GAME OVER");
				}
				else
				{
					System.out.println();
					turnCounter++;
					System.out.print(turnCounter + ".");
					board.nextMove();
					
					board.printDisplay();
				}
				
			}
		}
		else
		{
			while(board.isGameContinue())
			{
				System.out.println("Enter Opponent Move (Ex: e5)");
				move = scan.nextLine().trim().toLowerCase();
				
				//this is what is crashing since no move is entered yet
				while(!board.opponentMove(move))
				{
					System.out.println("Error With Move, Try Again");
					System.out.println();
					System.out.println("Enter Opponent Move (Ex: e5)");
					move = scan.nextLine().trim().toLowerCase();
				}
				
				if (board.isGameContinue())
				{
					System.out.println("GAME OVER");
				}
				else
				{
					System.out.println();
					turnCounter++;
					System.out.print(turnCounter + ".");
					board.nextMove();
					
					board.printDisplay();
				}
				
			}
			
		}
		
		


	}

	private static void recreateGame()
	{
		System.out.println("test2");
	}

}
