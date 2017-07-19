package com.pro.danglph.game2dbegin.Utility;

import java.util.Random;

/**
 * Created by danglph on 18/07/2017.
 */

public class CommonFeatures {

    public static int randomIntValue(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
