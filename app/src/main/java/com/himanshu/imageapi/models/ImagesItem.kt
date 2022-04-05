package com.himanshu.imageapi.models

data class ImagesItem(
    var author: String,
    val download_url: String,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)