package br.com.renovatiu.cinedrivein.presentation.components.card

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.renovatiu.cinedrivein.R
import br.com.renovatiu.cinedrivein.data.remote.model.request.DistributorRequest
import br.com.renovatiu.cinedrivein.presentation.components.input.SelectInput
import br.com.renovatiu.cinedrivein.ui.theme.LightGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardSessionBasicData(
    sessionHour: String,
    sessionHourChange: (hour: String) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.title_basic_data))
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = sessionHour,
                label = {
                    Text(text = stringResource(id = R.string.label_session_hour))
                },
                onValueChange = {
                    if (it.length <= 5) {
                        sessionHourChange(it)
                    }
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.placeholder_hour),
                        color = LightGray
                    )
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardSessionMovieData(
    movieCode: String,
    movieCodeChange: (movieCode: String) -> Unit,
    title: String,
    titleChange: (title: String) -> Unit,
    isOriginalAudio: Boolean,
    audioChange: () -> Unit,
    hasSubtitle: Boolean,
    hasSubtitleChange: () -> Unit,
    distributors : List<DistributorRequest>,
    distributorName: String? = "",
    onDistributorSelect: (distributor: DistributorRequest) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = stringResource(id = R.string.title_movie_session))
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = movieCode,
                label = {
                    Text(text = stringResource(id = R.string.label_movie_code))
                },
                onValueChange = {
                    movieCodeChange(it.uppercase())
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.placeholder_movie_code),
                        color = LightGray
                    )
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                label = {
                    Text(text = stringResource(id = R.string.label_title))
                },
                onValueChange = {
                    titleChange(it.uppercase())
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.placeholder_title),
                        color = LightGray
                    )
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(15.dp))
            Column(
                modifier = Modifier
                    .border(1.dp, Color.Gray, RoundedCornerShape(3.dp))
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp)
            ){
                Text(
                    text = stringResource(id = R.string.title_movie_audio),
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
                            selected = isOriginalAudio,
                            onClick = {
                                audioChange()
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.label_original),
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
                            selected = !isOriginalAudio,
                            onClick = {
                                audioChange()
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.label_dubbed),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Column(
                modifier = Modifier
                    .border(1.dp, Color.Gray, RoundedCornerShape(3.dp))
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp)
            ){
                Text(
                    text = stringResource(id = R.string.title_has_subtitle),
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
                            selected = hasSubtitle,
                            onClick = {
                                hasSubtitleChange()
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
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            modifier = Modifier.width(15.dp),
                            selected = !hasSubtitle,
                            onClick = {
                                hasSubtitleChange()
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
            Spacer(modifier = Modifier.height(10.dp))
            SelectInput(
                optionSelected = distributorName,
                list = distributors,
                placeholder = stringResource(id = R.string.placeholder_select_distributor),
                onOptionSelected = {
                    onDistributorSelect(it)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardSessionSeats(
    quantityTickets: String,
    quantityTicketsChange: (tickets: String) -> Unit,
    quantityTicketsFull: String,
    quantityTicketsFullChange: (quantity: String) -> Unit,
    revenueFullTickets: String,
    revenueFullTicketsChange: (value: String) -> Unit,
    quantityTicketsHalf: String,
    quantityTicketsHalfChange: (quantity: String) -> Unit,
    revenueHalfTickets: String,
    revenueHalfTicketsChange: (value: String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
   ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = stringResource(id = R.string.title_tickets_sold))
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = quantityTickets,
                label = {
                    Text(text = stringResource(id = R.string.label_total_tickets))
                },
                onValueChange = {
                    quantityTicketsChange(it)
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.placeholder_total_tickets),
                        color = LightGray
                    )
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column {
                Text(
                    text = stringResource(id = R.string.title_tickets_full),
                    fontWeight = FontWeight.SemiBold
                )
                Row {
                    OutlinedTextField(
                        modifier = Modifier.weight(1.0f),
                        value = quantityTicketsFull,
                        label = {
                            Text(text = stringResource(id = R.string.label_quantity))
                        },
                        onValueChange = {
                            quantityTicketsFullChange(it)
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.placeholder_total_tickets))
                        },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        modifier = Modifier.weight(1.0f),
                        value = revenueFullTickets,
                        label = {
                            Text(text = stringResource(id = R.string.label_value))
                        },
                        onValueChange = {
                            revenueFullTicketsChange(it)
                        },
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.placeholder_value),
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_money),
                                contentDescription = null
                            )
                        },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Column {
                Text(
                    text = stringResource(id = R.string.title_tickets_half),
                    fontWeight = FontWeight.SemiBold
                )
                Row {
                    OutlinedTextField(
                        modifier = Modifier.weight(1.0f),
                        value = quantityTicketsHalf,
                        label = {
                            Text(text = stringResource(id = R.string.label_quantity))
                        },
                        onValueChange = {
                            quantityTicketsHalfChange(it)
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.placeholder_total_tickets))
                        },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        modifier = Modifier.weight(1.0f),
                        value = revenueHalfTickets,
                        label = {
                            Text(text = stringResource(id = R.string.label_value))
                        },
                        onValueChange = {
                            revenueHalfTicketsChange(it)
                        },
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.placeholder_value),
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_money),
                                contentDescription = null
                            )
                        },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        )
                    )
                }
            }
        }
    }
}