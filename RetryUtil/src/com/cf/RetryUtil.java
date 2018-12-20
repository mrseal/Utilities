package com.cf;

import java.util.function.Supplier;

public class RetryUtil<T> {

    /**
     * Execute the function
     */
    public T run(final Supplier<T> function) {
        return function.get();
    }
    
    /**
     * Execute the function with Retry
     */
    public T runWithRetry(final Supplier<T> function, int maxRetries) {
        for (int retryCount = 1; retryCount <= maxRetries; retryCount++) {
            try {
                return function.get();
            } catch (Exception e) {
                if (retryCount >= maxRetries) {
                    throw e;
                }
            }
        }
    }

}
