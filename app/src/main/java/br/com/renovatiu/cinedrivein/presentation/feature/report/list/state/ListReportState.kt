package br.com.renovatiu.cinedrivein.presentation.feature.report.list.state

import br.com.renovatiu.cinedrivein.core.functions.getSelectedDate
import br.com.renovatiu.cinedrivein.data.remote.model.request.ProtocolRequest
import java.util.Date

data class ListReportState(
    val protocols: List<ProtocolRequest>? = null,
    val selectedDate : String = getSelectedDate(month = Date().month, year = Date().year)
)
