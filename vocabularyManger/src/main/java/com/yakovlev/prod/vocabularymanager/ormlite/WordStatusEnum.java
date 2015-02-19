package com.yakovlev.prod.vocabularymanager.ormlite;

public enum WordStatusEnum {

    NORMAL, HARD;

    public static int getNormal(){
        return NORMAL.ordinal();
    }

    public static int getHard(){
        return HARD.ordinal();
    }

}
