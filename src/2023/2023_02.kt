fun main() {
    // only 12 red cubes, 13 green cubes, and 14 blue cubes

    // Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green

    fun part1(input: List<String>): Int {
        val limits = mapOf(
            "red" to 12,
            "green" to 13,
            "blue" to 14
        )

        fun checkTheGame(line: String): Int {
            val (name, setsLine) = line.split(": ")
            val (_, id) = name.split(" ")
            val sets = setsLine.split("; ")

            val isPossible = sets.all { set ->
                val cubes = set.split(", ")

                cubes.all {
                    val (number, key) = it.split(" ")

                    limits[key]!! >= number.toInt()
                }
            }

            if (isPossible){
                return id.toInt()
            }

            return 0
        }

        return input.sumOf { checkTheGame(it) }
    }

    fun part2(input: List<String>): Int {
        fun checkTheGame(line: String): Int {
            val (_, setsLine) = line.split(": ")
            val sets = setsLine.split("; ")

            val maximums = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)

            sets.forEach { set ->
                val cubes = set.split(", ")

                cubes.forEach {
                    val (number, key) = it.split(" ")

                    val intNumber = number.toInt()

                    if (maximums[key]!! < intNumber) {
                        maximums[key] = intNumber
                    }
                }
            }

            return maximums.values.fold(1) {acc, i -> acc * i }
        }

        return input.sumOf { checkTheGame(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2023/2023_02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("2023/2023_02")
    check(part1(input) == 2810)
    check(part2(input) == 69110)
    part1(input).println()
    part2(input).println()
}
