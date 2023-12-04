package `2023`.Day04

import println
import readInput

const val dayString = "Day04"

fun main() {
    fun part1(input: List<String>): Int {
        parseInput(input)

        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("${dayString}_test")
    val input = readInput("$dayString")

    part1(testInput).println()
    part1(input).println()

    part2(testInput).println()
    part2(input).println()
}

fun parseInput(input: List<String>) {
    input.forEach {
        val result = Regex("""Card (\d+):(\d+)+|(\d+)+""").

        result.println()
        result?.groupValues?.size.println()
    }
}