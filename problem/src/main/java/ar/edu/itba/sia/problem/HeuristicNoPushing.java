package ar.edu.itba.sia.problem;
import java.util.Arrays;

import ar.edu.itba.sia.interfaces.Heuristic;
import ar.edu.itba.sia.interfaces.State;

public class HeuristicNoPushing implements Heuristic {

	@Override
	public Integer getValue(State state) {
		if(!(state instanceof SSState))
            return null;
		int sumDistance = 0;
		for(Square sq : ((SSState) state).getSquares()) {
			int distance = Math.abs(sq.getPosition().x - sq.getGoal().x) + Math.abs(sq.getPosition().y - sq.getGoal().y);
			for(Square otherSq : ((SSState)state).getSquares()) {
				if(!sq.equals(otherSq)) {
						if(((Math.abs(sq.getPosition().x - otherSq.getPosition().x) < 2) &&  sq.getPosition().y == otherSq.getPosition().y) || 
								((Math.abs(sq.getPosition().y - otherSq.getPosition().y) < 2) &&  sq.getPosition().x == otherSq.getPosition().x) ||
								((Math.abs(sq.getPosition().x - otherSq.getPosition().x) < 2) &&  (Math.abs(sq.getPosition().y - otherSq.getPosition().y) < 2)))
							distance --;
				}
			}

			if(distance < 0) 
					distance = 0;
			sumDistance += distance;
		}
		long count = Arrays.stream(((SSState) state).getSquares())
				.filter(s -> s.getPosition().equals(s.getGoal()))
				.count();
		int inPlace = Math.toIntExact(count);
		if(sumDistance == 0) {
			if (inPlace == ((SSState) state).getSquares().length)
				return 0;
			else 
				return 1;
		}
		return sumDistance;
	}

}
