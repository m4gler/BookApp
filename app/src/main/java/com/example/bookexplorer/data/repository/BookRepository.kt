package com.example.bookexplorer.data.repository

import com.example.bookexplorer.data.api.OpenLibraryApi
import com.example.bookexplorer.data.model.Book
import com.example.bookexplorer.data.model.BookDetail

class BookRepository(
    private val api: OpenLibraryApi
) {

    suspend fun getBooks(): List<Book> {
        val response = api.getBooks()
        return response.works
    }

    suspend fun getBookDetails(workId: String): BookDetail? {
        try {
            var cleanWorkId = workId
            if (workId.startsWith("/works/")) {
                cleanWorkId = workId.removePrefix("/works/")
            }
            if (workId.startsWith("works/")) {
                cleanWorkId = workId.removePrefix("works/")
            }
            
            val response = api.getWorkDetails(cleanWorkId)

            var coverId: Int? = null
            if (response.coverIds != null && response.coverIds.isNotEmpty()) {
                coverId = response.coverIds[0]
            }
            var description: String? = null
            if (response.description != null) {
                if (response.description is String) {
                    description = response.description as String
                }
            }

            var publishYear: Int? = null
            if (response.firstPublishDate != null) {
                val yearString = response.firstPublishDate.substringBefore("-")
                try {
                    publishYear = yearString.toInt()
                } catch (e: Exception) {
                    publishYear = null
                }
            }
            
            return BookDetail(
                key = response.key,
                title = response.title,
                cover_id = coverId,
                authors = null,
                first_publish_year = publishYear,
                number_of_pages_median = null,
                description = description
            )
        } catch (e: Exception) {
            return null
        }
    }
}
