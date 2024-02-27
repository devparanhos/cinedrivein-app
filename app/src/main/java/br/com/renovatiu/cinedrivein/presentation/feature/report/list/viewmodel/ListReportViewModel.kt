package br.com.renovatiu.cinedrivein.presentation.feature.report.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.renovatiu.cinedrivein.core.extensions.formatDateForQuery
import br.com.renovatiu.cinedrivein.data.remote.api.service.AncineAPI
import br.com.renovatiu.cinedrivein.data.remote.model.request.ProtocolRequest
import br.com.renovatiu.cinedrivein.domain.usecase.firestore.GetAllProtocolUseCase
import br.com.renovatiu.cinedrivein.domain.usecase.firestore.UpdateProtocolNumberUseCase
import br.com.renovatiu.cinedrivein.domain.usecase.firestore.UpdateProtocolStatusUseCase
import br.com.renovatiu.cinedrivein.presentation.feature.report.list.action.ListReportAction
import br.com.renovatiu.cinedrivein.presentation.feature.report.list.state.ListReportState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListReportViewModel(
    private val ancineAPI: AncineAPI,
    private val getAllProtocolUseCase: GetAllProtocolUseCase,
    private val updateProtocolNumberUseCase: UpdateProtocolNumberUseCase,
    private val updateProtocolStatusUseCase: UpdateProtocolStatusUseCase
): ViewModel() {

    private val _state = MutableStateFlow(ListReportState())
    val state = _state.asStateFlow()

    init {
        getAllProtocols()
    }

    fun submitAction(action: ListReportAction) {
        when(action) {
            is ListReportAction.GetAllProtocols -> {
                _state.update { it.copy(protocols = null) }
                getAllProtocols()
            }

            is ListReportAction.SendReport -> {
                action.protocol?.let {
                    sendReport(protocol = it)
                }
            }

            is ListReportAction.ConsultReport -> {
                getProtocolStatus(
                    protocolNumber = action.protocolNumber,
                    id = action.id
                )
            }

            is ListReportAction.GetReportsByDate -> {
                _state.update {
                    it.copy(
                        selectedDate = action.date,
                        protocols = null
                    )
                }

                getAllProtocols()
            }
        }
    }

    private fun getAllProtocols() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    protocols = getAllProtocolUseCase.getAll(
                        startDate = state.value.selectedDate.formatDateForQuery() + "-01",
                        endDate = state.value.selectedDate.formatDateForQuery() + "-31"
                    )
                )
            }
        }
    }

    private fun sendReport(protocol: ProtocolRequest) {
        viewModelScope.launch {
            protocol.report?.let {
                val result = ancineAPI.sendReport(report = it)
                if (result.isSuccessful) {
                    val protocolNumber = result.body()?.protocolNumber
                    val status = result.body()?.status
                    updateProtocolNumberUseCase.update(number = protocolNumber, id = protocol.id)
                    updateProtocolStatusUseCase.update(status = status, id = protocol.id)

                }
            }
        }
    }

    private fun getProtocolStatus(protocolNumber: String?, id: String?){
        viewModelScope.launch {
            val result = ancineAPI.getReportResultById(protocolNumber)
            if (result.isSuccessful){
                val status = result.body()?.status
                updateProtocolStatusUseCase.update(status = status, id = id)
            }
        }
    }
}