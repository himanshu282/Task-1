package com.himanshu.imageapi.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.himanshu.imageapi.api.ImagesApiInterface
import com.himanshu.imageapi.models.DetailedImageInfo
import com.himanshu.imageapi.models.Images
import com.himanshu.imageapi.models.ImagesItem

class ImagesRepository(private val imagesApiInterface: ImagesApiInterface) {
    private val imagesLiveData = MutableLiveData<ArrayList<ImagesItem>>()
    val images : LiveData<ArrayList<ImagesItem>>
    get() = imagesLiveData

    suspend fun getImages(page:Int){
        val response =imagesApiInterface.getData(page)
        if(response.body() != null){
            imagesLiveData.postValue(response.body())
        }
    }

     val infoLiveData = MutableLiveData<DetailedImageInfo>()


    suspend fun getInfo(id : String){
        val response = imagesApiInterface.getInfo(id)
        if(response.body() != null){
            infoLiveData.postValue(response.body())
        }
    }
}