package com.synacy.moviehouse.cinema;

public enum CinemaType {
    IMAX,
    SPECIAL,
    STANDARD;

    public static boolean contains(String s) {
        for(CinemaType type:values())
            if (type.name().equals(s))
                return true;
        return false;
    }
}
