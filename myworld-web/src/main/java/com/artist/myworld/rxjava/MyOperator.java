//package com.artist.myworld.rxjava;
//
//import rx.Observable;
//import rx.Subscriber;
//
///**
// * Created by asiamastor on 2016/8/26.
// */
//public class MyOperator<T> implements Observable.Operator<T, T> {
//    public MyOperator( /* any necessary params here */ ) {
//        /* 这里添加必要的初始化代码 */
//    }
//
//    @Override
//    public Subscriber<? super T> call(Subscriber<? super T> s) {
//        return new Subscriber<T>(s) {
//            @Override
//            public void onCompleted() {
///* 这里添加你自己的onCompleted行为，或者仅仅传递完成通知： */
//                if(!s.isUnsubscribed()) {
//                    s.onCompleted();
//                }
//            }
//            @Override
//            public void onError(Throwable t) {
///* 这里添加你自己的onError行为, 或者仅仅传递错误通知：*/
//                if(!s.isUnsubscribed()) {
//                    s.onError(t);
//                }
//            }
//            @Override
//            public void onNext(T item) {
///* 这个例子对结果的每一项执行排序操作，然后返回这个结果 */
//                if(!s.isUnsubscribed()) {
////                    transformedItem = myOperatorTransformOperation(item);
////                    s.onNext(transformedItem);
//                    s.onNext(item);
//                }
//            }
//        };
//    }
//}