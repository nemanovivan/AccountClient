package org.accountclient.utils;

import java.util.concurrent.ThreadFactory;

/**
 * Account client thread factory
 */
public class AccountClientThreadFactoryImpl implements ThreadFactory {

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        return thread;
    }
}
