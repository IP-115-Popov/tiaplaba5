package ru.sergey.domain.models

data class Chain(
    var chain: String,
    var isRight: Status
) {
    enum class Status {
        isRight,
        untested,
        isLeft,
    }
}