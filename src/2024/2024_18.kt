import helpers.point.Point

fun main() {
  fun solve(input: List<String>, dimension: Int, startingCount: Int): Pair<Int, String> {
    val map = MutableList(dimension + 1) { MutableList(dimension + 1) { 0 } }
    val bytes = input.map { row -> row.split(",").map { it.toInt() } }

    fun bfs(): Int {
      val queue = mutableListOf(Point(0, 0) to 0)
      val visited = mutableSetOf<Point>()

      while (queue.isNotEmpty()) {
        val (current, distance) = queue.removeFirst()

        if (current == Point(dimension, dimension)) {
          return distance
        }

        if (current in visited) continue
        visited.add(current)

        current.neighbours().filter { it.within2DArray(map) && map[it.x][it.y] == 0 }.forEach { target ->
          queue.add(target to (distance + 1))
        }
      }

      return -1
    }

    bytes.take(startingCount).forEach { (y, x) ->
      map[x][y] = 1
    }

    val initialFastestRoute = bfs()

    bytes.drop(startingCount).forEach { (y, x) ->
      map[x][y] = 1

      if (bfs() == -1) {
        return initialFastestRoute to "$y,$x"
      }
    }

    throw Exception("None of bytes block the road")
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2024/2024_18_test")
  check(solve(testInput, 6, 12) == 22 to "6,1")

  val input = readInput("2024/2024_18")
  check(solve(input, 70, 1024) == 290 to "64,54")
  solve(input, 70, 1024).println()
}
