package com.yztc.fazhi.net;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wanggang on 2017/2/22.
 */

public class RxHelper {

    public static Observable.Transformer schedulers() {
        return new Observable.Transformer() {
            @Override
            public Object call(Object observable) {
                return ((Observable)observable)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> Observable.Transformer transform() {
        return new Observable.Transformer() {
            @Override
            public Object call(Object observable) {
                return ((Observable)observable)
                        .map(new TransformerFun())
                        .onErrorResumeNext(new ServerExceptionFun());
            }
        };
    }

    static class ServerExceptionFun<T> implements Func1<Throwable,Observable<T>>{

        @Override
        public Observable<T> call(Throwable throwable) {
            return Observable.error(ExceptionHandle.handleException(throwable));
        }
    }

    static class  TransformerFun<T> implements Func1<BaseResponse<T>,T>{

        @Override
        public T call(BaseResponse<T> baseResponse) {
            if(baseResponse.is_success()){
                return baseResponse.getData();
            }
            ExceptionHandle.ServerException exception=
                    new ExceptionHandle.ServerException();
            exception.message=baseResponse.getError_content();
            throw exception;
        }
    }
}
