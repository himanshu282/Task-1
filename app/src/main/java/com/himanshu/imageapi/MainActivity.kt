package com.himanshu.imageapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.himanshu.imageapi.adapters.RecyclerViewAdapter
import com.himanshu.imageapi.api.ApiClient
import com.himanshu.imageapi.api.ImagesApiInterface
import com.himanshu.imageapi.repository.ImagesRepository
import com.himanshu.imageapi.viewmodel.MainViewModel
import com.himanshu.imageapi.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    private lateinit var newRecyclerView: RecyclerView
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private var isLoading: Boolean = true
    private lateinit var layoutManager : LinearLayoutManager
    lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progress_bar)
        val imagesApiInterface = ApiClient.retrofit.create(ImagesApiInterface::class.java)
        val repository = ImagesRepository(imagesApiInterface)
        initRecyclerView()
        mainViewModel = ViewModelProvider(this,MainViewModelFactory(repository)).get(MainViewModel::class.java)
        progressBar.visibility = View.VISIBLE
        mainViewModel.images.observe(this, Observer {
            recyclerViewAdapter.setDataItems(it)
            progressBar.visibility = View.GONE
            isLoading = true
        })

    }

    private fun initRecyclerView(){
        newRecyclerView= findViewById(R.id.recycler_view)
        layoutManager= LinearLayoutManager(this)
        newRecyclerView.layoutManager = layoutManager
        recyclerViewAdapter = RecyclerViewAdapter()
        newRecyclerView.adapter = recyclerViewAdapter
        newRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy>0){
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstCompletelyVisibleItemPosition()
                    if(isLoading){
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount){
                            isLoading = false
                            mainViewModel.currentPage = mainViewModel.currentPage + 1
                            progressBar.visibility = View.VISIBLE
                            mainViewModel.loadImages()
                        }
                    }
                }
            }
        })
    }



}