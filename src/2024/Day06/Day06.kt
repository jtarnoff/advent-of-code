fun main() {
    val DAY = "Day06"
    val SIZE = 130

    fun part1(input: List<String>): Int {
        var grid = mutableMapOf<Point, NodeType>()
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                grid[Point(x, y)] = NodeType.values().first { c == it.char }
            }
        }

        grid.println()

        var guard = grid.firstNotNullOf { (key, value) -> if (value.isGuard()) key to value else null }

        while (guard.first.isValid(SIZE)) {
            grid = moveGuard(grid)

            guard = grid.firstNotNullOf { (key, value) -> if (value.isGuard()) key to value else null }
            guard.println()
        }

        return grid.count {
            it.value == NodeType.TRAVERSED
        }
    }

    fun part2(input: List<String>): Int {
        val originalGrid = mutableMapOf<Point, NodeType>()
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                originalGrid[Point(x, y)] = NodeType.values().first { c == it.char }
            }
        }

        val emptySpaces = originalGrid.filterValues { it == NodeType.EMPTY }.keys

        return emptySpaces.sumOf { point ->
            var grid = originalGrid.toMutableMap().apply {
                this[point] = NodeType.OBSTRUCTION
            }
            var obstructionMarkers = mutableListOf<Pair<Point, NodeType>>()

            var guard = grid.firstNotNullOf { (key, value) -> if (value.isGuard()) key to value else null }
            while (guard.first.isValid(SIZE)) {

                val p = moveGuardAndMarkObstructions(grid, obstructionMarkers)
                grid = p.first
                obstructionMarkers = p.second

                guard = grid.firstNotNullOf { (key, value) -> if (value.isGuard()) key to value else null }
//                guard.println()

                if(grid.any { it.value == NodeType.OBSTRUCTION_MARKED_TWICE}) {
                    println("point: $point was a loop!")
                    return@sumOf 1.toInt()
                }

                if (!guard.first.isValid(SIZE)) {
                    println("point: $point was valid!")
                    return@sumOf 0
                }
            }
            return@sumOf 0
        }

        return 0
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

fun moveGuard(grid: Map<Point, NodeType>): MutableMap<Point, NodeType> {
    val guard = grid.firstNotNullOf { (key, value) -> if (value.isGuard()) key to value else null }
    val spaceInFrontOfGuard = findSpaceInFrontOfGuard(grid, guard)

    val ret = grid.toMutableMap()
    when (ret[spaceInFrontOfGuard]) {
        NodeType.OBSTRUCTION -> {
            // turn right
            ret[guard.first] = guard.second.turnRight()
        }
        else -> {
            ret[spaceInFrontOfGuard] = guard.second
            ret[guard.first] = NodeType.TRAVERSED
        }
    }
    return ret
}

fun moveGuardAndMarkObstructions(
    grid: Map<Point, NodeType>,
    obstructionMarkers: MutableList<Pair<Point, NodeType>>): Pair<MutableMap<Point, NodeType>, MutableList<Pair<Point, NodeType>>>
{
    val guard = grid.firstNotNullOf { (key, value) -> if (value.isGuard()) key to value else null }
    val spaceInFrontOfGuard = findSpaceInFrontOfGuard(grid, guard)

    val ret = grid.toMutableMap()

    when (ret[spaceInFrontOfGuard]) {
        NodeType.OBSTRUCTION -> {
            if(obstructionMarkers.contains(Pair(spaceInFrontOfGuard, guard.second))) {
                ret[spaceInFrontOfGuard] = NodeType.OBSTRUCTION_MARKED_TWICE
            } else {
                // turn right and mark
                obstructionMarkers.add(Pair(spaceInFrontOfGuard, guard.second))
                ret[guard.first] = guard.second.turnRight()
            }
        }
        else -> {
            ret[spaceInFrontOfGuard] = guard.second
            ret[guard.first] = NodeType.TRAVERSED
        }
    }
    return Pair(ret, obstructionMarkers)
}

fun findSpaceInFrontOfGuard(grid: Map<Point, NodeType>, guard: Pair<Point, NodeType>): Point {
    val (x,y) = when(guard.second) {
        NodeType.GUARD_UP -> Pair(guard.first.x, guard.first.y - 1)
        NodeType.GUARD_RIGHT -> Pair(guard.first.x + 1, guard.first.y)
        NodeType.GUARD_DOWN -> Pair(guard.first.x, guard.first.y + 1)
        NodeType.GUARD_LEFT -> Pair(guard.first.x - 1, guard.first.y)
        else -> { Pair(0,0) }
    }
    return Point(x, y)
}

enum class NodeType(val char: Char) {
    GUARD_UP('^'),
    GUARD_RIGHT('>'),
    GUARD_DOWN('v'),
    GUARD_LEFT('<'),
    OBSTRUCTION('#'),
    OBSTRUCTION_MARKED_TWICE('%'),
    EMPTY('.'),
    TRAVERSED('#');

    fun isGuard(): Boolean {
        return this in listOf(GUARD_UP, GUARD_DOWN, GUARD_RIGHT, GUARD_LEFT)
    }

    fun turnRight(): NodeType {
        return when(this) {
            GUARD_UP -> GUARD_RIGHT
            GUARD_RIGHT -> GUARD_DOWN
            GUARD_DOWN -> GUARD_LEFT
            GUARD_LEFT -> GUARD_UP
            else -> this
        }
    }
}