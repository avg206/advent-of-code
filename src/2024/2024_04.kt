import helpers.point.Point

fun main() {
  fun part1(input: List<String>): Int {
    val directions = listOf(
      Point(1, 0),
      Point(-1, 0),
      Point(0, 1),
      Point(0, -1),
      Point(1, 1),
      Point(-1, 1),
      Point(1, -1),
      Point(-1, -1),
    )

    var answer = 0

    for (i in input.indices) {
      for (j in input.first().indices) {
        if (input[i][j] == 'X') {
          for (dir in directions) {
            var curr = Point(i, j)
            var valid = true

            for (char in listOf('M', 'A', 'S')) {
              curr += dir

              if (!curr.within2DArrayString(input) || input[curr.x][curr.y] != char) {
                valid = false
              }
            }

            if (valid) {
              answer += 1
            }
          }
        }
      }
    }

    return answer
  }

  fun part2(input: List<String>): Int {
    var answer = 0

    fun takeMas(points: List<Point>): String {
      return points
        .map { if (it.within2DArrayString(input)) input[it.x][it.y] else '_' }
        .joinToString("")
    }

    for (i in input.indices) {
      for (j in input.indices) {
        if (input[i][j] != 'A') continue

        // Bottom-Left to Top-Right
        val set1 = takeMas(
          listOf(
            Point(i - 1, j - 1),
            Point(i, j),
            Point(i + 1, j + 1),
          )
        )

        // Top-Left to Bottom-Right
        val set2 = takeMas(
          listOf(
            Point(i + 1, j - 1),
            Point(i, j),
            Point(i - 1, j + 1),
          )
        )

        if ((set1 == "MAS" || set1 == "SAM") && (set2 == "MAS" || set2 == "SAM")) {
          answer += 1
        }
      }
    }

    return answer
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2024/2024_04_test")
  check(part1(testInput) == 18)
  check(part2(testInput) == 9)

  val input = readInput("2024/2024_04")
  check(part1(input) == 2358)
  check(part2(input) == 1737)
  part1(input).println()
  part2(input).println()
}
