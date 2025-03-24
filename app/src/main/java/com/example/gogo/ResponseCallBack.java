package com.example.gogo;

public interface ResponseCallBack {

    void onResponse(String Response);

    void onError(Throwable throwable);
}
