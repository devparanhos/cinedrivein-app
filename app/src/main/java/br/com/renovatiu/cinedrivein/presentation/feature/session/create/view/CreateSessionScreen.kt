package br.com.renovatiu.cinedrivein.presentation.feature.session.create.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.renovatiu.cinedrivein.R
import br.com.renovatiu.cinedrivein.core.extensions.setCnpjMask
import br.com.renovatiu.cinedrivein.presentation.components.card.CardSessionBasicData
import br.com.renovatiu.cinedrivein.presentation.components.card.CardSessionMovieData
import br.com.renovatiu.cinedrivein.presentation.components.card.CardSessionSeats
import br.com.renovatiu.cinedrivein.presentation.components.topbar.DefaultTopBar
import br.com.renovatiu.cinedrivein.presentation.feature.session.create.action.CreateSessionAction
import br.com.renovatiu.cinedrivein.presentation.feature.session.create.viewmodel.CreateSessionViewModel
import br.com.renovatiu.cinedrivein.ui.theme.LightGray
import br.com.renovatiu.cinedrivein.ui.theme.Primary
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateSessionScreen(
    viewModel: CreateSessionViewModel = koinViewModel(),
    onReturn: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    
    LaunchedEffect(state.saved) {
        if (state.saved) {
            onReturn()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LightGray,
        topBar = {
             DefaultTopBar(
                 title = stringResource(id = R.string.title_create_session),
                 icon = R.drawable.ic_arrow_back,
                 navigateTo = {
                     onReturn()
                 }
             )
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(54.dp),
                shape = RoundedCornerShape(5.dp),
                enabled = !state.isSaving,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary
                ),
                onClick = {
                    viewModel.submitAction(
                        action = CreateSessionAction.CreateSession
                    )
                }
            ) {
                Text(
                    text = stringResource(id = R.string.button_lable_register).uppercase(),
                    fontSize = 16.sp
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ){
                item {
                    CardSessionBasicData(
                        sessionHour = state.sessionHour,
                        sessionHourChange = { hour ->
                            viewModel.submitAction(
                                action = CreateSessionAction.UpdateSessionHour(
                                    hour = hour
                                )
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    CardSessionMovieData(
                        movieCode = state.movieCode,
                        movieCodeChange = { code ->
                            viewModel.submitAction(
                                action = CreateSessionAction.UpdateMovieCode(
                                    code = code
                                )
                            )
                        },
                        title = state.title,
                        titleChange = { title ->
                            viewModel.submitAction(
                                action = CreateSessionAction.UpdateTitle(
                                    title = title
                                )
                            )
                        },
                        isOriginalAudio = state.isOriginalAudio,
                        audioChange = {
                            viewModel.submitAction(
                                action = CreateSessionAction.UpdateAudio
                            )
                        },
                        hasSubtitle = state.hasSubtitle,
                        hasSubtitleChange = {
                            viewModel.submitAction(
                                action = CreateSessionAction.UpdateSubtitle
                            )
                        },
                        distributors = state.distributors,
                        distributorName = state.distributorName,
                        onDistributorSelect = {
                            viewModel.submitAction(
                                action = CreateSessionAction.UpdateDistributor(
                                    distributor = it
                                )
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    CardSessionSeats(
                        quantityTickets = state.totalTickets,
                        quantityTicketsChange = { totalTickets ->
                            viewModel.submitAction(
                                action = CreateSessionAction.UpdateTotalTickets(
                                    tickets = totalTickets
                                )
                            )
                        },
                        quantityTicketsFull = state.quantityTicketsFull,
                        quantityTicketsFullChange = { ticketsFull ->
                            viewModel.submitAction(
                                action = CreateSessionAction.UpdateTotalFullTickets(
                                    tickets = ticketsFull
                                )
                            )
                        },
                        revenueFullTickets = state.revenueTicketsFull,
                        revenueFullTicketsChange = { revenueFullTickets ->
                            viewModel.submitAction(
                                action = CreateSessionAction.UpdateRevenueFullTickets(
                                    value = revenueFullTickets
                                )
                            )
                        },
                        quantityTicketsHalf = state.quantityTicketsHalf,
                        quantityTicketsHalfChange = { ticketsHalf ->
                            viewModel.submitAction(
                                action = CreateSessionAction.UpdateTotalHalfTickets(
                                    tickets = ticketsHalf
                                )
                            )
                        },
                        revenueHalfTickets = state.revenueTicketsHalf,
                        revenueHalfTicketsChange = { revenueHalfTickets ->
                            viewModel.submitAction(
                                action = CreateSessionAction.UpdateRevenueHalfTickets(
                                    value = revenueHalfTickets
                                )
                            )
                        }
                    )
                }
            }
        }
        
        if (state.isSaving) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(alpha = 0.5f))
                    .padding(horizontal = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(54.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(color = Primary, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = stringResource(id = R.string.loading_saving_session))
                    }
                }
            }
        }
    }
}