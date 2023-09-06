package com.example.rxjava3.rx;

import com.example.rxjava3.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RandomRxJavaChainExample {
    // Must use .observeOn(Schedulers.single())
    // For work requiring strongly-sequential execution on the same background thread.
    public static @NonNull Maybe<Integer> randomChainExample() {

        return Observable
                .range(1, new Random().nextInt(20))
                // Start on a computation scheduler
                .subscribeOn(Schedulers.computation())
                // Transform each number to its square
                .map(number -> number * number)
                // Filter out even numbers
                .filter(square -> square % 2 != 0)
                // Add 10 to each square
                .map(square -> square + 10)
                // Introduce delay for odd squares
                .flatMap(square -> {
                    if (square % 2 != 0) {
                        Logger.logging("Will be delayed: " + square);
                        return Observable.just(square).delay(2, TimeUnit.SECONDS);
                    } else {
                        return Observable.just(square);
                    }
                })
                // Sum all the values
                .reduce(Integer::sum)
                .observeOn(Schedulers.single());
    }

    public static Observable<String> clickEmissionExample () {
        // Simulate button click events
        return Observable.create(emitter -> {
            emitter.onNext("Click 1");
            Thread.sleep(500);
            emitter.onNext("Click 2");
            Thread.sleep(300);
            emitter.onNext("Click 3");
            Thread.sleep(1000);
            emitter.onNext("Click 4");
            emitter.onComplete();
        });
    }
}
