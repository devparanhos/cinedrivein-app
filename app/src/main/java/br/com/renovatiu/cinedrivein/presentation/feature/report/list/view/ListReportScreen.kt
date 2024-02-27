package br.com.renovatiu.cinedrivein.presentation.feature.report.list.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.renovatiu.cinedrivein.presentation.feature.report.list.viewmodel.ListReportViewModel
import org.koin.androidx.compose.koinViewModel
import br.com.renovatiu.cinedrivein.R
import br.com.renovatiu.cinedrivein.core.enums.TypeStatus
import br.com.renovatiu.cinedrivein.core.extensions.formatDateBR
import br.com.renovatiu.cinedrivein.core.extensions.formatToReal
import br.com.renovatiu.cinedrivein.core.functions.extractMoviesName
import br.com.renovatiu.cinedrivein.core.functions.extractTicketsQuantity
import br.com.renovatiu.cinedrivein.core.functions.extractTicketsValue
import br.com.renovatiu.cinedrivein.core.functions.getSelectedDate
import br.com.renovatiu.cinedrivein.data.remote.model.request.ProtocolRequest
import br.com.renovatiu.cinedrivein.presentation.components.bottomsheet.ProtocolOptionsBottomSheet
import br.com.renovatiu.cinedrivein.presentation.components.topbar.DefaultTopBar
import br.com.renovatiu.cinedrivein.presentation.feature.report.list.action.ListReportAction
import br.com.renovatiu.cinedrivein.ui.theme.LightGray
import br.com.renovatiu.cinedrivein.ui.theme.Primary
import br.com.renovatiu.cinedrivein.ui.theme.PrimaryDark
import com.dt.composedatepicker.ComposeCalendar
import com.dt.composedatepicker.SelectDateListener
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ListReportScreen(
    viewModel: ListReportViewModel = koinViewModel(),
    navigateCreateReport: () -> Unit
) {
    var protocolSelected: ProtocolRequest? by remember { mutableStateOf(null) }
    var openCalendar by remember { mutableStateOf(false) }
    val state by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { true }
    )

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            ProtocolOptionsBottomSheet(
                hasProtocol = protocolSelected?.protocol != null,
                onSendReport = {
                    viewModel.submitAction(
                        action = ListReportAction.SendReport(
                            protocol = protocolSelected
                        )
                    )
                },
                onConsultProtocol = {
                    viewModel.submitAction(
                        action = ListReportAction.ConsultReport(
                            protocolNumber = protocolSelected?.protocol,
                            id = protocolSelected?.id
                        )
                    )
                }
            )
        }
    ){
        Scaffold(
            containerColor = LightGray,
            topBar = {
                Column {
                    DefaultTopBar(
                        title = stringResource(id = R.string.app_name),
                        trailingIcon = R.drawable.ic_refresh,
                        onIconClick = {
                            viewModel.submitAction(
                                action = ListReportAction.GetAllProtocols
                            )
                        }
                    )
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(PrimaryDark)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Primary)
                            .padding(start = 26.dp, bottom = 16.dp, top = 16.dp, end = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.title_reports),
                            color = Color.White,
                            fontSize = 16.sp
                        )
                        Button(
                            modifier = Modifier.height(30.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White
                            ),
                            onClick = {
                                openCalendar = true
                            }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = state.selectedDate,
                                    color = Primary,
                                    fontSize = 10.sp
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    modifier = Modifier.size(13.dp),
                                    tint = Primary,
                                    painter = painterResource(id = R.drawable.ic_calendar),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }

            },
            floatingActionButton = {
                if (!openCalendar) {
                    FloatingActionButton(
                        containerColor = Primary,
                        onClick = {
                            navigateCreateReport()
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(id = R.string.button_lable_create_report),
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                tint = Color.White,
                                painter = painterResource(id = R.drawable.ic_add),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            when {
                state.protocols == null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        CircularProgressIndicator()
                    }
                }

                state.protocols!!.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.relatorio),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = stringResource(id = R.string.title_no_report_found)
                            )
                        }
                    }
                }

                else -> {
                    state.protocols?.let { protocols ->
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(innerPadding),
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            items(protocols) { protocol ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 5.dp
                                    ),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(text = protocol.protocol ?: "Sem protocolo")
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_ellipsis),
                                                contentDescription = null,
                                                modifier = Modifier.clickable {
                                                    coroutineScope.launch {
                                                        protocolSelected = protocol
                                                        modalSheetState.show()
                                                    }
                                                }
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Row {
                                            val typeStatus = TypeStatus.fromAcronym(protocol.status)
                                            Card(
                                                shape = RoundedCornerShape(5.dp),
                                                colors = CardDefaults.cardColors(
                                                    containerColor = typeStatus.color
                                                )
                                            ) {
                                                Text(
                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                                    text = typeStatus.description,
                                                    color = Color.White,
                                                    fontSize = 12.sp
                                                )
                                            }

                                            if(protocol.report?.rectifing == "S" ) {
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Card(
                                                    shape = RoundedCornerShape(5.dp),
                                                    colors = CardDefaults.cardColors(
                                                        containerColor = Color.Black
                                                    )
                                                ) {
                                                    Text(
                                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                                        text = "Retificando",
                                                        color = Color.White,
                                                        fontSize = 12.sp
                                                    )
                                                }
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(24.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(painter = painterResource(id = R.drawable.ic_calendar), contentDescription = null)
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(text = protocol.date.toString().formatDateBR())
                                        }
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_movie),
                                                contentDescription = null
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = extractMoviesName(protocol.report?.sessions).lowercase(),
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_ticket),
                                                contentDescription = null
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = "${extractTicketsQuantity(protocol.report?.sessions)} ingressos",
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_money),
                                                contentDescription = null
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = extractTicketsValue(protocol.report?.sessions).formatToReal(),
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }
            }

            if(openCalendar) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(color = Color.Black.copy(0.7f))
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.YEAR, 2022)
                    calendar.set(Calendar.MONTH, 1)

                    val calendarMax = Calendar.getInstance()
                    calendarMax.set(Calendar.YEAR, 2050)
                    calendarMax.set(Calendar.MONTH, 12)

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.6f)
                            .padding(24.dp)
                    ) {
                        ComposeCalendar(
                            minDate = calendar.time,
                            maxDate = calendarMax.time,
                            locale = Locale("pt"),
                            themeColor = Primary,
                            title = "Selecione o mês do relatório",
                            listener = object : SelectDateListener {
                                override fun onDateSelected(date: Date) {
                                    openCalendar = false

                                    viewModel.submitAction(
                                        action = ListReportAction.GetReportsByDate(
                                            date = getSelectedDate(date.month, date.year)
                                        )
                                    )
                                }

                                override fun onCanceled() {
                                    openCalendar = false
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}