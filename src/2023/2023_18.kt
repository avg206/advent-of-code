import kotlin.math.abs
import helpers.point.LongPoint

fun main() {
    fun solver(points: List<LongPoint>): Long {
        var S = 0L
        var P = 0L

        for (i in points.indices) {
            val p1 = if (i == 0) points.last() else points[i - 1]
            val p2 = points[i]

            P += abs(p2.x - p1.x) + abs(p2.y - p1.y)
            S += ((p2.x - p1.x) * (p1.y + p2.y))
        }

        return abs(S) / 2 + P / 2 + 1
    }

    fun part1(input: List<String>): Long {
        val points = mutableListOf(LongPoint(0, 0))

        input.forEach { line ->
            val (direction, step, _) = line.split(" ")
            val last = points.last()

            val newPoint = when (direction) {
                "R" -> last + (LongPoint(0, 1) * step.toLong())
                "D" -> last + (LongPoint(1, 0) * step.toLong())
                "L" -> last + (LongPoint(0, -1) * step.toLong())
                "U" -> last + (LongPoint(-1, 0) * step.toLong())
                else -> throw Exception("Unknown move")
            }

            points.add(newPoint)
        }

        return solver(points)
    }

    fun part2(input: List<String>): Long {
        val points = mutableListOf(LongPoint(0, 0))

        input.forEach { line ->
            val (_, _, color) = line.split(" ")

            val step = color.substring(2..6).toLong(16)
            val move = color[7].digitToInt()

            val last = points.last()

            val newPoint = when (move) {
                0 -> last + (LongPoint(0, 1) * step)
                1 -> last + (LongPoint(1, 0) * step)
                2 -> last + (LongPoint(0, -1) * step)
                3 -> last + (LongPoint(-1, 0) * step)
                else -> throw Exception("Unknown move")
            }

            points.add(newPoint)
        }

        return solver(points)
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2023/2023_18_test")
    check(part1(testInput) == 62L)
    check(part2(testInput) == 952_408_144_115L)

    val input = readInput("2023/2023_18")
    check(part1(input) == 49_578L)
    check(part2(input) == 52_885_384_955_882L)
    part1(input).println()
    part2(input).println()
}
