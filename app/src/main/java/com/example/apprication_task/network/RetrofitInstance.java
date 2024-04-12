package com.example.apprication_task.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    public static final String BASEURL = "https://dummyjson.com/";
    public static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            OkHttpClient httpClient;

                httpClient = new OkHttpClient.Builder()
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request.Builder requestBuilder = chain.request().newBuilder();
                                requestBuilder.addHeader("Accept", "application/json");
                                requestBuilder.addHeader("Authorization", "Hello Android");

                                // Response response = chain.proceed(requestBuilder.build());

                                return chain.proceed(requestBuilder.build());
                            }
                        })
                        .readTimeout(2, TimeUnit.MINUTES)
                        .writeTimeout(2, TimeUnit.MINUTES)
                        .connectTimeout(1, TimeUnit.MINUTES)
                        /*.addInterceptor(new ChuckerInterceptor(getDefaultContext()))*/
                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build();
            retrofit = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).client(httpClient).build();
        }
        return retrofit;
    }
}
