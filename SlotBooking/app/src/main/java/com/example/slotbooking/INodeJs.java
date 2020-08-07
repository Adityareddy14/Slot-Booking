package com.example.slotbooking;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by aditya
 */

public interface INodeJs {

    @POST("/bookslot")
    @FormUrlEncoded
    Observable<String> slot(
            @Field("username") String username,
            @Field("email") String email);

}
