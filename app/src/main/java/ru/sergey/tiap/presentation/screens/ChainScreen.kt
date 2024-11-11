package ru.sergey.tiap.presentation.screens


import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.sergey.domain.models.Chain
import ru.sergey.tiap.R
import ru.sergey.tiap.viewmodel.ChainScreenViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChainScreen(vm: ChainScreenViewModel = viewModel()) {
    val chain = vm.chain.collectAsState()
    val checkChainToast = Toast.makeText(
        LocalContext.current, stringResource(R.string.checkingChains), Toast.LENGTH_SHORT
    )
    val failCheckChainToast = Toast.makeText(
        LocalContext.current, stringResource(R.string.fail_check), Toast.LENGTH_SHORT
    )
    Box(modifier = Modifier.fillMaxSize()) {

        StyledTextField(chain, placeholderText = "chain", chain.value.isRight, vm)

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
fun StyledTextField(
    text: State<Chain>,
    placeholderText: String,
    isRight: Chain.Status,
    vm: ChainScreenViewModel
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val showInfoDialog = remember { mutableStateOf(false) }

    OutlinedTextField(
        placeholder = { Text(placeholderText) },
        value = text.value.chain,
        singleLine = true,
        textStyle = TextStyle(fontSize = 25.sp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xffeeeeee),
            unfocusedTextColor = Color(0xff888888),
            focusedContainerColor = Color.White,
            focusedTextColor = Color(0xff222222),
        ),
        leadingIcon = {
            when (isRight) {
                Chain.Status.untested -> Icon(
                    Icons.Filled.Refresh, contentDescription = stringResource(R.string.Unchecked)
                )
                Chain.Status.isRight -> Icon(
                    Icons.Filled.Check, contentDescription = stringResource(R.string.chain_fits)
                )
                else -> Icon(Icons.Filled.Clear, contentDescription = stringResource(R.string.chain_does_not_fit))
            }
        },
        trailingIcon = {
            Icon(Icons.Filled.Info,
                contentDescription = stringResource(R.string.Additional_information),
                Modifier.clickable { showInfoDialog.value = true })
        },
        onValueChange = { newText -> text.value.chain = newText },
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
            vm.checkChain()
        })
    )
    if (showInfoDialog.value) {
        Dialog(onDismissRequest = { showInfoDialog.value = false }) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.place_chain))
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { showInfoDialog.value = false }) {
                    Text(stringResource(R.string.close))
                }
            }
        }
    }
}