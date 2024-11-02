package ru.sergey.domain.repository

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter

interface FileStorage {
    // Сохранение строк в файл
    fun saveStrings(strings: List<String>, file : String)

    // Загрузка строк из файла
    fun loadStrings(file : String): List<String>
}