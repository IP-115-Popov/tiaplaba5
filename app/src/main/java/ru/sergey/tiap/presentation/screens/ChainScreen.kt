package ru.sergey.tiap.presentation.screens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.sergey.tiap.R
import ru.sergey.tiap.models.ShowChain
import ru.sergey.tiap.viewmodel.ChainScreenViewModel
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChainScreen(vm: ChainScreenViewModel = viewModel()) {
    val chains = vm.chains.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(chains.value) { chain ->
                ChainItem(vm, chain) { updatedChain ->
                    vm.updateChain(updatedChain)
                }
            }
        }
        Button(
            onClick = {
                vm.addChain()
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
            Text(stringResource(R.string.add), fontSize = 25.sp)
        }
        Button(
            onClick = {
                vm.checkChain()
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
            Text("Check", fontSize = 25.sp)
        }
    }

}
@Composable
fun ChainItem(vm : ChainScreenViewModel,chain: ShowChain, onChainChanged: (ShowChain) -> Unit) {
    val text = chain.chain
    val placeholderText = "chain"
    StyledTextField(text, placeholderText, chain.isRight, vm)
}
@Composable
fun  StyledTextField(
    text: MutableState<String>,
    placeholderText: String,
    isRight : ShowChain.Status,
    vm: ChainScreenViewModel
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var showInfoDialog = remember { mutableStateOf(false) }

    OutlinedTextField(
        placeholder = { Text(placeholderText) },
        value = text.value,
        singleLine = true,
        textStyle = TextStyle(fontSize = 25.sp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xffeeeeee),
            unfocusedTextColor = Color(0xff888888),
            focusedContainerColor = Color.White,
            focusedTextColor = Color(0xff222222),
        ),
        leadingIcon = {
            if (isRight == ShowChain.Status.untested) Icon(Icons.Filled.Refresh, contentDescription = "Непроверено")
            else if (isRight == ShowChain.Status.isRight) Icon(Icons.Filled.Check, contentDescription = "Цепочка подходит")
            else  Icon(Icons.Filled.Clear, contentDescription = "Цепочка не подходит") },
        trailingIcon = {
            Icon(
                Icons.Filled.Info,
                contentDescription = "Дополнительная информация",
                Modifier.clickable {  showInfoDialog.value = true }
            )
        },
        onValueChange = { newText -> text.value = newText },
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide();
                vm.checkChain()
            })
    )
    if (showInfoDialog.value) {
        Dialog(onDismissRequest = { showInfoDialog.value = false }) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Это место для ввода цепочки")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { showInfoDialog.value = false }) {
                    Text("Закрыть")
                }
            }
        }
    }
}