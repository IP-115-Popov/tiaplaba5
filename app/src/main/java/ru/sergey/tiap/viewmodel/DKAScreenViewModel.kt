package ru.sergey.tiap.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.sergey.domain.State
import ru.sergey.tiap.models.DKAClass
import ru.sergey.tiap.models.ShowChain

class DKAScreenViewModel() : ViewModel() {
    private val _items = MutableStateFlow<List<State>>(emptyList())
    val DKD : StateFlow<List<State>> = _items.asStateFlow()
    fun addState() {
        _items.value = _items.value + State("f)") // Создаем mutableStateOf при добавлении
    }

    fun updateItem(oldItem: State, newItem: State) {
        _items.value = _items.value.map {
            if (it.name == oldItem.name) newItem else it
        }
    }
    fun setDKA() {
        DKAClass.DKA = _items.value
    }
}