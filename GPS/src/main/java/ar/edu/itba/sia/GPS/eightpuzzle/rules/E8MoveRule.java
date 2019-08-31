package ar.edu.itba.sia.GPS.eightpuzzle.rules;

import ar.edu.itba.sia.interfaces.Rule;
import ar.edu.itba.sia.interfaces.State;
import ar.edu.itba.sia.GPS.eightpuzzle.E8State;
import ar.edu.itba.sia.GPS.Pair;

import java.util.Optional;

/**
 * Created by eric on 15/03/17.
 */
public abstract class E8MoveRule implements Rule {

    @Override
    public Integer getCost() {
        return 1;
    }

    public Optional<State> apply(State state, Pair destiny){
        E8State e8state = (E8State) state;
        Pair[] array = e8state.getArray().clone();
        Pair blank = e8state.getBlank();
        int index;
        E8State ans = e8state;
        for(int i = 0; i < 8; i++){
            if(destiny.equals(array[i])){
                array[i]=blank;
                blank=destiny;
                ans = new E8State(blank,array);
            }
        }
        /*System.out.print(e8state);

        System.out.print("  |  \n  v  \n");

        System.out.print(ans);

        System.out.print('\n');*/
        return Optional.of(ans);
    }
}
