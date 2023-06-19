package com.example.mybela

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mybela.screens.game.GameScreen
import com.example.mybela.screens.game.GameViewModel
import com.example.mybela.screens.game.RoundScreen
import com.example.mybela.screens.login.LoginScreen
import com.example.mybela.screens.main.MainScreen
import com.example.mybela.screens.register.RegisterScreen
import com.example.mybela.screens.stats.StatScreen
import com.example.mybela.screens.stats.StatsViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val gameViewModel : GameViewModel = viewModel()
    val statsViewModel : StatsViewModel = viewModel()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("register") {
            RegisterScreen(navController = navController)
        }
        composable(
            route = "main" + "/{name}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                }
            )) { entry ->
            MainScreen(name = entry.arguments?.getString("name"),
                navController = navController)
        }
        composable(
            route = "game" + "/{selectedPlayer1}" + "/{selectedPlayer2}" + "/{selectedPlayer3}" + "/{selectedPlayer4}" + "/{selectedLength}",
            arguments = listOf(
                navArgument("selectedPlayer1") {
                    type = NavType.StringType
                },
                navArgument("selectedPlayer2") {
                    type = NavType.StringType
                },
                navArgument("selectedPlayer3") {
                    type = NavType.StringType
                },
                navArgument("selectedPlayer4") {
                    type = NavType.StringType
                },
                navArgument("selectedLength") {
                    type = NavType.StringType
                }
            )) { entry ->
            GameScreen(
                selectedPlayer1 = entry.arguments?.getString("selectedPlayer1"),
                selectedPlayer2 = entry.arguments?.getString("selectedPlayer2"),
                selectedPlayer3 = entry.arguments?.getString("selectedPlayer3"),
                selectedPlayer4 = entry.arguments?.getString("selectedPlayer4"),
                selectedLength = entry.arguments?.getString("selectedLength"),
                navController = navController,
                gameViewModel = gameViewModel,
                statsViewModel = statsViewModel
            )
        }
        composable("stats") {
            StatScreen(navController = navController,
                statsViewModel = statsViewModel)
        }
        composable(
            route = "round" + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )) { entry ->
            RoundScreen(
                id = entry.arguments?.getInt("id"),
                navController = navController,
                gameViewModel = gameViewModel
            )
        }
    }
}