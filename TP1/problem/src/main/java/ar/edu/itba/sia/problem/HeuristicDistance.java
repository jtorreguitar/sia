package ar.edu.itba.sia.problem;


import ar.edu.itba.sia.interfaces.Heuristic;
import ar.edu.itba.sia.interfaces.State;

public class HeuristicDistance implements Heuristic {
	@Override
	public Integer getValue(State state) {
		if(!(state instanceof SSState))
            return null;
		int sumDistance = 0;
		for(Square sq : ((SSState) state).getSquares()) {
			sumDistance += Math.abs(sq.getPosition().x - sq.getGoal().x) + Math.abs(sq.getPosition().y - sq.getGoal().y);
		}
		return sumDistance;
	}

}
