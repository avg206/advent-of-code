fun main() {
    fun part1(input: List<String>): Int {
        val emptyLine = input.indexOfFirst { it == "" }

        val player1 = ArrayDeque(input.subList(1, emptyLine).map { it.toInt() })
        val player2 = ArrayDeque(input.subList(emptyLine + 2, input.size).map { it.toInt() })

        while (player1.isNotEmpty() && player2.isNotEmpty()) {
            val top1 = player1.removeFirst()
            val top2 = player2.removeFirst()

            when {
                top1 > top2 -> {
                    player1.addLast(top1)
                    player1.addLast(top2)
                }

                top1 < top2 -> {
                    player2.addLast(top2)
                    player2.addLast(top1)
                }
            }
        }

        return buildList { addAll(player1); addAll(player2) }
            .reversed()
            .mapIndexed { index, i -> i * (index + 1) }
            .sum()
    }

    fun part2(input: List<String>): Int {
        val emptyLine = input.indexOfFirst { it == "" }

        fun game(player1: ArrayDeque<Int>, player2: ArrayDeque<Int>): Int {
            val memory = mutableSetOf<String>()

            while (player1.size > 0 && player2.size > 0) {
                if (!memory.add("$player1--$player2")) break

                val top1 = player1.removeFirst()
                val top2 = player2.removeFirst()

                val comparison = when {
                    top1 <= player1.size && top2 <= player2.size ->
                        game(
                            ArrayDeque(player1.take(top1)),
                            ArrayDeque(player2.take(top2))
                        )

                    else -> compareValues(top1, top2)
                }


                when {
                    comparison > 0 -> {
                        player1.addLast(top1)
                        player1.addLast(top2)
                    }

                    comparison < 0 -> {
                        player2.addLast(top2)
                        player2.addLast(top1)
                    }

                    else -> throw Exception()
                }
            }

            if (!player1.isEmpty()) return 1
            if (!player2.isEmpty()) return -1

            return 0;
        }

        val player1 = ArrayDeque(input.subList(1, emptyLine).map { it.toInt() })
        val player2 = ArrayDeque(input.subList(emptyLine + 2, input.size).map { it.toInt() })

        game(player1, player2)

        val winner = player1.ifEmpty { player2 }

        return winner
            .reversed()
            .mapIndexed { index, i -> i * (index + 1) }
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2020/2020_22_test")
    check(part1(testInput) == 306)
    check(part2(testInput) == 291)

    val input = readInput("2020/2020_22")
    part1(input).println()
    part2(input).println()
}
