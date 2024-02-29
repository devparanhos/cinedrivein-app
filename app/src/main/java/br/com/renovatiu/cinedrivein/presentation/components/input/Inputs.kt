package br.com.renovatiu.cinedrivein.presentation.components.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import br.com.renovatiu.cinedrivein.data.remote.model.request.DistributorRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectInput(
    list: List<DistributorRequest>,
    placeholder: String,
    optionSelected: String?,
    onOptionSelected: (distributor: DistributorRequest) -> Unit
){
    var isExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = Modifier
            .fillMaxWidth(),
        expanded = isExpanded,
        onExpandedChange = {
            isExpanded = it
        }
    ) {
        OutlinedTextField(
            value = optionSelected ?: "Selecione um distribuidor",
            shape = RoundedCornerShape(5.dp),
            readOnly = true,
            onValueChange = {},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            placeholder = {
                Text(text = placeholder)
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color.Gray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(5.dp)
                )
                .menuAnchor()
        )
        ExposedDropdownMenu(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            }
        ) {
            list.forEach { distributor ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    text = {
                        Text(
                            text = distributor.distributorName.toString(),
                            color = Color.Gray
                        )
                    },
                    onClick = {
                        isExpanded = false
                        onOptionSelected(distributor)
                    }
                )
            }
        }
    }
}
