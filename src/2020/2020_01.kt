fun main() {
    fun part1(input: List<String>): Int {
        val numbers = input.map { it.toInt() }

        for (i in numbers.indices) {
            for (j in i + 1 until numbers.size) {
                if (numbers[i] + numbers[j] == 2020) {
                    return numbers[i] * numbers[j]
                }
            }
        }

        throw Exception("Not found")
    }

    fun part2(input: List<String>): Int {
        val numbers = input.map { it.toInt() }

        for (i in numbers.indices) {
            for (j in i + 1 until numbers.size) {
                for (f in j + 1 until numbers.size) {
                    if (numbers[i] + numbers[j] + numbers[f] == 2020) {
                        return numbers[i] * numbers[j] * numbers[f]
                    }
                }
            }
        }

        throw Exception("Not found")
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2020/2020_01_test")
    check(part1(testInput) == 514579)

    val input = readInput("2020/2020_01")
    part1(input).println()
    part2(input).println()
}
