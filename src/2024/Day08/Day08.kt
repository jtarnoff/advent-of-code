fun main() {
    val DAY = "Day08"

    fun part1(input: List<String>): Long {
        val map = mutableMapOf<Point, Char>()
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                map[Point(x,y)] = c
            }
        }

        val frequencyMap = map.values
            .filter { it != '.' }
            .groupingBy { it }
            .eachCount()

        val antennae = mutableListOf<Point>()
        frequencyMap.keys.forEach { char ->
            val list = map.filter { it.value == char }
            val pairs = convertToPairs(list)

            pairs.forEach { pair ->
                val distance = Pair(pair.first.x - pair.second.x, pair.first.y - pair.second.y)
                val pointA = Point(pair.first.x + distance.first, pair.first.y + distance.second)
                val pointB = Point(pair.second.x - distance.first, pair.second.y - distance.second)
                antennae.add(pointA)
                antennae.add(pointB)
            }
        }

        val filteredAntennae = antennae.filter { point ->
            point.x in (input.indices) && point.y in (input.indices)
        }

        return filteredAntennae.toSet().size.toLong()
    }

    fun part2(input: List<String>): Long {
        val map = mutableMapOf<Point, Char>()
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                map[Point(x,y)] = c
            }
        }

        val frequencyMap = map.values
            .filter { it != '.' }
            .groupingBy { it }
            .eachCount()

        val antennae = mutableListOf<Point>()
        frequencyMap.keys.forEach { char ->
            val list = map.filter { it.value == char }
            val pairs = convertToPairs(list)

            pairs.forEach { pair ->
                val distance = Pair(pair.first.x - pair.second.x, pair.first.y - pair.second.y)
                val pointA = Point(pair.first.x + distance.first, pair.first.y + distance.second)
                val pointB = Point(pair.second.x - distance.first, pair.second.y - distance.second)

                antennae.add(pair.first)
                antennae.add(pair.second)

                antennae.add(pointA)
                antennae.add(pointB)

                var beyondA = true
                var prevPoint = pointA
                while (beyondA) {
                    val newPoint = Point(prevPoint.x + distance.first, prevPoint.y + distance.second)
                    if (newPoint.isValid(input.size)) {
                        antennae.add(newPoint)
                        prevPoint = newPoint
                    } else {
                        beyondA = false
                    }
                }

                var beyondB = true
                prevPoint = pointB
                while (beyondB) {
                    val newPoint = Point(prevPoint.x - distance.first, prevPoint.y - distance.second)
                    if (newPoint.isValid(input.size)) {
                        antennae.add(newPoint)
                        prevPoint = newPoint
                    } else {
                        beyondB = false
                    }
                }
            }
        }

        val filteredAntennae = antennae.filter { point ->
            point.x in (input.indices) && point.y in (input.indices)
        }

        printMap(filteredAntennae, input.size)

        return filteredAntennae.toSet().size.toLong()
    }

    val testInput = readInput("${DAY}_test")
//    val testInput2 = readInput("${DAY}_test2")
//    val testInput3 = readInput("${DAY}_test3")
    val input = readInput(DAY)

//    part1(testInput).println()
//    part1(testInput2).println()
//    part1(input).println()

//    part2(testInput).println()
//    part2(testInput2).println()
//    part2(testInput3).println()
    part2(input).println()
}

fun printMap(filteredAntennae: List<Point>, size: Int) {
    for (i in 0 until size) {
        for (j in 0 until size) {
            if(filteredAntennae.contains(Point(j,i))) {
                print('#')
            } else {
                print('.')
            }
        }
        print('\n')
    }
}

fun convertToPairs(entries: Map<Point, Char>): List<Pair<Point, Point>> {
    val list = mutableListOf<Pair<Point, Point>>()
    for (i in 0 until entries.size) {
        for (j in i + 1 until entries.size) {
            list.add(Pair(entries.keys.toList()[i], entries.keys.toList()[j]))
        }
    }
    return list
}