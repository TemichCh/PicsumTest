package com.example.picsumtest

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHost
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
//import com.example.picsumtest.R.id.nav_host_fragment
import com.example.picsumtest.data.model.Picsum
import com.example.picsumtest.ui.adapters.PicsumAdapter
import com.example.picsumtest.ui.main.PicsumListFragment
import com.example.picsumtest.ui.main.PicsumListViewModelFactory
import com.example.picsumtest.ui.main.PiscumListViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.ktor.client.request.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : AppCompatActivity() {
    lateinit var vLIst: LinearLayout
    val api = Api()
    private val picsumListViewModel by viewModels<PiscumListViewModel> {
        PicsumListViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val picsumAdapter = PicsumAdapter { picsum ->
            run { print("test") }
        }
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = picsumAdapter

        lifecycleScope.launch {
            picsumListViewModel.loadPicsumPage(1, 20)
        }*/

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, PicsumListFragment.newInstance())
                .commitNow()
        }

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_host, R.id.nav_auth))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


}

/*
fun showLinearLayout(items: List<Picsum>?) {
    val inflater = layoutInflater
    if (items != null) {
        for (item in items) {
            val view = inflater.inflate(R.layout.picsum_item, vLIst)
            val pict = view.findViewById<ImageView>(R.id.item_pict)
            val auth = view.findViewById<TextView>(R.id.item_author)
            auth.text = item.author
            pict.setImageURI(item.url.toUri())
            vLIst.addView(view)
        }
    }

}


}*/