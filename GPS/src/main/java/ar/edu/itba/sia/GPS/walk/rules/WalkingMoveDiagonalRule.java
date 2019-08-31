package ar.edu.itba.sia.GPS.walk.rules;

import ar.edu.itba.sia.GPS.Pair;

public class WalkingMoveDiagonalRule extends WalkingMoveRule {

    public WalkingMoveDiagonalRule(Pair step) {
        super(step);
    }

    @Override
    public Integer getCost() {
        return 3;
    }

    @Override
    protected boolean controlStep() {
        return (Math.abs(step.getX()) == 1) && (Math.abs(step.getY()) == 1);
    }

    @Override
    public String getName() {
        return "Diagonal " + step;
    }
}
