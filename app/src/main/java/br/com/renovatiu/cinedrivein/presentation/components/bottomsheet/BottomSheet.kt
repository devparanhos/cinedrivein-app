package br.com.renovatiu.cinedrivein.presentation.components.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.renovatiu.cinedrivein.R

@Composable
fun ProtocolOptionsBottomSheet(
    hasProtocol: Boolean,
    onSendReport: () -> Unit,
    onConsultProtocol: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(
                modifier = Modifier
                    .width(100.dp)
                    .height(2.dp)
                    .background(Color.Black)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (hasProtocol) {
            Row(
                modifier = Modifier.fillMaxWidth().clickable {
                    onConsultProtocol()
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_consult_report),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Consultar protocolo")
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth().clickable {
                    onSendReport()
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_send),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Enviar relatório")
            }
        }
    }
}
