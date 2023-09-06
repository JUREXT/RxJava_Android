package com.example.rxjava3.observable_type;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;

public class Delay {
    public Observable<String> delayedObservable(@NonNull String value) {
        return Observable.just(value).delay(2, TimeUnit.SECONDS);
    }
}