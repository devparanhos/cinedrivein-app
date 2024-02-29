package br.com.renovatiu.cinedrivein.presentation.components.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.renovatiu.cinedrivein.ui.theme.Primary
import br.com.renovatiu.cinedrivein.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentTimePicker(
    onConfirm: (time: String) -> Unit,
    onCancel: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.7f))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val state = rememberTimePickerState(19, 0, true)

                TimePicker(
                    state = state,
                    colors = TimePickerDefaults.colors(
                        selectorColor = Primary,
                        timeSelectorSelectedContainerColor = Primary,
                        timeSelectorSelectedContentColor = Color.White
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = {
                            onCancel()
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.button_label_cancel).uppercase(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    TextButton(
                        onClick = {
                            val minute = if (state.minute <= 9) {
                                "0${state.minute}"
                            } else {
                                state.minute.toString()
                            }

                            val hour = if (state.hour <= 9) {
                                "0${state.hour}"
                            } else {
                                state.hour.toString()
                            }

                            onConfirm("$hour:$minute")
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.button_label_confirm).uppercase(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
