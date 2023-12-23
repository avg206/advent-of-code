import helpers.point.*
import helpers.point.Point
import java.util.PriorityQueue

fun main() {
    fun part1(input: List<String>): Int {
        val map = MutableList(input.size) { MutableList(input[0].length) { 0 } }
        var start = Point(0, 0)
        var end = Point(0, 0)

        for (i in input.indices) {
            for (j in input[0].indices) {
                if (i == 0 && input[i][j] == '.') start = Point(i, j)
                if (i == input.size - 1 && input[i][j] == '.') end = Point(i, j)

                when (input[i][j]) {
                    '#' -> map[i][j] = -1
                }
            }
        }

        val paths = mutableListOf<Int>()

        fun dfs(current: Point, path: Set<Point>) {
            if (current == end) {
                paths.add(path.size)
                return
            }

            if (path.contains(current)) return

            when (input[current]) {
                '.' -> {
                    current.neighbours()
                        .filter { it.within2DArrayString(input) }
                        .filter { input[it] != '#' }
                        .forEach { dfs(it, path + current) }
                }

                '>' -> {
                    val target = current + Point(0, 1)

                    dfs(target, path + current)
                }

                'v' -> {
                    val target = current + Point(1, 0)

                    dfs(target, path + current)
                }

                '<' -> {
                    val target = current + Point(-1, 0)

                    dfs(target, path + current)
                }

                '^' -> {
                    val target = current + Point(0, -1)

                    dfs(target, path + current)
                }

                else -> throw Exception("Unexpected cell")
            }
        }

        dfs(start, mutableSetOf())

        println("Part 1 - ${paths.max()}")

        return paths.max()
    }

    fun part2(input: List<String>): Int {
        var start = Point(0, 0)
        var end = Point(0, 0)
        val crosses = mutableListOf<Point>()

        for (i in input.indices) {
            for (j in input[0].indices) {
                if (i == 0 && input[i][j] == '.') start = Point(i, j)
                if (i == input.size - 1 && input[i][j] == '.') end = Point(i, j)
                if (input[i][j] == '#') continue

                val count = Point(i, j).neighbours()
                    .filter { it.within2DArrayString(input) }
                    .filter { input[it] != '#' }
                    .size

                if (count > 2) {
                    crosses.add(Point(i, j))
                }
            }
        }

        crosses.add(0, start)
        crosses.add(end)

        val crossCount = crosses.size
        val distance = MutableList(crossCount) { MutableList(crossCount) { 0 } }

        for (i in crosses.indices) {
            val cross = crosses[i]

            val map = MutableList(input.size) { MutableList(input[0].length) { setOf<Point>() } }
            val queue = mutableListOf(cross)

            while (queue.isNotEmpty()) {
                val current = queue.removeFirst()

                if (current != cross && crosses.contains(current)) continue

                when (input[current.x][current.y]) {
                    '#' -> continue
                    else -> {
                        current.neighbours()
                            .filter { it.within2DArrayString(input) }
                            .filter { input[it] != '#' && map[it].isEmpty() }
                            .forEach { target ->
                                map[target] = map[current] + current
                                queue.add(target)
                            }
                    }
                }
            }

            for (j in crosses.indices) {
                if (i == j) continue

                val to = crosses[j]

                if (map[to].isNotEmpty()) {
                    distance[i][j] = map[to].size
                }
            }
        }

        val queue = PriorityQueue(
            Comparator<Triple<Int, Int, List<Int>>> { a, b -> (b.first) - (a.first) }
        )
        var maxPath = -1

        queue.add(Triple(0, 0, MutableList(crossCount) { 0 }))

        while (queue.isNotEmpty()) {
            val (path, i, map) = queue.poll()

            if (i == crosses.size - 1) {
                if (path > maxPath) {
                    maxPath = path
                }

                continue
            }

            val newMap = map.toMutableList()
            newMap[i] = 1

            for (j in crosses.indices) {
                if (i == j) continue
                if (distance[i][j] == 0) continue
                if (newMap[j] != 0) continue

                queue.add(Triple(path + distance[i][j], j, newMap))
            }
        }

        println("Part 2 - $maxPath")

        return maxPath
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("2023/2023_23_test")
    check(part1(testInput) == 94)
    check(part2(testInput) == 154)

    val input = readInput("2023/2023_23")
    check(part1(input) == 2298)
    check(part2(input) == 6602)
}
