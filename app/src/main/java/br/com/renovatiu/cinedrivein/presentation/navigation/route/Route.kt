package br.com.renovatiu.cinedrivein.presentation.navigation.route

sealed class Route(val name: String) {
    object ListReport : Route(name = "list_report")
    object CreateReport: Route(name = "create_report")
    object CreateSession: Route(name = "create_session")
}
