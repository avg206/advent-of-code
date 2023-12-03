fun main() {
    fun part1(input: List<String>): Int {
        fun validate(line: String): Boolean {
            val (condition, string) = line.split(": ")
            val (range, letter) = condition.split(" ")
            val (min, max) = range.split("-").map { it.toInt() }

            val occurrences = string.fold(0) { acc: Int, currentLetter ->
                when (letter[0] == currentLetter) {
                    true -> acc + 1
                    false -> acc
                }
            }

            return occurrences in min..max
        }

        return input.fold(0) { acc: Int, s ->
            when (validate(s)) {
                true -> acc + 1
                false -> acc
            }
        }
    }

    fun part2(input: List<String>): Int {
        fun validate(line: String): Boolean {
            val (condition, string) = line.split(": ")
            val (range, letter) = condition.split(" ")
            val positions = range.split("-").map { it.toInt() - 1 }

            val occurrences = positions.fold(0) { acc, i ->
                when (letter[0] == string[i]) {
                    true -> acc + 1
                    false -> acc
                }
            }

            return occurrences == 1
        }

        return input.fold(0) { acc: Int, s ->
            when (validate(s)) {
                true -> acc + 1
                false -> acc
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2020/2020_02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 1)

    val input = readInput("2020/2020_02")
    part1(input).println()
    part2(input).println()
}
