import helpers.point.Point

fun main() {
    fun applyDirection(position: Point, direction: Char) = when (direction) {
        '>' -> position + Point(0, 1)
        '<' -> position + Point(0, -1)
        'v' -> position + Point(-1, 0)
        '^' -> position + Point(1, 0)
        else -> throw IllegalArgumentException()
    }

    fun travel(instructions: String, snacksInput: List<String>, canEatItself: Boolean): Triple<Int, Int, Int> {
        val snacks = ArrayDeque(
            snacksInput
                .map { line -> line.split(",").map { it.toInt() } }.map { (a, b) -> Point(b, a) }
        )

        var eatenSnack = 0
        var eatenItself = 0
        val snake = ArrayDeque(listOf(Point(0, 0)))

        for (i in instructions.indices) {
            val next = applyDirection(snake.first(), instructions[i])

            if (next == snacks.firstOrNull()) {
                eatenSnack += 1
                snacks.removeFirst()
            } else {
                snake.removeLast()
            }

            if (next in snake) {
                if (!canEatItself) break

                while (next != snake.removeLast());

                snake.removeLast()

                eatenItself += 1
            }

            snake.addFirst(next)
        }

        return Triple(eatenSnack, eatenItself, snake.size)
    }

    fun part1(input: List<String>): Int {
        val instructions = input.first().take(input.first().length / 2)

        val (eaten, _, _) = travel(instructions, input.drop(2), true)

        return eaten
    }

    fun part2(input: List<String>): Int {
        val (_, _, snakeSize) = travel(input.first(), input.drop(2), false)

        return snakeSize + 1 // +1 because 2 segments overlay
    }

    fun part3(input: List<String>): Int {
        val (_, eatenItself, snakeSize) = travel(input.first(), input.drop(2), true)

        return snakeSize * eatenItself
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("flip_flop/2026/ff_2026_07_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 7)
    check(part3(testInput) == 18)

    val input = readInput("flip_flop/2026/ff_2026_07")
    check(part1(input) == 205)
    check(part2(input) == 47)
    check(part3(input) == 156)
    part1(input).println()
    part2(input).println()
    part3(input).println()
}

