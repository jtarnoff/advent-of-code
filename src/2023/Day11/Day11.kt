package `2023`.Day11

import println
import readInput
import kotlin.math.abs

const val dayString = "Day11"

fun main() {
    fun part1(input: List<String>): Int {
        val universe = parseInput(input)

        universe.forEach { row ->
            row.forEach {
                it.println()
            }
        }

        val emptyCols = findEmptyCols(universe)
        val emptyRows = findEmptyRows(universe)

        emptyCols.println()
        emptyRows.println()

        // expand universe
        val expandedCols = universe.map {
            addCols(it, emptyCols)
        }
        expandedCols.forEach { row ->
            row.forEach {
                it.println()
            }
        }

        val expandedUniverse = addRows(expandedCols, emptyRows)
        expandedUniverse.forEach { row ->
            row.forEach {
                it.println()
            }
        }

        val galaxies = expandedUniverse.map {
            it.filter { it.isGalaxy }
        }.flatten()
        galaxies.println()

        var sumOfLengths = 0
        galaxies.forEachIndexed { index, galaxy ->
            (index + 1 until galaxies.size).forEachIndexed { index, point ->
                println("distance(galaxies[index], galaxies[point]) ${distance(galaxies[index], galaxies[point])}")
                println("index: $index and otherIndex: $point")
                sumOfLengths += distance(galaxies[index], galaxies[point])
            }
        }

        return sumOfLengths
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("${dayString}_test")
    val input = readInput("$dayString")

    part1(testInput).println()
    part1(input).println()
//
//    part2(testInput).println()
//    part2(input).println()
}

fun parseInput(input: List<String>): List<List<SpacePoint>> {
    return input.mapIndexed { rowIndex, row ->
        row.mapIndexed { colIndex, col ->
            SpacePoint(colIndex, rowIndex, col == '#')
        }
    }
}

data class SpacePoint(
    val x: Int,
    val y: Int,
    val isGalaxy: Boolean
)

data class Universe(
    val grid: List<List<SpacePoint>>,
    val emptyCols: List<Int>,
    val emptyRows: List<Int>
)

fun findEmptyRows(universe: List<List<SpacePoint>>): List<Int> {
    return universe.mapIndexed { index, spacePoints ->
        if (spacePoints.all { !it.isGalaxy }) index else null
    }.filterNotNull()
}

fun findEmptyCols(universe: List<List<SpacePoint>>): List<Int> {
    return (0 until universe[0].size).map { index ->
        if (universe.all { !it[index].isGalaxy }) index else null
    }.filterNotNull()
}

fun addCols(row: List<SpacePoint>, listEmptyCols: List<Int>): List<SpacePoint> {
    val newList = mutableListOf<SpacePoint>()
    var currentIndex = 0
    row.forEachIndexed { index, point ->
        repeat(if (listEmptyCols.contains(index)) 2 else 1) {
            newList.add(SpacePoint(currentIndex++, point.y, point.isGalaxy))
        }
    }
    return newList
}

fun addRows(universe: List<List<SpacePoint>>, listEmptyRows: List<Int>): List<List<SpacePoint>> {
    val newList = mutableListOf<List<SpacePoint>>()
    var currentIndex = 0
    universe.forEachIndexed { index, row ->
        repeat(if (listEmptyRows.contains(index)) 2 else 1) {
            newList.add(expandedRow(row, currentIndex++))
        }
    }
    return newList
}

fun expandedRow(row: List<SpacePoint>, currentIndex: Int): List<SpacePoint> {
    return row.mapIndexed { index, point ->
        SpacePoint(point.x, currentIndex, point.isGalaxy)
    }
}

fun distance(p1: SpacePoint, p2: SpacePoint): Int {
    return abs(p1.x - p2.x) + abs(p1.y - p2.y)
}