package com.example.vocabulary2.service

import com.example.vocabulary2.model.VocabularyModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface APIService {
    @GET("{word}")
    suspend fun getWord(@Path("word") word: CharSequence?): Response<List<VocabularyModel>>
}