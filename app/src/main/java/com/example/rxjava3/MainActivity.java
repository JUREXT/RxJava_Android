package com.example.rxjava3;

import static com.example.rxjava3.Logger.logging;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rxjava3.callbackvsRx.UserManagerCallImpl;
import com.example.rxjava3.callbackvsRx.UserManagerCallback;
import com.example.rxjava3.callbackvsRx.UserManagerRx;
import com.example.rxjava3.databinding.ActivityMainBinding;
import com.example.rxjava3.experimental.TestMapper;
import com.example.rxjava3.model.City;
import com.example.rxjava3.model.Weather;
import com.example.rxjava3.rx.CustomOperatorTutorial;
import com.example.rxjava3.rx.PublishSubjectTutorial;
import com.example.rxjava3.rx.RandomRxJavaChainExample;
import com.example.rxjava3.rx.RxThreadingTutorial;
import com.example.rxjava3.rx.Tutorial;
import com.example.rxjava3.rx.jurel.DataSource;
import com.example.rxjava3.rx.jurel.Task;
import com.example.rxjava3.rx.jurel.network.RetrofitClient;
import com.example.rxjava3.rx.jurel.network.model.Post1DTO;
import com.example.rxjava3.tutorial.ConcurrencyWithFlowable;
import com.example.rxjava3.tutorial.Continuations;
import com.example.rxjava3.util.ListUtil;
import com.example.rxjava3.util.DummyJsonData;
import com.example.rxjava3.util.SearchViewHelperRx;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Important Read:
 * <a href="https://stackoverflow.com/questions/28175702/what-is-the-difference-between-flatmap-and-switchmap-in-rxjava">...</a>
 * <a href="https://medium.com/appunite-edu-collection/rxjava-flatmap-switchmap-and-concatmap-differences-examples-6d1f3ff88ee0">...</a>
 */

// TODO: Use SwitchMap, terminate previous stream and execute the new latest one.

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AutoDisposableObserver disposable;

    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        disposable = new AutoDisposableObserver();
        getLifecycle().addObserver(disposable);

        clearTextViews();
        initRecyclerView();
        initClickListeners();
        initSearchViewObservables();
    }

    private void testCallbackVsRx() {
        var userManager = new UserManagerCallImpl();

        userManager.setAge(-15, new UserManagerCallback.UpdateListener() {
            @Override
            public void success() {
                logging("Age updated");
            }

            @Override
            public void error(String error) {
                logging("Age update error: " + error);
            }
        });

        var userManagerRx = new UserManagerRx();

        disposable.add(userManagerRx.setName("Rx user")
                .flatMap(user -> userManagerRx.setAge(33))
                .subscribe(user -> {
                    logging("Rx User Updated: " + user.toString());
                }));
    }

    private void gptLabExamples() {
//        disposable.add(GPTLab.mergeLab()
//                //.onErrorComplete()
//                .subscribe(s -> {
//                    logging("Rx Merge Lab Value: " + s);
//                }, throwable -> {
//                    logging("Rx Merge Lab onError: " + throwable.getMessage());
//                }));

//        disposable.add(GPTLab.concatLab()
//                //.onErrorComplete()
//                .subscribe(s -> {
//                    logging("Rx Concat Lab Value: " + s);
//                }, throwable -> {
//                    logging("Rx Concat Lab onError: " + throwable.getMessage());
//                }));

//        disposable.add(GPTLab.combineLatestLab()
//                .subscribe(value -> {
//                    logging("Rx Combine Latest Lab Value: " + value);
//                }));


        disposable.add(Observable.just(1, 2, 3, 4, 5)
                .flatMap(integer -> new TestMapper().map(integer)) // Returns inner value from observable.
               // .map(integer -> new TestMapper().map(integer)) // Returns observable.
                .subscribe(s -> {
                    logging("Mapped: " + s);
                }, throwable -> {
                    logging("Error: " + throwable.getMessage());
                }));
    }

    private void initSearchViewObservables() {
        disposable.add(SearchViewHelperRx.getSearchedTextObservable(binding.searchView)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchViewText -> {
                    var text = searchViewText.text;
                    if (searchViewText.textType == SearchViewHelperRx.TextType.TextChange) {
                        logging("SearchViewHelperRx Search text: " + text);
                        if (text.isBlank()) {
                            binding.action1Text.setText("----");
                            binding.action2Text.setText(null);
                        } else {
                            binding.action2Text.setText(text);
                        }
                    } else if (searchViewText.textType == SearchViewHelperRx.TextType.TextSubmit) {
                        binding.action2Text.setText("Last Submitted: " + text);
                        getRestDataViaObservable();
                    }
                }));

//        disposable.add(SearchViewHelperRx.getSearchViewOnCloseObservable(binding.searchView)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(state -> {
//                    logging("SearchView On Close Called!");
//                    adapter.clear();
//                    binding.action2.setText(null);
//                }));
    }

    private void initRecyclerView() {
        adapter = new RecyclerAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
    }

    private void clearTextViews() {
        binding.action1Text.setText(null);
        binding.action2Text.setText(null);
    }

    private void initClickListeners() {
        binding.action1.setOnClickListener(view -> {
            //emitterExample();
            //justExample();
            //fromIterableExample();
            //fromRangeExample();
            // intervalExample();
            //timerExample();
            // actionExample();
            // singleWithErrorExample();
            /// maybeExample();
            // completableExample();
            // synchronousObservableExample();
            //  asynchronousObservableExample();
            //  asynchronousFlowableExample();

            //  coldObservableExample();

//            try {
//                hotObservableExample();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }

            //sortObservableExample();
            // bufferObservableExample();
            //groupByObservableExample();
            // flatMapExample();
            //mergeWithExample();
            // zipWithExample();
            // timeOutObservableWithErrorExample();
            // errorHandlingExample();
            //publishSubjectExample();
            //  behaviorSubjectExample();
            // replaySubjectExample();
            // asyncSubjectExample();
            // customOperatorExample();
            //   threadingExample();
            // threadingWithSubscribeOnIOAdnUIUpdateExample();

            //mitchRx();
           // getPostPaginationFromObservable();
           // testCallbackVsRx();
            //gptLabExamples();
           // testContinuations();
            testConcurrency();
        });

//        binding.action2.setOnClickListener(view -> {
//            logging("Action 2 Clicked!");
//             getRestDataViaObservable();
//            // getRestDataViaSingle();
//           // randomChainExample();
//        });

//        disposable.add(ButtonHelperRx.oneTimeClickCompletable(binding.action1)
//                .observeOn(Schedulers.io())
//                .subscribe(() -> {
//                    logging("Action 1 Clicked! - oneTimeClickCompletable");
//                }));

//        disposable.add(ButtonHelperRx.clickWithThrottleObservable(binding.action1)
//                .subscribe(integer -> {
//                    logging("Action 1 Clicked! - clickWithThrottleObservable");
//                }));

//        disposable.add(MitchRxTutorial.concatMapExample()
//                .subscribe(value -> Logger.logging("Test ConcatMapExample Value: " + value)));
//
//        disposable.add(MitchRxTutorial.flatMapExample()
//                .subscribe(value -> Logger.logging("Test FlatMapExample Value: " + value)));

        binding.actionDispose.setOnClickListener(view -> {
            disposable.forceDispose();
        });
    }

    private void testConcurrency() {
//        disposable.add(ConcurrencyWithFlowable.sequential()
//                .subscribe(value -> {
//                    logging("Value " + value);
//                }));

//        disposable.add(ConcurrencyWithFlowable.parallel()
//                .subscribe(value -> {
//                    logging("Value " + value);
//                }));

//        disposable.add(ConcurrencyWithFlowable.fromFlowableParallel()
//                .subscribe(value -> {
//                    logging("Value " + value);
//                }));

        disposable.add(ConcurrencyWithFlowable.fromObservableWithParallel()
                .subscribe(value -> {
                    logging("Value " + value);
                }));
    }

    private void testContinuations() {
        var randomIndex = (new Random()).nextInt(15);
        logging("Random Index: " + randomIndex);
        disposable.add(Observable.just(randomIndex)
                .map(Continuations::getIdValue)
                .flatMapSingle(Continuations::valueToSingle)
                .flatMapSingle(Continuations::getSubstringByStartingPositionSingle)
                .flatMapSingle(Continuations::getDataSingle)
                .subscribe(pair -> {
                    Boolean valid = pair.first;
                    String value = pair.second;
                    logging("Result: " + value + " Valid: " + valid);
                }, throwable -> {
                    logging("Result Error: " + throwable.getMessage());
                }));
    }

    private void randomChainExample() {
        disposable.add(RandomRxJavaChainExample.randomChainExample()
                .observeOn(Schedulers.single())
                .subscribe(
                        result -> logging("RandomChainExample result: " + result),
                        Throwable::printStackTrace
                ));
    }

    // Works, but in emitting all in one shoot. It does not load separately when calling
    // getUpdatedPostWithCommentsSingle.
    // Bacause it is Single type.
    private void getRestDataViaSingle() {
        adapter.clear();
        binding.action2Text.setText("Loading posts...");

        getPostsSingle()
                .flatMap(posts -> {
                    List<Single<Post1DTO>> list = new ArrayList<>();
                    for (Post1DTO post1DTO : posts) {
                        list.add(getUpdatedPostWithCommentsSingle(post1DTO));
                    }
                    return Single.merge(list).toList();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull List<Post1DTO> post1DTOS) {
                        adapter.setPostList(post1DTOS);
                        binding.action2Text.setText("Post count: " + adapter.getItemCount());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        logging("getPostsSingle onError: " + e.getMessage());
                    }
                });

    }

    private void getRestDataViaObservable() {
        long start = System.currentTimeMillis();
        adapter.clear();
        binding.action2Text.setText("Loading posts...");

        getPostsObservable()
                .subscribeOn(Schedulers.io())
                .flatMap(this::getUpdatedPostWithCommentsObservable) // Random order, is fast.
               // .concatMap(this::getUpdatedPostWithCommentsObservable) // Serial order, is slow.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Post1DTO post1DTO) {
                        adapter.updatePost(post1DTO);
                        logging("getPostsObservable onNext: " + post1DTO.getId());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        binding.action2Text.setText(null);
                        logging("getPostsObservable onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        logging("getPostsObservable onCompleted!");
                        binding.action2Text.setText("Post count: " + adapter.getItemCount());
                        long time = System.currentTimeMillis() - start;
                        logging("WHAT Execute time: " + time / 1000 + " Seconds");
                    }
                });
    }

    private void getPostPaginationFromObservable() {
        disposable.add(DummyJsonData.getPostPaginationObservable(0, 3)
                .doOnError(throwable -> {
                    logging("Pagination error: " + throwable.getMessage());
                })
                .onErrorComplete()
                .subscribe(pagination -> {
                    logging("Pagination data size: " + pagination.posts.size());
                    logging("WHAT Pagination: " + pagination);
                }));
    }

    private @NonNull Observable<Post1DTO> getPostsObservable() {
        return RetrofitClient
                .getApiV1()
                .getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(posts -> {
                    logging("getPostsObservable flatMap :: " + posts.size());
                    adapter.setPostList(posts);
                    return Observable.fromIterable(posts).subscribeOn(Schedulers.io());
                });
    }

    private Observable<Post1DTO> getUpdatedPostWithCommentsObservable(Post1DTO post1DTO) {
        return RetrofitClient
                .getApiV1()
                .getComments(post1DTO.getId())
                .map(comments -> {

                    int delay = ((new Random()).nextInt(2) + 1) * 300; // Sleep thread for x ms.
                    Thread.sleep(delay);
                    logging("apply: sleeping thread " + Thread.currentThread().getName() + " for " + delay + "ms");

                    post1DTO.setComments(comments);
                    return post1DTO;
                })
                .subscribeOn(Schedulers.io());
    }

    private Single<List<Post1DTO>> getPostsSingle() {
        return RetrofitClient
                .getApiV2()
                .getPosts()
                .subscribeOn(Schedulers.io());
    }

    private @NonNull Single<Post1DTO> getUpdatedPostWithCommentsSingle(Post1DTO post1DTO) {
        return RetrofitClient
                .getApiV2()
                .getComments(post1DTO.getId())
                .map(comments -> {
                    int delay = ((new Random()).nextInt(5) + 1) * 1000; // Sleep thread for x ms.
                    Thread.sleep(delay);
                    logging("apply: sleeping thread " + Thread.currentThread().getName() + " for " + delay + "ms");

                    post1DTO.setComments(comments);
                    return post1DTO;
                })
                .subscribeOn(Schedulers.io());
    }

    private void mitchRx() {
        Observable<Task> taskObservable = Observable
                .fromIterable(DataSource.createTaskList())
                .subscribeOn(Schedulers.io())
                .filter(Task::isCompleted)
                .observeOn(AndroidSchedulers.mainThread());

        taskObservable.subscribe(new Observer<>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable.add(d);
            }

            @Override
            public void onNext(@NonNull Task task) {
                logging("mitchRx onNext, thread: " + Thread.currentThread().getName());
                logging("mitchRx onNext: " + task.getDescription());

                // Will freeze UI, because we are freezing main thread.
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                logging("mitchRx onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                logging("mitchRx onComplete");
            }
        });

        disposable.add(taskObservable.subscribe(task -> {
            logging("mitchRx subscribe: " + task.getDescription());
        }));

    }

    private void emitterExample() {
        disposable.add(Tutorial
                .emitterExample()
                .subscribe(
                        item -> {
                            logging(item);
                        }, throwable -> {
                            logging("Error: " + throwable.getMessage());
                        },
                        () -> {
                            logging("On Complete Called!");
                        }
                ));
    }

    private void justExample() {
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable.add(d);
                logging("onSubscribe");
            }

            @Override
            public void onNext(@NonNull String s) {
                logging("onNext: " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                logging("onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                logging("onComplete");
            }
        };

        Tutorial.justExample().subscribe(observer);
    }

    private void fromIterableExample() {
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable.add(d);
                logging("onSubscribe");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                logging("onNext: " + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                logging("onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                logging("onComplete");
            }
        };

        Tutorial.fromIterableExample().subscribe(observer);
    }

    private void rangeExample() {
        disposable.add(Tutorial.rangeExample()
                .subscribe(integer -> logging("Range: " + integer)));
    }

    private void intervalExample() {
        disposable.add(Tutorial.intervalExample()
                .subscribe(integer -> logging("Range: " + integer)));
    }

    private void timerExample() {
        disposable.add(Tutorial.timerExample()
                .subscribe(aLong -> logging("Timer Finished")
                ));
    }

    private void actionExample() {
        disposable.add(Tutorial.fromActionExample()
                .subscribe(() -> {
                    logging("Completable called Action");
                }));
    }

    private void singleWithErrorExample() {
        Tutorial.singleWithErrorExample()
                .subscribe(new SingleObserver<>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull String s) {
                        logging("onSuccess: " + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        logging("onError: " + e);
                    }
                });
    }

    private void maybeExample() {
        Tutorial.maybeExample()
                .subscribe(new MaybeObserver<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull String s) {
                        logging("onSuccess: " + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        logging("onError: " + e);
                    }

                    @Override
                    public void onComplete() {
                        logging("onComplete - No new content!");
                    }
                });
    }

    private void completableExample() {
        Tutorial.completableExample()
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        logging("onComplete");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        logging("onError: " + e);
                    }
                });
    }

    private void synchronousObservableExample() {
        disposable.add(Tutorial.synchronousObservableExample()
                .subscribe(item -> {
                    Thread.sleep(1000);
                    logging("Item: " + item.toString());
                }));
    }

    private void asynchronousObservableExample() {
        disposable.add(Tutorial.asynchronousObservableExample()
                .subscribe(item -> {
                    Thread.sleep(1000);
                    logging("Item: " + item.toString());
                }));
    }

    private void asynchronousFlowableExample() {
        disposable.add(Tutorial.asynchronousFlowableExample()
                .onErrorComplete()
                .subscribe(item -> {
                    Thread.sleep(1000);
                    logging("Item: " + item.toString());
                }));
    }

    private void coldObservableExample() {
        disposable.add(Tutorial.dataForCOLDObservableExample().subscribe(s -> logging("Cold Observable 1: " + s)));
        disposable.add(Tutorial.dataForCOLDObservableExample().subscribe(s -> logging("Cold Observable 2: " + s)));
        disposable.add(Tutorial.dataForCOLDObservableExample().subscribe(s -> logging("Cold Observable 3: " + s)));
    }

    private void hotObservableExample() throws InterruptedException {
        var hotObservable = Tutorial.dataForHOTObservableExample();
        hotObservable.connect();

        disposable.add(hotObservable.subscribe(item -> logging("Observer 1: " + item)));

        Thread.sleep(1000);
        disposable.add(hotObservable.subscribe(item -> logging("Observer 2: " + item)));
    }

    private void sortObservableExample() {
        disposable.add(Tutorial.sortObservableExample().subscribe(item -> logging("Sorted: " + item)));
    }

    private void bufferObservableExample() {
        disposable.add(Tutorial.bufferObservableExample().subscribe(item -> logging("Buffer: " + item)));
    }

    private void groupByObservableExample() {
        disposable.add(Tutorial.groupByObservableExample().subscribe(item -> logging("Group: " + item)));
    }

    private void flatMapExample() {
        disposable.add(Tutorial.flatMapExample().subscribe(item -> logging("FlatMap: " + item)));
    }

    private void mergeWithExample() {
        disposable.add(Tutorial.mergeWithExample().subscribe(item -> logging("MergeWith: " + item)));
    }

    private void zipWithExample() {
        disposable.add(Tutorial.zipWithExample().subscribe(item -> logging("ZipWith: " + item)));
    }

    private void timeOutObservableWithErrorExample() {
        disposable.add(Tutorial.timeOutObservableWithErrorExample()
                // .subscribeOn(Schedulers.io()) // Will Create observable on IO.
                .subscribe(item -> logging("TimeOut example value: " + item)));
    }

    private void errorHandlingExample() {
        disposable.add(Tutorial.errorHandlingExample()
                .subscribe(item -> logging("Handling value: " + item),
                        throwable -> logging("Error Handling Error: " + throwable.getMessage())
                ));
    }

    private void publishSubjectExample() {
        disposable.add(
                PublishSubjectTutorial.publishSubjectExample()
                        .subscribe(item -> logging("Publish Subject-subscribe Received value: " + item))
        );
    }

    private void behaviorSubjectExample() {
        disposable.add(
                PublishSubjectTutorial.behaviorSubjectExample()
                        .subscribe(item -> logging("Behavior Subject-subscribe Last Value: " + item))
        );
    }

    private void replaySubjectExample() {
        disposable.add(
                PublishSubjectTutorial.replaySubjectExample()
                        .subscribe(item -> logging("Replay Subject-subscribe Last Value: " + item))
        );
    }

    private void asyncSubjectExample() {
        disposable.add(
                PublishSubjectTutorial.asyncSubjectExample()
                        .subscribe(item -> logging("Async Subject-subscribe Last Value: " + item))
        );
    }

    private void customOperatorExample() {
        disposable.add(
                CustomOperatorTutorial.customOperatorTakeEvenExample()
                        .subscribe(item -> logging("Custom Operator Take Even Example Value: " + item))
        );
    }

    private void threadingExample() {
        disposable.add(
                RxThreadingTutorial.threadingWithSubscribeOnIOExample()
                        .doOnNext(item -> logging("Pushing item " + item + " on " + Thread.currentThread().getName() + " thread"))
                        .subscribe(item -> logging("Threading Example Value: " + item))
        );
    }

    private void threadingWithSubscribeOnIOAdnUIUpdateExample() {
        binding.action1Text.setText("---");
        disposable.add(
                RxThreadingTutorial.threadingWithSubscribeOnIOAdnUIUpdateExample()
                        .doOnNext(item -> logging("Pushing item " + item + " on " + Thread.currentThread().getName() + " thread"))
                        .observeOn(AndroidSchedulers.mainThread()) // If not used, will crash app!
                        .doOnNext(item -> logging("Pushing item (observeOn) " + item + " on " + Thread.currentThread().getName() + " thread"))
                        .subscribe(item -> {
                            logging("Threading Example Value: " + item);
                            binding.action1Text.setText(item.toString());
                            binding.action1Text.setTextSize(22f);
                        })
        );
    }

    //  Other examples.
    public void startChainOperation() {
        Observable<List<City>> getCitiesAPI = Observable.fromArray(ListUtil.removeNullValues(City.cityList()));

        disposable.add(getCitiesAPI
                .concatMap(Observable::fromIterable)
                .map(city -> Weather.getWeatherByCityId(city.id))
                .onErrorReturnItem(Weather.FAKE_OBJ)
                .filter(Weather::isValid)
                .doOnError(throwable -> Log.d("WHAT", "doOnError message: " + throwable.getMessage()))
                .onErrorComplete()
                .subscribe(weather -> {
                    Log.d("WHAT", "Subscribe: " + weather);
                })
        );
    }

    public void startGroupByOperation() {
        @NonNull Observable<Weather> getWeatherList = Observable.fromIterable(ListUtil.removeNullValues(Weather.weatherList()));

        disposable.add(getWeatherList
                .groupBy(weather -> weather.temp)
                .flatMapSingle(Observable::toList)
                .doOnError(throwable -> Log.d("WHAT", "doOnError message: " + throwable.getMessage()))
                .onErrorComplete()
                .subscribe(lists -> {
                    Log.d("WHAT", "Group size: " + lists.size());
                    for (Weather w : lists) {
                        Log.d("WHAT", "Subscribe: " + w);
                    }
                    Log.d("WHAT", "\n\n");
                }));
    }

    private void testNoOrderExecution() {
        disposable.add(noOrderExecution()
                .subscribe(s -> {
                    Log.d("WHAT", "No order Execution Finished");
                }));
    }


    private @NonNull Observable<String> noOrderExecution() {
        Completable apiCall = Completable.fromObservable(Observable
                .just("Api call")
                .delay(2, TimeUnit.SECONDS)
                .onErrorComplete()
                .doOnNext(s -> Log.d("WHAT", ":: " + s + " done"))
        );

        Completable apiCall1 = Completable.fromObservable(Observable
                .just("Api call 1")
                .onErrorComplete()
                .doOnNext(s -> Log.d("WHAT", ":: " + s + " done"))
        );

        Completable apiCall2 = Completable.fromObservable(Observable
                .just("Api call 2")
                .onErrorComplete()
                .doOnNext(s -> Log.d("WHAT", ":: " + s + " done"))
        );

        // Using merge.
        return Observable.merge(apiCall.toObservable(), apiCall1.toObservable(), apiCall2.toObservable());
    }

    private void testSerialExecutionAndNoOrderExecution() {
        Log.d("WHAT", "Serial Execution Started");
        disposable.add(serialExecution()
                .delay(1, TimeUnit.SECONDS)
                .doFinally(() -> {
                    Log.d("WHAT", "Serial Execution Finished");
                    Log.d("WHAT", "No Order Execution Started");
                    testNoOrderExecution();
                })
                .subscribe(s -> {
                    Log.d("WHAT", "Serial Execution");
                }));
    }

    private @NonNull Observable<String> serialExecution() {
        Completable apiCall = Completable.fromObservable(Observable
                .just("Api call")
                .delay(2, TimeUnit.SECONDS)
                .onErrorComplete()
                .doOnNext(s -> Log.d("WHAT", ":: " + s + " done"))
        );

        Completable apiCall1 = Completable.fromObservable(Observable
                .just("Api call 1")
                .onErrorComplete()
                .doOnNext(s -> Log.d("WHAT", ":: " + s + " done"))
        );

        Completable apiCall2 = Completable.fromObservable(Observable
                .just("Api call 2")
                .onErrorComplete()
                .doOnNext(s -> Log.d("WHAT", ":: " + s + " done"))
        );

        // Using concat.
        return Observable.concat(apiCall.toObservable(), apiCall1.toObservable(), apiCall2.toObservable());
    }

    private void displayImages() {
        disposable.add(Observable.fromArray("british UK", "siamese GER", "persian ASIA")
                .subscribeOn(Schedulers.io())
                .map(this::downloadAllImages)
                .flatMap(Single::toObservable)
                .map(s -> s + "-" + s.length())
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        images -> {
                            Log.d("WHAT", "Result Images: " + images);
                            binding.action1.setText(images);
                        },
                        error -> {
                            Log.d("WHAT", "Result Error Images: " + error.getMessage());
                        }
                ));
    }

    private Single<String> downloadAllImages(String cat) {
        Log.d("WHAT", "DownloadAllImages for: " + cat);
        return Single.just("Image Cat " + cat).delay(1, TimeUnit.SECONDS);
    }

    public void testZip() {
        disposable.add(
                Observable.zip(obAsyncString(), obAsyncString1(), obAsyncString2(), (s, s2, s3) -> "Together: " + s + s2 + s3)
                        .subscribe(result -> {
                            Log.d("WHAT", "Result: " + result);
                        }));
    }

    private Observable<String> obAsyncString() {
        return Observable.just("Request1")
                .map(val -> "Hello")
                .delay(1, TimeUnit.SECONDS)
                .doOnNext(s -> Log.d("WHAT", "Request1 Finished"));

    }

    private Observable<String> obAsyncString1() {
        return Observable.just("Request2")
                .map(val -> " World")
                .delay(3, TimeUnit.SECONDS)
                .doOnNext(s -> Log.d("WHAT", "Request2 Finished"));
    }

    private Observable<String> obAsyncString2() {
        return Observable.just("Request3")
                .map(val -> "!")
                .delay(2, TimeUnit.SECONDS)
                .doOnNext(s -> Log.d("WHAT", "Request3 Finished"));
    }

    private void parallelMultipleNetworkAPICalls() {
        disposable.add(Observable.merge(
                        networkCall().subscribeOn(Schedulers.io()),
                        networkCall().subscribeOn(Schedulers.io()),
                        networkCall().subscribeOn(Schedulers.io())
                )
                .subscribeOn(Schedulers.io())
                .map(String::toUpperCase)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.d("WHAT", "Response: " + response);
                    binding.action1.setText("Response size: " + response);
                }));
    }

    private void parallelMultipleNetworkAPICalls2() {
        @NonNull Observable<String> obsMerged = Observable.merge(
                networkCall(),
                networkCallDelayed(),
                networkCall(),
                networkCall()
        );
        disposable.add(Observable.merge(
                        obsMerged,
                        networkCallDelayed()
                )
                .subscribe(s -> {
                    Log.d("WHAT", "Response: " + s);
                }));
    }

    private @NonNull Observable<String> networkCall() {
        return Observable.just("API Call Id:" + Math.abs(new Random().nextInt()));
    }

    private @NonNull Observable<String> networkCallDelayed() {
        return Observable
                .just("API Call Id:" + Math.abs(new Random().nextInt()) + " Id delayed")
                .delay(2, TimeUnit.SECONDS);
    }

    public Single<List<Integer>> getAvailableRestaurants(int userId) {
        List<Integer> list = new ArrayList<>();
        list.add(5 * userId);
        list.add(3);
        list.add(2);
        return Single.just(list);
    }

    private Single<Boolean> getShowPhotosExperiment(int userId) {
        return Single.just(userId % 2 == 0);
    }

    public Single<Pair<List<Integer>, Boolean>> getRestaurantList(int userId) {
        return getAvailableRestaurants(userId)
                .zipWith(getShowPhotosExperiment(userId), Pair::new)
                .delay(5, TimeUnit.SECONDS);
    }

    private void combiningObservable() {
//        Disposable disposable1 =  Observable.just("Spock", "McCoy")
//                .startWithItem("Test")
//                .zipWith(Observable.interval(1, TimeUnit.SECONDS), (item, interval) -> item) // To delay.
//                .subscribe(item -> binding.textViewData.setText(item));
//        compositeDisposable.addAll(disposable1);
//
//        compositeDisposable.add(
//                Observable.just(1, 2, 3)
//                .mergeWith(Observable.just(4, 5, 6))
//                .map(integer -> integer * 2)
//                .flatMap(integer -> Observable.just(integer + 2))
//                .zipWith(Observable.interval(1, TimeUnit.SECONDS), (item, interval) -> item) // To delay.
//                .subscribe(item -> binding.textViewData2.setText(item.toString().toUpperCase()))
//        );
//
//        compositeDisposable.add(Observable
//                .range(1, 28)
//                .mergeWith(Observable.just(4, 5, 30))
//                .groupBy(it -> it%3 == 0)
//                .flatMapSingle(Observable::toList)
//                .subscribe(group -> {
//                    binding.textViewData2.setText("Group size: " + group.size());
//                    Log.d("WHAT", "Group");
//                    for (Integer integer : group) {
//                        Log.d("WHAT", "Group element: " + integer);
//                    }
//                    Log.d("WHAT", "New Group");
//                }));

    }

    private void errorHandling() {
        Observable<String> observable1 = Observable.error(new IllegalArgumentException("Problem is IllegalArgumentException"));
        Observable<String> observable2 = Observable.just("Four", "Five", "Six");

        disposable.add(
                Observable
                        .mergeDelayError(observable1, observable2)
                        //.onErrorReturnItem("Also Error Happened")
                        .doOnError(throwable -> {
                            binding.action1.setText("Error happened!");
                        })
                        // .onErrorComplete()
                        .onErrorResumeNext(throwable -> {
                            Log.d("WHAT", "Error: " + throwable.getMessage());
                            return Observable.just("Error: " + throwable.getMessage());
                        })
                        .zipWith(Observable.interval(1, TimeUnit.SECONDS), (item, interval) -> item) // To delay.
                        .subscribe(item -> binding.action2Text.setText(item)));

        disposable.add(
                Observable.range(0, 10)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(integer -> {
                            if (integer == 3) {
                                throw new IllegalArgumentException("Problem is IllegalArgumentException");
                            }
                        })
                        .retry(3) // retry 3 times and then calls onErrorComplete, or other option onErrorResumeNext..
                        .onErrorComplete()
                        .subscribe(integer -> {
                            Log.d("WHAT", "Error: " + integer);
                            binding.action2Text.setText(integer.toString());
                        })
        );
    }

    private @NonNull Observable<ArrayList<String>> errorHandlingWithAPIMockExample() {
        ArrayList<String> apiResponseOne = new ArrayList<>();
        ArrayList<String> apiResponseTwo = new ArrayList<>();
        apiResponseOne.add("Response of API-1");
        apiResponseTwo.add("Response of API-2");

        @NonNull Observable<ArrayList<String>> observableOne = Observable.just(apiResponseOne)
                .map(list -> {
                    if (list.size() == 1) {
                        Log.d("WHAT", "NullPointerException");
                        throw new NullPointerException("Its a NPE");
                    }
                    return list;
                }).onErrorReturnItem(new ArrayList<>());

        @NonNull Observable<ArrayList<String>> observableTwo = Observable.just(apiResponseTwo)
                .map(strings -> strings)
                .onErrorReturnItem(new ArrayList<>());

        return observableOne.zipWith(observableTwo, this::outputFunction);
    }

    private ArrayList<String> outputFunction(ArrayList<String> outputOne, ArrayList<String> outputTwo) {
        ArrayList<String> output = new ArrayList<>();
        output.addAll(outputOne);
        output.addAll(outputTwo);
        return output;
    }

    private void testRx2() {
        // Combining Observables
        Disposable disposable1 = Observable.just("Spock", "McCoy")
                .startWithItem("Test")
                .zipWith(Observable.interval(1, TimeUnit.SECONDS), (item, interval) -> item) // To delay.
                .delay(2, TimeUnit.SECONDS)
                // .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> {
                    binding.action1.setText(item.toUpperCase());
                });

        Disposable disposable2 = Observable.just(1, 2, 3)
                .mergeWith(Observable.just(4, 5, 6))
                .subscribe(item -> binding.action2Text.setText(item.toString().toUpperCase()));

        disposable.add(disposable1, disposable2);
    }

    private void rxCompletable() {
        Disposable disposable = Completable
                .complete()
                .subscribe(() -> binding.action1.setText("Completable done"));

        this.disposable.add(disposable);
    }

    private void rxMaybe() {
        Disposable disposable = Maybe.just("Maybe")
                .subscribe(s -> {
                    binding.action1.setText(s);
                });

        this.disposable.add(disposable);
    }

    private void testRx() {
        Disposable disposable1 = Observable.just("one", "two", "three", "four", "five")
                .subscribeOn(Schedulers.io())
                .zipWith(Observable.interval(1, TimeUnit.SECONDS), (item, interval) -> item) // To delay.
                .delay(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sign -> {
                    binding.action1.setText(sign);
                });

        String[] stringList = new String[]{"A", "B", "C", "D", "E", "F"};
        Observable<String> observableFromArray = Observable.fromArray(stringList);

        Disposable disposable = observableFromArray
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .delay(2, TimeUnit.SECONDS)
                .subscribe(sign -> {
                    binding.action1.setText(sign);
                });

        this.disposable.add(disposable1, disposable);
    }

    private void rxSingle() {
        Disposable disposable = Single.just("Test Single")
                .delay(2, TimeUnit.SECONDS)
                .subscribe((s, throwable) -> {
                    binding.action1.setText(s);
                });

        this.disposable.add(disposable);
    }

    private void genericCallbackMethod(Runnable runnable) {
        if (runnable != null) {
            binding.action1.setText("Running GenericCallbackMethod");
            runnable.run();
        }
    }
}