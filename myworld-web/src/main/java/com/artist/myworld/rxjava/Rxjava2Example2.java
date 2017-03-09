package com.artist.myworld.rxjava;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;

/**
 * Created by asiam on 2017/3/3.
 */
public class Rxjava2Example2 {
    public static void main1(String[] args) throws IOException, InterruptedException {

//        Observable<Integer> observable= Observable.create(new ObservableOnSubscribe<Integer>() {
//
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
////                for(int i=0;i<1000;i++) {
//                    e.onNext(1);
////                }
//
//                e.onComplete();
//            }
//        }).map(s -> {
//            System.out.println("");
//            return s+1;});
//
//        Disposable disposable = observable.subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                //这里接收数据项
//                System.out.println("accept:"+integer);
//                Thread.sleep(100);
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                //这里接收onError
//            }
//        }, new Action() {
//            @Override
//            public void run() throws Exception {
//                //这里接收onComplete。
//            }
//        });



        //Flowable背压示例
//        Flowable.create(new FlowableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
//                System.out.println("subscribe thread:"+Thread.currentThread().getName());
//                for(int i=0;i<1000;i++){
//                    e.onNext(i);
//                }
//                e.onComplete();
//            }
//        }, BackpressureStrategy.DROP) //指定背压处理策略，抛出异常
//                .subscribeOn(Schedulers.computation())
//                .observeOn(Schedulers.newThread())
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        System.out.println("accept thread:"+Thread.currentThread().getName());
//                        System.out.println("JG"+ integer.toString());
//                        Thread.sleep(10);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        System.out.println("throwable thread:"+Thread.currentThread().getName());
//                        System.out.println("JG"+throwable.toString());
//                    }
//                }, new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        System.out.println("complete thread:"+Thread.currentThread().getName());
//                        //这里接收onComplete。
//                    }
//                });
//            System.in.read("click and key exit!".getBytes());



        //Subject
//        BehaviorSubject<String> subject = BehaviorSubject.create();
//        subject.subscribe(o -> {
//            System.out.println(Thread.currentThread().getName()+":JD"+o);
//
//        });
//        subject.onNext("one");
//        subject.onNext("two");
//        subject.onNext("three");
//        subject.onComplete();
//        System.in.read("click and key exit!".getBytes());

        //Processor
//        AsyncProcessor<String> processor = AsyncProcessor.create();
//        processor.subscribe(o -> System.out.println(Thread.currentThread().getName()+":JD"+o)); //three
//        processor.onNext("one");
//        processor.onNext("two");
//        processor.onNext("three");
//        processor.onComplete();
//        System.in.read("click and key exit!".getBytes());

//        Observable<Integer> observable2 = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
//            @Override
//            public ObservableSource<? extends Integer> call() throws Exception {
//                System.out.println("hello");
//                return Observable.just(1).map(s->{
//                    System.out.println("map");
//                    Thread.sleep(1000);
//                    return s+1;
//                });
//            }
//        });
//
//        Disposable d1=observable2.subscribe( s-> {
//            System.out.println("D1:"+s);
//        });
//        Disposable d2=observable2.subscribe( s-> {
//            System.out.println("D2:"+s);
//        });
//        d1.dispose();

        //背压测试，创建订阅者
//        Subscriber<String> subscriber = new Subscriber<String>() {
//            @Override
//            public void onSubscribe(Subscription s) {
//                //这一步是必须，我们通常可以在这里做一些初始化操作，调用request()方法表示初始化工作已经完成
//                //调用request()方法，会立即触发onNext()方法
//                //在onComplete()方法完成，才会再执行request()后边的代码
//                s.request(Long.MAX_VALUE);
//            }
//
//            @Override
//            public void onNext(String value) {
//                System.out.println("onNext"+ value);
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                System.out.println("onError"+ t.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//                //由于Reactive-Streams的兼容性，方法onCompleted被重命名为onComplete
//                System.out.println("onComplete");
//            }
//        };
//        Subscriber<String> subscriber1 = Flowable.create(new FlowableOnSubscribe<String>() {
//            @Override
//            public void subscribe(FlowableEmitter<String> e) throws Exception {
//                e.onNext("Hello,I am China!");
//            }
//        }, BackpressureStrategy.BUFFER).map(s->s+" after map").flatMap(s -> Flowable.just(s+" after flatMap"))
////                .subscribe(subscriber);
//                .subscribeWith(subscriber);


        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                //这一步是必须，我们通常可以在这里做一些初始化操作，调用request()方法表示初始化工作已经完成
                //调用request()方法，会立即触发onNext()方法
                //在onComplete()方法完成，才会再执行request()后边的代码
                System.out.println(Thread.currentThread().getName()+",onSubscribe");
                s.request(Long.MAX_VALUE);
                System.out.println("after request");
            }

            @Override
            public void onNext(String value) {
                System.out.println(Thread.currentThread().getName()+",onNext, value:"+ value);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(Thread.currentThread().getName()+",onError:"+ t.getMessage());
            }

            @Override
            public void onComplete() {
                //由于Reactive-Streams的兼容性，方法onCompleted被重命名为onComplete
                System.out.println(Thread.currentThread().getName()+",onComplete");
            }
        };
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext(Thread.currentThread().getName()+",subscribe");
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.newThread())
                .map(
                        s-> {
                            String str = Thread.currentThread().getName()+","+s+",after map";
                            Thread.sleep(3000);
                            System.out.println(str);
                            return str;
                        }

                )
                .observeOn(Schedulers.newThread())
                .flatMap(s ->{
                    String str = Thread.currentThread().getName() +","+ s + ",after flatMap";
                    Thread.sleep(3000);
                    System.out.println(str);
                   return Flowable.just(str);
                        }
                )
                .subscribe(subscriber);
        Thread.sleep(3000);
        System.out.println("main thread is over");

        System.in.read();

    }
}
