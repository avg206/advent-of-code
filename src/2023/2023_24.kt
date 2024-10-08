fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2023/2023_24_test")
    check(part1(testInput) == 1)

    val input = readInput("2023/2023_24")
    part1(input).println()
    part2(input).println()
}
