package com.example.bookexplorer.data.model

import com.google.gson.annotations.SerializedName

data class Book(
    val key: String,
    val title: String,
    @SerializedName("cover_id") val cover_id: Int?,
    val authors: List<Author>?,
    @SerializedName("first_publish_year") val first_publish_year: Int? = null,
    @SerializedName("number_of_pages_median") val number_of_pages_median: Int? = null
)

data class BookDetail(
    val key: String,
    val title: String,
    val cover_id: Int?,
    val authors: List<Author>?,
    val first_publish_year: Int?,
    val number_of_pages_median: Int?,
    val description: String?
)
