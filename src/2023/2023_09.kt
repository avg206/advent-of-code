fun main() {
    fun part1(input: List<String>): Long {
        fun processLine(line: String): Long {
            val numbers = line.split(" ").map { it.toLong() }

            fun dfs(list: List<Long>): Long {
                if (list.toSet().size == 1 && list[0] == 0L) {
                    return 0
                }

                val newList = mutableListOf<Long>()

                for (i in 1 until list.size) {
                    newList.add(list[i] - list[i - 1])
                }

                return dfs(newList) + list.last()
            }

            return dfs(numbers)
        }

        return input.sumOf { processLine(it) }
    }

    fun part2(input: List<String>): Long {
        fun processLine(line: String): Long {
            val numbers = line.split(" ").map { it.toLong() }


            fun dfs(list: List<Long>): Long {
                if (list.toSet().size == 1 && list[0] == 0L) {
                    return 0
                }

                val newList = mutableListOf<Long>()

                for (i in 1 until list.size) {
                    newList.add(list[i] - list[i - 1])
                }

                return list.first() - dfs(newList)
            }

            return dfs(numbers)
        }

        return input.sumOf { processLine(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2023/2023_09_test")
    check(part1(testInput) == 114L)
    check(part2(testInput) == 2L)

    val input = readInput("2023/2023_09")
    check(part1(input) == 1_772_145_754L)
    check(part2(input) == 867L)
    part1(input).println()
    part2(input).println()
}
