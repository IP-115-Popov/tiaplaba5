package ru.sergey.tiap.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.sergey.domain.logic.VerificationDPMA
import ru.sergey.domain.models.Chain

import ru.sergey.tiap.models.DPMAClass

class ChainScreenViewModel() : ViewModel() {
    private val _chain = MutableStateFlow<Chain>(Chain(
        chain = "",
        isRight = Chain.Status.isLeft
    ))
    val chain: StateFlow<Chain> = _chain.asStateFlow()

    val _rez = MutableStateFlow("")
    val rez = _rez.asStateFlow()


    fun updateChain(chain: Chain) {
        _chain.value = chain
    }

    fun checkChain() {
        if (DPMAClass.DPMA.size < 1) {
            _rez.value = "DKA не должен быть пустым"
            return
        }

        val DPMA = DPMAClass.DPMA

        val sipleChain = _chain.value.chain
        val rez = VerificationDPMA.isPrenadlezhit("q0", _chain.value.chain, DPMA)

        _rez.value = rez

        Log.i("myLog",rez)

    }
}