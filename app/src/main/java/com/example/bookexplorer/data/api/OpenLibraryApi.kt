package com.example.bookexplorer.data.api

import com.example.bookexplorer.data.model.BookResponse
import com.example.bookexplorer.data.model.WorkDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenLibraryApi {

    @GET("subjects/fiction.json")
    suspend fun getBooks(
        @Query("limit") limit: Int = 20
    ): BookResponse

    @GET("works/{workId}.json")
    suspend fun getWorkDetails(
        @Path("workId") workId: String
    ): WorkDetailResponse
}
