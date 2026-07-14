package com.remcalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.remcalendar.ui.components.GlassBottomNav
import com.remcalendar.ui.navigation.NavGraph
import com.remcalendar.ui.navigation.Screen
import com.remcalendar.ui.theme.BackgroundLayer
import com.remcalendar.ui.theme.RemCalendarTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RemCalendarTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavItems = listOf(
        "日历" to Icons.Default.CalendarMonth,
        "事件" to Icons.Default.Event,
        "纪念日" to Icons.Default.Star,
        "搜索" to Icons.Default.Search,
        "设置" to Icons.Default.Settings
    )

    val bottomNavRoutes = listOf(
        Screen.Calendar.route,
        Screen.Events.route,
        Screen.Anniversaries.route,
        Screen.Search.route,
        Screen.Settings.route
    )

    val showBottomBar = currentRoute in bottomNavRoutes
    val selectedIndex = bottomNavRoutes.indexOf(currentRoute).takeIf { it >= 0 } ?: 0

    Box(modifier = Modifier.fillMaxSize()) {
        // 背景层
        BackgroundLayer()

        // 主内容
        Scaffold(
            containerColor = Color.Transparent,
            contentColor = Color.Transparent,
            bottomBar = {
                if (showBottomBar) {
                    GlassBottomNav(
                        items = bottomNavItems,
                        selectedIndex = selectedIndex,
                        onItemSelected = { index ->
                            val route = bottomNavRoutes[index]
                            if (currentRoute != route) {
                                navController.navigate(route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        ) { innerPadding ->
            NavGraph(
                navController = navController,
                startDestination = Screen.Calendar.route,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
