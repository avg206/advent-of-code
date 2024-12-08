import helpers.gridReader
import helpers.point.Point
import kotlin.math.max
import kotlin.math.min

fun main() {
  fun readInput(input: List<String>): Pair<List<List<Char>>, MutableMap<Char, List<Point>>> {
    val map = MutableList(input.size) { MutableList(input.first().length) { ' ' } }

    val antennas = mutableMapOf<Char, List<Point>>()

    gridReader(input) { char, i, j ->
      map[i][j] = char

      if (char != '.') {
        antennas[char] = (antennas[char] ?: listOf()) + Point(i, j)
      }
    }

    return Pair(
      map, antennas
    )
  }

  fun part1(input: List<String>): Int {
    val (map, antennas) = readInput(input)

    val antinodes = mutableSetOf<Point>()

    for ((_, group) in antennas) {
      for (i in group.indices) {
        for (j in i + 1..<group.size) {
          val a = group[i]
          val b = group[j]

          val xRange = min(a.x, b.x)..max(a.x, b.x)
          val yRange = min(a.y, b.y)..max(a.y, b.y)

          val diffX = (a.x - b.x)
          val diffY = (a.y - b.y)

          listOf(
            Pair(a, Point(diffX, diffY)),
            Pair(a, Point(-1 * diffX, -1 * diffY)),
            Pair(b, Point(diffX, diffY)),
            Pair(b, Point(-1 * diffX, -1 * diffY)),
          ).forEach { (from, diff) ->
            val node = from + diff

            if (node.within2DArray(map) && (node.x !in xRange || node.y !in yRange)) {
              antinodes.add(node)
            }
          }
        }
      }
    }

    return antinodes.size
  }

  fun part2(input: List<String>): Int {
    val (map, antennas) = readInput(input)

    val antinodes = mutableSetOf<Point>()

    for ((_, group) in antennas) {
      for (i in group.indices) {
        for (j in i + 1..<group.size) {
          val a = group[i]
          val b = group[j]

          antinodes.add(a)
          antinodes.add(b)

          val xRange = min(a.x, b.x)..max(a.x, b.x)
          val yRange = min(a.y, b.y)..max(a.y, b.y)

          val diffX = (a.x - b.x)
          val diffY = (a.y - b.y)

          listOf(
            Pair(a, Point(diffX, diffY)),
            Pair(a, Point(-1 * diffX, -1 * diffY)),
            Pair(b, Point(diffX, diffY)),
            Pair(b, Point(-1 * diffX, -1 * diffY)),
          ).forEach { (from, diff) ->
            var node = from

            while (node.within2DArray(map)) {
              node += diff

              if (node.within2DArray(map) && (node.x !in xRange || node.y !in yRange)) {
                antinodes.add(node)
              }
            }
          }
        }
      }

    }

    return antinodes.size
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2024/2024_08_test")
  check(part1(testInput) == 14)
  check(part2(testInput) == 34)

  val input = readInput("2024/2024_08")
  check(part1(input) == 426)
  check(part2(input) == 1_359)
  part1(input).println()
  part2(input).println()
}
