package `2023`.Day10

import println
import readInput

const val dayString = "Day10"

fun main() {
    fun part1(input: List<String>): Int {
        val grid = parseInput(input)

        grid.println()

        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("${dayString}_test")
    val input = readInput("$dayString")

    part1(testInput).println()
//    part1(input).println()
//
//    part2(testInput).println()
//    part2(input).println()
}

fun parseInput(input: List<String>): List<List<Point2D>> {
    return input.mapIndexed { y, str ->
        str.mapIndexed { x, chr ->
            Point2D(x, y, getPipeFromChar(chr))
        }
    }
}

class Point2D(x: Int, y: Int, pipe: Int) {
    val x: Int = x
    val y: Int = y
    val pipe: Int = pipe
}

fun getPipeFromChar(pipesChar: Char): Int { // Up, Down, Left, Right
    return when (pipesChar) {
        '|' -> 12 // 1100
        '-' -> 3 // 0011
        'F' -> 5 // 0101
        'L' -> 7 // 1001
        '7' -> 6 // 0110
        'J' -> 10 // 1010
        '.' -> 0
        'S' -> 15
        else -> 0
    }
}