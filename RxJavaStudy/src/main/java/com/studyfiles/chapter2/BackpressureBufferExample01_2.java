package com.studyfiles.chapter2;

import com.studyfiles.utils.LogType;
import com.studyfiles.utils.Logger;
import com.studyfiles.utils.TimeUtil;
import io.reactivex.BackpressureOverflowStrategy;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class BackpressureBufferExample01_2 {
    public static void main(String[] args) {
        System.out.println("# start : " + TimeUtil.getCurrentTimeFormatted());
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .doOnNext(data -> Logger.log("#interval doOnNext()", data))
                .onBackpressureBuffer(
                        2,//버퍼가 가질 수 있는 최대 데이터의 개수
                        //버퍼가 가득 찼을 경우 출력하는 데이터
                        () -> Logger.log("overflow!"),
                        //DROP_OLDEST : 버퍼 내부에 가장 오래 남아있던 데이터를 DROP
                        BackpressureOverflowStrategy.DROP_OLDEST
                )
                //버퍼 내에서 데이터가 통지될 때, 해당 데이터를 출력하는 부분
                .doOnNext(data -> Logger.log("#onBackpressureBuffer doOnNext()", data))
                .observeOn(Schedulers.computation(), false, 1)
                //데이터를 소비하는 곳
                .subscribe(
                        data -> {
                            TimeUtil.sleep(1000L);
                            //데이터를 소비할 때, 출력
                            Logger.log(LogType.ON_NEXT, data);
                        },
                        error -> Logger.log(LogType.ON_ERROR, error)
                );
        TimeUtil.sleep(2500L);
    }
}
