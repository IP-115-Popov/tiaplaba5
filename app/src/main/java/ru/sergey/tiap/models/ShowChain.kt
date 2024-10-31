package ru.sergey.tiap.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import ru.sergey.domain.Chain

data class ShowChain(
    var chain: MutableState<String>, // Храним ссылку на mutableStateOf
    var isRight: ShowChain.Status
) {
    constructor(chain: Chain) : this(mutableStateOf(""), Status.untested)
    {
        this.chain = mutableStateOf( chain.chain)
        when (chain.isRight)
        {
            Chain.Status.isRight -> Status.isRight
            Chain.Status.isLeft -> Status.isLeft
            Chain.Status.untested -> Status.untested
        }

    }
    fun getChaun() : Chain = Chain(
        chain = chain.value,
        isRight = when (this.isRight) {
            ShowChain.Status.isRight -> Chain.Status.isRight
            ShowChain.Status.isLeft -> Chain.Status.isLeft
            ShowChain.Status.untested -> Chain.Status.untested
        }
    )
    enum class Status {
        isRight,
        untested,
        isLeft,
    }
}