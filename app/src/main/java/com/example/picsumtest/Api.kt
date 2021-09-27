package com.example.picsumtest

import com.example.picsumtest.data.model.Picsum
import com.example.picsumtest.data.model.WeatherInfo
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*

class Api {
    val json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
    val apiClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }
    }

     suspend fun loadPicsumList(page: Int, limit: Int = 100) =
        apiClient.get<List<Picsum>>("https://picsum.photos/v2/list?page=$page&limit=$limit")

    suspend fun loadWeather(cityId: Int = 498817) =
        apiClient.get<WeatherInfo>("https://api.openweathermap.org/data/2.5/weather?appid=c35880b49ff95391b3a6d0edd0c722eb&id=$cityId&lang=ru&units=metric")


}