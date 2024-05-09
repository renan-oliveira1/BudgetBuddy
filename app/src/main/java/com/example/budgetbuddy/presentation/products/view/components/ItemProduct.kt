package com.example.budgetbuddy.presentation.products.view.components

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgetbuddy.domain.model.Product

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ItemProduct(
    product: Product = Product(name = "Nome Produto", description = "Desccricao Produto", value = 150.0),
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit = {}
){
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
                Text(text = product.name,style = TextStyle(fontSize = 18.sp))
                Text(text = "Descriçao: ${product.name}", style = TextStyle(fontSize = 12.sp))
                Text(text = "Valor: ${String.format("%.2f", product.value)}", style = TextStyle(
                    fontSize = 12.sp
                )
                )
            }

            IconButton(onClick = { onDeleteClick.invoke() }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete item", tint = Color.Red)
            }

        }
    }
}