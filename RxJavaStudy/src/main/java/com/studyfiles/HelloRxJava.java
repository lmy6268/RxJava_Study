package com.studyfiles;

import io.reactivex.Observable;

//프로젝트가 제대로 작동하는지 확인
public class HelloRxJava {
    public static void main(String[] args) {
        Observable<String> observable = Observable.just("Hello", "RxJava");
        observable.subscribe(data -> System.out.println(data));
    }
}
