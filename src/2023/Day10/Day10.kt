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
    val pipes: Int = getPipeFromChar(pipes) // Up, Down, Left, Right

    fun getPipeFromChar(pipesChar: Char): Int {
        when (pipesChar) {
            '|' -> 12 // 1100
            '-' -> 3 // 0011
            'F' -> 5 // 0101
            'L' -> 7 // 1001
            '7' -> 6 // 0110
            'J' -> 10 // 1010
            '.' -> 0
            'S' -> 15
        }

        return 0
    }
}