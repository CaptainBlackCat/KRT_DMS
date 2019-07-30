package com.krt.base.utils;

public class UUIDUtils {

    public static String newId() {
        return Long.toString(System.currentTimeMillis()) + Integer.toString((int) (Integer.MAX_VALUE * Math.random()));
    }
}
