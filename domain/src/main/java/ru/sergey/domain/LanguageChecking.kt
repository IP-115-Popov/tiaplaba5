package ru.sergey.domain

object LanguageChecking {
    fun isBelongs(input: String, dka: List<State>, nameStartState : String, maxStep : Int = 20) : Boolean{
        if (maxStep == 0) return true
        val nextMaxStep = maxStep - 1

        var string = input
        val currentSymbol = input.getOrNull(0)
        val currentState : State = dka.find { it.name == nameStartState } ?: throw Exception("not find State")
        if (currentSymbol == null) //строка закончилась
        {
            if (currentState.isFinalState) return true
            else return false
        }
        val nextStateString = currentState.path.getOrDefault(currentSymbol.toString(), null)

        if (nextStateString == null)//коненый афтомат не нашли следующих состаяний куда перейти
            if (currentSymbol != null) return false //строка пуста

        return isBelongs(input.drop(1), dka, nextStateString!!, nextMaxStep)
    }
}