package br.com.renovatiu.cinedrivein.presentation.feature.report.create.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import br.com.renovatiu.cinedrivein.R
import br.com.renovatiu.cinedrivein.core.extensions.formatToReal
import br.com.renovatiu.cinedrivein.data.remote.model.request.ReportRequest
import br.com.renovatiu.cinedrivein.domain.model.SessionDomain
import br.com.renovatiu.cinedrivein.presentation.components.bottomsheet.ProtocolOptionsBottomSheet
import br.com.renovatiu.cinedrivein.presentation.components.bottomsheet.SessionOptionsBottomSheet
import br.com.renovatiu.cinedrivein.presentation.components.text.TextSmallWithLeftIcon
import br.com.renovatiu.cinedrivein.presentation.feature.report.create.action.CreateReportAction
import br.com.renovatiu.cinedrivein.presentation.feature.report.create.viewmodel.CreateReportViewModel
import br.com.renovatiu.cinedrivein.presentation.feature.report.list.action.ListReportAction
import br.com.renovatiu.cinedrivein.presentation.feature.session.create.action.CreateSessionAction
import br.com.renovatiu.cinedrivein.presentation.navigation.route.Route
import br.com.renovatiu.cinedrivein.ui.theme.LightGray
import br.com.renovatiu.cinedrivein.ui.theme.Primary
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun CreateReportScreen(
    viewModel: CreateReportViewModel = koinViewModel(),
    navigateToCreateSession: (toUpdate: Boolean, session: SessionDomain?) -> Unit,
    navigateBack: () -> Unit
) {
    var sessionSelected: SessionDomain?  by remember { mutableStateOf(null) }
    val state by viewModel.state.collectAsState()
    var buttonPressed by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { true }
    )


    LaunchedEffect(state.saved) {
        if (state.saved) {
            navigateBack()
        }
    }

    LaunchedEffect(true) {
        viewModel.submitAction(
            action = CreateReportAction.GetSessions
        )
    }

    if (state.showDialog) {
        Dialog(
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            onDismissRequest = {

            },
            content = {
                Card(
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
                            Text(
                                text = "Manter sessão",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.ic_close),
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    viewModel.submitAction(
                                        action = CreateReportAction.OpenCloseDialog
                                    )
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Ao salvar este relatório, você deseja manter as sessões para facilitar o próximo cadastro ou prefere que sejam removidas",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent
                                ),
                                onClick = {
                                    viewModel.submitAction(
                                        action = CreateReportAction.CreateReport(
                                            deleteSessions = true
                                        )
                                    )
                                }
                            ) {
                                Text(
                                    text = "Remover",
                                    color = Color.Gray
                                )
                            }

                            Button(
                                shape = RoundedCornerShape(5.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Primary
                                ),
                                onClick = {
                                    viewModel.submitAction(
                                        action = CreateReportAction.CreateReport(
                                            deleteSessions = false
                                        )
                                    )
                                }
                            ) {
                                Text(
                                    text = "Manter sessões",
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        )
    }
    
    if (state.requesting) {
        Dialog(
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            onDismissRequest = {

            },
            content = {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "Salvando relatório...")
                    }
                }
            }
        )
    }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            SessionOptionsBottomSheet(
                session = sessionSelected,
                onDelete = {
                    viewModel.submitAction(
                        action = CreateReportAction.DeleteSession(
                            id = sessionSelected?.id
                        )
                    )
                    coroutineScope.launch {
                        modalSheetState.hide()
                    }
                },
                onEdit = {
                    navigateToCreateSession(true, sessionSelected)
                    coroutineScope.launch {
                        modalSheetState.hide()
                    }
                },
                onDuplicate = {
                    navigateToCreateSession(false, sessionSelected)
                    coroutineScope.launch {
                        modalSheetState.hide()
                    }
                }
            )
        }
    ){
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
                Card(
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
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
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Sessões registradas: ${state.sessions?.let{ it.size } ?: '0'}")
                            Text(
                                text = "Limpar sessões",
                                color = Primary,
                                modifier = Modifier.clickable {
                                    viewModel.submitAction(
                                        action = CreateReportAction.DeleteAllSessions
                                    )
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Caso marque que não houve sessão você precisa remover as sessões cadastradas",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp),
                            shape = RoundedCornerShape(5.dp),
                            enabled = state.date.length == 10 &&
                                    if (state.hasSession) state.sessions?.isNotEmpty() == true
                                    else state.sessions?.isEmpty() == true,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Primary,
                            ),
                            onClick = {
                                viewModel.submitAction(
                                    action = CreateReportAction.OpenCloseDialog
                                )
                            }
                        ) {
                            Text(
                                text = stringResource(id = R.string.button_label_register).uppercase(),
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            },
            containerColor = LightGray
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ){
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 16.dp)
                    ) {
                        Row {
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1.0f),
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
//                        TextButton(
//                            modifier = Modifier.weight(1.0f),
//                            shape = RoundedCornerShape(5.dp),
//                            onClick = {
//
//                            }
//                        ) {
//                            Row(
//                                horizontalArrangement = Arrangement.Center,
//                                verticalAlignment = Alignment.CenterVertically
//                            ) {
//                                Text(
//                                    text = stringResource(id = R.string.button_label_date),
//                                    fontSize = 16.sp,
//                                    color = Primary,
//                                    fontWeight = FontWeight.SemiBold
//                                )
//                                Spacer(modifier = Modifier.width(4.dp))
//                                Icon(
//                                    tint = Primary,
//                                    modifier = Modifier.size(16.dp),
//                                    painter = painterResource(id = R.drawable.ic_calendar),
//                                    contentDescription = null
//                                )
//                            }
//                        }
                        }
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Sessões cadastradas",
                    )
                    Button(
                        modifier = Modifier.height(30.dp),
                        enabled = !buttonPressed,
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Primary
                        ),
                        onClick = {
                            buttonPressed = true
                            navigateToCreateSession(false, null)
                        }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (buttonPressed) {
                                CircularProgressIndicator(modifier = Modifier.size(13.dp))
                            } else {
                                Text(
                                    text = "adicionar sessão",
                                    color = Color.White,
                                    fontSize = 10.sp
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    modifier = Modifier.size(13.dp),
                                    tint = Color.White,
                                    painter = painterResource(id = R.drawable.ic_add),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = if (state.sessions?.isNotEmpty() == true) Arrangement.Top else Arrangement.Center
                ) {
                    item {
                        when {
                            state.sessions == null -> {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    CircularProgressIndicator()
                                }
                            }

                            state.sessions!!.isEmpty() -> {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.no_session),
                                        contentDescription = null,
                                        modifier = Modifier.size(100.dp)
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(text = "Nenhuma sessão cadastrada")
                                }
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
                                                        text = session.movieTitle,
                                                        fontSize = 16.sp,
                                                        fontWeight = FontWeight.SemiBold,
                                                        overflow = TextOverflow.Ellipsis,
                                                        maxLines = 1
                                                    )
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.ic_ellipsis),
                                                        contentDescription = null,
                                                        modifier = Modifier.clickable {
                                                            coroutineScope.launch {
                                                                sessionSelected = session
                                                                modalSheetState.show()
                                                            }
                                                        }
                                                    )
                                                }

                                                Text(
                                                    text = session.time,
                                                    fontSize = 16.sp,
                                                    overflow = TextOverflow.Ellipsis,
                                                    maxLines = 1,
                                                    textAlign = TextAlign.End,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = Primary
                                                )
                                                Spacer(modifier = Modifier.height(16.dp))
                                                Row(
                                                    modifier = Modifier.fillMaxWidth()
                                                ) {
                                                    Column(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .weight(1.0f)
                                                    ) {
                                                        Text(
                                                            text = "Inteiras",
                                                            modifier = Modifier.padding(bottom = 8.dp)
                                                        )
                                                        TextSmallWithLeftIcon(
                                                            color = Color.Gray,
                                                            text = "${session.totalFullQuantity} ingressos",
                                                            icon = painterResource(id = R.drawable.ic_ticket)
                                                        )
                                                        Spacer(modifier = Modifier.height(8.dp))
                                                        TextSmallWithLeftIcon(
                                                            color = Color.Gray,
                                                            text = session.totalFullSold.formatToReal(),
                                                            icon = painterResource(id = R.drawable.ic_money)
                                                        )
                                                    }
                                                    Column(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .weight(1.0f)
                                                    ) {
                                                        Text(
                                                            text = "Meias",
                                                            modifier = Modifier.padding(bottom = 8.dp)
                                                        )
                                                        TextSmallWithLeftIcon(
                                                            color = Color.Gray,
                                                            text = "${session.totalHalfQuantity} ingressos",
                                                            icon = painterResource(id = R.drawable.ic_ticket)
                                                        )
                                                        Spacer(modifier = Modifier.height(8.dp))
                                                        TextSmallWithLeftIcon(
                                                            color = Color.Gray,
                                                            text = session.totalHalfSold.formatToReal(),
                                                            icon = painterResource(id = R.drawable.ic_money)
                                                        )
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
//        Box(
//           modifier = Modifier
//               .fillMaxSize()
//               .background(Color.Black.copy(0.7f))
//               .padding(innerPadding)
//        ) {
//            Column(
//                modifier = Modifier.background(Color.White)
//            ) {
//                val state = rememberDatePickerState()
//
//                DatePicker(
//                    state = state
//                )
//            }
//        }
        }
    }
}