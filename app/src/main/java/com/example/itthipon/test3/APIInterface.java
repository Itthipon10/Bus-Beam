package com.example.itthipon.test3;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;

/**
 * Created by Itthipon on 7/14/2017.
 */

public interface APIInterface {

    @GET("?id=99999&lat=15&lon=100&timestamp=1499855381&hdop=0&altitude=0&speed=0")
    Call<LoginRespone> updateLocation(
            @Field("id") String id,
            @Field("lat") String lat,
            @Field("lon") String lon,
            @Field("timestamp") String timestamp,
            @Field("hdop") String hdop,
            @Field("altitude") String altitude,
            @Field("speed") String speed);
}
