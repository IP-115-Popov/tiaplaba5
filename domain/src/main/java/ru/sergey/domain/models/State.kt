package ru.sergey.domain.models

class State {
    var isFinalState = false
    var name : String
    var path : Map<String, String> // symvol , nameNextState
    constructor(string: String) {
        val arr = string.split(')')
        this.name = arr[0]

        //финнальное состояние либо должно не иметь переходов либо имя должно быть f
        if (name == "f") isFinalState = true

        if (arr.getOrNull(1) == "")
        {
            path = mapOf()
            isFinalState == true
            return
        }

        val pList = mutableMapOf<String, String>()

        val arrPath = arr[1].split("|")
        arrPath.forEach {
            val p = it.split("->")
            pList.put(p[0], p[1])
        }

        this.path = pList
    }
    constructor(name : String, path : Map<String, String>)
    {
        this.name = name
        this.path = path
        if (path.isEmpty()) {
            this.isFinalState = true
        }
    }
    // Добавляем функцию copy
    fun copy(name: String = this.name, path: Map<String, String> = this.path): State {
        return State(name, path)
    }

    override fun toString(): String {
        val pathString : String = path.map { (key, value) ->  key + "->" + value}.joinToString(separator = "|")
        return name + ")" + pathString
    }
}