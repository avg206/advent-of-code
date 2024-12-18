import helpers.graph.bfs
import helpers.point.Point
import kotlin.math.abs

fun main() {
  fun solve(input: List<String>, dimension: Int, startingCount: Int): Pair<Int, String> {
    val bytes = input.map { row -> row.split(",").map { it.toInt() } }

    val buildMap = { initialBytes: List<List<Int>> ->
      val result = MutableList(dimension + 1) { MutableList(dimension + 1) { 0 } }

      initialBytes.forEach { (y, x) -> result[x][y] = 1 }
      result
    }

    val run = { map: List<List<Int>> ->
      bfs(
        start = listOf(Point(0, 0) to 0),
        target = Point(dimension, dimension),
        comparator = compareBy { it.second },
        nextSteps = { (key, value) ->
          key.neighbours().filter { it.within2DArray(map) && map[it.x][it.y] == 0 }.map { it to (value + 1) }
        }
      )
    }

    val (_, initialFastestRoute) = run(buildMap(bytes.take(startingCount))) ?: throw Exception("No route as all")

    val index = abs(bytes.indices.toList().binarySearch { middle ->
      val distance = run(buildMap(bytes.take(middle + 1)))

      if (distance == null) 1 else -1
    })

    return initialFastestRoute to bytes[index - 1].joinToString(",")
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2024/2024_18_test")
  check(solve(testInput, 6, 12) == 22 to "6,1")

  val input = readInput("2024/2024_18")
  check(solve(input, 70, 1024) == 290 to "64,54")
  solve(input, 70, 1024).println()
}
