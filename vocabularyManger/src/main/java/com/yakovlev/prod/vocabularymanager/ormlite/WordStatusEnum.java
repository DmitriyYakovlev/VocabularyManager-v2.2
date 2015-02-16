package com.yakovlev.prod.vocabularymanager.ormlite;

public enum WordStatusEnum {

    NORMAL, HARD;

    public int getNormal(){
        return NORMAL.ordinal();
    }

    public int getHard(){
        return HARD.ordinal();
    }

}
