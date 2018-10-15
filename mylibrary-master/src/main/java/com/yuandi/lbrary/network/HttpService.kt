package com.yuandi.lbrary.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by EdgeDi
 * 2018/8/3 14:27
 */
interface HttpService {

    @Headers("ed_change:url")
    @GET("{path}")
    fun GET(@Path("path") path: String, @QueryMap map: HashMap<String, String>): Call<ResponseBody>

    @Headers("ed_change:url")
    @POST("{path}")
    @FormUrlEncoded
    fun POST(@Path("path") path: String, @FieldMap map: HashMap<String, String>): Call<ResponseBody>

    @Headers("ed_change:url")
    @POST("{path}")
    @FormUrlEncoded
    fun Head(@Path("path") path: String, @FieldMap map: HashMap<String, String>, @HeaderMap head: HashMap<String, String>): Call<ResponseBody>

    @Headers("Content-Type:application/json;charset=UTF-8", "ed_change:url")
    @POST("{path}")
    fun JSON(@Path("path") path: String, @Body body: RequestBody, @QueryMap map: HashMap<String, String>): Call<ResponseBody>

    @Headers("ed_change:url")
    @POST("{path}")
    fun UPLOAD(@Path("path") path: String, @Body body: MultipartBody): Call<ResponseBody>

    @Headers("ed_change:url")
    @GET("{path}")
    fun LOAD(@Path("path") path: String, @QueryMap map: HashMap<String, String>): Call<ResponseBody>
}
