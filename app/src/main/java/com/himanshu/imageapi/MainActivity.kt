package com.himanshu.imageapi

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private var isLoading: Boolean = true
    private lateinit var layoutManager : LinearLayoutManager
    lateinit var progressBar : ProgressBar
    private var timeoutHandler: Handler? = null
    private var interactionTimeoutRunnable: Runnable? = null

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


        timeoutHandler =  Handler()
        interactionTimeoutRunnable =  Runnable {
            Toast.makeText(this,"User is inactive since 5 seconds",Toast.LENGTH_SHORT).show()
        }
        startHandler()
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        stopHandler()
        startHandler()

    }

    private fun stopHandler() {
        timeoutHandler?.removeCallbacks(interactionTimeoutRunnable!!)
    }


    private fun startHandler() {
        timeoutHandler?.postDelayed(interactionTimeoutRunnable!!, 5000)
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

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val lastItem = layoutManager.findLastCompletelyVisibleItemPosition()
                val secondLastItem = layoutManager.findLastCompletelyVisibleItemPosition()-1
                Log.d("last", "onScrollStateChanged: $lastItem $secondLastItem")
            }
        })
    }



}