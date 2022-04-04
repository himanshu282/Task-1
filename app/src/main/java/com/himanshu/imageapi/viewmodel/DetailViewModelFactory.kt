package com.himanshu.imageapi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.himanshu.imageapi.repository.ImagesRepository

class DetailViewModelFactory(private val repository: ImagesRepository,var id: String?) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return DetailViewModel(repository,id!!) as T
    }
}