package com.example.itthipon.test3;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Itthipon on 7/14/2017.
 */

public interface APIInterface {

    @GET("/")
    Call<UpdateLocationRespond> updateLocation(
            @Query("id") int id,
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("timestamp") long timestamp,
            @Query("hdop") float hdop,
            @Query("altitude") double altitude,
            @Query("speed") float speed);
}
