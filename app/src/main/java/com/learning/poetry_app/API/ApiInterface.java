package com.learning.poetry_app.API;

import com.learning.poetry_app.Response.DeletetResponse;
import com.learning.poetry_app.Response.GetPoetryResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    //after creating GetPoetryResponse class
    //Used to ask from apis in request format for data
    //After creating this interface define this interface in mainActivity
    //Make request to the server using call method
    //You have to call for each operation
    //And for each response you need to create separate POJO class[Model class]

    @GET("readpoetry.php")
    Call<GetPoetryResponse> getpoetry();            //select * from poetry perform

    @FormUrlEncoded
    @POST("deletepoetry.php")
    Call<DeletetResponse> deletepoetry(@Field("poetry_id")String poetry_id);        //ask for parameters which are you used in api when you call it from postman

    @FormUrlEncoded
    @POST("poetryApi.php")
    Call<DeletetResponse> addpoetry(@Field("poetry")String poetryData,@Field("poet_name")String poet_name);     //Use same class as DeleteResponse in call because they shared same data in api check in api status and message

    @FormUrlEncoded
    @POST("updatepoetry.php")
    Call<DeletetResponse> updatepoetry(@Field("poetry_data")String poetryData,@Field("id")String id);
}
