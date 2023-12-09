package `2023`.Day08

import println
import readInput

const val dayString = "Day08"

fun main() {
    fun part1(input: List<String>): Int {
        val instructionsString = input[0]
        val nodes = parseInput(input)

        var currentNode = nodes.first { it.node == "AAA" }
        var totalCycles = -1
        var steps = 0

        while (steps == 0) {
            instructionsString.forEachIndexed { index, node ->
                currentNode = goToNextNode(node, currentNode, nodes)
                println("Now on step ${currentNode.node}")
                if (steps == 0 && currentNode.node == "ZZZ") {
                    steps = index + 1
                }
            }
            totalCycles++
            println("totalCycles: $totalCycles")
        }

        return totalCycles * instructionsString.length + steps
    }

    fun part2(input: List<String>): Long {
        val instructionsString = input[0]
        val nodes = parseInput(input)

        var startNodes = nodes.filter { it.node.endsWith('A') }

        val nodeCounts = startNodes.mapIndexed { index, node ->
            node.println()
            var totalCycles = -1
            var steps = 0
            var currentNode = node

            while (steps == 0) {
                instructionsString.forEachIndexed { index, node ->
                    currentNode = goToNextNode(node, currentNode, nodes)
                    println("Now on step ${currentNode.node}")
                    if (steps == 0 && currentNode.node.endsWith('Z')) {
                        steps = index + 1
                    }
                }
                totalCycles++
                println("steps: $steps")
                println("totalCycles: $totalCycles")
            }
            node to totalCycles * instructionsString.length + steps
        }
        nodeCounts.println()

        val a = (0 until nodeCounts.size).fold(1L) { acc, i ->
            calculateLcm(acc, nodeCounts[i].second.toLong())
        }

        return a
    }

    val testInput = readInput("${dayString}_test")
    val testInput2 = readInput("${dayString}_test2")
    val testInput3 = readInput("${dayString}_test3")
    val input = readInput("$dayString")

//    part1(testInput).println()
//    part1(testInput2).println()
//    part1(input).println()
//
//    part2(testInput).println()
//    part2(testInput2).println()
//    part2(testInput3).println()
    part2(input).println()
}

fun parseInput(input: List<String>): List<NetworkNode> {
    return input.drop(2).map {
        val d = Regex("""(\w{3}) = \((\w{3}), (\w{3})\)""").matchEntire(it)!!.destructured
        NetworkNode(d.component1(), d.component2(), d.component3())
    }
}
data class NetworkNode(
    val node: String,
    val left: String,
    val right: String
)

fun goToNextNode(instruction: Char, currentNode: NetworkNode, nodes: List<NetworkNode>): NetworkNode {
    return when(instruction) {
        'L' -> nodes.first { it.node == currentNode.left }
        'R' -> nodes.first { it.node == currentNode.right }
        else -> return currentNode
    }
}

fun calculateLcm(a: Long, b: Long): Long {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm % a == 0L && lcm % b == 0L) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}