package ru.sergey.tiap.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.sergey.State
import ru.sergey.data.FileStorageImp
import ru.sergey.domain.UseCase.DownloadUseCase
import ru.sergey.domain.UseCase.UploadUseCase
import ru.sergey.tiap.models.DPMAClass

class DKAScreenViewModel(context: Context) : ViewModel() {
    private val _items = MutableStateFlow<List<State>>(emptyList())
    val DKD: StateFlow<List<State>> = _items.asStateFlow()

    val fileStorage = FileStorageImp(context)
    val downloadUseCase = DownloadUseCase(fileStorage)
    val uploadUseCase = UploadUseCase(fileStorage)

    var fileList : List<String>
        get() = downloadUseCase.execute("fileListSave").distinct()
        set(value) {uploadUseCase.execute(value.distinct(), "fileListSave")}

    fun DownloadDKA(file : String){
        _items.value = downloadUseCase.execute(file).map { State(it) }
    }
    fun UploadDKA(file : String){
        uploadUseCase.execute(_items.value.map { it.ToString() }, file)
    }

    fun addState() {
        _items.value = _items.value + State("f)") // Создаем mutableStateOf при добавлении
    }

    fun updateItemName(index: Int, stateString: String) {
        _items.value = _items.value.mapIndexed { indexInMap, it ->
            if (index == indexInMap) {
                //нашли изменёный элемент
                it.copy(name = stateString)
            } else {
                it
            }
        }
    }

    fun <K, V> List<K>.zipToMap(values: List<V>): Map<K, V> {
        require(size == values.size) { "Lists must have the same size" } // Проверка на одинаковую длину
        return zip(values).associate { (key, value) -> key to value } // Создание Map с помощью zip и associate
    }

    fun updateItemsPath(
        index: Int,
        stateString: String,
        symbols: List<String>,
        newStatesString: List<String>
    ) {
        //проверил значение приходит надо занести
        _items.value = _items.value.mapIndexed { indexInMap, it ->
            if (index == indexInMap) {
                it
            } else {
                it
            }
        }
    }

    fun addPathToState(index: Int, symbol: String, newStateString: String) {
        _items.value = _items.value.mapIndexed { indexInMap, it ->
            if (index == indexInMap) {
                it
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

        fun mergeItems(states: List<State>): List<State> {
            val groupedItems = states.groupBy { it.name }

            return groupedItems.map { (name, path) ->
                // Объединяем вложенные map для элементов с одинаковым именем
                val mergedMap = path.fold(HashMap<String, String>()) { acc, item ->
                    acc
                }
                State("")
            }
        }
        DPMAClass.DPMA = mergeItems(_items.value)
    }

    fun updateItem(index: Int, newItem: State) {
        _items.value = _items.value.mapIndexed { indexInMap, it ->
            if (index == indexInMap) {
                //нашли изменёный элемент
                newItem
            } else {
                it
            }
        }
    }
}