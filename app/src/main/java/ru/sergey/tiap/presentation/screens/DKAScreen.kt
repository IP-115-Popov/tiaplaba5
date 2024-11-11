package ru.sergey.tiap.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.sergey.State
import ru.sergey.tiap.R
import ru.sergey.tiap.presentation.theme.ui.Pink80
import ru.sergey.tiap.viewmodel.DKAScreenViewModel


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DKAScreen(vm: DKAScreenViewModel = viewModel()) {
    //val items = vm.DKD.collectAsState()
    val items = remember { mutableStateOf(vm.DKD.value) } // Используем mutableStateOf
    LaunchedEffect(key1 = vm.DKD) {
        vm.DKD.collect { newItems ->
            items.value = newItems
        }
    }

    val setDKAToast = Toast.makeText(
        LocalContext.current, "DKA установлен", Toast.LENGTH_SHORT
    )
    val errorSetDKAToast = Toast.makeText(
        LocalContext.current, "Добавте состояния", Toast.LENGTH_SHORT
    )
    val saveDKAToast = Toast.makeText(
        LocalContext.current, "DKA сохранён", Toast.LENGTH_SHORT
    )
    val loadDKAToast = Toast.makeText(
        LocalContext.current, "DKA загружен", Toast.LENGTH_SHORT
    )
    val  selectedItemIsNull= Toast.makeText(
        LocalContext.current, "Файл не выбран", Toast.LENGTH_SHORT
    )
    val  fileExists= Toast.makeText(
        LocalContext.current, "файл уже существует", Toast.LENGTH_SHORT
    )
    val showSaveDialog = remember { mutableStateOf(false) }
    val showLoadDialog = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), // Добавляем отступ 8 dp
            horizontalArrangement = Arrangement.End
        ) {
            DkaMenu(vm, showSaveDialog, showLoadDialog)
        }

        LazyColumn(Modifier.fillMaxSize(0.9f)) {
            itemsIndexed(items.value) { index, state ->
                StateItem(index, state, vm)
            }
        }
        Row(Modifier.fillMaxSize()) {
            Button(
                onClick = {
                    vm.addState()
                },
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.5f),
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
                    if (vm.DKD.value.size > 0) {
                        vm.setDKA()
                        setDKAToast.show()
                    } else {
                        errorSetDKAToast.show()
                    }

                }, Modifier.fillMaxSize(), colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xff004D40),       // цвет текста
                    containerColor = Color(0xff9ed6df)
                ),   // цвет фона
                border = BorderStroke(3.dp, Color.DarkGray)
            ) {
                Text(stringResource(R.string.set_dka), fontSize = 25.sp)
            }
        }
    }


    if (showSaveDialog.value) {
        Dialog(onDismissRequest = { showSaveDialog.value = false }) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp).background(Pink80, RoundedCornerShape(20.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var selectedItem : String? = null
                Text("Сохранить")
                SingleSelectionList(vm.fileList,{i -> selectedItem = i})
                Spacer(modifier = Modifier.height(8.dp))

                val newFileName = remember { mutableStateOf("") }
                TextField(value = newFileName.value,
                    textStyle = TextStyle(fontSize = 25.sp),
                    onValueChange = { newText -> newFileName.value = newText })

                Button(onClick = {
                    if (newFileName.value != "")
                    {
                        if (!vm.fileList.any({it : String -> it == newFileName.value})) {
                            vm.UploadDKA(newFileName.value)
                            vm.fileList = vm.fileList + newFileName.value
                            saveDKAToast.show()
                        } else {
                            fileExists.show()
                        }
                    }
                }) {
                    Text("Сохранить в новый файл")
                }
                Button(onClick = {
                    if (selectedItem != null) {
                        vm.UploadDKA(selectedItem!!)
                        saveDKAToast.show()
                    } else {
                        selectedItemIsNull.show()
                    }
                }) {
                    Text("Сохранить")
                }
                Button(onClick = {
                    showSaveDialog.value = false
                }) {
                    Text(stringResource(R.string.close))
                }
            }
        }
    }
    if (showLoadDialog.value) {
        Dialog(onDismissRequest = { showLoadDialog.value = false }) {
            Column(
                modifier = Modifier.padding(16.dp).background(Pink80, RoundedCornerShape(20.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var selectedItem : String? = null
                Text("Загрузить")
                SingleSelectionList(vm.fileList,{i -> selectedItem = i})
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    if (selectedItem != null)
                    {
                        vm.DownloadDKA(selectedItem!!)
                        loadDKAToast.show()
                    }
                }) {
                    Text("Загрузить")
                }
                Button(onClick = { showLoadDialog.value = false }) {
                    Text(stringResource(R.string.close))
                }
            }
        }
    }
}
@Composable
fun SingleSelectionList(
    items: List<String>,
    onItemSelected: (String) -> Unit // Функция для обработки выбора
) {
    var selectedItem = remember { mutableStateOf<String?>(null) }

    Column {
        LazyColumn {
            itemsIndexed(items) { i, item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    RadioButton(
                        selected = selectedItem.value == item,
                        onClick = {
                            selectedItem.value = if (selectedItem.value == item) null else item
                            onItemSelected(item) // Вызываем функцию при изменении выбора
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = item)
                }
            }
        }
    }
}

@Composable
fun DkaMenu(vm : DKAScreenViewModel, showSaveDialog: MutableState<Boolean>, showLoadDialog: MutableState<Boolean>) {
    var expanded = remember { mutableStateOf(false) }
    val items = listOf("Сохранить ДКА", "Загрузить ДКА")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { expanded.value = true }) {
            Text("Меню ДКА")
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier.width(200.dp)
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        expanded.value = false // Закрываем меню после выбора
                        // Обрабатываем выбор элемента меню
                        when (item) {
                            "Сохранить ДКА" -> {
                                showSaveDialog.value = true
                            }
                            "Загрузить ДКА" -> {
                                showLoadDialog.value = true
                            }
                        }
                    },
                    text = { Text(item) }
                )
            }
        }
    }
}
@Composable
fun StateItem(index: Int, state: State, vm: DKAScreenViewModel) {
    val name= remember { mutableStateOf(state.name) }
    val symbol= remember { mutableStateOf(state.symbol) }
    val stack= remember { mutableStateOf(state.stack) }
    val nameNextState= remember { mutableStateOf(state.nameNextState) }
    val nextSymbolStack= remember { mutableStateOf(state.nextSymbolStack) }
    Row {
        Column(modifier = Modifier.weight(1.5f)) {
            Text(text = "Name")
            TextField(value = name.value,
                textStyle = TextStyle(fontSize = 25.sp),
                onValueChange = { newText ->
                    name.value = newText
                    vm.updateItem(index, state.copy(name = name.value))
                })
        }
        Column(modifier = Modifier.weight(1.5f)) {
            Text(text = "symbol")
            TextField(value = symbol.value,
                textStyle = TextStyle(fontSize = 25.sp),
                onValueChange = { newText ->
                    symbol.value = newText
                    vm.updateItem(index, state.copy(symbol = symbol.value))
                })
        }
        Column(modifier = Modifier.weight(1.5f)) {
            Text(text = "stack")
            TextField(value = stack.value,
                textStyle = TextStyle(fontSize = 25.sp),
                onValueChange = { newText ->
                    stack.value = newText
                    vm.updateItem(index, state.copy(stack = stack.value))
                })
        }
        Column(modifier = Modifier.weight(1.5f)) {
            Text(text = "nameNextState")
            TextField(value = nameNextState.value,
                textStyle = TextStyle(fontSize = 25.sp),
                onValueChange = { newText ->
                    nameNextState.value = newText
                    vm.updateItem(index, state.copy(nameNextState = nameNextState.value))
                })
        }
        Column(modifier = Modifier.weight(1.5f)) {
            Text(text = "nextSymbolStack")
            TextField(value = nextSymbolStack.value,
                textStyle = TextStyle(fontSize = 25.sp),
                onValueChange = { newText ->
                    nextSymbolStack.value = newText
                    vm.updateItem(index, state.copy(nextSymbolStack = nextSymbolStack.value))
                })
        }

    }
}