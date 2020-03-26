package com.zxh.Util;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    public static String doGet(String address) throws IOException {
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(address)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String doPost(String address, String json) throws IOException {
        OkHttpClient client=new OkHttpClient();
        RequestBody body = RequestBody.create(json, JSON);
        Request request=new Request.Builder()
                .url(address)
                .post(body)
                .build();
        Response response =client.newCall(request).execute();
        return response.body().string();
    }

    public static String doPut(String address, String json) throws IOException {
        OkHttpClient client=new OkHttpClient();
        RequestBody body = RequestBody.create(json, JSON);
        Request request=new Request.Builder()
                .url(address)
                .put(body)
                .build();
        Response response =client.newCall(request).execute();
        return response.body().string();
    }

    public static String doDelete(String address, String json) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(address)
                .delete(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
//    public static void doGet(String address, okhttp3.Callback callback){
//        OkHttpClient client=new OkHttpClient();
//        Request request=new Request.Builder()
//                .url(address)
//                .build();
//        client.newCall(request).enqueue(callback);
//    }

//    public static void doPost(String address, String json, okhttp3.Callback callback){
//        OkHttpClient client=new OkHttpClient();
//        RequestBody body = RequestBody.create(json, JSON);
//        Request request=new Request.Builder()
//                .url(address)
//                .post(body)
//                .build();
//        client.newCall(request).enqueue(callback);
//    }
//
//    public static void doPut(String address, String json, okhttp3.Callback callback){
//        OkHttpClient client=new OkHttpClient();
//        RequestBody body = RequestBody.create(json, JSON);
//        Request request=new Request.Builder()
//                .url(address)
//                .put(body)
//                .build();
//        client.newCall(request).enqueue(callback);
//    }
//
//    public static void doDelete(String address, String json, okhttp3.Callback callback){
//        OkHttpClient client=new OkHttpClient();
//        RequestBody body = RequestBody.create(json, JSON);
//        Request request=new Request.Builder()
//                .url(address)
//                .delete(body)
//                .build();
//        client.newCall(request).enqueue(callback);
//    }
}
