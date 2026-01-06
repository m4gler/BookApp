package com.example.bookexplorer.data.model

import com.google.gson.annotations.SerializedName

data class WorkDetailResponse(
    val key: String,
    val title: String,
    @SerializedName("covers") val coverIds: List<Int>?,
    @SerializedName("authors") val authorKeys: List<AuthorKey>?,
    @SerializedName("first_publish_date") val firstPublishDate: String?,
    @SerializedName("description") val description: Any?
)

data class AuthorKey(
    val key: String
)

