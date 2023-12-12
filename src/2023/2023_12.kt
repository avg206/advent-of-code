fun main() {
    fun Memo2<String, List<Int>, Long>.counter(map: String, config: List<Int>): Long {
        if (map.isEmpty() && config.isEmpty()) return 1
        if (map.isEmpty()) return 0

        if (config.isEmpty()) {
            if (!map.contains("#")) return 1

            return 0
        }

        when (map.first()) {
            '.' -> return recurse(map.drop(1), config)

            '#' -> {
                val length = config.first()

                if (map.take(length).contains('.') || map[length] == '#') {
                    return 0
                }

                return recurse(map.drop(length + 1), config.drop(1))
            }

            '?' -> return recurse("#" + map.drop(1), config) + recurse("." + map.drop(1), config)

            else -> throw Exception("Unknown symbol")
        }
    }

    val memoCounter = Memo2<String, List<Int>, Long>::counter.memoize()

    fun part1(input: List<String>): Long {
        fun processLine(line: String): Long {
            val (a, b) = line.split(" ")
            val map = ".$a."
            val config = b
                .split(",")
                .map { it.toInt() }
                .toMutableList()

            return memoCounter(map, config)
        }

        return input.sumOf { processLine(it) }
    }

    fun part2(input: List<String>): Long {
        fun processLine(line: String): Long {
            val (a, b) = line.split(" ")

            val map = '.' + listOf(a, a, a, a, a).joinToString("?") + '.'
            val config = listOf(b, b, b, b, b)
                .joinToString(",")
                .split(",")
                .map { it.toInt() }
                .toMutableList()

            return memoCounter(map, config)
        }

        return input.sumOf { processLine(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2023/2023_12_test")
    check(part1(testInput) == 21L)
    check(part2(testInput) == 525_152L)

    println("----")

    val input = readInput("2023/2023_12")
    check(part1(input) == 6_852L)
    check(part2(input) == 8_475_948_826_693L)
    part1(input).println()
    part2(input).println()
}
