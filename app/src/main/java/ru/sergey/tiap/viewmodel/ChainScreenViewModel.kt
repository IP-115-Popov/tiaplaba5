package ru.sergey.tiap.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.sergey.domain.logic.LanguageChecking
import ru.sergey.domain.models.State
import ru.sergey.tiap.models.DKAClass
import ru.sergey.tiap.models.ShowChain

class ChainScreenViewModel() : ViewModel() {
    private val _chains = MutableStateFlow<List<ShowChain>>(mutableListOf())
    val chains: StateFlow<List<ShowChain>> = _chains.asStateFlow()

    fun addChain() {
        _chains.value = _chains.value + ShowChain(
            mutableStateOf(""), ShowChain.Status.untested
        ) // Создаем mutableStateOf при добавлении
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

    fun checkChain(): Boolean {
        if (DKAClass.DKA.size < 1) return false //DKA не должен быть пустым
        if (DKAClass.DKA.all({ it -> it.isFinalState == false })) return false//DKA должен иметь конечное сотояние
        val DKA: List<State> = DKAClass.DKA
        _chains.value = _chains.value.map { chain ->
            val sipleChain = chain.getChaun().chain
            chain.copy(
                isRight = if (LanguageChecking.isBelongs(
                        sipleChain,
                        DKA,
                        DKA.first().name
                    )
                ) ShowChain.Status.isRight
                else ShowChain.Status.isLeft
            )
        }
        return true
    }
}