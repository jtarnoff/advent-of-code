package Day01

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            it.toDigitString().toCalibrationValue()
        }
    }

    fun part2(input: List<String>): Int {
        val matcher = Regex("""(?=(one|two|three|four|five|six|seven|eight|nine))""")
        return input.map { str ->
            var newStr = str
            matcher.findAll(str).forEach {
                newStr = newStr.replaceRange(it.range.first, it.range.first + 1, changeToDigit(it))
            }
            newStr
        }.sumOf {
            it.toDigitString().toCalibrationValue()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    val testInput2 = readInput("Day01_test2")
    val input = readInput("Day01")

//    part1(testInput).println()
    part1(input).println()

    part2(testInput2).println()
    part2(input).println()
}
fun String.toDigitString(): String {
    return this.filter { chr -> chr.isDigit() }
}
fun String.toCalibrationValue(): Int {
    return (this.first().toString() + this.last()).toInt()
}

fun changeToDigit(result: MatchResult): CharSequence {
    val digitMap = mapOf<CharSequence, Int>(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )
    println(result.groupValues[1])

    return digitMap[result.groupValues[1]].toString()
}
