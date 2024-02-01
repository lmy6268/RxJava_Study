package com.studyfiles;

import com.studyfiles.utils.LogType;
import com.studyfiles.utils.Logger;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class HelloRxJavaFlowableCreateExample {
    public static void main(String[] args) throws InterruptedException {
//        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
//            @Override
//            public void subscribe(FlowableEmitter<String> emitter) throws Exception {
//                String[] datas = {"Hello", "RxJava!"};
//                for (String data : datas) {
//                    //구독이 해지되는 경우 처리를 중단한다.
//                    if (emitter.isCancelled()) return;
//                    //데이터 통지
//                    emitter.onNext(data);
//                }
//
//                //데이터 통지 완료를 알린다.
//                emitter.onComplete();
//            }
//        }, BackpressureStrategy.BUFFER); //구독자의 처리가 늦을 경우 데이터를 버퍼에 담아두는 설정
//
//        flowable.observeOn(Schedulers.computation())
//                .subscribe(new Subscriber<String>() {
//                    private Subscription subscription;
//
//                    @Override
//                    public void onSubscribe(Subscription subscription) {
//                        this.subscription = subscription;
//                        this.subscription.request(Long.MAX_VALUE);
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

        Flowable<String> flowable1 = Flowable.create(emitter -> {
            String[] datas = {"Hello", "RxJava!"};
            for (String data : datas) {
                //구독이 해지되면 처리 중단
                if (emitter.isCancelled()) return;
                emitter.onNext(data);//데이터 밠행

            }
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER);

        flowable1.observeOn(Schedulers.computation())
                .subscribe(data -> Logger.log(LogType.ON_NEXT, data),
                        error -> Logger.log(LogType.ON_ERROR, error),
                        () -> Logger.log(LogType.ON_COMPLETE),
                        subscription -> subscription.request(Long.MAX_VALUE));
        Thread.sleep(500L);
    }

}
