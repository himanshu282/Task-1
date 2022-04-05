package com.himanshu.imageapi.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.himanshu.imageapi.CallBack
import com.himanshu.imageapi.ImageDetailActivity
import com.himanshu.imageapi.R
import com.himanshu.imageapi.models.ImagesItem


class RecyclerViewAdapter(var callback:CallBack): RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    private var imagesItem: ArrayList<ImagesItem> = ArrayList<ImagesItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setDataItems(data: ArrayList<ImagesItem>) {
        imagesItem.addAll(data)
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView : View, var callback: CallBack) : RecyclerView.ViewHolder(itemView){
        val downloadUrl : ImageView = itemView.findViewById(R.id.demo_image)
        val author : TextView = itemView.findViewById(R.id.text_view)
        fun bind(imagesItem: ImagesItem){
            author.text = imagesItem.author
            downloadUrl.load(imagesItem.download_url)
            downloadUrl.setOnClickListener {
                callback.called(imagesItem.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.rows,parent,false)
        return MyViewHolder(inflater,callback)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(imagesItem.get(position))

    }

    override fun getItemCount() = imagesItem.size
    fun setText(secondLast: Int, last: Int) {
        var secondLastItem=imagesItem.get(secondLast)
        secondLastItem.author="Maine Change Kiya!!"
        var lastItem=imagesItem.get(last)
        lastItem.author="Maine Change Kiya!!"
        notifyItemChanged(secondLast)
        notifyItemChanged(last)

    }

}