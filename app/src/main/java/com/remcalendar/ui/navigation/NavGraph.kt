package com.remcalendar.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.remcalendar.ui.anniversary.AnniversaryEditScreen
import com.remcalendar.ui.anniversary.AnniversaryListScreen
import com.remcalendar.ui.calendar.CalendarScreen
import com.remcalendar.ui.event.EventEditScreen
import com.remcalendar.ui.event.EventListScreen
import com.remcalendar.ui.search.SearchScreen
import com.remcalendar.ui.settings.SettingsScreen
import com.remcalendar.ui.theme.BackgroundPickerScreen

sealed class Screen(val route: String) {
    object Calendar : Screen("calendar")
    object Events : Screen("events")
    object EventEdit : Screen("event_edit/{eventId}") {
        fun createRoute(eventId: Long?) = "event_edit/${eventId ?: -1}"
    }
    object Anniversaries : Screen("anniversaries")
    object AnniversaryEdit : Screen("anniversary_edit/{anniversaryId}") {
        fun createRoute(anniversaryId: Long?) = "anniversary_edit/${anniversaryId ?: -1}"
    }
    object Search : Screen("search")
    object Settings : Screen("settings")
    object BackgroundPicker : Screen("background_picker")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Calendar.route,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Calendar.route) {
            CalendarScreen(
                onAddEventClick = {
                    navController.navigate(Screen.EventEdit.createRoute(null))
                },
                onEventClick = { event ->
                    navController.navigate(Screen.EventEdit.createRoute(event.id))
                }
            )
        }

        composable(Screen.Events.route) {
            EventListScreen(
                onAddEvent = {
                    navController.navigate(Screen.EventEdit.createRoute(null))
                },
                onEditEvent = { eventId ->
                    navController.navigate(Screen.EventEdit.createRoute(eventId))
                }
            )
        }

        composable(
            route = Screen.EventEdit.route,
            arguments = listOf(navArgument("eventId") { type = NavType.LongType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getLong("eventId")
            EventEditScreen(
                eventId = if (eventId == -1L) null else eventId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Anniversaries.route) {
            AnniversaryListScreen(
                onAddAnniversary = {
                    navController.navigate(Screen.AnniversaryEdit.createRoute(null))
                },
                onEditAnniversary = { anniversaryId ->
                    navController.navigate(Screen.AnniversaryEdit.createRoute(anniversaryId))
                }
            )
        }

        composable(
            route = Screen.AnniversaryEdit.route,
            arguments = listOf(navArgument("anniversaryId") { type = NavType.LongType })
        ) { backStackEntry ->
            val anniversaryId = backStackEntry.arguments?.getLong("anniversaryId")
            AnniversaryEditScreen(
                anniversaryId = if (anniversaryId == -1L) null else anniversaryId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Search.route) {
            SearchScreen(
                onNavigateBack = { navController.popBackStack() },
                onEventClick = { eventId ->
                    navController.navigate(Screen.EventEdit.createRoute(eventId))
                },
                onAnniversaryClick = { anniversaryId ->
                    navController.navigate(Screen.AnniversaryEdit.createRoute(anniversaryId))
                }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() },
                onBackgroundClick = {
                    navController.navigate(Screen.BackgroundPicker.route)
                }
            )
        }

        composable(Screen.BackgroundPicker.route) {
            BackgroundPickerScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
