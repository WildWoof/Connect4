
public class abPruning {
	int depth;
	
	public abPruning() {
		depth = 0;
	}

	/**
	 * MiniMax recursive algorithm. 
	 * a = the value of best Max, b = value of best Min
	 * a, b initialized to hugely impossible values so that anything 
	 * will be accepted over them.
	 * @param state
	 * @return
	 */
	public Square abSearch(Board state) {
		Square move = maxValue(state,-1000000000, 1000000000);
		
		//Square bestMove = 
		return move;
	}
	
	public Square maxValue(Board state, int a, int b) {
		
		return null;
	}
	
	public int minValue(Board state, int a, int b) {
		//TODO
		return 0;
	}
	
	
}
