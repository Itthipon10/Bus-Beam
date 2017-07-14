package com.example.itthipon.test3;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Itthipon on 7/14/2017.
 */

public class APIClient {

    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://bus-beam.com:5055/";

    public static Retrofit getClient() {


        if (retrofit == null) {


            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();


            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient)
                    .build();
        }
        return retrofit;
    }


    public static Retrofit getClient(final String token) {


        if (retrofit == null) {


            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();


            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    HttpUrl originalHttpUrl = original.url();


                    HttpUrl url = originalHttpUrl.newBuilder()
                            .addQueryParameter("x-access-token", token)
                            .build();


                    Request.Builder requestBuilder = original.newBuilder()
                            .url(url);


                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });


            OkHttpClient httpClient = builder.build();


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient)
                    .build();
        }
        return retrofit;
    }


}
