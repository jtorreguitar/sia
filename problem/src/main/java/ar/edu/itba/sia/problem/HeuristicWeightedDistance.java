package ar.edu.itba.sia.problem;

import ar.edu.itba.sia.interfaces.Heuristic;
import ar.edu.itba.sia.interfaces.State;

public class HeuristicWeightedDistance implements Heuristic {
	@Override
	public Integer getValue(State state) {
		if(!(state instanceof SSState))
            return null;
		int sumDistance = 0;
		int distance;
		for(Square sq : ((SSState) state).getSquares()) {
			distance = 0;
			switch (sq.getDirection()) {
            	case UP: 
            		distance += 3*Math.abs(sq.getPosition().x - sq.getGoal().x);
            		distance += (sq.getPosition().y > sq.getGoal().y) ? Math.abs(sq.getPosition().y - sq.getGoal().y) :
            			3*Math.abs(sq.getPosition().y - sq.getGoal().y);
            		break;
            	case DOWN: 
            		distance += 3*Math.abs(sq.getPosition().x - sq.getGoal().x);
            		distance += (sq.getPosition().y < sq.getGoal().y) ? Math.abs(sq.getPosition().y - sq.getGoal().y) :
            			3*Math.abs(sq.getPosition().y - sq.getGoal().y);
            		break;
            	
            	case LEFT:
            		distance += 3*Math.abs(sq.getPosition().y - sq.getGoal().y);
            		distance += (sq.getPosition().x > sq.getGoal().x) ? Math.abs(sq.getPosition().x - sq.getGoal().x) :
            			3*Math.abs(sq.getPosition().x - sq.getGoal().x);
            		break;
            	
            	case RIGHT:
            		distance += 3*Math.abs(sq.getPosition().y - sq.getGoal().y);
            		distance += (sq.getPosition().x < sq.getGoal().x) ? Math.abs(sq.getPosition().x - sq.getGoal().x) :
            			3*Math.abs(sq.getPosition().x - sq.getGoal().x);
            		break;
			}
			sumDistance =+ distance;
		}
		return sumDistance;
	}

}

