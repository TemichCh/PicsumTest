package com.example.picsumtest.data.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherInfo(
    val weather: Array<Weather>,
    val base:String,
    val main:Main,
    val visibility: Int,
    val id:Int,
    val name:String,
) {

    @Serializable
    class Weather(
        val id:Int,
        val main:String,
        val description:String,
        val icon:String
    )
    @Serializable
    class Main(
        val temp :Float,
        val feels_like:Float,
        val temp_min:Float,
        val temp_max:Float,
        val pressure:Int,
        val humidity:Int
    )

}

