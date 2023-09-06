package com.example.rxjava3.tutorial;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ConcurrencyWithFlowable {
    public static Flowable<Integer> sequential() {
        return Flowable.range(1, 10)
                .map(v -> v * v);
    }

    public static Flowable<Integer> parallel() {
        return Flowable.range(1, 10)
                .flatMap(value -> Flowable.just(value)
                        .subscribeOn(Schedulers.computation())
                        .map(v -> v * v));
    }

    public static Flowable<Integer> fromFlowableParallel() {
        return Flowable.range(1, 10)
                .parallel()
                .runOn(Schedulers.computation())
                .map(v -> v * v)
                .sequential();
    }

    public static Observable<Integer> fromObservableWithParallel() {
        return Observable.range(1, 10)
                .flatMap(value -> Observable.just(value)
                        .subscribeOn(Schedulers.computation())
                        .map(v -> v * v));
    }
}