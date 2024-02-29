package br.com.renovatiu.cinedrivein.presentation.feature.report.create.viewmodel

import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.renovatiu.cinedrivein.core.enums.Modality
import br.com.renovatiu.cinedrivein.core.extensions.convertToYesNo
import br.com.renovatiu.cinedrivein.core.extensions.toFormattedDate
import br.com.renovatiu.cinedrivein.core.functions.generateUniqueId
import br.com.renovatiu.cinedrivein.data.remote.model.request.CinemaRequest
import br.com.renovatiu.cinedrivein.data.remote.model.request.DistributorRequest
import br.com.renovatiu.cinedrivein.data.remote.model.request.MovieRequest
import br.com.renovatiu.cinedrivein.data.remote.model.request.ProtocolRequest
import br.com.renovatiu.cinedrivein.data.remote.model.request.ReportRequest
import br.com.renovatiu.cinedrivein.data.remote.model.request.RevenueRequest
import br.com.renovatiu.cinedrivein.data.remote.model.request.Seat
import br.com.renovatiu.cinedrivein.data.remote.model.request.SessionRequest
import br.com.renovatiu.cinedrivein.data.remote.model.request.TicketsSold
import br.com.renovatiu.cinedrivein.domain.usecase.firestore.CreateProtocolUseCase
import br.com.renovatiu.cinedrivein.domain.usecase.session.DeleteAllSessionUseCase
import br.com.renovatiu.cinedrivein.domain.usecase.session.DeleteSessionUseCase
import br.com.renovatiu.cinedrivein.domain.usecase.session.GetAllSessionsUseCase
import br.com.renovatiu.cinedrivein.presentation.feature.report.create.action.CreateReportAction
import br.com.renovatiu.cinedrivein.presentation.feature.report.create.state.CreateReportState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CreateReportViewModel(
    private val getAllSessionsUseCase: GetAllSessionsUseCase,
    private val createProtocolUseCase: CreateProtocolUseCase,
    private val deleteAllSessionUseCase: DeleteAllSessionUseCase,
    private val deleteSessionUseCase: DeleteSessionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CreateReportState())
    val state = _state.asStateFlow()

    fun submitAction(action: CreateReportAction) {
        when(action) {
            is CreateReportAction.UpdateDate -> {
                _state.update { it.copy(date = action.date) }
            }

            is CreateReportAction.UpdateRectifing -> {
                _state.update { it.copy(isRectifing = !_state.value.isRectifing) }
            }

            is CreateReportAction.UpdateHasSession -> {
                _state.update { it.copy(hasSession = !_state.value.hasSession) }
            }

            is CreateReportAction.CreateReport -> {
                createReport(deleteSessions = action.deleteSessions)
            }

            is CreateReportAction.GetSessions -> {
                getSessions()
            }

            is CreateReportAction.DeleteAllSessions -> {
                deleteAllSessions()
            }

            is CreateReportAction.OpenCloseDialog -> {
                _state.update { it.copy(showDialog = !_state.value.showDialog) }
            }

            is CreateReportAction.DeleteSession -> {
                deleteSession(id = action.id)
            }
        }
    }

    private fun getSessions()  {
        _state.update { it.copy(sessions = null) }

        viewModelScope.launch {
            _state.update { it.copy(sessions = getAllSessionsUseCase.getAll()) }
        }
    }

    private fun createReport(deleteSessions: Boolean) {
        _state.update { it.copy(requesting = true, showDialog = false) }
        val report = ReportRequest(
            ancineExhibitorCode = "5465",
            ancineRoomCode = "5002102",
            date = state.value.date.toFormattedDate(),
            hasSession = state.value.hasSession.convertToYesNo(),
            rectifing = state.value.isRectifing.convertToYesNo(),
            sessions = buildSessionRequest()
        )

        val protocol = ProtocolRequest(
            id = generateUniqueId(),
            protocol = null,
            status = "X",
            ancineExhibitorCode = "5465",
            ancineRoomCode = "5002102",
            date = state.value.date.toFormattedDate(),
            report = report
        )

        viewModelScope.launch {
            when(createProtocolUseCase.create(protocol = protocol)){
                true -> {
                    if (deleteSessions) {
                        deleteAllSessionUseCase.deleteAll()
                    }
                    delay(3000)
                    _state.update { it.copy(saved = true, requesting = false) }
                }

                false -> {
                    _state.update { it.copy( requesting = false) }
                }
            }
        }
    }

    private fun buildSessionRequest() : List<SessionRequest>{
        val sessionsRequest = mutableListOf<SessionRequest>()

        state.value.sessions?.let { sessions ->
            sessions.forEach { session ->
                sessionsRequest.add(
                    SessionRequest(
                        time = "${state.value.date.toFormattedDate()} ${session.time}:00",
                        modality = Modality.A.name,
                        cinema = CinemaRequest(
                            cnpj = session.cinemaCnpj,
                            cinemaName = session.cinemaName
                        ),
                        movies = listOf(
                            MovieRequest(
                                number = session.movieCode,
                                title = session.movieTitle,
                                typeScreen = session.screenType,
                                digital = session.isDigital,
                                typeProjection = session.typeProjection,
                                audio = session.audio,
                                subtitle = session.hasSubtitle,
                                signLanguage = session.hasSign,
                                captionDescriptive = session.captionDescriptive,
                                audioDescription = session.audioDescription,
                                distributor = DistributorRequest(
                                    cnpj = session.distributorCnpj,
                                    distributorName = session.distributorName
                                )
                            )
                        ),
                        seats = listOf(
                            Seat(
                                seatType = "P",
                                quantitySeats = session.totalSeats,
                                ticketsSold = listOf(
                                    TicketsSold(
                                        ticketType = "01",
                                        quantitySold = session.totalFullQuantity,
                                        revenue = listOf(
                                            RevenueRequest(
                                                paymentType = 1,
                                                totalRecieved = session.totalFullSold
                                            ),
                                            RevenueRequest(
                                                paymentType = 2,
                                                totalRecieved = 0.00f
                                            ),
                                            RevenueRequest(
                                                paymentType = 3,
                                                totalRecieved = 0.00f
                                            )
                                        )
                                    ),
                                    TicketsSold(
                                        ticketType = "02",
                                        quantitySold = session.totalHalfQuantity,
                                        revenue = listOf(
                                            RevenueRequest(
                                                paymentType = 1,
                                                totalRecieved = session.totalHalfSold
                                            ),
                                            RevenueRequest(
                                                paymentType = 2,
                                                totalRecieved = 0.00f
                                            ),
                                            RevenueRequest(
                                                paymentType = 3,
                                                totalRecieved = 0.00f
                                            )
                                        )
                                    ),
                                    TicketsSold(
                                        ticketType = "03",
                                        quantitySold = 0,
                                        revenue = listOf(
                                            RevenueRequest(
                                                paymentType = 1,
                                                totalRecieved = 0.00f
                                            ),
                                            RevenueRequest(
                                                paymentType = 2,
                                                totalRecieved = 0.00f
                                            ),
                                            RevenueRequest(
                                                paymentType = 3,
                                                totalRecieved = 0.00f
                                            )
                                        )
                                    ),
                                    TicketsSold(
                                        ticketType = "04",
                                        quantitySold = 0,
                                        revenue = listOf(
                                            RevenueRequest(
                                                paymentType = 1,
                                                totalRecieved = 0.00f
                                            ),
                                            RevenueRequest(
                                                paymentType = 2,
                                                totalRecieved = 0.00f
                                            ),
                                            RevenueRequest(
                                                paymentType = 3,
                                                totalRecieved = 0.00f
                                            )
                                        )
                                    )
                                )
                            ),
                            Seat(
                                seatType = "E",
                                quantitySeats = session.totalSeats,
                                ticketsSold = listOf(
                                    TicketsSold(
                                        ticketType = "01",
                                        quantitySold = 0,
                                        revenue = listOf(
                                            RevenueRequest(
                                                paymentType = 1,
                                                totalRecieved = 0.00f
                                            ),
                                            RevenueRequest(
                                                paymentType = 2,
                                                totalRecieved = 0.00f
                                            ),
                                            RevenueRequest(
                                                paymentType = 3,
                                                totalRecieved = 0.00f
                                            )
                                        )
                                    ),
                                    TicketsSold(
                                        ticketType = "02",
                                        quantitySold = 0,
                                        revenue = listOf(
                                            RevenueRequest(
                                                paymentType = 1,
                                                totalRecieved = 0.00f
                                            ),
                                            RevenueRequest(
                                                paymentType = 2,
                                                totalRecieved = 0.00f
                                            ),
                                            RevenueRequest(
                                                paymentType = 3,
                                                totalRecieved = 0.00f
                                            )
                                        )
                                    ),
                                    TicketsSold(
                                        ticketType = "03",
                                        quantitySold = 0,
                                        revenue = listOf(
                                            RevenueRequest(
                                                paymentType = 1,
                                                totalRecieved = 0.00f
                                            ),
                                            RevenueRequest(
                                                paymentType = 2,
                                                totalRecieved = 0.00f
                                            ),
                                            RevenueRequest(
                                                paymentType = 3,
                                                totalRecieved = 0.00f
                                            )
                                        )
                                    ),
                                    TicketsSold(
                                        ticketType = "04",
                                        quantitySold = 0,
                                        revenue = listOf(
                                            RevenueRequest(
                                                paymentType = 1,
                                                totalRecieved = 0.00f
                                            ),
                                            RevenueRequest(
                                                paymentType = 2,
                                                totalRecieved = 0.00f
                                            ),
                                            RevenueRequest(
                                                paymentType = 3,
                                                totalRecieved = 0.00f
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            }
        }

        return sessionsRequest
    }

    private fun deleteAllSessions() {
        viewModelScope.launch {
            deleteAllSessionUseCase.deleteAll()
            getSessions()
        }
    }

    private fun deleteSession(id: Int?) {
        id?.let {
            viewModelScope.launch {
                deleteSessionUseCase.delete(id = id)
                getSessions()
            }
        }
    }
}