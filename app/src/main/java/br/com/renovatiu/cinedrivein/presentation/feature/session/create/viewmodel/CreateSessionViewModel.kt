package br.com.renovatiu.cinedrivein.presentation.feature.session.create.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.renovatiu.cinedrivein.core.extensions.convertToAudioText
import br.com.renovatiu.cinedrivein.core.extensions.convertToYesNo
import br.com.renovatiu.cinedrivein.core.handler.HandlerRequest
import br.com.renovatiu.cinedrivein.domain.model.SessionDomain
import br.com.renovatiu.cinedrivein.domain.usecase.firestore.GetAllDistributorsUseCase
import br.com.renovatiu.cinedrivein.domain.usecase.session.CreateSessionUseCase
import br.com.renovatiu.cinedrivein.domain.usecase.session.DeleteSessionUseCase
import br.com.renovatiu.cinedrivein.presentation.feature.session.create.action.CreateSessionAction
import br.com.renovatiu.cinedrivein.presentation.feature.session.create.state.CreateSessionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateSessionViewModel(
    private val createSessionUseCase: CreateSessionUseCase,
    private val getAllDistributorsUseCase: GetAllDistributorsUseCase,
    private val deleteSessionUseCase: DeleteSessionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CreateSessionState())
    val state = _state.asStateFlow()

    init {

        viewModelScope.launch {
            _state.update { it.copy(distributors = getAllDistributorsUseCase.getAll()) }
        }
    }

    fun submitAction(action: CreateSessionAction) {
        when(action) {
            is CreateSessionAction.UpdateSessionHour -> {
                _state.update { it.copy(sessionHour = action.hour) }
            }

            is CreateSessionAction.UpdateMovieCode -> {
                _state.update { it.copy(movieCode = action.code) }
            }

            is CreateSessionAction.UpdateTitle -> {
                _state.update { it.copy(title = action.title) }
            }

            is CreateSessionAction.UpdateAudio -> {
                _state.update { it.copy(isOriginalAudio = !_state.value.isOriginalAudio) }
            }

            is CreateSessionAction.UpdateSubtitle -> {
                _state.update { it.copy(hasSubtitle = !_state.value.hasSubtitle) }
            }

            is CreateSessionAction.UpdateDistributor -> {
                _state.update {
                    it.copy(
                        distributorName = action.distributor.distributorName.toString(),
                        distributorCnpj = action.distributor.cnpj.toString()
                    )
                }
            }

            is CreateSessionAction.UpdateTotalTickets -> {
                _state.update { it.copy(totalTickets = action.tickets) }
            }

            is CreateSessionAction.UpdateTotalFullTickets -> {
                _state.update { it.copy(quantityTicketsFull = action.tickets) }
            }

            is CreateSessionAction.UpdateRevenueFullTickets -> {
                _state.update { it.copy(revenueTicketsFull = action.value) }
            }

            is CreateSessionAction.UpdateTotalHalfTickets -> {
                _state.update { it.copy(quantityTicketsHalf = action.tickets) }
            }

            is CreateSessionAction.UpdateRevenueHalfTickets -> {
                _state.update { it.copy(revenueTicketsHalf = action.value) }
            }

            is CreateSessionAction.CreateSession -> {
                createSession()
            }

            is CreateSessionAction.UpdateSession -> {
                viewModelScope.launch {
                    action.id?.let {
                        deleteSessionUseCase.delete(it)
                        createSession()
                    }
                }
            }
        }
    }

    private fun createSession() {
        _state.update { it.copy(isSaving = true) }
        viewModelScope.launch {
            val session = SessionDomain(
                time = state.value.sessionHour,
                modality = state.value.modality,
                cinemaCnpj = state.value.cinemaCnpj,
                cinemaName = state.value.cinemaName,
                movieCode = state.value.movieCode,
                movieTitle = state.value.title,
                screenType = "P",
                isDigital = "S",
                typeProjection = "2",
                audio = state.value.isOriginalAudio.convertToAudioText(),
                hasSubtitle = state.value.hasSubtitle.convertToYesNo(),
                hasSign = "N",
                captionDescriptive = "N",
                audioDescription = "N",
                distributorCnpj = state.value.distributorCnpj,
                distributorName = state.value.distributorName,
                totalSeats = state.value.totalTickets.toInt(),
                totalFullQuantity = state.value.quantityTicketsFull.toInt(),
                totalFullSold = state.value.revenueTicketsFull.toFloat(),
                totalHalfQuantity = state.value.quantityTicketsHalf.toInt(),
                totalHalfSold = state.value.revenueTicketsHalf.toFloat()
            )
            delay(2000)
            when(val result = createSessionUseCase.create(session = session)){
                is HandlerRequest.Success -> {
                    _state.update {
                        it.copy(
                            isSaving = false,
                            saved = true
                        )
                    }
                }

                is HandlerRequest.Error -> {
                    _state.update {
                        it.copy(
                            isSaving = false
                        )
                    }
                }
            }
        }
    }

    fun updateState(session: SessionDomain) {
        _state.update {
            it.copy(
                sessionHour = session.time,
                title = session.movieTitle,
                movieCode = session.movieCode,
                isOriginalAudio = session.audio == "O",
                hasSubtitle = session.hasSubtitle == "S",
                distributorName = session.distributorName,
                distributorCnpj = session.distributorCnpj,
                totalTickets = session.totalSeats.toString(),
                quantityTicketsFull = session.totalFullQuantity.toString(),
                quantityTicketsHalf = session.totalHalfQuantity.toString(),
                revenueTicketsHalf = session.totalHalfSold.toString(),
                revenueTicketsFull = session.totalFullSold.toString()
            )
        }

    }
}