package ru.sergey.data

import android.content.Context
import ru.sergey.domain.repository.FileStorage
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class FileStorageImp(private val context: Context) : FileStorage {

    private fun getFile(file : String): File {
        return File(context.filesDir, file) // Имя файла для хранения данных
    }

    // Сохранение строк в файл
    override fun saveStrings(strings: List<String>,file : String) {
        val file = getFile(file)
        FileOutputStream(file).use {
            OutputStreamWriter(it).use { writer ->
                strings.forEach { string ->
                    writer.write(string)
                    writer.write("\n") // Записываем каждую строку в новый ряд
                }
            }
        }
    }

    // Загрузка строк из файла
    override fun loadStrings(file : String): List<String> {
        val file = getFile(file)
        return if (file.exists()) {
            InputStreamReader(FileInputStream(file)).use { inputStreamReader ->
                inputStreamReader.readLines() // Читаем все строки из файла
            }
        } else {
            emptyList() // Возвращаем пустой список, если файл не существует
        }
    }
}