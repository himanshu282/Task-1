package com.himanshu.imageapi

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.himanshu.imageapi.api.ApiClient
import com.himanshu.imageapi.api.ImagesApiInterface
import com.himanshu.imageapi.repository.ImagesRepository
import com.himanshu.imageapi.viewmodel.DetailViewModel
import com.himanshu.imageapi.viewmodel.DetailViewModelFactory


class ImageDetailActivity : AppCompatActivity() {
    private lateinit var detailViewModel : DetailViewModel
    private lateinit var imageView : ImageView
    private lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)
        val imageID = intent.getStringExtra("id")
        imageView = findViewById(R.id.image_view)
        textView = findViewById(R.id.text_view)
        val imagesDetailInterface = ApiClient.retrofit.create(ImagesApiInterface::class.java)
        val repository = ImagesRepository(imagesDetailInterface)
        detailViewModel = ViewModelProvider(this,
            DetailViewModelFactory(repository,imageID)
        ).get(DetailViewModel::class.java)

       detailViewModel.loadDetails(imageID!!)
        detailViewModel.detail.observe(this, Observer {
            imageView.load(it.download_url)
            textView.text = it.author

        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
}