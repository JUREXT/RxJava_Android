package com.example.rxjava3.rx;


import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOperator;
import io.reactivex.rxjava3.observers.DisposableObserver;

public class CustomOperatorTutorial {
    public static @NonNull Observable<Integer> customOperatorTakeEvenExample() {
        return Observable.just(1, 2, 3, 4)
                .lift(takeEvenOperator());
                //.filter(integer -> integer % 2 == 0);
    }


    private static ObservableOperator<Integer, Integer> takeEvenOperator() {
        return observer -> new DisposableObserver<>() {
            @Override
            public void onNext(@NonNull Integer integer) {
                if (integer % 2 == 0) {
                    observer.onNext(integer);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }
}