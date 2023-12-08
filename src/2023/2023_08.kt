fun main() {
    fun gcd(a: Long, b: Long): Long {
        if (b == 0L) return a
        return gcd(b, a % b)
    }

    fun lcm(a: Long, b: Long): Long {
        return a / gcd(a, b) * b
    }

    fun find(node: String, routes: Map<String, Pair<String, String>>, instructions: List<Char>): Long {
        var current = node
        var count = 0L
        var index = 0

        while (current[2] != 'Z') {
            count += 1L

            current = when (instructions[index]) {
                'L' -> routes[current]!!.first
                'R' -> routes[current]!!.second
                else -> throw Exception()
            }

            index = (index + 1) % instructions.size
        }

        return count
    }

    fun part1(input: List<String>): Long {
        val instructions = input[0].toList()
        val routes = mutableMapOf<String, Pair<String, String>>()

        for (i in 2 until input.size) {
            val (from, tos) = input[i].split(" = ")
            val (left, right) = tos.slice(1..tos.length - 2).split(", ")

            routes[from] = Pair(left, right)
        }

        return find("AAA", routes, instructions)
    }

    fun part2(input: List<String>): Long {
        val instructions = input[0].toList()
        val routes = mutableMapOf<String, Pair<String, String>>()

        for (i in 2 until input.size) {
            val (from, tos) = input[i].split(" = ")
            val (left, right) = tos.slice(1..tos.length - 2).split(", ")

            routes[from] = Pair(left, right)
        }

        val current: List<String> = routes.keys.filter { it[2] == 'A' }

        return current
            .map { find(it, routes, instructions) }
            .fold(1) { acc, l -> lcm(acc, l) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2023/2023_08_test")
    check(part1(testInput) == 2L)

    val input = readInput("2023/2023_08")
    check(part1(input) == 18_827L)
    check(part2(input) == 20_220_305_520_997)
    part1(input).println()
    part2(input).println()
}
