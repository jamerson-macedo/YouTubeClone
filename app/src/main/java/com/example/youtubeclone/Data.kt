package com.example.youtubeclone

data class Data(
    val duration: Int,
    val id: String,
    val publishedAt: String,
    val publisher: Publisher,
    val thumbnailUrl: String,
    val title: String,
    val videoUrl: String,
    val viewsCount: Int,
    val viewsCountLabel: String
)
data class Publisher(
    val id: String,
    val name: String,
    val pictureProfileUrl: String
)
data class ListVideo(val status:Int,val data:List<Data>)