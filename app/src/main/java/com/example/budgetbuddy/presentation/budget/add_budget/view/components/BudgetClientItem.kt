package com.example.budgetbuddy.presentation.budget.add_budget.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgetbuddy.domain.model.Client
import java.util.Date

@Preview
@Composable
fun BudgetClientItem(
    client: Client =  Client(name = "renan", cpf = "44344.1..231", timestamp = System.currentTimeMillis()),
    modifier: Modifier = Modifier,
    isSelected: Boolean = true
) {
    Card(
        modifier = if(isSelected){
            modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(BorderStroke(2.dp, Color.Green), RoundedCornerShape(10.dp))
        }else
        {
            modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .clip(RoundedCornerShape(10.dp))
        }

    ) {
        Row(
            modifier = Modifier
//                .border(BorderStroke(3.dp, Color.Green), RoundedCornerShape(5.dp))
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
            if(isSelected){
                Icon(imageVector = Icons.Default.Check, contentDescription = "SelectedItem", tint = Color.Green)
            }

        }
    }
}