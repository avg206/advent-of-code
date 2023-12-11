fun main() {
    data class Dot(var x: Int, var y: Int) {
        override fun toString(): String {
            return "$x|$y"
        }

        operator fun plus(dot: Dot): Dot {
            return Dot(x + dot.x, y + dot.y)
        }
    }

    val moves = buildList {
        add(Dot(-1, 0))
        add(Dot(0, 1))
        add(Dot(0, -1))
        add(Dot(1, 0))
    }

    fun solve(input: List<String>, gap: Long): Long {
        val expandedRows = mutableSetOf<Int>()
        val expandedColumns = mutableSetOf<Int>()

        input.forEachIndexed { index, s ->
            if (!s.contains("#")) {
                expandedRows.add(index)
            }
        }

        for (i in input[0].indices) {
            var hasHash = false

            for (j in input.indices) {
                if (input[j][i] == '#') {
                    hasHash = true
                }
            }

            if (!hasHash) {
                expandedColumns.add(i)
            }
        }

        val symbols = mutableListOf<Dot>()

        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] == '#') {
                    symbols.add(Dot(i, j))
                }
            }
        }

        fun distances(from: Dot, tos: List<Dot>): List<Long> {
            val result = MutableList(input.size) { MutableList(input[0].length) { 0L } }

            result[from.x][from.y] = 0L
            val queue = mutableListOf(from)

            while (queue.isNotEmpty()) {
                val point = queue.removeFirst()

                moves.forEach { move ->
                    val target = point + move

                    if (target.x !in input.indices ||
                        target.y !in input[0].indices ||
                        result[target.x][target.y] != 0L
                    ) {
                        return@forEach
                    }

                    val isExpanded = expandedRows.contains(target.x) || expandedColumns.contains(target.y)

                    result[target.x][target.y] = when (isExpanded) {
                        true -> result[point.x][point.y] + gap
                        false -> result[point.x][point.y] + 1L
                    }

                    queue.add(target)
                }
            }

            return tos.map { result[it.x][it.y] }
        }

        val result = mutableMapOf<String, Long>()

        for (i in symbols.indices) {
            val distance = distances(symbols[i], symbols)

            for (j in i + 1 until symbols.size) {
                result["${symbols[i]} - ${symbols[j]}"] = distance[j]
            }
        }

        return result.values.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2023/2023_11_test")
    check(solve(testInput, 2) == 374L)
    check(solve(testInput, 10) == 1_030L)
    check(solve(testInput, 100) == 8_410L)

    val input = readInput("2023/2023_11")
    solve(input, 2).println()
    solve(input, 1_000_000).println()
}
