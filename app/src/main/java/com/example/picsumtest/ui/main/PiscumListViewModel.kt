package com.example.picsumtest.ui.main

import androidx.lifecycle.ViewModel
import com.example.picsumtest.Api
import com.example.picsumtest.data.model.Picsum

class PiscumListViewModel : ViewModel() {
    private val api = Api()

    suspend fun loadPicsumPage(page: Int, limit: Int): List<Picsum> =
        api.loadPicsumList(page, limit)
}

