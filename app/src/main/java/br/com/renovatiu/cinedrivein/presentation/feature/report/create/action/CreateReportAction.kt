package br.com.renovatiu.cinedrivein.presentation.feature.report.create.action

sealed class CreateReportAction {
    data class UpdateDate(val date: String) : CreateReportAction()

    object UpdateHasSession : CreateReportAction()
    object UpdateRectifing : CreateReportAction()
    object CreateReport : CreateReportAction()
}
