package com.zstu.natsukawa.fasterner.base;

import android.annotation.SuppressLint;
import android.content.Context;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

class MyRetrofit {

    private Context context;
    @SuppressLint("StaticFieldLeak")
    private static MyRetrofit myRetrofit = null;
    private Retrofit retrofit = null;
    private String url;
    private MyRetrofit(Context context,String url){
        this.context = context.getApplicationContext();
        this.url = url;
        initialiseRetrofit(url);
    }

    static MyRetrofit getInstance(Context context, String url){
        if(null == myRetrofit){
            synchronized (MyRetrofit.class){
                if(null == myRetrofit)
                    myRetrofit = new MyRetrofit(context, url);
            }
        }
        myRetrofit.setUrl(url);
        return myRetrofit;
    }

    private void initialiseRetrofit(String url){
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(new OkHttpClient.Builder().connectTimeout(6, TimeUnit.SECONDS).build())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    static void destroyMyRetrofit(){
        myRetrofit = null;
    }

    public Context getContext() {
        return context;
    }

    APIService getAPIService(){
        return retrofit.create(APIService.class);
    }
}
