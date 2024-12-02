package ru.sergey

class State {
    var name: String = "f"
    var symbol: String = ""
    var stack: String = ""
    var nameNextState: String = "f"
    var nextSymbolStack = ""
    var cainSymbol = ""

    constructor(input: String) {
        // Разделим строку сначала по символу '='
        val parts = input.split("=")

        // Получим части слева от '=' и справа от '='
        val leftPart = parts[0].split(",").map {
            if (it == "l") ""
            else it
        } // p1, p2, p3
        val rightPart = parts[1].split(",").map {
            if (it == "l") ""
            else it
        } // p4, p5, p6

        // Присвоим значения переменным
        val (p1, p2, p3) = leftPart
        val (p4, p5, p6) = rightPart

        name = p1
        symbol = p2
        stack = p3
        nameNextState = p4
        nextSymbolStack = p5
        cainSymbol = p6
    }

    constructor(
        name: String = "f",
        symbol: String = "",
        stack: String = "",
        nameNextState: String = "f",
        nextSymbolStack: String = "",
        cainSymbol: String = ""
    ) {
        this.name = name
        this.symbol = symbol
        this.stack = stack
        this.nameNextState = nameNextState
        this.nextSymbolStack = nextSymbolStack
        this.cainSymbol = cainSymbol
    }

    fun ToString() = name + "," + symbol + "," + stack + "=" + nameNextState + "," + nextSymbolStack + "," + cainSymbol

    fun copy(
        name: String = this.name,
        symbol: String = this.symbol,
        stack: String = this.stack,
        nameNextState: String = this.nameNextState,
        nextSymbolStack: String = this.nextSymbolStack,
        cainSymbol: String = this.cainSymbol
    ): State {
        val newState = State("$name,$symbol,$stack=$nameNextState,$nextSymbolStack,$cainSymbol")
        return newState
    }
}
