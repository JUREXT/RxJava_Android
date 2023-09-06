package com.example.rxjava3.tutorial;

import java.util.Random;

import io.reactivex.rxjava3.annotations.Nullable;

public class FakeData {
    public static @Nullable String fetchUser() {
        boolean status = new Random().nextBoolean();
        if (status) {
            return "User";
        } else {
            return null;
        }
    }

    public static @Nullable String readFileContent() {
        boolean status = new Random().nextBoolean();
        if (status) {
            return "File Content is here.";
        } else {
            return null;
        }
    }
}