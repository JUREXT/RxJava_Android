package com.example.rxjava3.rx;

import static com.example.rxjava3.Logger.logging;

import com.example.rxjava3.model.Item;
import com.example.rxjava3.tutorial.FakeData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.observables.ConnectableObservable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Tutorial {
    public static Observable<String> emitterExample() {
        return Observable.create(emitter -> {
            emitter.onNext("Click 1");
            emitter.onNext("Click 2");
            emitter.onNext("Click 3");

            emitter.onComplete();
            // throw new Exception("Test Exception");
        });
    }

    public static Observable<String> justExample() {
        return Observable.just("Item 1", "Item 2"); // All at once.
    }

    public static @NonNull Observable<Integer> fromIterableExample() {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        return Observable.fromIterable(numbers); // One by One emission.
    }

    public static Observable<Integer> rangeExample() {
        return Observable.range(0, 5);
    }

    public static Observable<Long> intervalExample() {
        return Observable.interval(1, TimeUnit.SECONDS);
    }

    public static @NonNull Observable<Long> timerExample() {
      return Observable.timer(2, TimeUnit.SECONDS); // Returns Completable if Success.
    }

    public static @NonNull Completable fromActionExample() {
        Action action = () -> logging("Action Called");
        return Completable.fromAction(action);
    }

    public static Single<String> singleWithErrorExample() {
        return creteSingle();
    }

    private static Single<String> creteSingle() { // Returns only one item or error.
        return Single.create(emitter -> {
            var user = FakeData.fetchUser();
            if(user != null) {
                emitter.onSuccess(user);
            } else {
                emitter.onError(new Throwable("User not found!"));
            }
        });
    }

    public static @NonNull Maybe<String> maybeExample() { // Returns one item or no items.
        return Maybe.create(emitter -> {
            var content = FakeData.readFileContent();
            if(content != null) {
                emitter.onSuccess(content);
            } else {
                emitter.onComplete();
            }
        });
    }

    public static @NonNull Completable completableExample() { // Returns one item or no items.
       return Completable.fromAction(deleteItemFromDatabaseAction());
    }

    private static Action deleteItemFromDatabaseAction() {
        return new Action() {
            @Override
            public void run() throws Throwable {
                logging("Deleted item from Db Action Called!");
            }
        };
    }

    // Synchronous and Asynchronous.
    public static @NonNull Observable<Item> synchronousObservableExample() { // All is done on main thread.
        return Observable
                .range(1, 100000)
                .map(Item::new);
    }

    public static @NonNull Observable<Item> asynchronousObservableExample() { // Main thread creates range, but processing is done on IO.
        return Observable
                .range(1, 100000)
                .observeOn(Schedulers.io())
                .map(Item::new);
    }

    public static @NonNull Flowable<Item> asynchronousFlowableExample() { // Main thread creates range, but processing is done on IO.
        return Flowable
                .range(1, 100000)
                .observeOn(Schedulers.io())
                .map(Item::new);
    }

    public static @NonNull Observable<String> dataForCOLDObservableExample() { // Every new one subscriber gets all items.
        return Observable.just("Val 1", "Val 2", "Val 3");
    }

    public static @NonNull ConnectableObservable<Long> dataForHOTObservableExample() { // Every new one subscriber gets only items from subscribe time forward.
        return Observable.interval(1, TimeUnit.SECONDS).publish();
    }

    public static @NonNull Observable<Integer> sortObservableExample() {
        return Observable.just(220, 3, 3, 25, 9, 5, 1000, -1, 252).sorted();
    }

    public static @NonNull Observable<List<Integer>> bufferObservableExample() {
        return Observable.just(220, 3, 3, 25, 9, 5, 1000, -1, 252).buffer(3);
    }

    public static @NonNull Observable<@NonNull List<String>> groupByObservableExample() {
        return Observable.just("a", "a", "A", "AA", "aa", "bb", "ccc", "cc", "ii", "lol 123")
                .map(String::toUpperCase)
                .groupBy(String::length)
                .flatMapSingle(Observable::toList);
    }

    public static @NonNull Observable<Integer> flatMapExample() {
        return Observable.just(220, 3, 3, 25, 9, 5, 1000, -1, 252)
                .flatMap(item -> Observable.just(item * 2)); // Must return a rx type (Observable, Single...).
    }

    public static @NonNull Observable<Integer> mergeWithExample() {
        var obs1 = Observable.just(220, 25);
        var obs2 = Observable.just(1, 2);
        return obs1.mergeWith(obs2); // Emits values together in in one Observable.
    }

    public static @NonNull Observable<Integer> zipWithExample() {
        var obs1 = Observable.just(10, 20 /*,30*/);
        var obs2 = Observable.just(1, 2, 3);
        return obs1.zipWith(obs2, Integer::sum); // Will zip if it has pair to zip with, or will ignore value without pair.
    }

    // Utility operators.

    public static @NonNull Observable<Integer> timeOutObservableWithErrorExample() {
        return Observable
                .just(220, 3, 3, 25, 9, 5, 1000, 252)
                .doOnNext(integer -> logging("timeOutObservableExample, Started on Thread: " + Thread.currentThread().getName()))
                .delay(1, TimeUnit.SECONDS) // Delay set his own Scheduler computation.
                .doOnNext(integer -> logging("timeOutObservableExample, Delay on Thread: " + Thread.currentThread().getName()))
                .observeOn(Schedulers.io())
                .doOnNext(integer -> logging("timeOutObservableExample, Observing on Thread: " + Thread.currentThread().getName()))
                .timeout(2, TimeUnit.SECONDS) // If timeout happens, on error gets called.
                .doOnError(throwable -> logging("Time out happened!"))
                .onErrorReturnItem(-1)
                .doOnDispose(() -> logging("timeOutObservableWithErrorExample is now disposed!"));
    }

    public static @NonNull Observable<Integer> errorHandlingExample() {
        return Observable.just(220, 500, 600, 25, 580, 0)
                .map(item -> 1000 / item)
                //.onErrorReturnItem(-1)
                .doOnError(throwable -> logging("Error: " + throwable.getMessage()))
                .onErrorResumeWith(Observable.just(220, 500, 600, 25, 580, 0) // Just for example.
                        .filter(value -> value > 0)
                        .map(item -> 1000 / item)
                );

//                .onErrorResumeNext(throwable -> {
//                    Log.d("WHAT", "Error: " + throwable.getMessage());
//                    logging("retryExample Error: " + throwable.getMessage());
//                    return Observable.just(0);
//                })

    }
}