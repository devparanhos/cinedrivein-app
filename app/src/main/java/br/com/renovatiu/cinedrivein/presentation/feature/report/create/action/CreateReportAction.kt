package br.com.renovatiu.cinedrivein.presentation.feature.report.create.action

sealed class CreateReportAction {
    data class UpdateDate(val date: String) : CreateReportAction()
    data class CreateReport(val deleteSessions: Boolean) : CreateReportAction()
    data class DeleteSession(val id: Int?) : CreateReportAction()

    object UpdateHasSession : CreateReportAction()
    object UpdateRectifing : CreateReportAction()
    object GetSessions : CreateReportAction()
    object DeleteAllSessions : CreateReportAction()
    object OpenCloseDialog : CreateReportAction()
}
