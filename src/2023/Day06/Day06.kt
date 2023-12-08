package `2023`.Day06

import println
import readInput

const val dayString = "Day06"

fun main() {
    fun part1(input: List<String>): Int {
        val list = parseInput(input)

        val races = list[0].zip(list[1])
            .map { Race(it.first, it.second) }

        return races.fold(1) { acc, race ->
            (0 until race.time).map { timeHeld ->
                timeHeld * (race.time - timeHeld)
            }.count { distance ->
                distance > race.distance
            } * acc
        }
    }

    fun part2(input: List<String>): Int {
        val parsed = parseInput2(input)
        val race = RaceLong(parsed[0], parsed[1])

        val e = (0 until race.time).map { timeHeld ->
            timeHeld * (race.time - timeHeld)
        }.count { distance ->
            distance > race.distance
        }

        return e
    }

    val testInput = readInput("${dayString}_test")
    val input = readInput("$dayString")

//    part1(testInput).println()
//    part1(input).println()

    part2(testInput).println()
    part2(input).println()
}

fun parseInput(input: List<String>): List<List<Int>> {
    return input.indices.map {
        Regex(""".*:\s+(.*)""")
            .find(input[it])!!
            .groupValues[1]
            .split(' ')
            .filter { it.isNotEmpty() }
            .map { it.toInt() }
    }
}

fun parseInput2(input: List<String>): List<Long> {
    return input.indices.map {
        Regex(""".*:\s+(.*)""")
            .find(input[it])!!
            .groupValues[1]
            .filter { !it.isWhitespace() }
            .toLong()
    }
}

data class Race(
    val time: Int,
    val distance: Int
)

data class RaceLong(
    val time: Long,
    val distance: Long
)