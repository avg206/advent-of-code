fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val firstDig = line.first { it in '0'..'9' }.digitToInt()
            val lastDig = line.last { it in '0'..'9' }.digitToInt()

            (firstDig * 10 + lastDig)
        }
    }

    fun part2(input: List<String>): Int {
        val digits = listOf(
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9"
        );

        val mapping = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9
        )

        fun process(line: String): Int {
            val firstDigIndices = digits.map { line.indexOf(it) }
            val lastDigIndices = digits.map { line.lastIndexOf(it) }

            val firstWordIndex = firstDigIndices.filter { it != -1 }.min()
            val lastWordIndex = lastDigIndices.max()

            val firstWordDigIndex = firstDigIndices.indexOfFirst { it == firstWordIndex }
            val lastWordDigIndex = lastDigIndices.indexOfFirst { it == lastWordIndex }


            val first = when(val x = digits[firstWordDigIndex]) {
                else -> mapping[x] ?: x.toInt()
            }

            val last = when(val x = digits[lastWordDigIndex]) {
                else -> mapping[x] ?: x.toInt()
            }

            return (first * 10 + last)
        }

        return input.sumOf { process(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2023/Day01_test")
    check(part1(testInput) == 142)
    val testInput2 = readInput("2023/Day01_test_2")
    check(part2(testInput2) == 281)

    val input = readInput("2023/Day01")
    part1(input).println()
    check(part1(input) == 55834)
    part2(input).println()
    check(part2(input) == 53221)
}
