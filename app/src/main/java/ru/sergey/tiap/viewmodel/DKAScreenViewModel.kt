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

    fun updateItem(index : Int, stateString : String, symbol : String, newStateString : String) {
        _items.value = _items.value.mapIndexed {indexInMap, it ->
            if (index == indexInMap) {
                //нашли изменёный элемент
                //
                if (symbol != "" || newStateString != "") {
                    val a: String = stateString + ")" + symbol + "->" + newStateString
                    State(a)
                } else {
                    val a: String = stateString + ")"
                    State(a)
                }
            } else {
                it
            }
        }
    }
    fun updateItem(oldItem: State, newItem: State) {
        _items.value = _items.value.map {
            if (it.name == oldItem.name) newItem else it
        }
    }
    fun setDKA() {
        //val grupState = _items.value.groupBy { it.name }

        fun mergeItems(states: List<State>): List<State> {
            val groupedItems = states.groupBy { it.name }

            return groupedItems.map { (name, path) ->
                // Объединяем вложенные map для элементов с одинаковым именем
                val mergedMap = path.fold(HashMap<String, String>()) { acc, item ->
                    acc.putAll(item.path) // Добавляем элементы из каждого вложенного map
                    acc
                }
                State(name, mergedMap)
            }
        }
        DKAClass.DKA = mergeItems(_items.value)
        //val arr = text.split(".").filterNot { it == "" }
        //DKAClass.DKA = arr.map { State(it)}
        //DKAClass.DKA = _items.value
    }
}