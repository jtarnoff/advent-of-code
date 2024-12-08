fun main() {
    fun part1(input: List<String>): Int {
        val rules = input.filter { it.contains('|') }
            .map {line ->
                Pair(line.split('|').component1().toInt(), line.split('|').component2().toInt())
            }
        rules.println()

        val pageNumbers = input.filter { it.contains(',') }
            .map { line -> line.split(',').map { it.toInt() } }
        pageNumbers.println()

        return pageNumbers.sumOf { list ->
            val valid = list.mapIndexed { index, page ->
//                if (index == list.size - 1) true
                println()
                val subList = list.subList(index+1, list.size)
                page.println()
                subList.println()

                val all = subList.all { right -> rules.filter { it.first == page }.any { it.second == right } }
                all.println()
                all
            }

            val ret = if (valid.all { it }) { list[list.size / 2] } else { 0 }
            println("ret $ret")
            ret
        }

        return 0
    }

    fun part2(input: List<String>): Int {


        return 0
    }

    val testInput = readInput("Day05_test")
    val testInput2 = readInput("Day05_test2")
//    val testInput3 = readInput("Day05_test3")
    val input = readInput("Day05")

//    part1(testInput).println()
//    part1(testInput2).println()
    part1(input).println()

//    part2(testInput).println()
//    part2(testInput2).println()
//    part2(testInput3).println()
//    part2(input).println()
}