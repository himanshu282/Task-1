package com.himanshu.imageapi

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.himanshu.imageapi.api.ApiClient
import com.himanshu.imageapi.api.ImagesApiInterface
import com.himanshu.imageapi.repository.ImagesRepository
import com.himanshu.imageapi.viewmodel.DetailViewModel
import com.himanshu.imageapi.viewmodel.DetailViewModelFactory


class ImageDetailActivity : AppCompatActivity() {
    lateinit var detailViewModel : DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)
        val imageID = intent.getStringExtra("id")

        val imagesDetailInterface = ApiClient.retrofit.create(ImagesApiInterface::class.java)
        val repository = ImagesRepository(imagesDetailInterface)
        detailViewModel = ViewModelProvider(this,
            DetailViewModelFactory(repository,imageID)
        ).get(DetailViewModel::class.java)

       detailViewModel.loadDetails(imageID!!)
        detailViewModel.detail.observe(this, Observer {
            Log.d("aya", "onCreate: $it")
        })


    }
}