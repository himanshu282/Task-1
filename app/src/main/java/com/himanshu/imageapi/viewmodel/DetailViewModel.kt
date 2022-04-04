package com.himanshu.imageapi.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himanshu.imageapi.ImageDetailActivity
import com.himanshu.imageapi.models.DetailedImageInfo
import com.himanshu.imageapi.repository.ImagesRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: ImagesRepository,var id: String) : ViewModel() {

    init {
        Log.d("id", "$id ")
//        loadDetails(id)
    }
    fun loadDetails(id: String){
        viewModelScope.launch {
            repository.getInfo(id)
        }
    }

    val detail : LiveData<DetailedImageInfo>
    get() = repository.infoLiveData
}