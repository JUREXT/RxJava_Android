package com.example.rxjava3.tutorial;

import android.util.Pair;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;

public class Continuations {
    public static Integer getIdValue(Integer id) {
        return id;
    }

    public static Single<Integer> valueToSingle(Integer id) {
        return Single.just(id);
    }

    public static @NonNull Single<String> getSubstringByStartingPositionSingle(Integer beginIndex) {
        String helloWorld = "Hello World";
        if (helloWorld.length() < beginIndex) {
            return Single.error(new Throwable("Begin Index is larger then string length!"));
        }
        return Single.just(helloWorld.substring(beginIndex));
    }

    private static Single<Boolean> isStringBlankSingle(String value) {
        return Single.just(!value.isBlank());
    }

    public static Single<Pair<Boolean, String>> getDataSingle(String value) {
        return Single.just(value)
                .flatMap(Continuations::isStringBlankSingle)
                .map(aBoolean -> new Pair<>(aBoolean, value));
    }
}