package com.himanshu.imageapi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himanshu.imageapi.models.Images
import com.himanshu.imageapi.models.ImagesItem
import com.himanshu.imageapi.repository.ImagesRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ImagesRepository) : ViewModel() {
    var currentPage = 1
    init {
        loadImages()
    }

    fun loadImages(){
        viewModelScope.launch {
            repository.getImages(currentPage)
        }
    }



    val images : LiveData<ArrayList<ImagesItem>>
    get() = repository.images


}