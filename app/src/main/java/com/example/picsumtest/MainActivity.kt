package com.example.picsumtest

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.picsumtest.ui.Auth.AuthCompose
import com.example.picsumtest.ui.main.PicsumList
import com.example.picsumtest.ui.main.PiscumListViewModel


sealed class Screen(val route: String,  val icon: ImageVector) {
    @SuppressLint("ResourceType")
    object NavHostScreen : Screen("nav_host", Icons.Filled.List)

    @SuppressLint("ResourceType")
    object LoginScren : Screen("auth_host", Icons.Filled.AccountCircle)
}


class MainActivity : AppCompatActivity() {
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
                    composable(Screen.NavHostScreen.route) { PicsumList(viewModel = viewModel) /*NavHostCompose()*/ }
                    composable(Screen.LoginScren.route) { AuthCompose() }
                }
            }

        }
    }
}

@Composable
fun NavHostCompose() {
    Text(text = "Хост скрин")
}


