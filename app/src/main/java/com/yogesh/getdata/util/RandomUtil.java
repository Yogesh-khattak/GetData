package com.yogesh.getdata.util;

import java.util.Random;

public class RandomUtil {
    private static final int HIGH_LIMIT = 99999;
    private static final int LOW_LIMIT = 9999;

    public static long getRandomId(){
        Random random = new Random();
        long id = 1;
        while(id<999999999){
            id *= random.nextInt(HIGH_LIMIT-LOW_LIMIT);
            id = id % 10000000000L;
        }
        return id;
    }
}
