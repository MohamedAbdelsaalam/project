package com.example.task1000

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.task1000.databinding.ActivityMain2Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit as Retrofit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
       loadNews()
    }

    private fun loadNews() {
        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val c = retrofit.create(NewsCallable::class.java)
        c.getNews().enqueue(object : Callback<News> {
            override fun onResponse(p0: Call<News>, p1: Response<News>) {
                val articles = p1.body()?.articles?: arrayListOf()
                binding.newsList.adapter = NewsAdapter(this@MainActivity, articles)
                binding.progress.isVisible = false
               // Log.d("trace", "${articles?.get(0)?.urlToImage}")
            }

            override fun onFailure(p0: Call<News>, p1: Throwable) {
                Log.d("trace", "Error: ${p1.message}")
                binding.progress.isVisible = false
            }
        })
    }
}
