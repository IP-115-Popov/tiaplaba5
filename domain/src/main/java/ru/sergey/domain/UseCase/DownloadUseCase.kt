package ru.sergey.domain.UseCase

import ru.sergey.domain.repository.FileStorage

class DownloadUseCase(val storage : FileStorage) {
    fun execute() = storage.loadStrings()
}