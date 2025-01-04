import helpers.point.Point
import kotlin.math.max

fun main() {
  fun parseLine(line: String, version: Int): Triple<Point, Point, (value: Int) -> Int> {
    val (left, right) = line.split(" through ").map { it.split(" ") }
    val command = left.dropLast(1).joinToString(" ")

    val (x1, y1) = left.last().split(",").map { it.toInt() }
    val (x2, y2) = right.last().split(",").map { it.toInt() }

    if (version == 2) {
      return when (command) {
        "turn off" -> Triple(Point(x1, y1), Point(x2, y2), { max(0, it - 1) })
        "turn on" -> Triple(Point(x1, y1), Point(x2, y2), { it + 1 })
        "toggle" -> Triple(Point(x1, y1), Point(x2, y2), { it + 2 })

        else -> throw Exception("Unknown command - $command")
      }
    }

    return when (command) {
      "turn off" -> Triple(Point(x1, y1), Point(x2, y2), { 0 })
      "turn on" -> Triple(Point(x1, y1), Point(x2, y2), { 1 })
      "toggle" -> Triple(Point(x1, y1), Point(x2, y2), { if (it == 0) 1 else 0 })

      else -> throw Exception("Unknown command - $command")
    }
  }

  fun part1(input: List<String>): Int {
    val grid = mutableMapOf<Point, Int>()

    input.map { parseLine(it, 1) }.forEach { (from, to, changer) ->
      for (i in from.x..to.x) {
        for (j in from.y..to.y) {
          val key = Point(i, j)

          grid[key] = changer(grid.getOrDefault(key, 0))
        }
      }
    }

    return grid.values.count { it == 1 }
  }

  fun part2(input: List<String>): Long {
    val grid = mutableMapOf<Point, Int>()

    input.map { parseLine(it, 2) }.forEach { (from, to, changer) ->
      for (i in from.x..to.x) {
        for (j in from.y..to.y) {
          val key = Point(i, j)

          grid[key] = changer(grid.getOrDefault(key, 0))
        }
      }
    }

    return grid.values.sumOf { it.toLong() }
  }

  val input = readInput("2015/2015_06")
  part1(input).println()
  part2(input).println()
}
