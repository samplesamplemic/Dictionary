package com.example.vocabulary2.service

import com.example.vocabulary2.module.VocabularyModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {
    @GET("{word}")
    //json res is an array
    //suspend fun: at the center of coroutines || a function that can be paused and resumed at a later time
    //They can execute a long running operation and wait for it to complete without blocking.
    suspend fun getWord(@Path("word") word: CharSequence?): Response<List<VocabularyModel>>
}