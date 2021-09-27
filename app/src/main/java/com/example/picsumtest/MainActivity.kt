package com.example.picsumtest

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.picsumtest.R.id.nav_host
//import com.example.picsumtest.R.id.nav_host_fragment
import com.example.picsumtest.data.model.Picsum
import com.example.picsumtest.ui.Auth.AuthCompose
import com.example.picsumtest.ui.adapters.PicsumAdapter
import com.example.picsumtest.ui.main.PicsumList
import com.example.picsumtest.ui.main.PicsumListFragment
import com.example.picsumtest.ui.main.PicsumListViewModelFactory
import com.example.picsumtest.ui.main.PiscumListViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.ktor.client.request.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon:ImageVector) {
    @SuppressLint("ResourceType")
    object NavHostScreen : Screen("nav_host", nav_host,Icons.Filled.List)

    @SuppressLint("ResourceType")
    object LoginScren : Screen("auth_host", R.id.nav_auth,Icons.Filled.AccountCircle)
}


class MainActivity : AppCompatActivity() {
    /*lateinit var vLIst: LinearLayout
    val api = Api()
    private val picsumListViewModel by viewModels<PiscumListViewModel> {
        PicsumListViewModelFactory(this)
    }*/

    private val viewModel by lazy { ViewModelProvider(this).get(PiscumListViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val items = listOf(Screen.NavHostScreen, Screen.LoginScren)
        setContent {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = {
                    BottomNavigation {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        items.forEach { screen ->
                            BottomNavigationItem(
                                icon = { Icon(screen.icon, contentDescription = null) },
                                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                onClick = {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                })
            { innerPadding ->
                NavHost(
                    navController,
                    startDestination = Screen.NavHostScreen.route,
                    Modifier.padding(innerPadding)
                )
                {
                    composable(Screen.NavHostScreen.route) { PicsumList(viewModel =  viewModel) /*NavHostCompose()*/ }
                    composable(Screen.LoginScren.route) { AuthCompose() }
                }
            }


            /* val navView: BottomNavigationView = findViewById(R.id.nav_view)

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
             navView.setupWithNavController(navController)*/
        }


    }
}

@Composable
fun NavHostCompose() {
    Text(text = "Хост скрин")
}


