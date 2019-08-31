package ar.edu.itba.sia.GPS.eightpuzzle.rules;

import ar.edu.itba.sia.interfaces.State;
import ar.edu.itba.sia.GPS.eightpuzzle.E8State;
import ar.edu.itba.sia.GPS.Pair;

import java.util.Optional;

/**
 * Created by eric on 15/03/17.
 */
public class E8MoveDownRule extends E8MoveRule{

    @Override
    public String getName() {
        return "Move Down";
    }

    @Override
    public Optional<State> apply(State state) {
        E8State e8state = (E8State) state;
        Pair blank = e8state.getBlank();
        if(blank.getX() == 2)
            return Optional.empty();
        Pair destiny = new Pair(blank.getX(), blank.getY()+1);
        return super.apply(state,destiny);
    }
}
