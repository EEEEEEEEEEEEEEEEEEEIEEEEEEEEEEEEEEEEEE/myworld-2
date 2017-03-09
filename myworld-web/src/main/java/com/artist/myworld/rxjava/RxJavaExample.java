//package com.artist.myworld.rxjava;
//
//import rx.Observable;
//import rx.Observer;
//import rx.Subscriber;
//import rx.functions.Action0;
//import rx.functions.Action1;
//import rx.schedulers.Schedulers;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by asiamastor on 2016/8/23.
// */
//public class RxJavaExample {
//
//    /**
//     * 普通用法示例
//     */
//    public static void commonExample() {
//        Observable.create(
//                new Observable.OnSubscribe<String>() {
//                    @Override
//                    public void call(Subscriber<? super String> sub) {
//                        sub.onNext("星际 炉石 $风暴");
//                        sub.onCompleted();
//                    }
//                }
//        )
//        //map操作符，就是用来把把一个事件转换为另一个事件的
//        //map()操作符就是用于变换Observable对象的，map操作符返回一个Observable对象，这样就可以实现链式调用，在一个Observable对象上多次使用map操作符，最终将最简洁的数据传递给Subscriber对象
//        //map操作符更有趣的一点是它不必返回Observable对象返回的类型，你可以使用map操作符返回一个发出新的数据类型的observable对象
//        .map(s -> s.split(" "))
//        //Observable.flatMap()接收一个Observable的输出作为输入，同时输出另外一个Observable
//        .flatMap(urls -> Observable.from(urls))
//        .map(s -> getTitle(s))
////                //filter()输出和输入相同的元素，并且会过滤掉那些不满足检查条件的。
//        .filter(s -> !s.startsWith("$"))
////        ofType  是 filter  操作符的一个特殊形式。它过滤一个Observable只返回指定类型的数据。
////        ofType  默认不在任何特定的调度器上指定。
////                .ofType(String.class)
//        //take()输出最多指定数量的结果。
//        .take(3)
//        //doOnNext()允许我们在每次输出一个元素之前做一些额外的事情，比如这里的保存标题。
//        .doOnNext(s -> {
//            check(s);
//            save(s);
//        })
//        .subscribe(s -> System.out.println("subscribe:" + s));
//    }
//
//
//    /**
//     * 线程调度器示例,经测试就算是不同线程，也是按顺序执行的
//     * <p>
//     * subscribeOn(): 指定 Observable.OnSubscribe 被激活时所处的默认线程。或者叫做事件产生的线程。
//     * 如果在operator中有observeOn指定，则会覆盖subscribeOn指定的线程
//     * observeOn(): 指定 Subscriber 所运行在的线程(也可以指定operator所在线程)。或者叫做事件消费的线程。
//     */
//    public static void threadExample() throws IOException {
//        Observable.just(1)
////这个调度器时用于I/O操作。它基于根据需要，增长或缩减来自适应的线程池。我们将使用它来修复我们之前看到的StrictMode违规做法。由于它专用于I/O操作，所以并不是RxJava的默认方法；正确的使用它是由开发者决定的。
////重点需要注意的是线程池是无限制的，大量的I/O调度操作将创建许多个线程并占用内存。一如既往的是，我们需要在性能和简捷两者之间找到一个有效的平衡点。
//                .observeOn(Schedulers.io())
//                .map(s -> {
//                    System.out.println(Thread.currentThread().getName() + ":1:" + s);
//                    return s;
//                })
////这个是计算工作默认的调度器，它与I/O操作无关。它也是许多RxJava方法的默认调度器：buffer(),debounce(),delay(),interval(),sample(),skip()。
//                .observeOn(Schedulers.computation())
//                .map(s -> {
//                    System.out.println(Thread.currentThread().getName() + ":2:" + s);
//                    return s;
//                })
////当我们想在当前线程执行一个任务时，并不是立即，我们可以用.trampoline()将它入队。这个调度器将会处理它的队列并且按序运行队列中每一个任务。它是repeat()和retry()方法默认的调度器。
//                .observeOn(Schedulers.trampoline())
//                .map(s -> {
//                    System.out.println(Thread.currentThread().getName() + ":3:" + s);
//                    return s;
//                })
////这个调度器正如它所看起来的那样：它为指定任务启动一个新的线程。
//                .observeOn(Schedulers.newThread())
//                .map(s -> {
//                    System.out.println(Thread.currentThread().getName() + ":4:" + s);
//                    return s;
//                })
////这个调度器允许你立即在当前线程执行你指定的工作。它是timeout(),timeInterval(),以及timestamp()方法默认的调度器。
//                .observeOn(Schedulers.io())
//                .subscribeOn(Schedulers.newThread())
//                .subscribe(s -> {
//                    System.out.println(Thread.currentThread().getName() + ":final:" + s);
//                });
//
//
//    }
//
//    private static void check(String o) {
//        System.out.println(Thread.currentThread().getName() + ",check:" + o);
//    }
//
//    private static void save(String o) {
//        System.out.println(Thread.currentThread().getName() + ",save:" + o);
//    }
//
//    private static String getTitle(String url) {
//        return url + " title!";
//    }
//
//    /**
//     * concat处理示例
//     */
//    public static void concatExample() {
//        /**
//         * concat连接操作
//         * 取数据先检查缓存的场景
//         取数据，首先检查内存是否有缓存
//         然后检查文件缓存中是否有
//         最后才从网络中取
//         前面任何一个条件满足，就不会执行后面的
//         */
//        Observable<String> memory = Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                if ("缓存" != null) {
//                    subscriber.onNext(Thread.currentThread().getName() + ",缓存");
//                } else {
//                    subscriber.onCompleted();
//                }
//            }
//        });
//        Observable<String> disk = Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                if ("磁盘" != null) {
//                    subscriber.onNext(Thread.currentThread().getName() + ",磁盘");
//                } else {
//                    subscriber.onCompleted();
//                }
//            }
//        });
//        Observable<String> network = Observable.just("network");
//        Observable.concat(memory, disk, network).first().subscribeOn(Schedulers.io()).subscribe(s -> System.out.println(s));
//    }
//
//    /**
//     * merge处理示例
//     * 界面需要等到多个接口并发取完数据，再更新
//     * 拼接两个Observable的输出，不保证顺序，按照事件产生的顺序发送给订阅者
//     */
//    public static void mergeExample() {
//        long start = System.currentTimeMillis();
//        System.out.println("开始时间:" + new Date(start));
////        Observable.just(1).observeOn(Schedulers.newThread()).map(s->s).subscribeOn(Schedulers.io()).subscribe(s->System.out.println(Thread.currentThread().getName()+":"+s));
//        Observable o1 = Observable.just(1)
//                .map(s -> {
//
//                    try {
//                        Thread.sleep(2000);
//                        System.out.println("map1 线程:" + Thread.currentThread() + ",消耗时间:" + (System.currentTimeMillis() - start));
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    return s;
//                }).subscribeOn(Schedulers.newThread());
//        Observable o2 = Observable.just(2)
//                .map(s -> {
//                    try {
//                        Thread.sleep(3000);
//                        System.out.println("map2 线程:" + Thread.currentThread() + ",消耗时间:" + (System.currentTimeMillis() - start));
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    return s;
//                }).subscribeOn(Schedulers.newThread());
//        Observable.merge(o1, o2)
//                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
//                .observeOn(Schedulers.newThread()) // 指定 Subscriber 的回调发生在新线程
//                .subscribeOn(Schedulers.newThread())
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer number) {
//                        System.out.println("subscribe 线程:" + Thread.currentThread() + ",number:" + number + ",消耗时间:" + (System.currentTimeMillis() - start));
//                    }
//                });
//    }
//
//    /**
//     * scanReduceCollect示例
//     */
//    public static void scanReduceCollectExample() {
//        Observable.just(1, 2, 3, 4)
//                .scan((x, y) -> {
//                    return x + y;
//                })
////        Reduce操作符应用一个函数接收Observable发射的数据和函数的计算结果作为下次计算的参数，输出最后的结果。跟前面我们了解过的scan操作符很类似，只是scan会输出每次计算的结果，而reduce只会输出最后的结果。
//                .reduce((x, y) -> x * y)
////        Collect操作符类似于Reduce，但是其目的不同，collect用来将源Observable发射的数据给收集到一个数据结构里面，需要使用两个参数：
////        1.一个产生收集数据结构的函数。
////        2.一个接收第一个函数产生的数据结构和源Observable发射的数据作为参数的函数。
//                .collect(() -> new ArrayList<>(), (integers, integer) -> {
//                    integers.add(integer);
//                })
//                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
//                .observeOn(Schedulers.newThread()) // 指定 Subscriber 的回调发生在新线程
//
//                .subscribe(new Action1<ArrayList>() {
//                    @Override
//                    public void call(ArrayList number) {
//                        System.out.println("subscribe 线程:" + Thread.currentThread() + ",number:" + number);
//                    }
//                });
//    }
//
//    /**
//     * interval示例
//     * 使用interval做周期性操作。当有“每隔xx秒后执行yy操作”类似的需求的时候，想到使用interval
//     */
//    public static void intervalExample() {
//        Observable.interval(2, TimeUnit.SECONDS)
//                .subscribe(new Observer<Long>() {
//                    @Override
//                    public void onCompleted() {
//                        System.out.println("completed");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        System.out.println("error");
//                    }
//
//                    @Override
//                    public void onNext(Long number) {
//                        System.out.println("2秒以后执行此操作");
//                    }
//                });
//    }
//
//    /**
//     * 使用schedulePeriodically做轮询请求
//     */
//    public static void schedulePeriodicallyExample() {
//        Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(final Subscriber<? super String> observer) {
//                Schedulers.newThread().createWorker()
//                        .schedulePeriodically(new Action0() {
//                            @Override
//                            public void call() {
//                                observer.onNext("result");
//                            }
//                        }, /* INITIAL_DELAY */ 2000, /*POLLING_INTERVAL*/1000, TimeUnit.MILLISECONDS);
//            }
//        }).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                System.out.println("polling…." + s);
//            }
//        });
//    }
//
//    /**
//     * combineLatest示例
//     * 使用combineLatest合并最近N个结点
//     * 例如：注册的时候所有输入信息（邮箱、密码、电话号码等）合法才点亮注册按钮。
//     */
//    public static void combineLatestExample() {
//        long start = System.currentTimeMillis();
//        Observable<Boolean> nameValid = Observable.just("name")
//                .observeOn(Schedulers.newThread()).map(p -> {
//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println(Thread.currentThread().getName() + ", check name");
//                    return p != null;
//                });
//        Observable<Boolean> emailValid = Observable.just(null)
//                .observeOn(Schedulers.io()).map(p -> {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println(Thread.currentThread().getName() + ", check email");
//                    return p != null;
//                });
//        Observable.combineLatest(nameValid, emailValid, (p1, p2) -> {
//            System.out.println(Thread.currentThread().getName() + ", combineLatest p1&&p2:" + (p1 && p2));
//            System.out.println("combineLatest cost:" + (System.currentTimeMillis() - start));
//            return p1 && p2;
//        }).subscribeOn(Schedulers.computation()).observeOn(Schedulers.computation()).subscribe(p -> {
//            System.out.println("subscribe cost:" + (System.currentTimeMillis() - start));
//            System.out.println(Thread.currentThread().getName() + ", subscribe:" + p);
//        });
//    }
//
//    /**
//     * 组合操作符
//     */
//    public static void composeExample(){
//        Observable.just("someSource")
//                .map(data -> data+"_suffix")
//                .compose(applySchedulers())
//                .subscribe(RxJavaExample::save);
//    }
//
//    private static <T> Observable.Transformer<T, T> applySchedulers() {
////        return new Observable.Transformer<T, T>() {
////            @Override
////            public Observable<T> call(Observable<T> observable) {
////                return observable.subscribeOn(Schedulers.io())
////                        .observeOn(Schedulers.newThread());
////            }
////        };
//        //lambdas写法
//        return observable -> observable.subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.newThread());
//    }
//
//    /**
//     * lift实现自定义操作符
//     */
//    public static void liftExample(){
//        Observable.just("someSource").lift(new MyOperator<String>()).subscribe(s->{
//            System.out.println(s);
//        });
//    }
//
//    public static void main1(String[] args) throws IOException {
////        commonExample();
////        combineLatestExample();
////        composeExample();
//        liftExample();
//        System.out.println("this is branch,点击任意键退出!");
//        System.in.read();
//    }
//}
