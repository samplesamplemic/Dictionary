package com.example.vocabulary2.network;

import com.example.vocabulary2.model.VocabularyModel
import com.example.vocabulary2.service.APIService
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

 class WordRetriever {

    private var baseURL = "https://api.dictionaryapi.dev/api/v2/entries/en/"
    private var apiService: APIService = TODO();

     init {
         val retrofit  = Retrofit.Builder()
             .baseUrl(baseURL)
             .addConverterFactory(GsonConverterFactory.create())
             .build()

         apiService = retrofit.create(APIService::class.java)

     }
    suspend fun getData(word: CharSequence?): Response<List<VocabularyModel>> {
        return apiService.getWord(word);
    }
 }
