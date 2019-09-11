package ar.edu.itba.sia.problem;

import ar.edu.itba.sia.interfaces.Heuristic;
import ar.edu.itba.sia.interfaces.State;

public class HeuristicNoPushing implements Heuristic {
    @Override
    public Integer getValue(State state) {
        if (!(state instanceof SSState))
            return null;
        int sumDistance = 0;
        for (Square sq : ((SSState) state).getSquares()) {
            int distance = Math.abs(sq.getPosition().x - sq.getGoal().x) + Math.abs(sq.getPosition().y - sq.getGoal().y);
            for (Square otherSq : ((SSState) state).getSquares()) {
                switch (otherSq.getDirection()) {
                    case UP:
                        if (sq.getPosition().x == otherSq.getPosition().x) {
                            distance--;
                        }
                        break;
                    case DOWN:
                        if (sq.getPosition().x == otherSq.getPosition().x) {
                            distance--;
                        }
                        break;

                    case LEFT:
                        if (sq.getPosition().y == otherSq.getPosition().y) {
                            distance--;
                        }
                        break;
                    case RIGHT:
                        if (sq.getPosition().y == otherSq.getPosition().y) {
                            distance--;
                        }
                        break;
                }

            }
            if(distance < 0){
                distance = 0;
            }
            sumDistance += distance;
        }
        return sumDistance;
    }
}
