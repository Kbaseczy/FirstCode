package com.example.net_networktest;

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
