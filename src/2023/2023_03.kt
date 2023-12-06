fun main() {
    data class Dot(val x: Int, val y: Int);

    val moves =
        buildList {
            add(Pair(1, 1))
            add(Pair(1, 0))
            add(Pair(1, -1))
            add(Pair(0, 1))
            add(Pair(0, -1))
            add(Pair(-1, 1))
            add(Pair(-1, 0))
            add(Pair(-1, -1))
        }

    fun part1(input: List<String>): Int {
        val numbers = mutableListOf<String>()

        var current = ""
        var inScope = false;

        for (i in input.indices) {
            for (j in input[0].indices) {
                if (input[i][j] !in '0'..'9') {
                    if (current != "" && inScope) {
                        numbers.add(current);
                    }

                    current = "";
                    inScope = false;

                    continue;
                }

                val hasNearChar = moves.any {
                    val nI = i + it.first;
                    val nJ = j + it.second;

                    if (nI in input.indices && nJ in input[0].indices) {
                        val nearChar = input[nI][nJ]

                        return@any nearChar !in '0'..'9' && nearChar != '.'
                    }

                    return@any false
                }

                if (hasNearChar) {
                    inScope = true;
                }

                current += input[i][j]
            }
        }

        return numbers.sumOf { it.toInt() }
    }

    fun part2(input: List<String>): Int {
        val asterisks = mutableMapOf<Dot, MutableList<Int>>()

        var current = ""
        val currentAsterisks = mutableListOf<Dot>()

        for (i in input.indices) {
            for (j in input[0].indices) {
                if (input[i][j] !in '0'..'9') {
                    currentAsterisks.forEach {
                        if (asterisks[it] == null) {
                            asterisks[it] = mutableListOf<Int>()
                        }

                        asterisks[it]?.add(current.toInt())
                    }

                    current = "";
                    currentAsterisks.clear()

                    continue;
                }

                moves.forEach {
                    val nI = i + it.first;
                    val nJ = j + it.second;

                    if (nI in input.indices && nJ in input[0].indices) {
                        val nC = input[nI][nJ]

                        if (nC == '*' && Dot(nI, nJ) !in currentAsterisks) {
                            currentAsterisks.add(Dot(nI, nJ))
                        }
                    }
                }

                current += input[i][j]
            }
        }

        return asterisks.values.filter { it.size == 2 }.sumOf { it[0] * it[1] }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2023/2023_03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    // add `.` ad the end of each input line
    val input = readInput("2023/2023_03")
    check(part1(input) == 517021)
    check(part2(input) == 81296995)
    part1(input).println()
    part2(input).println()
}
