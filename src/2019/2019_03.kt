import helpers.point.Point
import kotlin.math.max
import kotlin.math.min

fun main() {
  fun generateWire(moves: String): List<Point> {
    val lines = mutableListOf(Point(0, 0))

    for (move in moves.split(",")) {
      val direction = move[0]

      val distance = move.substring(1).toInt()

      when (direction) {
        'U' -> lines.add(lines.last() + Point(0, distance))
        'D' -> lines.add(lines.last() + Point(0, -distance))
        'L' -> lines.add(lines.last() + Point(-distance, 0))
        'R' -> lines.add(lines.last() + Point(distance, 0))
      }
    }

    return lines
  }

  fun checkCross(one: List<Point>, two: List<Point>): Point? {
    when {
      one[0].x == one[1].x -> {
        return when {
          two[0].x == two[1].x -> {
            null
          }

          (two[0].y in min(one[0].y, one[1].y)..max(one[0].y, one[1].y))
            && (one[0].x in min(two[0].x, two[1].x)..max(two[0].x, two[1].x)) -> {
            Point(one[0].x, two[0].y)
          }

          else -> null
        }
      }

      one[0].y == one[1].y -> {
        return when {
          two[0].y == two[1].y -> {
            null
          }

          (two[0].x in min(one[0].x, one[1].x)..max(one[0].x, one[1].x))
            && (one[0].y in min(two[0].y, two[1].y)..max(two[0].y, two[1].y)) -> {
            Point(two[0].x, one[0].y)
          }

          else -> null
        }
      }
    }

    return null
  }

  fun part1(input: List<String>): Int {
    val wireOne = generateWire(input[0])
    val wireTwo = generateWire(input[1])

    val start = Point(0, 0)

    var closest: Point? = null

    for (one in wireOne.windowed(2, 1, false)) {
      for (two in wireTwo.windowed(2, 1, false)) {
        val point = checkCross(one, two)

        if (point != null && point != start) {
          if (closest == null || point.manhattan(start) < closest.manhattan(start)) {
            closest = point
          }
        }
      }
    }

    return closest!!.manhattan(start)
  }

  fun part2(input: List<String>): Int {
    val wireOne = generateWire(input[0])
    val wireTwo = generateWire(input[1])

    var minDistance = Int.MAX_VALUE

    var oneDistance = 0
    var twoDistance = 0

    for (one in wireOne.windowed(2, 1, false)) {
      twoDistance = 0

      for (two in wireTwo.windowed(2, 1, false)) {
        val point = checkCross(one, two)

        if (point != null && point != Point(0, 0)) {
          val distance = oneDistance + twoDistance + one[0].manhattan(point) + two[0].manhattan(point)

          if (distance < minDistance) {
            minDistance = distance
          }
        }

        twoDistance += two[0].manhattan(two[1])
      }

      oneDistance += one[0].manhattan(one[1])
    }

    return minDistance
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2019/2019_03_test")
  val testInput2 = readInput("2019/2019_03_test_2")
  val testInput3 = readInput("2019/2019_03_test_3")
  check(part1(testInput) == 6)
  check(part2(testInput) == 30)
  check(part2(testInput2) == 610)
  check(part2(testInput3) == 410)

  val input = readInput("2019/2019_03")
  check(part1(input) == 403)
  check(part2(input) == 4158)

  part1(input).println()
  part2(input).println()
}
