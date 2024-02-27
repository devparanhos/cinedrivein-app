package br.com.renovatiu.cinedrivein.presentation.feature.session.create.action

import br.com.renovatiu.cinedrivein.data.remote.model.request.DistributorRequest

sealed class CreateSessionAction {
    data class UpdateSessionHour(val hour: String) : CreateSessionAction()
    data class UpdateMovieCode(val code: String) : CreateSessionAction()
    data class UpdateTitle(val title: String) : CreateSessionAction()
    data class UpdateDistributor(val distributor: DistributorRequest) : CreateSessionAction()
    data class UpdateTotalTickets(val tickets: String) : CreateSessionAction()
    data class UpdateTotalFullTickets(val tickets: String) : CreateSessionAction()
    data class UpdateRevenueFullTickets(val value: String) : CreateSessionAction()
    data class UpdateTotalHalfTickets(val tickets: String) : CreateSessionAction()
    data class UpdateRevenueHalfTickets(val value: String) : CreateSessionAction()

    object UpdateAudio: CreateSessionAction()
    object UpdateSubtitle: CreateSessionAction()
    object CreateSession: CreateSessionAction()
}