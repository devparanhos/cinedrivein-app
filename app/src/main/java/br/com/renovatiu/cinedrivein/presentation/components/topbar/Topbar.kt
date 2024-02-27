package br.com.renovatiu.cinedrivein.presentation.components.topbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.renovatiu.cinedrivein.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopBar(
    icon: Int? = null,
    trailingIcon: Int? = null,
    title: String,
    navigateTo: (() -> Unit)? = null,
    onIconClick: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = title)
                trailingIcon?.let {
                    Icon(
                        painter = painterResource(id = trailingIcon),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp).clickable {
                            onIconClick?.invoke()
                        }
                    )
                }
            }
        },
        navigationIcon = {
            icon?.let {
                Icon(
                    painter = painterResource(id = it),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        navigateTo?.invoke()
                    }
                )
            }

        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Primary,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        )
    )
}