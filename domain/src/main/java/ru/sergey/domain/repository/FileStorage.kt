package ru.sergey.domain.repository

interface FileStorage {
    // Сохранение строк в файл
    fun saveStrings(strings: List<String>, file : String)

    // Загрузка строк из файла
    fun loadStrings(file : String): List<String>
}