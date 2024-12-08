package `2024`.Day03

import println
import readInput
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            val regex = """mul\(\d+,\d+\)""".toRegex()
            val matches = regex.findAll(it)

            val products = matches.map {
                val innerRegex = """\d+""".toRegex()
                val innerMatches = innerRegex.findAll(it.value)

                innerMatches.fold(1) { acc, it ->
                    acc * it.value.toInt()
                }
            }
            products.sum()
        }
    }

    fun part2(input: List<String>): Int {
        val newInput = """(?:^|do\(\)).*?(?:don't\(\)|$)""".toRegex()
            .findAll(input.joinToString())
            .map { it.value }
            .joinToString()

        val matches = """mul\(\d+,\d+\)""".toRegex().findAll(newInput)
        val products = matches.map {
            val innerRegex = """\d+""".toRegex()
            val innerMatches = innerRegex.findAll(it.value)

            innerMatches.fold(1) { acc, it ->
                acc * it.value.toInt()
            }
        }
        return products.sum()
    }

    val testInput = readInput("Day03_test")
//    val testInput2 = readInput("Day03_test2")
    val input = readInput("Day03")

//    part1(testInput).println()
//    part1(input).println()
//    part2(testInput).println()
    part2(input).println()
}