//Currently not used. Keeping it here just in case.
public class abPruning {
	final int maxDepth;
	
	public abPruning() {
		maxDepth = 2;
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
		state.scoreBoard();
		Square move = maxValue(state, 1, -1000000000, 1000000000);
		
		return move;
	}
	
	
	public Square maxValue(Board state, int depth, int alpha, int beta) {
		//terminal condition, max depth reached, or no more moves available
		if (depth == maxDepth || !state.moreMoves()) {
			// TODO break or something
			
		}
		minValue(state, depth+1, alpha, beta);
		
		
		return null;
	}
	
	public int minValue(Board state, int depth, int alpha, int beta) {
		//TODO
		return 0;
	}
	
	
}
