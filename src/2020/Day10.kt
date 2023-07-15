fun main() {
    fun part1(input: List<String>): Int {
        val sortedInput = input.map { it.toInt() }.sorted()
        val adapters = listOf(0) + sortedInput + listOf(sortedInput.last() + 3)

        val jolts = mutableListOf(0, 0, 0, 0)

        for (i in 1 until adapters.size) {
            val diff = adapters[i] - adapters[i - 1]

            jolts[diff]++
        }

        return jolts[1] * jolts[3]
    }

    fun part2(input: List<String>): Long {
        val sortedInput = input.map { it.toInt() }.sorted()
        val adapters = listOf(0) + sortedInput + listOf(sortedInput.last() + 3)

        val arrangements = List(adapters.size) { 0L }.toMutableList()
        arrangements[arrangements.size - 1] = 1L

        for (i in arrangements.size - 2 downTo 0) {
            for (j in i + 1 until adapters.size) {
                if (adapters[j] - adapters[i] <= 3) {
                    arrangements[i] += arrangements[j]
                }
            }
        }

        return arrangements[0]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2020/Day10_test")
    check(part1(testInput) == 220)
    val testInput2 = readInput("2020/Day10_test_2")
    check(part2(testInput2) == 8L)
    check(part2(testInput) == 19208L)

    val input = readInput("2020/Day10")
    part1(input).println()
    part2(input).println()
}
