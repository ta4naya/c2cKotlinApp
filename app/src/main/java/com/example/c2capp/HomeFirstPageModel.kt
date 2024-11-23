package com.example.c2capp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class HomeFirstPageViewModel : ViewModel() {
    val latestVideo = MutableLiveData<C2Cyoutube?>()
    val latestNews = MutableLiveData<LatestNews?>()
    val carousels = MutableLiveData<List<Carousel>>()
    val dailyVerses = MutableLiveData<List<DailyVerse>>()

    private val client = OkHttpClient()
    private val gson = Gson()

    init {
        fetchLatestVideo()
        fetchLatestNews()
        fetchCarousels()
        fetchDailyVerses()
    }

    fun fetchLatestVideo() {
        val url = "https://covertocoverbible.org/api/ApiC2Cyoutube/latest"
        fetchData(url, C2Cyoutube::class.java) { data ->
            latestVideo.postValue(data)
        }
    }

    fun fetchLatestNews() {
        val url = "https://covertocoverbible.org/api/ApiPostNews/latest"
        fetchData(url, LatestNews::class.java) { data ->
            latestNews.postValue(data)
        }
    }

    fun fetchCarousels() {
        val url = "https://covertocoverbible.org/api/ApiC2Cyoutube/carousels"
        fetchListData(url, object : TypeToken<List<Carousel>>() {}.type) { data ->
            carousels.postValue(data)
        }
    }

    fun fetchDailyVerses() {
        val url = "https://covertocoverbible.org/api/ApiC2Cyoutube/dailyverse"
        fetchListData(url, object : TypeToken<List<DailyVerse>>() {}.type) { data ->
            dailyVerses.postValue(data)
        }
    }

    private fun <T> fetchData(url: String, type: Class<T>, onSuccess: (T?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = Request.Builder().url(url).build()
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val body = response.body?.string()
                        val data = gson.fromJson(body, type)
                        withContext(Dispatchers.Main) {
                            onSuccess(data)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            onSuccess(null)
                        }
                        println("Error: ${response.code}")
                    }
                }
            } catch (e: Exception) {
                println("Error fetching data from $url: ${e.message}")
            }
        }
    }

    private fun <T> fetchListData(url: String, type: java.lang.reflect.Type, onSuccess: (List<T>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = Request.Builder().url(url).build()
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val body = response.body?.string()
                        val data = gson.fromJson<List<T>>(body, type)
                        withContext(Dispatchers.Main) {
                            onSuccess(data)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            onSuccess(emptyList())
                        }
                        println("Error: ${response.code}")
                    }
                }
            } catch (e: Exception) {
                println("Error fetching list data from $url: ${e.message}")
            }
        }
    }
}
