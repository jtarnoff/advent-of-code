package `2023`.Day09

import println
import readInput

const val dayString = "Day09"

fun main() {
    fun part1(input: List<String>): Int {
        val numberLists = parseInput(input)

        return numberLists.sumOf {
            processNext(it)
        }
    }

    fun part2(input: List<String>): Int {
        val numberLists = parseInput(input)

        return numberLists.sumOf {
            processPrevious(it)
        }
    }

    val testInput = readInput("${dayString}_test")
    val input = readInput("$dayString")

//    part1(testInput).println()
//    part1(input).println()
//
//    part2(testInput).println()
    part2(input).println()
}

fun parseInput(input: List<String>): List<List<Int>> {
    return input.map {
        it.split(' ').map {
            it.toInt()
        }
    }
}

fun processNext(nums: List<Int>): Int {
    val listOfLists = mutableListOf(nums)
    var currentList = nums
    var reachedZeroes = false
    while (!reachedZeroes) {
        currentList = (1 until currentList.size).map {
            currentList[it] - currentList[it - 1]
        }
        listOfLists.add(currentList)
        reachedZeroes = currentList.all { it == 0 }
    }

    listOfLists.forEach {
        it.println()
    }

    return listOfLists.reversed().sumOf {
        it[it.size - 1]
    }
}

fun processPrevious(nums: List<Int>): Int {
    val listOfLists = mutableListOf(nums)
    var currentList = nums
    var reachedZeroes = false
    while (!reachedZeroes) {
        currentList = (1 until currentList.size).map {
            currentList[it] - currentList[it - 1]
        }
        listOfLists.add(currentList)
        reachedZeroes = currentList.all { it == 0 }
    }

    return listOfLists.reversed().fold(0) { acc, it ->
        it[0] - acc
    }
}