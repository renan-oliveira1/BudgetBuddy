package com.example.budgetbuddy.presentation.home

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.budgetbuddy.R
import com.example.budgetbuddy.presentation.util.ScreensRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Row (
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ){
            Card(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .height(90.dp)
                    .width(90.dp),
                onClick = {navController.navigate(ScreensRoute.ProductScreen.route)}
            ){
                Column(verticalArrangement = Arrangement.Center){
                    Icon(painter = painterResource(id = R.drawable.baseline_content_paste_24), contentDescription = "Client Option", modifier = Modifier
                        .height(70.dp)
                        .fillMaxWidth())
                    Text(text = "Produtos", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                }
            }
            Card(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .height(90.dp)
                    .width(90.dp),
                onClick = {navController.navigate(ScreensRoute.ClientScreen.route)}
                ){
                Column(verticalArrangement = Arrangement.Center){
                    Icon(painter = painterResource(id = R.drawable.baseline_groups_24), contentDescription = "Client Option", modifier = Modifier
                        .height(70.dp)
                        .fillMaxWidth())
                    Text(text = "Clientes", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                }
            }
            Card(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .height(90.dp)
                    .width(90.dp),
                onClick = {navController.navigate(ScreensRoute.BudgetsScreen.route)}
                ){
                Column(verticalArrangement = Arrangement.Center){
                    Icon(painter = painterResource(id = R.drawable.baseline_monetization_on_24), contentDescription = "Client Option", modifier = Modifier
                        .height(70.dp)
                        .fillMaxWidth())
                    Text(text = "Orçamentos", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                }
            }
        }
    }
}