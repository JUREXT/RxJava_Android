package com.example.rxjava3;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;

public class SingleHelper {
    public @NonNull Single<String> toUpperCase(@NonNull String value) {
        return Single.just(value.toUpperCase());
    }

    public @NonNull Single<Integer> length(@NonNull String value) {
        return Single.just(value.length());
    }
}