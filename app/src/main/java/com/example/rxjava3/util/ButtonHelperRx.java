package com.example.rxjava3.util;

import android.widget.Button;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ButtonHelperRx {

    public static @NonNull Completable oneTimeClickCompletable(@NonNull Button button) {
        return Completable.create(emitter -> button.setOnClickListener(view -> emitter.onComplete()));
    }

    public static @NonNull Observable<Integer> clickWithThrottleObservable(@NonNull Button button) {
        return Observable.create(emitter -> button.setOnClickListener(view -> {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(0);
                    }
                }))
                .switchMap(value -> Observable.just((Integer) value))
                .throttleLast(2, TimeUnit.SECONDS)
                .observeOn(Schedulers.single()); // Switch back to single thread
    }
}