import helpers.point.Point
import java.util.*

enum class Direction {
  Vertical, Horizontal
}

typealias Node = Triple<Int, Point, Direction>

fun main() {
  fun solve(input: List<String>, maxSteps: Int, stepRange: IntRange): Int {
    val queue = PriorityQueue(Comparator<Node> { a, b -> a.first - b.first })
    val visited = mutableSetOf<String>()

    queue.add(Triple(0, Point(0, 0), Direction.Horizontal))
    queue.add(Triple(0, Point(0, 0), Direction.Vertical))

    while (queue.isNotEmpty()) {
      val (dist, dot, dir) = queue.poll()
      val key = "$dot|$dir"

      if (visited.contains(key)) continue
      visited.add(key)

      if (dot.x == input.size - 1 && dot.y == input[0].length - 1) {
        return dist
      }

      when (dir) {
        Direction.Horizontal -> {
          var newDistance = dist

          for (i in 1..maxSteps) {
            val nX = dot.x + i

            if (nX !in input.indices) break
            newDistance += input[nX][dot.y].digitToInt()

            if (i !in stepRange) continue
            queue.add(Triple(newDistance, Point(nX, dot.y), Direction.Vertical))
          }

          newDistance = dist

          for (i in 1..maxSteps) {
            val nX = dot.x - i

            if (nX !in input.indices) break
            newDistance += input[nX][dot.y].digitToInt()

            if (i !in stepRange) continue
            queue.add(Triple(newDistance, Point(nX, dot.y), Direction.Vertical))
          }
        }

        Direction.Vertical -> {
          var newDistance = dist

          for (i in 1..maxSteps) {
            val nY = dot.y + i

            if (nY !in input.indices) break
            newDistance += input[dot.x][nY].digitToInt()

            if (i !in stepRange) continue
            queue.add(Triple(newDistance, Point(dot.x, nY), Direction.Horizontal))
          }

          newDistance = dist

          for (i in 1..maxSteps) {
            val nY = dot.y - i

            if (nY !in input.indices) break
            newDistance += input[dot.x][nY].digitToInt()

            if (i !in stepRange) continue
            queue.add(Triple(newDistance, Point(dot.x, nY), Direction.Horizontal))
          }
        }
      }
    }

    throw Exception("Path not found")
  }

  fun part1(input: List<String>): Int {
    return solve(input, 3, 1..3)
  }

  fun part2(input: List<String>): Int {
    return solve(input, 10, 4..10)
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2023/2023_17_test")
  check(part1(testInput) == 102)
  check(part2(testInput) == 94)

  val input = readInput("2023/2023_17")
  check(part1(input) == 668)
  check(part2(input) == 788)
  part1(input).println()
  part2(input).println()
}
