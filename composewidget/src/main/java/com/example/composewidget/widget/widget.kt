
package com.example.composewidget.widget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.composewidget.model.FuncInfo

/**
 * 所有的依赖版本得一致，不然就会报错
 */
@ExperimentalMaterialApi
@Composable
fun FunctionList(list: ArrayList<FuncInfo>,navHostController: NavHostController){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ){
        item {
            Spacer(modifier = Modifier.size(0.dp))
        }
        items(list){
            item: FuncInfo -> FuncItem(func = item, navHostController = navHostController )
        }
        item {
            Spacer(modifier = Modifier.size(0.dp))
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun FuncItem(func: FuncInfo, navHostController: NavHostController) {
    Card(
        onClick = {
            navHostController.navigate(func.path)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(15.dp, 0.dp),
        backgroundColor = Color.Blue,
        elevation = 2.dp
        /**
         * backgroundColor = func.color  这样设置报错
         */

    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = func.name,
                modifier = Modifier.padding(15.dp, 0.dp, 0.dp, 0.dp),
                textAlign = TextAlign.Center,
                fontSize = MaterialTheme.typography.h5.fontSize,
                color = Color.White
            )
        }
    }
}

@Composable
fun VerticalNoMoreItem(){
    Text(
        text = "--没有更多了--",
        color = Color.DarkGray,
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 0.dp, 0.dp, 20.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun HorizontalNoMoreItem(){
    Column (
        modifier = Modifier.height(160.dp),
        verticalArrangement = Arrangement.Center
            ){
        Text(
            text = "没有更多了",
            color = Color.DarkGray,
            modifier = Modifier.padding(0.dp,0.dp,20.dp,0.dp),
            textAlign = TextAlign.Center
        )
    }
}
