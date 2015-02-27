package com.yakovlev.prod.vocabularymanager.ormlite;

public enum WordStatusEnum {

    NORMAL, HARD_FIRST_RANK, HARD_SECOND_RANK;

    public static int getNormal(){
        return NORMAL.ordinal();
    }

    public static int getHardFirstRank(){
        return HARD_FIRST_RANK.ordinal();
    }

    public static int getHardSecondRank(){
        return HARD_SECOND_RANK.ordinal();
    }


}
