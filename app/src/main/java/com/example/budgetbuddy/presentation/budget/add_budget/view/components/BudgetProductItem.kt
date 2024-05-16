package com.example.budgetbuddy.presentation.budget.add_budget.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgetbuddy.R
import com.example.budgetbuddy.domain.model.Product
import com.example.budgetbuddy.domain.model.relations.ProductsWithQuantity
import java.util.Date

@Preview
@Composable
fun BudgetProductItem(
    product: ProductsWithQuantity = ProductsWithQuantity(Product(name = "Espelho", value = 14.00), 0),
    modifier: Modifier = Modifier,
    onClick: ()-> Unit = {}
) {
    var quantity by remember { mutableIntStateOf(product.quantity) }
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
                Text(text = product.product.name,style = TextStyle(fontSize = 18.sp))
                Text(text = "Valor: ${String.format("%.2f", product.product.value)}", style = TextStyle(fontSize = 12.sp))
                Text(text = "Total: ${String.format("%.2f", (product.product.value*quantity))}", style = TextStyle(fontSize = 12.sp))
            }
            Row {
                Icon(
                    modifier = Modifier.clickable {
                        product.quantity--
                        quantity--
                        onClick.invoke()
                    },
                    painter = painterResource(id = R.drawable.baseline_remove_24), contentDescription = "SelectedItem")
                Text(modifier = Modifier.height(25.dp), text = quantity.toString(), style = TextStyle(fontSize = 16.sp))
                Icon(modifier = Modifier.clickable {
                        product.quantity++
                        quantity++
                        onClick.invoke()
                    },
                    imageVector = Icons.Default.Add, contentDescription = "SelectedItem")
            }
        }
    }
}