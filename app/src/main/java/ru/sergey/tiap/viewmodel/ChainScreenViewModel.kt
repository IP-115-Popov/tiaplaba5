package ru.sergey.tiap.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.sergey.domain.LanguageChecking
import ru.sergey.domain.State
import ru.sergey.tiap.models.ShowChain

class ChainScreenViewModel() : ViewModel(){
    private val _chains = MutableStateFlow<List<ShowChain>>(mutableListOf())
    val chains: StateFlow<List<ShowChain>> = _chains.asStateFlow()

    fun addChain() {
        _chains.value = _chains.value + ShowChain(mutableStateOf(""), ShowChain.Status.untested) // Создаем mutableStateOf при добавлении
    }

    fun updateChain(chain: ShowChain) {
        _chains.value = _chains.value.map {
            if (it.chain == chain.chain) {
                chain
            } else {
                it
            }
        }
    }
    fun checkChain() {
        val q1 = State("q1", mapOf("a" to "q2", "b" to "q3"))
        val q2 = State("q2", mapOf("a" to "f"))
        val q3 = State("q3", mapOf("b" to "f"))
        val q4 = State("f", mapOf())
        val DKA : List<State> = listOf(q1,q2,q3,q4)

        _chains.value = _chains.value.map {chain ->
            chain.copy(isRight =
                if(LanguageChecking.isBelongs(chain.getChaun().chain, DKA, "q1"))
                    ShowChain.Status.isRight
                else
                    ShowChain.Status.isLeft
            )
        }
    }
}