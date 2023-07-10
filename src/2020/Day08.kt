fun main() {
    fun checkCommandsSet(commands: List<Pair<String, Int>>): Pair<Boolean, Int> {
        val visited = mutableSetOf<Int>()
        var accumulator = 0
        var index = 0

        while (index < commands.size) {
            if (index in visited) {
                break
            }

            visited.add(index)
            val command = commands[index]

            when (command.first) {
                "acc" -> accumulator += command.second

                "jmp" -> {
                    index += command.second; continue
                }
            }
            
            index += 1
        }

        if (index >= commands.size) {
            return Pair(true, accumulator)
        }

        return Pair(false, accumulator)
    }

    fun part1(input: List<String>): Int {
        val commands = input.map { it.split(" ") }.map { Pair(it[0], it[1].toInt()) }

        val (_, accumulator) = checkCommandsSet(commands)

        return accumulator
    }

    fun part2(input: List<String>): Int {
        val originalCommands = input.map { it.split(" ") }.map { Pair(it[0], it[1].toInt()) }

        for (i in originalCommands.indices) {
            if (originalCommands[i].first == "acc") {
                continue
            }

            val commands = originalCommands.toMutableList()
            commands[i] = Pair(
                    when (commands[i].first) {
                        "nop" -> "jmp"
                        "jmp" -> "nop"
                        else -> throw Exception()
                    },
                    commands[i].second
            )

            val (successful, accumulator) = checkCommandsSet(commands)

            if (successful) {
                return accumulator
            }
        }

        throw Exception()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2020/Day08_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 8)

    val input = readInput("2020/Day08")
    part1(input).println()
    part2(input).println()
}
