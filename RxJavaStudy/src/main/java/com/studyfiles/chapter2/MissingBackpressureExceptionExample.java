package com.studyfiles.chapter2;

import com.studyfiles.utils.LogType;
import com.studyfiles.utils.Logger;
import com.studyfiles.utils.TimeUtil;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

//배압기능이 없을 떄의 문제
public class MissingBackpressureExceptionExample {
    public static void main(String[] args) throws InterruptedException {
        Flowable //데이터를 통지
                .interval(1L, TimeUnit.MILLISECONDS) //일정한 시간마다 반복적으로 숫자를 통지
                .doOnNext(data -> Logger.log(LogType.DO_ON_NEXT, data)) //넘어오는 데이터가 어떤 형태인지 확인할 수 있음
                .observeOn(Schedulers.computation()) //데이터를 처리하는 스레드를 분리한다. 
                //데이터를 구독
                .subscribe(
                        data -> {
                            Logger.log(LogType.PRINT, "# 소비자 처리 대기 중..");
                            TimeUtil.sleep(1000L);
                            Logger.log(LogType.ON_NEXT, data);
                        },
                        error -> Logger.log(LogType.ON_ERROR, error),
                        () -> Logger.log(LogType.ON_COMPLETE)
                );
        Thread.sleep(2000L);
    }
}
