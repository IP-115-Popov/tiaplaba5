package ru.sergey.domain

data class Chain(
    var chain: String,
    var isRight: Chain.Status
) {
    enum class Status {
        isRight,
        untested,
        isLeft,
    }
}