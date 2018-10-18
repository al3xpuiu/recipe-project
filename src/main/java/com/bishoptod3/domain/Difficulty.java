package com.bishoptod3.domain;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by Loky on 23/08/2018.
 */
public enum Difficulty {
    EASY, MODERATE, HARD;


    @Override
    public String toString() {
        return Arrays.stream(this.name().split( "_" ))
                .map( s -> s.substring( 0 , 1 ).toUpperCase() + s.substring( 1 ).toLowerCase())
                .collect( Collectors.joining( " " ) );
    }
}
