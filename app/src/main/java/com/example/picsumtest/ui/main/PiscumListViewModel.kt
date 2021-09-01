package com.example.picsumtest.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.picsumtest.Api
import com.example.picsumtest.data.model.Picsum
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PiscumListViewModel : ViewModel() {
    private val api = Api()

   // private val _response = MutableLiveData<List<Picsum>?>()

    suspend fun loadPicsumPage(page: Int, limit: Int): List<Picsum> =
        api.loadPicsumList(page, limit)

    /*val picsum = MutableStateFlow<List<Picsum>?>(null)
    init {
        viewModelScope.launch {
            picsum.value= api.loadPicsumList(2)
        }
    }*/

    /*private fun getMarsRealEstateProperties() {
        viewModelScope.launch {
            try {
                val listResult = api.loadPicsumList(2)
                _response.value = listResult//"Success: ${listResult.size} Mars properties retrieved"
            } catch (e: Exception) {
                _response.value = null //"Failure: ${e.message}"
            }
        }
    }*/
}

class PicsumListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PiscumListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PiscumListViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}

