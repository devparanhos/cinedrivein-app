package br.com.renovatiu.cinedrivein.presentation.navigation.manager

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.renovatiu.cinedrivein.domain.model.SessionDomain
import br.com.renovatiu.cinedrivein.presentation.feature.report.create.view.CreateReportScreen
import br.com.renovatiu.cinedrivein.presentation.feature.report.list.view.ListReportScreen
import br.com.renovatiu.cinedrivein.presentation.feature.session.create.view.CreateSessionScreen
import br.com.renovatiu.cinedrivein.presentation.navigation.route.Route
import com.google.gson.Gson

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
                navigateToCreateSession = { toUpdate, session ->
                    val sessionArgument = Gson().toJson(session)

                    navController.navigate(route = Route.CreateSession.name+"/$toUpdate/$sessionArgument") {
                        popUpTo(Route.CreateReport.name) {
                            inclusive = false
                        }
                    }
                }
            )
        }

        composable(
            route = Route.CreateSession.name+"/{toUpdate}/{session}",
            arguments = listOf(
                navArgument("toUpdate") {
                    type = NavType.BoolType
                },
                navArgument("session"){
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { navBackStackEntry ->

            val toUpdate = navBackStackEntry.arguments?.get("toUpdate") as Boolean
            val string = navBackStackEntry.arguments?.get("session") as String?

            CreateSessionScreen(
                toUpdate = toUpdate,
                session = Gson().fromJson(string, SessionDomain::class.java),
                onReturn = {
                    navController.popBackStack()
                }
            )
        }
    }
}