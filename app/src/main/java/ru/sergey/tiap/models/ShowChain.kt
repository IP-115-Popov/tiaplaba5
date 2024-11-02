package ru.sergey.tiap.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import ru.sergey.domain.models.Chain

data class ShowChain(
    var chain: MutableState<String>, // Храним ссылку на mutableStateOf
    var isRight: ShowChain.Status
) {
    constructor(chain: Chain) : this(mutableStateOf(""), Status.untested) {
        this.chain = mutableStateOf(chain.chain)
        when (chain.isRight) {
            Chain.Status.isRight -> Status.isRight
            Chain.Status.isLeft -> Status.isLeft
            Chain.Status.untested -> Status.untested
        }

    }

    fun getChaun(): Chain = Chain(
        chain = chain.value,
        isRight = when (this.isRight) {
            Status.isRight -> Chain.Status.isRight
            Status.isLeft -> Chain.Status.isLeft
            Status.untested -> Chain.Status.untested
        }
    )

    enum class Status {
        isRight,
        untested,
        isLeft,
    }
}