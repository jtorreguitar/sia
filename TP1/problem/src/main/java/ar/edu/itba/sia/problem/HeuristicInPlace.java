package ar.edu.itba.sia.problem;

import java.util.Arrays;

import ar.edu.itba.sia.interfaces.Heuristic;
import ar.edu.itba.sia.interfaces.State;

public class HeuristicInPlace implements Heuristic {
	@Override
	public Integer getValue(State state) {
		if(!(state instanceof SSState))
	            return null;
		long count = Arrays.stream(((SSState) state).getSquares())
				.filter(s -> s.getPosition().equals(s.getGoal()))
				.count();
		int total = Math.toIntExact(count);
		int ret = ((SSState) state).getSquares().length - total;
		return ret;
	}

}
