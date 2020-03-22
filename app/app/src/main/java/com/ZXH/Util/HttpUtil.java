package com.ZXH.Util;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    public static void sendGetRequest(String address,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendPostRequest(String address, String json, okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        RequestBody body = RequestBody.create(json, JSON);
        Request request=new Request.Builder()
                .url(address)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

}
