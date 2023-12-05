package `2023`.Day04

import println
import readInput
import kotlin.math.pow

const val dayString = "Day04"

fun main() {
    fun part1(input: List<String>): Int {
        val cards = parseInput(input)

        return cards.sumOf { card ->
            val numWins = card.winningNumbers.fold(0) { acc, winningNumber ->
                if (card.cardNumbers.contains(winningNumber)) acc + 1 else acc
            }
            if (numWins == 0) 0.toDouble() else 2.toDouble().pow(numWins - 1)
        }.toInt()
    }

    fun part2(input: List<String>): Int {
        val cards = parseInput(input)

        val map = mutableMapOf<ScratchCard, Int>()
        cards.associateWithTo(map) { 1 }

        map.forEach { (card, quantity) ->
            val numWins = card.winningNumbers.fold(0) { acc, winningNumber ->
                if (card.cardNumbers.contains(winningNumber)) acc + 1 else acc
            }
            repeat(quantity) {
                repeat(numWins) {
                    map.increment(cards[card.index + it])
                }
            }
        }

        return map.values.sum()




//        cards.forEach {
//            return cards.sumOf { card ->
//                val numWins = card.winningNumbers.fold(0) { acc, winningNumber ->
//                    if (card.cardNumbers.contains(winningNumber)) acc + 1 else acc
//                }
//                println(card.index)
//                println(if (numWins == 0) 0.toDouble() else 2.toDouble().pow(numWins - 1))
//                if (numWins == 0) 0.toDouble() else 2.toDouble().pow(numWins - 1)
//            }.toInt()
//        }
    }

    val testInput = readInput("${dayString}_test")
    val input = readInput("$dayString")

//    part1(testInput).println()
//    part1(input).println()

//    part2(testInput).println()
    part2(input).println()
}

fun parseInput(input: List<String>): List<ScratchCard> {
    val list = mutableListOf<ScratchCard>()
    input.forEach {
        val (index, winningStr, cardNumbersStr) =
            Regex("""Card\s+(\d+):\s+([0-9 ]+)\|\s+([0-9 ]+)""").matchEntire(it)!!.destructured

//        index.println()
//        winningStr.println()
//        cardNumbersStr.println()

        list.add(
            ScratchCard(
                index.toInt(),
                winningStr.split(' ').filter { it != "" }.map { it.toInt() }.toSet(),
                cardNumbersStr.split(' ').filter { it != "" }.map { it.toInt() }.toSet()
            )
        )
    }
    return list
}

data class ScratchCard(
    val index: Int,
    val winningNumbers: Set<Int>,
    val cardNumbers: Set<Int>
)

fun MutableMap<ScratchCard, Int>.increment(card: ScratchCard) {
    this[card] = this[card]?.plus(1) ?: 1
}