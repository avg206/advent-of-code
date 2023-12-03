import kotlin.math.abs

fun main() {
    data class Pair(var x: Int, var y: Int) {
        operator fun plus(pair: Pair): Pair {
            return Pair(x + pair.x, y + pair.y)
        }

        fun rotateLeft(degree: Int) {
            when (degree) {
                90 -> {
                    val tmp = x
                    x = -1 * y
                    y = tmp
                }

                180 -> {
                    x *= -1
                    y *= -1
                }

                270 -> {
                    val tmp = x
                    x = y
                    y = -1 * tmp
                }

            }
        }

        fun rotateRight(degree: Int) {
            when (degree) {
                90 -> {
                    val tmp = x
                    x = y
                    y = -1 * tmp
                }

                180 -> {
                    x *= -1
                    y *= -1
                }

                270 -> {
                    val tmp = x
                    x = -1 * y
                    y = tmp
                }

            }
        }
    }

//       N
//     W   E
//       S

    fun part1(input: List<String>): Int {
        val leftMove = mapOf('E' to 'N', 'N' to 'W', 'W' to 'S', 'S' to 'E')
        val rightMove = mapOf('E' to 'S', 'S' to 'W', 'W' to 'N', 'N' to 'E')

        val sideMove = mapOf(
            'E' to Pair(1, 0),
            'N' to Pair(0, 1),
            'S' to Pair(0, -1),
            'W' to Pair(-1, 0),
        )

        var direction = 'E'
        var ship = Pair(0, 0)

        input.forEach { line ->
            val action = line[0]
            var number = line.substring(1).toInt()

            val directionMove = when (action) {
                'L' -> leftMove
                'R' -> rightMove
                else -> null
            }

            if (directionMove != null) {
                while (number > 0) {
                    direction = directionMove[direction] ?: throw Exception()
                    number -= 90
                }

                return@forEach
            }

            val moveDirection = when (action) {
                'F' -> direction
                else -> action
            }

            val (dx, dy) = sideMove[moveDirection] ?: throw Exception()

            ship += Pair(dx * number, dy * number)
        }

        return abs(ship.x) + abs(ship.y)
    }

    fun part2(input: List<String>): Int {
        val sideMove = mapOf(
            'E' to Pair(1, 0),
            'N' to Pair(0, 1),
            'S' to Pair(0, -1),
            'W' to Pair(-1, 0),
        )

        var ship = Pair(0, 0)
        var waypoint = Pair(10, 1)

        input.forEach { line ->
            val action = line[0]
            val number = line.substring(1).toInt()

            when (action) {
                'L' -> waypoint.rotateLeft(number)
                'R' -> waypoint.rotateRight(number)

                'N', 'S', 'E', 'W' -> {
                    val (dx, dy) = sideMove[action] ?: throw Exception()

                    waypoint += Pair(dx * number, dy * number)
                }

                'F' -> {
                    ship += Pair(
                        number * waypoint.x,
                        number * waypoint.y
                    )
                }

                else -> throw Exception()
            }
        }

        return abs(ship.x) + abs(ship.y)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2020/2020_12_test")
    check(part1(testInput) == 25)
    check(part2(testInput) == 286)

    val input = readInput("2020/2020_12")
    part1(input).println()
    part2(input).println()
}
