package br.com.renovatiu.cinedrivein.presentation.feature.report.list.action

import br.com.renovatiu.cinedrivein.data.remote.model.request.ProtocolRequest

sealed class ListReportAction {
    data class SendReport(val protocol: ProtocolRequest?) : ListReportAction()
    data class ConsultReport(val protocolNumber: String?, val id: String?) : ListReportAction()
    data class GetReportsByDate(val date: String) : ListReportAction()
    data class DeleteReport(val id: String?) : ListReportAction()

    object GetAllProtocols : ListReportAction()
}
