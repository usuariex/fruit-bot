package com.devs.frutybot.data.util;

public interface RepositoryCallback<T> {
    void onSuccess(T data);
    void onError(Throwable throwable);
}