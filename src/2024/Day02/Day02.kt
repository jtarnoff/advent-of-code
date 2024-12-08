package `2024`.Day02

import println
import readInput
import kotlin.math.abs

fun main() {
    val MIN_DIFF = 1
    val MAX_DIFF = 3

    fun part1(input: List<String>): Int {
        val lines = input.map { line -> line.split("\\s+".toRegex()).map { it.toInt() } }

        val diffs = lines.map { line ->
            var prevValue = 0
            line.map { level ->
                (level - prevValue)
                    .also { prevValue = level }
            }
        }

        return diffs.count { diffList ->
            val newList = diffList.takeLast(diffList.size - 1)
            val isAllSame = newList.isAllNegative() || newList.isAllPositive()
            val isAllInRange = newList.map { diff ->
                (abs(diff) in MIN_DIFF .. MAX_DIFF)
            }.all { it }
            isAllSame && isAllInRange
        }
    }

    fun part2(input: List<String>): Int {
        val lines = input.map { line -> line.split("\\s+".toRegex()).map { it.toInt() } }

        val lineSubsets = lines.map { line ->
            val listOfPermutations = mutableListOf<List<Int>>()
            line.forEachIndexed { index, _ ->
                listOfPermutations.add(line.subList(0,index) + line.subList(index + 1, line.size))
            }
            listOfPermutations
        }

        return lineSubsets.map { subsets ->
            val subsetDiffs = subsets.map { line ->
                var prevValue = 0
                line.map { level ->
                    (level - prevValue)
                        .also { prevValue = level }
                }
            }.map { diffList ->
                diffList.takeLast(diffList.size - 1)
            }

            val isAllSame = subsetDiffs.map { subset ->
                subset.isAllNegative() || subset.isAllPositive()
            }

            val isAllInRange = subsetDiffs.map { subset ->
                subset.map { diff ->
                    (abs(diff) in MIN_DIFF .. MAX_DIFF)
                }.all { it }
            }

            isAllSame.zip(isAllInRange).any {
                it.first && it.second
            }
        }.count { it }
    }

    val testInput = readInput("Day02_test")
//    val testInput2 = readInput("Day02_test2")
    val input = readInput("Day02")

//    part1(testInput).println()
//    part1(input).println()
//    part2(testInput).println()
    part2(input).println()
}

fun List<Int>.isAllPositive(): Boolean {
    return this.all { it > 0 }
}

fun List<Int>.isAllNegative(): Boolean {
    return this.all { it < 0 }
}