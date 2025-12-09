import helpers.point.Point
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.max

fun main() {
  fun part1(input: List<String>): Long {
    var result = 0L

    for (i in input.indices) {
      for (j in i + 1..input.lastIndex) {
        val (x1, y1) = input[i].split(",").map { it.toLong() }
        val (x2, y2) = input[j].split(",").map { it.toLong() }

        val square = (abs(x1 - x2) + 1) * (abs(y1 - y2) + 1)

        if (square > result) {
          result = square
        }
      }
    }

    return result
  }

  fun part2(input: List<String>): Long {
    val lines = mutableListOf<Pair<helpers.point.Point, helpers.point.Point>>()

    for (i in input.indices) {
      val (x1, y1) = input[i].split(",").map { it.toInt() }
      val (x2, y2) = input[(i + 1) % input.size].split(",").map { it.toInt() }

      lines += Point(min(x1, x2), min(y1, y2)) to Point(max(x1, x2), max(y1, y2))
    }

    fun check(a: Point, b: Point): Boolean {
      val (minX, maxX) = listOf(a.x, b.x).sorted()
      val (minY, maxY) = listOf(a.y, b.y).sorted()

      for (line in lines) {
        val (from, to) = line

        if (from.y == to.y) {
          val withinBoundaries = from.y in (minY + 1) until maxY
          val crossLeftEdge = minX in from.x until to.x
          val crossRightEdge = maxX in (from.x + 1)..to.x

          if (withinBoundaries && (crossLeftEdge || crossRightEdge)) {
            return false
          }
        }

        if (from.x == to.x) {
          val withinBoundaries = from.x in (minX + 1) until maxX
          val crossTopEdge = minY in from.y until to.y
          val crossBottomEdge = maxY in (from.y + 1)..to.y

          if (withinBoundaries && (crossTopEdge || crossBottomEdge)) {
            return false
          }
        }
      }

      return true
    }

    var result = 0L

    for (i in input.indices) {
      for (j in i + 1..input.lastIndex) {
        val (x1, y1) = input[i].split(",").map { it.toInt() }
        val (x2, y2) = input[j].split(",").map { it.toInt() }

        val square = (abs(x1 - x2).toLong() + 1L) * (abs(y1 - y2).toLong() + 1L)

        if (square > result && check(Point(x1, y1), Point(x2, y2))) {
          result = square
        }
      }
    }

    return result
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2025/2025_09_test")
  check(part1(testInput) == 50L)
  check(part2(testInput) == 24L)

  println("=======================")

  val input = readInput("2025/2025_09")
  check(part1(input) == 4_743_645_488L)
  check(part2(input) == 1_529_011_204L)

  part1(input).println()
  // - 2147410078
  // + 4743645488
  part2(input).println()
  // - 92860
  // + 1529011204
}
