package ru.sergey.domain.logic

import ru.sergey.State

class VerificationDPMA {
    companion object {
        fun isPrenadlezhit(nameState : String, chain : String, DPMA : List<State>, maxIterated : Int = 20) : String{
            if (chain.isEmpty()) return "цепочка пуста"
            if (!firstVerification(chain, DPMA)) return "Посторонние символы"
            return Verification(nameState , chain, "Z" , "", DPMA , maxIterated)
        }
        fun firstVerification(chain: String, DPMA: List<State>): Boolean {
            // Собираем все символы из списка состояний
            val validSymbols : List<String> = DPMA.map { it.symbol }

            // Проверяем каждый символ из цепочки
            for (char in chain) {
                // Если символ не найден среди допустимых, возвращаем false
                if (char.toString() !in validSymbols) {
                    return false
                }
            }

            // Если все символы валидны, возвращаем true
            return true
        }
        fun Verification(nameState : String, chain : String, stack : String, transletedChain : String , DPMA : List<State>, maxIterated : Int = 20): String{
            //выход
            if (chain == "" &&  stack == "") return "цепочка подходит" + transletedChain
            //if (chain == "" && stack != "Z" && stack != "") return "цепочка закончилась а стек не пуст" + transletedChain

            //читаем данные из цепочки и стека

            val symbolChain = chain.take(1)
            val symbolStck = stack.take(1)

            var updatedChein = chain.drop(1)
            var updatedStck = stack.drop(1)

            val currentState : State = DPMA.find {
                (
                        it.name == nameState &&
                                it.symbol == symbolChain &&
                                it.stack == symbolStck
                        )
            } ?: return "ненашли состояния что бы обработать цепочку" + updatedChein + "/" + transletedChain

            val nextSymbolStack = currentState.nextSymbolStack
            when(nextSymbolStack) {
                "" -> {
                    updatedStck = updatedStck
                }
                "e" -> {
                    updatedStck = updatedStck
                }
                else -> {
                    updatedStck = nextSymbolStack + updatedStck
                }
            }
            var updatedTransletedChain = transletedChain
            when(currentState.cainSymbol) {
                "" -> {}
                "l" -> {}
                else -> {
                    updatedTransletedChain = updatedTransletedChain + currentState.cainSymbol
                }
            }


            val nextNameState = currentState.nameNextState
            return Verification(nextNameState, updatedChein, updatedStck, updatedTransletedChain, DPMA ,maxIterated -1)
        }
    }
}