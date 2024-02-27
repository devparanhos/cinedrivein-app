package br.com.renovatiu.cinedrivein.presentation.feature.report.create.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.renovatiu.cinedrivein.R
import br.com.renovatiu.cinedrivein.core.extensions.formatToReal
import br.com.renovatiu.cinedrivein.presentation.feature.report.create.action.CreateReportAction
import br.com.renovatiu.cinedrivein.presentation.feature.report.create.viewmodel.CreateReportViewModel
import br.com.renovatiu.cinedrivein.presentation.feature.session.create.action.CreateSessionAction
import br.com.renovatiu.cinedrivein.ui.theme.LightGray
import br.com.renovatiu.cinedrivein.ui.theme.Primary
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateReportScreen(
    viewModel: CreateReportViewModel = koinViewModel(),
    navigateToCreateSession: (date: String) -> Unit,
    navigateBack: () -> Unit
) {
    
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.saved) {
        if (state.saved) {
            navigateBack()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier.clickable {
                    navigateBack()
                },
                title = {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(id = R.string.title_create_report)
                    )
                },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = null
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(54.dp),
                shape = RoundedCornerShape(5.dp),
                enabled = true,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                ),
                onClick = {
                    viewModel.submitAction(
                        action = CreateReportAction.CreateReport
                    )
                }
            ) {
                Text(
                    text = stringResource(id = R.string.button_lable_register).uppercase(),
                    fontSize = 16.sp
                )
            }
        },
        containerColor = LightGray
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
        ) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = state.date,
                            label = {
                                Text(text = stringResource(id = R.string.label_report_date))
                            },
                            onValueChange = { date ->
                                viewModel.submitAction(
                                    action = CreateReportAction.UpdateDate(
                                        date = date
                                    )
                                )
                            },
                            placeholder = {
                                Text(
                                    text = stringResource(id = R.string.placeholder_date),
                                    color = LightGray
                                )
                            },
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row {
                            Column(
                                modifier = Modifier
                                    .border(1.dp, Color.Gray, RoundedCornerShape(3.dp))
                                    .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                                    .weight(1.0f)
                            ){
                                Text(
                                    text = stringResource(id = R.string.title_has_session),
                                    fontWeight = FontWeight.SemiBold
                                )
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        modifier = Modifier.weight(1.0f),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            modifier = Modifier.width(15.dp),
                                            selected = state.hasSession,
                                            onClick = {
                                                viewModel.submitAction(
                                                    action = CreateReportAction.UpdateHasSession
                                                )
                                            }
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = stringResource(id = R.string.label_yes),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Row(
                                        modifier = Modifier.weight(1.0f),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            modifier = Modifier.width(15.dp),
                                            selected = !state.hasSession,
                                            onClick = {
                                                viewModel.submitAction(
                                                    action = CreateReportAction.UpdateHasSession
                                                )
                                            }
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = stringResource(id = R.string.label_no),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(
                                modifier = Modifier
                                    .border(1.dp, Color.Gray, RoundedCornerShape(3.dp))
                                    .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                                    .weight(1.0f)
                            ){
                                Text(
                                    text = stringResource(id = R.string.title_rectifing),
                                    fontWeight = FontWeight.SemiBold
                                )
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        modifier = Modifier.weight(1.0f),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            modifier = Modifier.width(15.dp),
                                            selected = state.isRectifing,
                                            onClick = {
                                                viewModel.submitAction(
                                                    action = CreateReportAction.UpdateRectifing
                                                )
                                            }
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = stringResource(id = R.string.label_yes),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Row(
                                        modifier = Modifier.weight(1.0f),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            modifier = Modifier.width(15.dp),
                                            selected = !state.isRectifing,
                                            onClick = {
                                                viewModel.submitAction(
                                                    action = CreateReportAction.UpdateRectifing
                                                )
                                            }
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = stringResource(id = R.string.label_no),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            item{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Sessões cadastradas")
                    Text(
                        modifier = Modifier.clickable {
                            navigateToCreateSession("2332")
                        },
                        text = "Adicionar",
                    )
                }
                
                when {
                    state.sessions == null -> {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    
                    state.sessions!!.isEmpty() -> {
                        
                    }
                    
                    else -> {
                        state.sessions?.let { sessions ->
                            sessions.forEach { session ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White
                                    )
                                ){
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                modifier = Modifier.weight(0.8f),
                                                text = session.movieTitle,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                overflow = TextOverflow.Ellipsis,
                                                maxLines = 1
                                            )
                                            Text(
                                                modifier = Modifier
                                                    .weight(0.2f)
                                                    .fillMaxWidth(),
                                                text = session.time,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                overflow = TextOverflow.Ellipsis,
                                                maxLines = 1,
                                                textAlign = TextAlign.End
                                            )
                                        }
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(bottom = 8.dp)
                                        ) {
                                            Text(text = (session.totalFullSold + session.totalHalfSold).formatToReal())
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(text = "${session.totalSeats} ingressos")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}