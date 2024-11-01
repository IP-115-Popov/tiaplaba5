package ru.sergey.tiap.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.sergey.domain.State
import ru.sergey.tiap.R
import ru.sergey.tiap.viewmodel.DKAScreenViewModel


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DKAScreen(vm : DKAScreenViewModel = viewModel()) {
    val items = vm.DKD.collectAsState()


    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            itemsIndexed(items.value) { index, state->
                StateItem(index , state ,vm)
            }
        }
        Button(
            onClick = {
                vm.addState()
            },
            Modifier
                .size(150.dp, height = 120.dp)
                .padding(top = 50.dp)
                .align(Alignment.BottomEnd),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color(0xff004D40),       // цвет текста
                containerColor = Color(0xff9ed6df)
            ),   // цвет фона
            border = BorderStroke(3.dp, Color.DarkGray)
        ) {
            Text("Add State", fontSize = 25.sp)
        }
        Button(
            onClick = {
                vm.setDKA()
            },
            Modifier
                .size(150.dp, height = 120.dp)
                .padding(top = 50.dp)
                .align(Alignment.BottomStart),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color(0xff004D40),       // цвет текста
                containerColor = Color(0xff9ed6df)
            ),   // цвет фона
            border = BorderStroke(3.dp, Color.DarkGray)
        ) {
            Text(stringResource(R.string.set_dka), fontSize = 25.sp)
        }
    }
}
@Composable
fun StateItem(index : Int, state: State , vm : DKAScreenViewModel) {
    val stateString = remember {mutableStateOf(state.name)}
    val symbol = remember {mutableStateOf(state.name)}
    val nextStateString = remember {mutableStateOf(state.name)}

    Row {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Name")
            TextField(
                value = stateString.value,
                textStyle = TextStyle(fontSize=25.sp),
                onValueChange = {
                    newText -> stateString.value = newText
                    vm.updateItem(index, stateString.value, symbol.value, nextStateString.value)
                }
            )
        }
        Column(modifier = Modifier.weight(1f))  {
            Text(text = "Symbol")
            TextField(
                value = symbol.value,
                textStyle = TextStyle(fontSize = 25.sp),
                onValueChange = { newText ->
                    // Проверяем, является ли новый текст пустым или равно 1 символу
                    if (newText.length <= 1) {
                        symbol.value = newText // разрешаем обновление
                        vm.updateItem(index ,stateString.value, symbol.value, nextStateString.value)
                    }
                }
            )
        }
        Column(modifier = Modifier.weight(1f))  {
            Text(text = "Next state")
            TextField(
                value = nextStateString.value,
                textStyle = TextStyle(fontSize = 25.sp),
                onValueChange = {
                    newText -> nextStateString.value = newText
                    vm.updateItem(index, stateString.value, symbol.value, nextStateString.value)
                }
            )
        }
    }
}