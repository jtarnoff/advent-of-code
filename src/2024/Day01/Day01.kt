import kotlin.math.abs

fun main() {
    val testInput = readInput("Day01_test")
//    val testInput2 = readInput("Day01_test2")
    val input = readInput("Day01")

    part1(input).println()
    part2(input).println()
}

fun part1(input: List<String>): Int {
    val lines = input.map { it.split("\\s+".toRegex()) }
    val left = lines.map { it.first() }.sorted()
    val right = lines.map { it.last() }.sorted()

    val distances = left.mapIndexed { index, left ->
        abs(left.toInt() - right[index].toInt())
    }

    return distances.sumOf { it }
}

fun part2(input: List<String>): Int {
    val lines = input.map { it.split("\\s+".toRegex()).map { str -> str.toInt() } }
    val left = lines.map { it.first() }.sorted()
    val right = lines.map { it.last() }.sorted()

    return left.sumOf { leftItem -> leftItem * right.count { rightItem -> rightItem == leftItem } }
}