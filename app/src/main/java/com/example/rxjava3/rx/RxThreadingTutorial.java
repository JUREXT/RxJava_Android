package com.example.rxjava3.rx;


import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RxThreadingTutorial {
    public static @NonNull Observable<Integer> noThreadingExample() {
        return Observable.just(1, 2, 3, 4)
                .filter(integer -> integer % 2 == 0);
    }

    public static @NonNull Observable<Integer> iOThreadingExample() {
        return Observable.just(1, 2, 3, 4)
                .filter(integer -> integer % 2 == 0)
                .subscribeOn(Schedulers.io());
        // Will work on background thread - IO.
        // I can be put in any place in the chain, the top one call is used only.
    }

    // Already has @SchedulerSupport(SchedulerSupport.COMPUTATION) to work on background thread - COMPUTATION.
    public static @NonNull Observable<Long> intervalWithExample() {
        return Observable.interval(1, TimeUnit.SECONDS);
    }

    public static @NonNull Observable<Integer> threadingWithSubscribeOnIOExample() {
        return Observable.just(1, 2, 3, 4).subscribeOn(Schedulers.io());
    }

    public static @NonNull Observable<Integer> threadingWithSubscribeOnIOAdnUIUpdateExample() {
        return Observable.just(1, 2, 3, 4)
                .delay(5, TimeUnit.SECONDS);
               // .subscribeOn(Schedulers.io());
    }
}