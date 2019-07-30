package com.krt.home;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;

class WrapDownStreamObserver<T> implements MaybeObserver<T> {

    private MaybeObserver<T> actual;

    public WrapDownStreamObserver(MaybeObserver<T> actual) {
        this.actual = actual;
    }

    @Override
    public void onSubscribe(Disposable d) {
        actual.onSubscribe(d);
    }

    @Override
    public void onSuccess(T t) {
        actual.onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        actual.onError(e);
    }

    @Override
    public void onComplete() {
        actual.onComplete();
    }
}