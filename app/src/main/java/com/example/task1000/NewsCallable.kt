package com.example.task1000


import retrofit2.Call
import retrofit2.http.GET

interface NewsCallable {

    @GET ("/v2/top-headlines?country=us&category=general&apiKey=c2a62d9a8802410cb8dd35fe3585af28")
    fun getNews(): Call<News>
}