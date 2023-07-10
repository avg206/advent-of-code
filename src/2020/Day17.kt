fun main() {
    fun part1(input: List<String>): Int {
        data class Dot(val x: Int, val y: Int, val z: Int)

        fun allNeighborsOfDot(dot: Dot): List<Dot> {
            val neighbors = mutableListOf<Dot>()

            for (i in -1..1) {
                for (j in -1..1) {
                    for (f in -1..1) {
                        if (i == 0 && j == 0 && f == 0) {
                            continue
                        }

                        neighbors.add(Dot(dot.x + i, dot.y + j, dot.z + f))
                    }
                }
            }

            return neighbors
        }

        fun simulateCycle(dots: Set<Dot>): Set<Dot> {
            fun howManyActiveNeighbors(dot: Dot) = allNeighborsOfDot(dot).map {
                when (it in dots) {
                    true -> 1
                    false -> 0
                }
            }.sumOf { it }

            val newDots = mutableSetOf<Dot>()

            // Check active dots
            dots.forEach {
                when (howManyActiveNeighbors(it)) {
                    2, 3 -> newDots.add(it)
                }
            }

            // Check inactive dots
            dots
                .fold(mutableSetOf<Dot>()) { acc, dot -> acc.addAll(allNeighborsOfDot(dot)); acc }
                .filter { it !in dots }
                .forEach {
                    when (howManyActiveNeighbors(it)) {
                        3 -> newDots.add(it)
                    }
                }

            return newDots
        }

        val dots = mutableSetOf<Dot>()

        val map = input.toList().map { it.toList() }

        for (i in map.indices) {
            for (j in map[i].indices) {
                if (map[i][j] == '#') {
                    dots.add(Dot(i, j, 0))
                }
            }
        }

        var currentDots = dots.toSet()

        for (i in 1..6) {
            currentDots = simulateCycle(currentDots)
        }

        return currentDots.size
    }

    fun part2(input: List<String>): Int {
        data class Dot(val x: Int, val y: Int, val z: Int, val w: Int)

        fun allNeighborsOfDot(dot: Dot): List<Dot> {
            val neighbors = mutableListOf<Dot>()

            for (i in -1..1) {
                for (j in -1..1) {
                    for (f in -1..1) {
                        for (r in -1..1) {
                            if (i == 0 && j == 0 && f == 0 && r == 0) {
                                continue
                            }

                            neighbors.add(Dot(dot.x + i, dot.y + j, dot.z + f, dot.w + r))
                        }
                    }
                }
            }

            return neighbors
        }

        fun simulateCycle(dots: Set<Dot>): Set<Dot> {
            fun howManyActiveNeighbors(dot: Dot) = allNeighborsOfDot(dot).map {
                when (it in dots) {
                    true -> 1
                    false -> 0
                }
            }.sumOf { it }

            val newDots = mutableSetOf<Dot>()

            // Check active dots
            dots.forEach {
                when (howManyActiveNeighbors(it)) {
                    2, 3 -> newDots.add(it)
                }
            }

            // Check inactive dots
            dots
                .fold(mutableSetOf<Dot>()) { acc, dot -> acc.addAll(allNeighborsOfDot(dot)); acc }
                .filter { it !in dots }
                .forEach {
                    when (howManyActiveNeighbors(it)) {
                        3 -> newDots.add(it)
                    }
                }

            return newDots
        }

        val dots = mutableSetOf<Dot>()

        val map = input.toList().map { it.toList() }

        for (i in map.indices) {
            for (j in map[i].indices) {
                if (map[i][j] == '#') {
                    dots.add(Dot(i, j, 0, 0))
                }
            }
        }

        var currentDots = dots.toSet()

        for (i in 1..6) {
            currentDots = simulateCycle(currentDots)
        }

        return currentDots.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2020/Day17_test")
    check(part1(testInput) == 112)
    check(part2(testInput) == 848)

    val input = readInput("2020/Day17")
    part1(input).println()
    part2(input).println()
}
