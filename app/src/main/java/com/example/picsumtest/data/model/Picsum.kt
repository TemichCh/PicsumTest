package com.example.picsumtest.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Picsum(
        val id: Int,
        val author: String,
        val width: Int,
        val height: Int,
        val url: String,
        val download_url: String
        )
