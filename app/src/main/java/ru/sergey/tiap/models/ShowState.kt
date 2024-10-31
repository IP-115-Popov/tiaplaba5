package ru.sergey.tiap.models

data class ShowState(
    var isFinalState : Boolean,
    var name : String,
    var path : Map<String, String>,
)