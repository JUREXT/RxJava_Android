package com.example.rxjava3.rx.jurel.network;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MitchRxTutorial {
    // Serial order, is slow.
    public static @NonNull Observable<Integer> concatMapExample() {
        return Observable.range(0, 5)
                .concatMap(integer -> Single.just(integer)
                        .delay(1, TimeUnit.SECONDS)
                        .toObservable()
                ).subscribeOn(Schedulers.io());
    }

    // Random order, is fast.
    public static @NonNull Observable<Integer> flatMapExample() {
        return Observable.range(0, 5)
                .flatMap(integer -> Single.just(integer)
                        .delay(1, TimeUnit.SECONDS)
                        .toObservable()
                ).subscribeOn(Schedulers.io());
    }
}