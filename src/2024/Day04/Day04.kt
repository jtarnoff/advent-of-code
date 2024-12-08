fun main() {
    fun part1(input: List<String>): Int {
        val grid = mutableMapOf<Point, Char>()
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                grid[Point(x, y)] = c
            }
        }

        return findWord("XMAS", grid)
    }

    fun part2(input: List<String>): Int {
        val grid = mutableMapOf<Point, Char>()
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                grid[Point(x, y)] = c
            }
        }

        val subgrid = mutableMapOf<Point, Char>(
            Point(0,0) to 'M',
//            Point(1,0) to '.',
            Point(2,0) to 'M',
//            Point(0,1) to '.',
            Point(1,1) to 'A',
//            Point(2,1) to '.',
            Point(0,2) to 'S',
//            Point(1,2) to '.',
            Point(2,2) to 'S',
        )

        val subgrid2 = mutableMapOf<Point, Char>(
            Point(0,0) to 'M',
//            Point(1,0) to '.',
            Point(2,0) to 'S',
//            Point(0,1) to '.',
            Point(1,1) to 'A',
//            Point(2,1) to '.',
            Point(0,2) to 'M',
//            Point(1,2) to '.',
            Point(2,2) to 'S',
        )

        val subgrid3 = mutableMapOf<Point, Char>(
            Point(0,0) to 'S',
//            Point(1,0) to '.',
            Point(2,0) to 'S',
//            Point(0,1) to '.',
            Point(1,1) to 'A',
//            Point(2,1) to '.',
            Point(0,2) to 'M',
//            Point(1,2) to '.',
            Point(2,2) to 'M',
        )

        val subgrid4 = mutableMapOf<Point, Char>(
            Point(0,0) to 'S',
//            Point(1,0) to '.',
            Point(2,0) to 'M',
//            Point(0,1) to '.',
            Point(1,1) to 'A',
//            Point(2,1) to '.',
            Point(0,2) to 'S',
//            Point(1,2) to '.',
            Point(2,2) to 'M',
        )

        val maps = listOf(
            subgrid.toMap(),
            subgrid2.toMap(),
            subgrid3.toMap(),
            subgrid4.toMap()
        )

        return maps.sumOf {
            val num = findSubgrid(it, grid)
            num.println()
            num
        }
    }

    val testInput = readInput("Day04_test")
    val testInput2 = readInput("Day04_test2")
    val testInput3 = readInput("Day04_test3")
//    val input = readInput("Day04")
    val input = readInput("Day04_z")

//    part1(testInput).println()
//    part1(testInput2).println()
    part1(input).println()

//    part2(testInput).println()
//    part2(testInput2).println()
//    part2(testInput3).println()
//    part2(input).println()
}

fun findSubgrid(subgrid: Map<Point, Char>, grid: Map<Point, Char>): Int {
    val subgridWidth = subgrid.keys.maxOf { it.y } + 1
    val subgridHeight = subgrid.keys.maxOf { it.x } + 1
    subgridWidth.println()
    subgridHeight.println()

    var count = 0

    (0 .. 140 - subgridWidth).forEach { x ->
        (0 .. 140 - subgridHeight).forEach { y ->
            val currentGrid: Map<Point, Char> = extractSubgrid(Point(x,y), 3, 3, grid)
            println(Point(x,y))
//            println("subgrid=$subgrid")
            println("currentGrid=$currentGrid")
            println("subgrid=$subgrid")
            println("${currentGrid == subgrid} compare")
            val filteredSubgrid = currentGrid.filter {
                subgrid.containsKey(it.key)
            }
            if (filteredSubgrid == subgrid) count++
        }
    }

//    return grid.filter { entry ->
//        entry.key.y in 0 until grid.keys.size - subgridWidth
//                && entry.key.x in 0 until grid.keys.size - subgridHeight
//    }
//        .count {
//            val window = grid.filter { it.key in subgrid.keys }
//            window.println()
//            subgrid.println()
//            window == subgrid
//        }

    return count
}

fun extractSubgrid(point: Point, width: Int, height: Int, grid: Map<Point, Char>): Map<Point, Char> {
    val map = mutableMapOf<Point, Char>()
    (point.x until point.x + width).forEachIndexed { xIndex, x ->
        (point.y until point.y + height).forEachIndexed { yIndex, y ->
            grid[Point(y,x)]?.let { map[Point(yIndex, xIndex)] = it }
        }
    }

    println("map: $map")
    return map.toMap()
}

fun findWord(s: String, grid: Map<Point, Char>): Int {
    if (s.isEmpty())
        return 0

    val startPoints = grid.filter { it.value == s[0] }.map { it.key }

    return startPoints.sumOf { point ->
        findWordFromNode(s.takeLast(s.length - 1), grid, point)
    }
}

fun findWordFromNode(
    word: String,
    grid: Map<Point, Char>,
    start: Point): Int
{
    return Direction.values().toList().sumOf { direction ->
        println("checking $word at $start")
        if (checkPointInDirection(start, word, grid, direction)) 1.toInt() else 0
    }
}

fun checkPointInDirection(
    point: Point,
    word: String,
    grid: Map<Point, Char>,
    direction: Direction): Boolean
{
    if (word.isEmpty()) return true

    val nextPoint = Point(point.x + direction.x, point.y + direction.y)

    // check if we're still in bounds of grid
    if (!nextPoint.isValid(grid.keys.size)) return false

    // check if next character is expected character
    if (grid[nextPoint] != word[0]) return false

    println("checking ${word.takeLast(word.length - 1)} at $point")
    return checkPointInDirection(nextPoint, word.takeLast(word.length - 1), grid, direction)
}

data class Point(
    val x: Int,
    val y: Int
) {
    fun isValid(size: Int): Boolean {
        return x in 0 until size && y in 0 until size
    }
}

enum class Direction(val x: Int, val y: Int) {
    E(1, 0),
    SE(1, -1),
    S(0, -1),
    SW(-1, -1),
    W(-1, 0),
    NW(-1, 1),
    N(0, 1),
    NE(1, 1);
}