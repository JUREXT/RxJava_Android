package com.example.rxjava3.rx;


import java.util.concurrent.TimeUnit;
import static com.example.rxjava3.Logger.logging;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.AsyncSubject;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import io.reactivex.rxjava3.subjects.Subject;

public class PublishSubjectTutorial {
    public static @NonNull Subject<Long> publishSubjectExample() { // Can receive multiple values in one subject in subscribe.
        var obs1 = Observable.interval(1, TimeUnit.SECONDS);
        var obs2 = Observable.interval(2, TimeUnit.SECONDS).map(item -> item * 2);

        Subject<Long> subject = PublishSubject.create();

        obs1.subscribe(subject);
        obs2.subscribe(subject);

        return subject;
    }

    public static Subject<String> behaviorSubjectExample() {
        Subject<String> subject = BehaviorSubject.create(); // Radio example. The latest subscribe gets the latest-current value.

        subject.onNext("Value 1");

        subject.subscribe(value -> logging("Behavior Subject 1 Received value: " + value));

        subject.onNext("Value 2");
        subject.onNext("Value 3");
        subject.onNext("Value 4");

        subject.subscribe(value -> logging("Behavior Subject 2 Received value: " + value));

        return subject;
    }

    public static Subject<String> replaySubjectExample() {
        Subject<String> subject = ReplaySubject.create(); // Replays all data to each new subscriber.

        subject.onNext("Value 1");

        subject.subscribe(value -> logging("Behavior Subject 1 Received value: " + value));

        subject.onNext("Value 2");
        subject.onNext("Value 3");
        subject.onNext("Value 4");

        subject.subscribe(value -> logging("Behavior Subject 2 Received value: " + value));

        return subject;
    }

    public static Subject<String> asyncSubjectExample() {
        Subject<String> subject = AsyncSubject.create(); // Last Value that every one gets onComplete is called.

        subject.onNext("Value 1");

        subject.subscribe(value -> logging("Async Subject 1 Received value: " + value));

        subject.onNext("Value 2");
        subject.onNext("Value 3");
        subject.onNext("Value 4");

        subject.subscribe(value -> logging("Async Subject 2 Received value: " + value));

        subject.onNext("Last Value that every one gets!");
        subject.onComplete();

        return subject;
    }
}