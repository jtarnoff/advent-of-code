package `2023`.Day02

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val games: List<Game> = input.map {
             generateGameObject(it)
        }

        val colorMap = mapOf(
            CubeColor.RED to 12,
            CubeColor.GREEN to 13,
            CubeColor.BLUE to 14
        )

        val count = games.sumOf { game ->
            if (isGamePossible(game, colorMap)) game.index else 0
        }

        return count
    }

    fun part2(input: List<String>): Int {
        val games: List<Game> = input.map {
            generateGameObject(it)
        }

        val colorSet = setOf(CubeColor.RED, CubeColor.GREEN, CubeColor.BLUE)

        return games.map { game ->
            colorSet.fold(1) { acc, cubeColor ->
                val maxOfColor = game.turns.maxOf { turn ->
                    turn.sets.firstOrNull { it.color == cubeColor }?.num ?: 0
                }
                println("${game.index} - $cubeColor - $maxOfColor")
                acc * maxOfColor
            }
        }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    val input = readInput("Day02")

    part1(testInput).println()
    part1(input).println()

    part2(testInput).println()
    part2(input).println()
}

data class CubeSet(val num: Int, val color: CubeColor)

data class Game(val index: Int, val turns: List<Turn>)

data class Turn(val sets: List<CubeSet>)

enum class CubeColor(val colorString: String) {
    RED("red"),
    GREEN("green"),
    BLUE("blue");

    companion object {
        fun from(findValue: String): CubeColor = CubeColor.values().first { it.colorString == findValue }
    }
}

fun generateGameObject(input: String): Game {
    // parse input
    val (left, right) = input.split(':').map { it.trim() }
    val turns = right.split(';').map { it.trim() }
    val index = left.split(' ').get(1).toInt()

    // store as game object
    return Game(
        index,
        turns.map { turn ->
            Turn(
                turn.split(',').map {
                    val (num, colorString) = it.trim().split(' ')
                    CubeSet(num.toInt(), CubeColor.from(colorString))
                }
            )
        })
}

fun isGamePossible(
    game: Game,
    colorMap: Map<CubeColor, Int>
): Boolean {
    fun isColorSupported(cubeSetList: List<CubeSet>, cubeColor: CubeColor): Boolean =
        cubeSetList.sumOf {
            if (it.color == cubeColor) it.num else 0
        } <= colorMap[cubeColor]!!

    fun isTurnSupported(turn: Turn): Boolean =
        colorMap.keys.all { color ->
            isColorSupported(turn.sets, color)
        }

    return game.turns.all { turn ->
        isTurnSupported(turn)
    }
}