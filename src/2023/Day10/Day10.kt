package `2023`.Day10

import println
import readInput

const val dayString = "Day10"

fun main() {
    fun part1(input: List<String>): Int {
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

fun parseInput(input: List<String>) {

}

class Point2D(x: Int, y: Int, pipes: Char) {
    val x: Int = x
    val y: Int = y
    val pipes: Byte = getPipesFromChar(pipes) // Up, Down, Left, Right

    fun getPipesFromChar(pipesChar: Char): Byte {
        when (pipesChar) {
            '|' -> "1100"
        }

        return 0.toByte()
    }
}