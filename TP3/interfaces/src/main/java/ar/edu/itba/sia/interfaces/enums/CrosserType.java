package ar.edu.itba.sia.interfaces.enums;

import javax.management.AttributeNotFoundException;

public enum CrosserType {
    SINGLE_POINT,
    TWO_POINT,
    UNIFORM,
    ANNULAR;

    public static CrosserType getCrossingMethod(final String method) throws AttributeNotFoundException {
        if(method.equals(SINGLE_POINT.toString())) {
            return SINGLE_POINT;
        } else if(method.equals(TWO_POINT.toString())) {
            return TWO_POINT;
        } else if(method.equals(UNIFORM.toString())) {
            return UNIFORM;
        } else if(method.equals(ANNULAR.toString())) {
            return ANNULAR;
        } else throw new AttributeNotFoundException("crossing method doesn\'t exist");
    }
}
