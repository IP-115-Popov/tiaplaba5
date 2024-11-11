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
        _items.value = _items.value + State(",,=,") // Создаем mutableStateOf при добавлении
    }


    fun setDKA() {
        DPMAClass.DPMA = _items.value
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