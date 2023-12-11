package `2023`.Day05

import println
import readInput
import sun.security.util.Length
import java.lang.Appendable

const val dayString = "Day05"

fun main() {
    fun part1(input: List<String>): Long {
        val (seeds, maps) = parseInput(input)

        return seeds.map {
            var current = it
            maps.forEach { almanacMap ->
                val map = almanacMap.conversions.firstOrNull { conversion ->
                    (conversion.sourceStart..(conversion.sourceStart + conversion.rangeLength)).contains(current)
                }
                current = if (map == null) current else current - map.sourceStart + map.destStart
            }
            current
        }.min()
    }

    fun part2(input: List<String>): Long {
        val (seeds, maps) = parseInput(input)
        val range1 = seeds[0]
        val range2 = seeds[2]..(seeds[2] + seeds[3])

        val f = Regex("""(\d+ \d+)""").findAll(input[0])
            .map {
                listOf(it.value.split(' ')[0].toLong(), it.value.split(' ')[0].toLong() + it.value.split(' ')[1].toLong())
            }
            .flatten()
            .toList()

        f.println()

        val a = f.map {
            var current = it
            maps.forEach { almanacMap ->
                val map = almanacMap.conversions.firstOrNull { conversion ->
                    (conversion.sourceStart..(conversion.sourceStart + conversion.rangeLength)).contains(current)
                }
                current = if (map == null) current else current - map.sourceStart + map.destStart
            }
            current.also { current.println() }
        }.min()

//        val b = range2.map {
//            var current = it
//            maps.forEach { almanacMap ->
//                val map = almanacMap.conversions.firstOrNull { conversion ->
//                    (conversion.sourceStart..(conversion.sourceStart + conversion.rangeLength)).contains(current)
//                }
//                current = if (map == null) current else current - map.sourceStart + map.destStart
//            }
//            current
//        }.min()

//        return minOf(a, b)
        return a
    }

    val testInput = readInput("${dayString}_test")
    val input = readInput("$dayString")

//    part1(testInput).println()
//    part1(input).println()

//    part2(testInput).println()
    part2(input).println()
}

fun parseInput(input: List<String>): Pair<List<Long>, List<AlmanacMap>> {
    val seeds = getSeeds(input[0])
    var currentIndex = 2
    val maps = mutableListOf<AlmanacMap>()

    while (currentIndex < input.size) {

        var endIndex = input.drop(currentIndex + 1).indexOfFirst { it.isEmpty() }
        if (endIndex == -1) endIndex = input.size - currentIndex - 1

        val mapLine = Regex("""(\w+)-to-(\w+) map:""").matchEntire(input[currentIndex])!!.destructured

        val conversions = mutableListOf<Conversion>()
        (1..endIndex).map {
            val (a, b, c) = Regex("""(\d+) (\d+) (\d+)""").matchEntire(input[currentIndex + it])!!.destructured
            conversions.add(Conversion(a.toLong(), b.toLong(), c.toLong()))
        }
        maps.add(AlmanacMap(conversions, mapLine.component1(), mapLine.component2()))

        currentIndex += endIndex + 2
    }

    return Pair(seeds, maps)
}

fun getSeeds(input: String): List<Long> =
    Regex("""\d+""").findAll(input)
        .map { it.value.toLong() }
        .toList()

data class Conversion(
    val destStart: Long,
    val sourceStart: Long,
    val rangeLength: Long
)

data class AlmanacMap(
    val conversions: List<Conversion>,
    val sourceType: String,
    val destinationType: String
)