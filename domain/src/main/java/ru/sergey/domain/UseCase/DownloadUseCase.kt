package ru.sergey.domain.UseCase

import ru.sergey.domain.repository.FileStorage

class DownloadUseCase(val storage : FileStorage) {
    fun execute(file : String) = storage.loadStrings(file)
}