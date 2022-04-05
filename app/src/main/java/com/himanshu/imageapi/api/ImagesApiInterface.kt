package com.himanshu.imageapi.api

import com.himanshu.imageapi.constant.Constant
import com.himanshu.imageapi.models.DetailedImageInfo
import com.himanshu.imageapi.models.ImagesItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImagesApiInterface {
    @GET(Constant.END_POINT_URL)
    suspend fun getData(@Query("page")page:Int,@Query("limit")limit:Int): Response<ArrayList<ImagesItem>>

    @GET("/id/{id}/info")
    suspend fun getInfo(@Path("id") id: String) : Response<DetailedImageInfo>
}