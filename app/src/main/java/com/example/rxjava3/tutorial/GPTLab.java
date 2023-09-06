package com.example.rxjava3.tutorial;

import com.example.rxjava3.callbackvsRx.UserTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class GPTLab {
    public static @NonNull Observable<String> mergeLab() {
        var obs1 = Observable.just("Test");
        var obs2 = Observable.just(1, 2, 3).delay(3, TimeUnit.SECONDS);
        //var obs2MergeWithObs2 = Observable.just(4, 5, 6).delay(3, TimeUnit.SECONDS).mergeWith(obs2);

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(4);
        var obs3 = Observable.just(list);

        var obs4 = Single.just(new UserTest()).toObservable();

        return Observable.merge(obs1, obs2, obs3, obs4)
                .flatMap(item -> {
                    if (item instanceof String) {
                        return Observable.just("Mapped String: " + item);
                    } else if (item instanceof Integer) {
                        return Observable.just("Mapped Integer: " + item);
                    } else if (item instanceof List) {
                        return Observable.just("Mapped List: " + item);
                    } else if(item instanceof UserTest) {
                        var user = (UserTest)item;
                        user.age = 20;
                        user.name = "Rx Map";
                        return Observable.just("Mapped UserTest: " + user);
                    } else {
                        return Observable.error(new Throwable("Unknown type"));
                    }
                });
    }

    public static @NonNull Observable<String> concatLab() {
        var obs1 = Observable.just("Test");
        var obs2 = Observable.just(1, 2, 3).delay(3, TimeUnit.SECONDS);
        //var obs2MergeWithObs2 = Observable.just(4, 5, 6).delay(3, TimeUnit.SECONDS).mergeWith(obs2);

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(4);
        var obs3 = Observable.just(list);

        var obs4 = Single.just(new UserTest()).toObservable();

        return Observable.concat(obs1, obs2, obs3, obs4)
                .flatMap(item -> {
                    if (item instanceof String) {
                        return Observable.just("Mapped String: " + item);
                    } else if (item instanceof Integer) {
                        return Observable.just("Mapped Integer: " + item);
                    } else if (item instanceof List) {
                        return Observable.just("Mapped List: " + item);
                    } else if(item instanceof UserTest) {
                        var user = (UserTest)item;
                        user.age = 20;
                        user.name = "Rx Map";
                        return Observable.just("Mapped UserTest: " + user);
                    } else {
                        return Observable.error(new Throwable("Unknown type"));
                    }
                });
    }
    public static @NonNull Observable<UserTest> combineLatestLab() {
        Observable<Integer> source1 = Observable.just(11, 22, 33, 44);
        Observable<String> source2 = Observable.just("Name A", "Name B", "Name C");

        var obs4 = Single.just(new UserTest()).toObservable();

        // OUTPUT: UserTest{name='Name C', age=44}
        return Observable.combineLatest(source1, source2, obs4, (integer, s, userTest) -> {
            userTest.name = s;
            userTest.age = integer;
            return userTest;
        });
    }

}