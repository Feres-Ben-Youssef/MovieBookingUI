package com.example.movieui.core.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.movieui.module.AppDatabase
import com.example.movieui.module.user.UserViewModel
import com.example.movieui.module.user.UserViewModelFactory
import com.example.movieui.module.account.presentation.AccountCreationScreen
import com.example.movieui.module.account.presentation.LoginScreen
import com.example.movieui.module.detail.presentation.DetailScreen
import com.example.movieui.module.home.model.nowPlayingMovie
import com.example.movieui.module.home.presentation.HomeScreen
import com.example.movieui.module.moviepass.MoviePassScreen
import com.example.movieui.module.seat_selector.presentation.SeatSelectorScreen
import java.time.LocalDate

object AppRoute {

    @Composable
    fun GenerateRoute(navController: NavHostController) {
        val context = LocalContext.current
        val userDao = AppDatabase.getDatabase(context).userDao()
        val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(userDao))

        NavHost(
            navController = navController,
            startDestination = AppRouteName.Login,
        ) {
            composable(
                route = "${AppRouteName.Home}/{username}",
                arguments = listOf(navArgument("username") { type = NavType.StringType })
            ) { backStackEntry ->
                val username = backStackEntry.arguments?.getString("username") ?: ""
                HomeScreen(navController = navController, username = username)
            }
            composable("${AppRouteName.Detail}/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")
                val movie = nowPlayingMovie.first { it.id == id }
                DetailScreen(navController = navController, movie)
            }
            composable(AppRouteName.SeatSelector) {
                SeatSelectorScreen(navController = navController)
            }
            composable(AppRouteName.AccountCreation) {
                AccountCreationScreen(navController = navController, userViewModel = userViewModel)
            }
            composable(AppRouteName.Login) {
                LoginScreen(navController = navController, userViewModel = userViewModel)
            }
            composable(
                "movie_pass_screen/{seatNumbers}/{selectedDate}/{selectedTime}",
                arguments = listOf(
                    navArgument("seatNumbers") { type = NavType.StringType },
                    navArgument("selectedDate") { type = NavType.StringType },
                    navArgument("selectedTime") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val seatNumbers = backStackEntry.arguments?.getString("seatNumbers")?.split(",") ?: emptyList()
                val selectedDate = backStackEntry.arguments?.getString("selectedDate")?.let { LocalDate.parse(it) }
                val selectedTime = backStackEntry.arguments?.getString("selectedTime")
                MoviePassScreen(
                    navController = navController,
                    seatNumbers = seatNumbers,
                    selectedDate = selectedDate,
                    selectedTime = selectedTime
                )
            }
        }
    }}
