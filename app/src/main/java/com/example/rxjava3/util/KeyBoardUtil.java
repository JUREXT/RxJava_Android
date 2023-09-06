package com.example.rxjava3.util;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

// https://stackoverflow.com/questions/1109022/how-to-close-hide-the-android-soft-keyboard-programmatically
public class KeyBoardUtil {
    public static void show(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY); // Show.
    }

    public static void hide(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // Hide.
    }

    public static void toggle(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            hide(activity);
        } else {
            show(activity);
        }
    }
}
