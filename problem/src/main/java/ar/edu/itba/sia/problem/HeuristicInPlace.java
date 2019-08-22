package ar.edu.itba.sia.problem;

import java.util.Arrays;

import ar.edu.itba.sia.interfaces.Heuristic;
import ar.edu.itba.sia.interfaces.State;

public class HeuristicInPlace implements Heuristic {
	
	private final SSState state;
	
	public HeuristicInPlace(SSState state) {
		this.state = state;
	}

	@Override
	public Integer getValue(State state) {
		if(!(state instanceof SSState))
	            return null;
		long count = Arrays.stream(((SSState) state).getSquares())
				.filter(s -> s.getPosition().equals(s.getGoal()))
				.count();
		int ret = Math.toIntExact(count);
		return ret;
	}

}
