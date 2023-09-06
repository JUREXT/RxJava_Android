package com.example.rxjava3.util;

import com.example.rxjava3.experimental.PostPaginationMapper;
import com.example.rxjava3.model.PostPagination;
import com.example.rxjava3.rx.jurel.network.RetrofitClientPagination;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DummyJsonData {
//    private static @NonNull Observable<@NonNull List<AirlineDataDTO>> getAirlinePagination() {
//        return Observable
//                .just(0)
//                .doOnNext(integer -> Logger.logging("TEST P range Called"))
//                .concatMap(page -> RetrofitClientPagination.getApiV3Pagination().getPassengersWithPagination(page, 5))
//                .delay(1, TimeUnit.SECONDS)
//                .doOnNext(integer -> Logger.logging("TEST P concatMap Called"))
//                .flatMap(airlinePaginationDTO -> Observable.fromIterable(airlinePaginationDTO.airlineData))
//                .doOnNext(integer -> Logger.logging("TEST P flatMap Called"))
//                .toList()
//                .toObservable()
//                .subscribeOn(Schedulers.io());
//    }

    // TODO: Example for API call and mapping.
    public static @NonNull Observable<PostPagination> getPostPaginationObservable(int skip, int limitPerPage) {
        return RetrofitClientPagination
                .getApiV3Pagination()
                .getPostsWithPagination(skip, limitPerPage)
                .flatMap(dto -> new PostPaginationMapper().map(dto)) // Map from observable to final object type with flatMap.
                .subscribeOn(Schedulers.io());
    }

//    private static @NonNull Observable<@NonNull List<AirlineDataDTO>> getAirlinePaginationOld() {
//        return Observable.defer(() -> {
//            Logger.logging("TEST P Called");
//            AtomicInteger currentPage = new AtomicInteger(0);
//
//            return Observable
//                    .range(currentPage.get(), Integer.MAX_VALUE)
//                    .doOnNext(integer -> Logger.logging("TEST P range Called"))
//                    .concatMap(page -> RetrofitClientPagination.getApiV3Pagination().getPassengersWithPagination(page, 5))
//                    .delay(1, TimeUnit.SECONDS)
//                    .doOnNext(integer -> Logger.logging("TEST P concatMap Called"))
//                    .flatMap(airlinePaginationDTO -> Observable.fromIterable(airlinePaginationDTO.airlineData))
//                    .doOnNext(integer -> Logger.logging("TEST P flatMap Called"))
//                    .toList()
//                    .toObservable()
//                    .doOnNext(list -> {
//                        Logger.logging("TEST P doOnNext");
//                        if (list.isEmpty()) {
//                            // All items have been fetched, complete the observable.
//                            currentPage.set(Integer.MAX_VALUE);
//                        } else {
//                            // Increment the page number for the next fetch.
//                            currentPage.incrementAndGet();
//                        }
//                    }).subscribeOn(Schedulers.io());
//        });
//    }
}