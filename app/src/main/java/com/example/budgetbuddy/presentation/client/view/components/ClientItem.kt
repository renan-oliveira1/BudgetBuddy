package com.example.budgetbuddy.presentation.client.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgetbuddy.domain.model.Client
import java.util.Date

@Composable
fun ClientItem(
    client: Client,
    modifier: Modifier,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column {
                Text(text = client.name,style = TextStyle(fontSize = 18.sp))
                Text(text = "Cpf: ${client.cpf}", style = TextStyle(fontSize = 12.sp))
                val date = Date(client.timestamp)
                Text(text = "Data registro: ${date.date}/${date.month}/${1900 + date.year}", style = TextStyle(fontSize = 12.sp))
            }

            IconButton(onClick = { onDeleteClick.invoke() }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete item", tint = Color.Red)
            }

        }
    }
}