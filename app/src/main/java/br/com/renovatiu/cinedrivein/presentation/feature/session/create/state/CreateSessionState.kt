package br.com.renovatiu.cinedrivein.presentation.feature.session.create.state

import br.com.renovatiu.cinedrivein.core.enums.Modality
import br.com.renovatiu.cinedrivein.data.remote.model.request.DistributorRequest

data class CreateSessionState(
    val sessionHour: String = "",
    val modality: String = Modality.A.type,
    val cinemaCnpj: String = "32917296000104",
    val cinemaName: String = "Stark 'S Cinema e Lanchonete LTDA",
    val title: String = "",
    val movieCode: String = "",
    val isOriginalAudio: Boolean = true,
    val hasSubtitle: Boolean = true,
    val distributorCnpj: String = "",
    val distributorName: String = "",
    val totalTickets: String = "",
    val quantityTicketsFull: String = "",
    val revenueTicketsFull: String = "",
    val quantityTicketsHalf: String = "",
    val revenueTicketsHalf: String = "",
    val isSaving: Boolean = false,
    val saved: Boolean = false,
    val distributors: List<DistributorRequest> = listOf()
)
