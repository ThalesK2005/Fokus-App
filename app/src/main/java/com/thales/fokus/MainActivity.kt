package com.thales.fokus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.thales.fokus.ui.screens.AddTaskScreen
import com.thales.fokus.ui.screens.CalendarScreen
import com.thales.fokus.ui.screens.HomeScreen
import com.thales.fokus.ui.screens.LoginScreen
import com.thales.fokus.ui.screens.RegisterScreen
import com.thales.fokus.ui.screens.SplashScreen
import com.thales.fokus.ui.theme.FokusTheme
import com.thales.fokus.ui.viewmodel.AuthViewModel
import com.thales.fokus.ui.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FokusTheme {
                val navController = rememberNavController()
                val taskViewModel: TaskViewModel = viewModel()
                val authViewModel: AuthViewModel = viewModel()


                NavHost(navController = navController, startDestination = "splash") {


                    composable("splash") {
                        SplashScreen(
                            onNavigateToNext = { route ->

                                if (route == "home") taskViewModel.loadTasks()

                                navController.navigate(route) {
                                    popUpTo("splash") { inclusive = true }
                                }
                            }
                        )
                    }


                    composable("login") {
                        LoginScreen(
                            viewModel = authViewModel,
                            onLoginSuccess = {
                                taskViewModel.loadTasks()
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            },
                            onNavigateToRegister = {
                                navController.navigate("register")
                            }
                        )
                    }


                    composable("register") {
                        RegisterScreen(
                            viewModel = authViewModel,
                            onRegisterSuccess = {
                                taskViewModel.loadTasks()
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            },
                            onBack = {
                                navController.popBackStack()
                            }
                        )
                    }


                    composable("home") {
                        HomeScreen(
                            viewModel = taskViewModel,
                            onNavigateToAdd = { navController.navigate("add_task/-1") },
                            onNavigateToCalendar = { navController.navigate("calendar") },
                            onLogout = {
                                authViewModel.logout()
                                taskViewModel.loadTasks()
                                navController.navigate("login") {
                                    popUpTo("home") { inclusive = true }
                                }
                            },
                            onTaskClick = { task ->
                                navController.navigate("add_task/${task.id}")
                            }
                        )
                    }


                    composable(
                        route = "add_task/{taskId}",
                        arguments = listOf(navArgument("taskId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val taskId = backStackEntry.arguments?.getInt("taskId") ?: -1
                        AddTaskScreen(
                            viewModel = taskViewModel,
                            taskId = taskId,
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }


                    composable("calendar") {
                        CalendarScreen(
                            viewModel = taskViewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}