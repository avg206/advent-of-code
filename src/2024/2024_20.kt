import helpers.gridReader
import helpers.point.Point

fun main() {
  fun solve(input: List<String>, cheatLimit: Int, sensitivity: Int): Int {
    val map = MutableList(input.size) { MutableList(input.first().length) { 0 } }
    var end = Point(0, 0)

    gridReader(input) { char, row, column ->
      when (char) {
        '#' -> map[row][column] = -1
        'E' -> end = Point(row, column)
      }
    }

    fun updateMapWithDistance(): List<Point> {
      val queue = mutableListOf(end to 0)
      val visited = mutableSetOf<Point>()

      while (queue.isNotEmpty()) {
        val (current, distance) = queue.removeFirst()

        if (current in visited) continue
        visited.add(current)

        map[current.x][current.y] = distance

        queue.addAll(
          current.neighbours().filter { it.within2DArray(map) && map[it.x][it.y] != -1 }
            .map { it to distance + 1 }
        )
      }

      return visited.toList()
    }

    val empties = updateMapWithDistance()
    val answer = mutableSetOf<Pair<Point, Point>>()

    empties.forEach { from ->
      val fromDistance = map[from.x][from.y]

      for (up in 0..(cheatLimit)) {
        for (down in 0..(cheatLimit - up)) {
          for (right in 0..(cheatLimit - up - down)) {
            for (left in 0..(cheatLimit - up - down - right)) {
              val steps = up + down + right + left
              val target = from + Point(-1, 0) * up + Point(1, 0) * down + Point(0, 1) * right + Point(0, -1) * left

              if (!target.within2DArray(map)) continue

              val targetDistance = map[target.x][target.y]

              if (target.within2DArray(map) && targetDistance != -1) {
                val diff = fromDistance - targetDistance - steps

                if (diff >= sensitivity) answer.add(from to target)
              }
            }
          }
        }
      }
    }

    return answer.size
  }

  fun part1(input: List<String>, sensitivity: Int): Int {
    return solve(input, 2, sensitivity)
  }

  fun part2(input: List<String>, sensitivity: Int): Int {
    return solve(input, 20, sensitivity)
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2024/2024_20_test")
  check(part1(testInput, 1) == 44)
  check(part2(testInput, 50) == 285)

  val input = readInput("2024/2024_20")
  check(part1(input, 100) == 1409)
  check(part2(input, 100) == 1012821)
  part1(input, 100).println()
  part2(input, 100).println()
}
