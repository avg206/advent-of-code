import helpers.point.Point

fun main() {
    fun part1(input: List<String>): Int {
        val letters = input.flatMap { it.split(" ") }.map { it.first() }.toSet()

        var stoats = letters.associateWith { 0 }.toMutableMap()
        stoats['A'] = 1
        stoats['B'] = 1

        for (i in 1..7) {
            val nextGeneration = letters.associateWith { 0 }.toMutableMap()

            for (letter in letters) {
                if (stoats[letter] == 0) continue

                val rule = input.find { it.first() == letter }!!.split(" ").drop(1).map { it.first() }

                for (target in rule) {
                    nextGeneration[target] = nextGeneration[target]!! + stoats.getValue(letter)
                }
            }

            stoats = nextGeneration
        }

        return stoats.values.sum()
    }

    fun solver(input: List<String>, generationsLimit: Int): Long {
        val pairs = input
            .map { row -> row.split(" ").map { it.first() } }
            .map { (it[0] to it[1]) to it.drop(2) }
            .associate { it }

        val memory = mutableMapOf<Triple<Char, Char, Int>, Long>()

        // How many stoat are between `left` and `right` exclusively
        fun dfs(left: Char, right: Char, generation: Int): Long {
            if (generation == 0) return 0

            val key = Triple(left, right, generation)

            if (key !in memory) {
                val result =
                    listOf<Char>() + left + pairs.getOrElse(left to right) { pairs.getValue(right to left) } + right

                memory[key] = result.size.toLong() - 2L

                for (i in 0 until result.size - 1) {
                    memory[key] = memory.getValue(key) + dfs(result[i], result[i + 1], generation - 1)
                }
            }

            return memory.getValue(key)
        }

        return dfs('A', 'B', generationsLimit) + 2L
    }

    fun part2(input: List<String>): Long {
        return solver(input, 7)
    }

    fun part3(input: List<String>): Long {
        return solver(input, 21)
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("flip_flop/2026/ff_2026_08_test")
    check(part1(testInput) == 4_102)
    check(part2(testInput) == 433L)
    check(part3(testInput) == 117_302_657L)

    val input = readInput("flip_flop/2026/ff_2026_08")
    check(part1(input) == 27_622)
    check(part2(input) == 10_917L)
    check(part3(input) == 1_706_455_351_576L)
    part1(input).println()
    part2(input).println()
    part3(input).println()
}
