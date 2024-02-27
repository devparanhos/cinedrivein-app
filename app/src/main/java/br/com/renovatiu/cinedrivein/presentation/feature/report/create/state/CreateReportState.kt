package br.com.renovatiu.cinedrivein.presentation.feature.report.create.state

import br.com.renovatiu.cinedrivein.domain.model.SessionDomain

data class CreateReportState(
    val sessions: List<SessionDomain>? = null,
    val date: String = "",
    val hasSession: Boolean = true,
    val isRectifing: Boolean = false,
    val saved: Boolean = false
)
