package com.example.rxjava3.util;

import android.graphics.Color;

import java.util.Random;

public class ColorUtil {
    public static int randomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
}