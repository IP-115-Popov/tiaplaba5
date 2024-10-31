package ru.sergey.domain

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
}