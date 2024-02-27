package br.com.renovatiu.cinedrivein.presentation.navigation.manager

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.renovatiu.cinedrivein.presentation.feature.report.create.view.CreateReportScreen
import br.com.renovatiu.cinedrivein.presentation.feature.report.list.view.ListReportScreen
import br.com.renovatiu.cinedrivein.presentation.feature.session.create.view.CreateSessionScreen
import br.com.renovatiu.cinedrivein.presentation.navigation.route.Route

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.ListReport.name) {
        composable(route = Route.ListReport.name){
            ListReportScreen {
                navController.navigate(route = Route.CreateReport.name)
            }
        }

        composable(route = Route.CreateReport.name) {
            CreateReportScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                navigateToCreateSession = {
                    navController.navigate(route = Route.CreateSession.name) {
                        popUpTo(Route.CreateReport.name) {
                            inclusive = false
                        }
                    }
                }
            )
        }

        composable(route = Route.CreateSession.name) {
            CreateSessionScreen {
                navController.popBackStack()
            }
        }
    }
}