package `2023`.Day12

import println
import readInput
import kotlin.math.pow

const val dayString = "Day12"

fun main() {
    fun part1(input: List<String>): Int {
        val records = parseInput(input)

        records.forEach {
            it.springs.println()
            it.groups.println()
        }

//        val arrangements: Int = processSpring(records[0])
//        arrangements.println()
        val nums = records.map {
            processSpring(it)
        }
        nums.println()

        return nums.sum()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("${dayString}_test")
    val input = readInput("$dayString")

//    part1(testInput).println()
    part1(input).println()
//
//    part2(testInput).println()
//    part2(input).println()
}

fun parseInput(input: List<String>): List<Record> {
    return input.map { row ->
        val springString = row.substringBefore(' ')
        val springs = springString.map {
            Spring.from(it)
        }

        val groupString = row.substringAfter(' ')
        val groups = groupString.split(',').map { it.toInt() }
        Record(springs, groups)
    }
}

enum class Spring(val value: Char) {
    OPERATIONAL('.'),
    DAMAGED('#'),
    UNKNOWN('?');

    companion object {
        fun from(findValue: Char): Spring = Spring.values().first { it.value == findValue }
    }
}

data class Record(
    val springs: List<Spring>,
    val groups: List<Int>
)

fun processSpring(record: Record): Int {
    val numUnknowns = record.springs.count { it == Spring.UNKNOWN }
    numUnknowns.println()

    val binaryList = (0 until (2.toDouble().pow(numUnknowns)).toInt()).map {
        it.binaryString().padStart(numUnknowns, '0')
    }
    binaryList.forEach {
        it.println()
    }

    val b = binaryList.map { binaries ->
        var index = 0
        binaries.println()
        val modded = record.springs.map {
            if (it == Spring.UNKNOWN) {
                when (binaries[index]) {
                    '0' -> Spring.OPERATIONAL
                    '1' -> Spring.DAMAGED
                    else -> Spring.UNKNOWN
                }.also { index++ }
            } else it
        }
        modded.println()
        if (isPossible(modded, record.groups)) 1 else 0
    }
    b.println()
    return b.sum()
}

fun isPossible(modded: List<Spring>, groups: List<Int>): Boolean {
    val list = mutableListOf<Int>()

    var count = 0
    modded.forEach {
        if (it == Spring.DAMAGED) count++
        else if (count > 0) {
            list.add(count)
            count = 0
        }
    }
    if (count > 0) list.add(count)

    return list == groups
}

fun Int.binaryString(): String{
    return Integer.toBinaryString(this)
}

//fun generateBinaryList(n: Int, i: Int): List<Spring> {
//    if (i == 0) return listOf()
//
//    val retList = mutableListOf<Spring>()
//
//    retList.add(Spring.OPERATIONAL)
//    retList.addAll(generateBinaryList(n, i - 1))
//
//    retList.add(Spring.DAMAGED)
//    retList.addAll(generateBinaryList(n,i - 1))
//
//    return retList
//}


//fun fitsConditions(combo: List<Spring>, groups: List<Int>): Boolean {
//
//}


// add 0 to list
// addAll(f(n - 1))

// add 1 to list
// addAll(f(n - 1))


