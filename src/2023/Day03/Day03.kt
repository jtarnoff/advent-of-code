package `2023`.Day03

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val xMax = input[0].length - 1
        val yMax = input.size - 1

        val engineParts: MutableSet<EnginePart> = mutableSetOf<EnginePart>()
        input.forEachIndexed { index, str ->
            engineParts.addAll(parseParts(str, index))
        }

        val schematic = EngineSchematic(engineParts, xMax, yMax)

        return engineParts.filter { part ->
            !part.isSymbol && isPartAdjacentToSymbol(part, schematic)
        }.sumOf { part ->
            part.partNumber.toInt()
        }
    }

    fun part2(input: List<String>): Int {
        val xMax = input[0].length - 1
        val yMax = input.size - 1

        val engineParts: MutableSet<EnginePart> = mutableSetOf<EnginePart>()
        input.forEachIndexed { index, str ->
            engineParts.addAll(parseParts(str, index))
        }

        val schematic = EngineSchematic(engineParts, xMax, yMax)

        return engineParts.filter { it.partNumber == "*" }.sumOf {
            getGearRatio(it, schematic)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    val input = readInput("Day03")

    part1(testInput).println()
    part1(input).println()

    part2(testInput).println()
    part2(input).println()
}

data class EngineSchematic(
    val parts: Set<EnginePart>,
    val xMax: Int,
    val yMax: Int
)

data class Coordinates(
    val x: Int,
    val y: Int
)

data class EnginePart(
    val partNumber: String,
    val startCoordinates: Coordinates,
    val isSymbol: Boolean
)

fun isPartAdjacentToSymbol(part: EnginePart, schematic: EngineSchematic): Boolean {
    val symbols = schematic.parts.filterTo(HashSet()) { it.isSymbol }
    val area = getAdjacentCoordinatesSet(part, schematic)

    return isSymbolInArea(symbols, area)
}

fun getAdjacentCoordinatesSet(part: EnginePart, schematic: EngineSchematic): Set<Coordinates> {
    val xRange =
        IntRange(
            (part.startCoordinates.x - 1).coerceAtLeast(0),
            (part.startCoordinates.x + part.partNumber.length).coerceAtMost(schematic.xMax)
        )
    val yRange =
        IntRange(
            (part.startCoordinates.y - 1).coerceAtLeast(0),
            (part.startCoordinates.y + 1).coerceAtMost(schematic.yMax)
        )

    val coordinatesSet = mutableSetOf<Coordinates>()
    xRange.forEach { x ->
        yRange.forEach { y ->
            coordinatesSet.add(Coordinates(x, y))
        }
    }

    return coordinatesSet
}

fun isSymbolInArea(symbols: Set<EnginePart>, area: Set<Coordinates>) =
    symbols.any { part ->
        area.contains(part.startCoordinates)
    }

fun parseParts(input: String, row: Int): Set<EnginePart> =
    Regex("""(\d+|!|@|#|\$|%|\^|&|\*|\+|-|=|/)""").findAll(input)
        .map {
        EnginePart(
            it.value,
            Coordinates(it.range.first, row),
            !Regex("""\d+""").matches(it.value)
        )
    }.toSet()

fun getGearRatio(part: EnginePart, schematic: EngineSchematic): Int {
    val area = getAdjacentCoordinatesSet(part, schematic)
    val adjacentNums = schematic.parts
        .filter { part ->
            val coordinatesList = mutableListOf<Coordinates>()
            repeat(part.partNumber.length) {
                coordinatesList.add(part.startCoordinates.incrementX(it))
            }
            !part.isSymbol && coordinatesList.any { area.contains(it) }
        }
    return if (adjacentNums.size == 2) {
        adjacentNums[0].partNumber.toInt() * adjacentNums[1].partNumber.toInt()
    } else 0
}

fun Coordinates.incrementX(num: Int): Coordinates {
    return Coordinates(this.x + num, this.y)
}