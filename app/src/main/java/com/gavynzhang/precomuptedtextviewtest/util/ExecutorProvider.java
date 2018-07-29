package com.gavynzhang.precomuptedtextviewtest.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by GavynZhang on 2018/7/29 21:24.
 */

public class ExecutorProvider {
    private static Executor mExecutor;

    public static Executor getExecutor(){
        if (mExecutor == null){
            synchronized (Executors.class){
                if (mExecutor == null){
                    mExecutor = Executors.newCachedThreadPool();
                }
            }
        }

        return mExecutor;
    }
}
