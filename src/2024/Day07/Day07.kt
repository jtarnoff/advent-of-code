fun main() {
    val DAY = "Day07"

    fun part1(input: List<String>): Long {
        val list = input.map { line ->
            val parts = line.split(": ")
            val result = parts[0]
            val operands = parts[1].split(' ').map { it.toLong() }
            result to operands
        }

        println(12L.concatenate(345L))
        println(12345L.concatenate(67890L))
        println(12L.concatenate(345L))

        list.forEach {
            println("Result: ${it.first} # of operands: ${it.second.size}")
        }

        val operatorList = list.map { equation ->
            val numOperators = equation.second.size - 2

            var lists = mutableListOf<MutableList<Operator>>()
            (0..numOperators).forEach {
                var oldLists = lists.toMutableList()
                var newLists = mutableListOf<MutableList<Operator>>()
                Operator.values().forEach { newOperator ->
                    if (oldLists.isEmpty()) {
                        newLists.add(mutableListOf(newOperator))
                    } else {
                        oldLists.forEach { oldList ->
                            val listToAdd = oldList.toMutableList()
                            listToAdd.add(newOperator)
                            newLists.add(listToAdd)
                        }
                    }
                }
                lists = newLists
            }
            lists
        }

        operatorList.forEach {
            it.println()
            println("size: ${it.size}")
        }

        val eqsPlusOperatorLists = list.zip(operatorList)

        return eqsPlusOperatorLists.sumOf { (equation, operatorsList) ->
            var isValid = false
            val (result, operands) = equation

            var value = 0L

            println("operatorList.size: ${operatorsList.size}")

            operatorsList.forEachIndexed { operatorsIndex, operators ->
                println("operators.size: ${operators.size}")
                operators.forEachIndexed { operatorIndex, operator ->
                    println("operators.size: ${operators.size}")
                    println("operator: $operator")
                    println("operands: $operands")
                    if (operatorIndex == 0) value = operands[0]
                    println("value: $value")
                    println("operatorsIndex: $operatorsIndex")
                    println("operatorIndex: $operatorIndex")
                    if (operatorIndex != operators.size) {
                        when (operator) {
                            Operator.ADD -> value += operands[operatorIndex + 1]
                            Operator.MULTIPLY -> value *= operands[operatorIndex + 1]
                            else -> value
                        }
                    }
                }
                if (value == result.toLong()) {
                    isValid = true
                    println("Found valid combo")
                    println("Equation: $equation | Operators: $operators")
                }
            }
            if (isValid) result.toLong() else 0L
        }

        return 0L
    }

    fun part2(input: List<String>): Long {
        val list = input.map { line ->
            val parts = line.split(": ")
            val result = parts[0]
            val operands = parts[1].split(' ').map { it.toLong() }
            result to operands
        }

        println(12L.concatenate(345L))
        println(12345L.concatenate(67890L))
        println(12L.concatenate(345L))

        list.forEach {
            println("Result: ${it.first} # of operands: ${it.second.size}")
        }

        val operatorList = list.map { equation ->
            val numOperators = equation.second.size - 2

            var lists = mutableListOf<MutableList<Operator>>()
            (0..numOperators).forEach {
                var oldLists = lists.toMutableList()
                var newLists = mutableListOf<MutableList<Operator>>()
                Operator.values().forEach { newOperator ->
                    if (oldLists.isEmpty()) {
                        newLists.add(mutableListOf(newOperator))
                    } else {
                        oldLists.forEach { oldList ->
                            val listToAdd = oldList.toMutableList()
                            listToAdd.add(newOperator)
                            newLists.add(listToAdd)
                        }
                    }
                }
                lists = newLists
            }
            lists
        }

        operatorList.forEach {
            it.println()
            println("size: ${it.size}")
        }

        val eqsPlusOperatorLists = list.zip(operatorList)

        return eqsPlusOperatorLists.sumOf { (equation, operatorsList) ->
            var isValid = false
            val (result, operands) = equation

            var value = 0L

            println("operatorList.size: ${operatorsList.size}")

            operatorsList.forEachIndexed { operatorsIndex, operators ->
                println("operators.size: ${operators.size}")
                operators.forEachIndexed { operatorIndex, operator ->
                    println("operators.size: ${operators.size}")
                    println("operator: $operator")
                    println("operands: $operands")
                    if (operatorIndex == 0) value = operands[0]
                    println("value: $value")
                    println("operatorsIndex: $operatorsIndex")
                    println("operatorIndex: $operatorIndex")
                    if (operatorIndex != operators.size) {
                        when (operator) {
                            Operator.ADD -> value += operands[operatorIndex + 1]
                            Operator.MULTIPLY -> value *= operands[operatorIndex + 1]
                            Operator.CONCATENATE -> value = value.concatenate(operands[operatorIndex + 1])
                        }
                    }
                }
                if (value == result.toLong()) {
                    isValid = true
                    println("Found valid combo")
                    println("Equation: $equation | Operators: $operators")
                }
            }
            if (isValid) result.toLong() else 0L
        }

        return 0L
    }

    val testInput = readInput("${DAY}_test")
//    val testInput2 = readInput("${DAY}_test2")
//    val testInput3 = readInput("${DAY}_test3")
    val input = readInput(DAY)

//    part1(testInput).println()
//    part1(testInput2).println()
//    part1(input).println()

//    part2(testInput).println()
//    part2(testInput2).println()
//    part2(testInput3).println()
    part2(input).println()
}

private fun Long.concatenate(operand: Long): Long {
    return (this.toString() + operand.toString()).toLong()
}

enum class Operator {
    ADD,
    MULTIPLY,
    CONCATENATE
}