import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    data class Dot(var x: Int, var y: Int) {
        override fun toString(): String {
            return "$x|$y"
        }

        operator fun plus(dot: Dot): Dot {
            return Dot(x + dot.x, y + dot.y)
        }
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

        var result = 0L

        for (i in symbols.indices) {
            for (j in i + 1 until symbols.size) {
                var distance = (abs(symbols[i].x - symbols[j].x) + abs(symbols[i].y - symbols[j].y)).toLong()

                val xRange = min(symbols[i].x, symbols[j].x)..max(symbols[i].x, symbols[j].x)
                val yRange = min(symbols[i].y, symbols[j].y)..max(symbols[i].y, symbols[j].y)

                distance += (expandedRows.filter { it in xRange }.size) * (gap - 1)
                distance += (expandedColumns.filter { it in yRange }.size) * (gap - 1)

                result += distance
            }
        }

        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2023/2023_11_test")
    check(solve(testInput, 2) == 374L)
    check(solve(testInput, 10) == 1_030L)
    check(solve(testInput, 100) == 8_410L)

    val input = readInput("2023/2023_11")
    check(solve(input, 1) == 9_427_545L)
    check(solve(input, 2) == 9_947_476L)
    check(solve(input, 1_000_000) == 519_939_907_614L)
    solve(input, 2).println()
    solve(input, 1_000_000).println()
}
