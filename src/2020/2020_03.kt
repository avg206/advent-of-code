fun main() {
    fun checkPath(input: List<String>, move: Pair<Int, Int>): Long {
        val map = input.map { it.toList() }

        val rowsCount = map.size
        val columnCount = map[0].size

        // (row, column)
        var position = Pair(0, 0)

        var trees = 0L

        while (position.first < rowsCount) {
            trees = when (map[position.first][position.second]) {
                '#' -> trees + 1L
                else -> trees
            }

            position = Pair(
                position.first + move.first,
                (position.second + move.second) % columnCount
            )
        }

        return trees
    }

    fun part1(input: List<String>): Long {
        return checkPath(input, Pair(1, 3))
    }

    fun part2(input: List<String>): Long {
        val moves = listOf(Pair(1, 1), Pair(1, 3), Pair(1, 5), Pair(1, 7), Pair(2, 1))
        return moves.fold(1L) { acc: Long, pair: Pair<Int, Int> -> acc * checkPath(input, pair) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2020/2020_03_test")
    check(part1(testInput) == 7L)
    check(part2(testInput) == 336L)

    val input = readInput("2020/2020_03")
    part1(input).println()
    part2(input).println()
}
