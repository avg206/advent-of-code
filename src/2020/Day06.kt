fun main() {
    fun parseGroups(input: List<String>): List<MutableList<String>> {
        val initialList = mutableListOf(mutableListOf<String>())

        return input.fold(initialList) { acc, s ->
            when (s) {
                "" -> {
                    acc.add(mutableListOf()); acc
                }

                else -> {
                    acc.last().add(s); acc
                }
            }
        }
    }

    fun part1(input: List<String>): Int {
        val groups = parseGroups(input)

        return groups.sumOf {
            it.fold(mutableSetOf<Char>()) { acc, s ->
                s.forEach { c -> acc.add(c) }; acc
            }.size
        }
    }

    fun part2(input: List<String>): Int {
        val groups = parseGroups(input)

        return groups.sumOf {
            it.fold(mutableMapOf<Char, Int>()) { acc, s ->
                s.forEach { c -> acc[c] = (acc[c] ?: 0) + 1 }; acc
            }.values.filter { count -> it.size == count }.size
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2020/Day06_test")
    part2(testInput)
    check(part1(testInput) == 11)
    check(part2(testInput) == 6)

    val input = readInput("2020/Day06")
    part1(input).println()
    part2(input).println()
}
