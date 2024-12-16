import helpers.gridReader
import helpers.point.Direction
import helpers.point.Point
import java.util.*

typealias Position = Pair<Point, Direction>

fun main() {
  fun solve(input: List<String>): Pair<Int, Int> {
    val map = MutableList(input.size) { MutableList(input.first().length) { 0 } }

    var start = Point(0, 0)
    var finish = Point(0, 0)

    gridReader(input) { char, row, column ->
      when (char) {
        '#' -> map[row][column] = 1
        'S' -> start = Point(row, column)
        'E' -> finish = Point(row, column)
      }
    }

    println("-----------")

    fun visualise(visited: Set<Point>) {
      println("Visual")
      for (i in map.indices) {
        for (j in map[i].indices) {
          when {
            Point(i, j) in visited -> print("*")
            map[i][j] == 1 -> print("#")
            else -> print(" ")
          }
        }
        println()
      }
    }

    fun buildTrace(
      chain: Map<Position, Position>,
      seen: Map<Position, Int>,
      from: Position
    ): Map<Position, Int> {
      val result = mutableMapOf<Position, Int>()
      var current = from

      while (current != Point(-1, -1) to Direction.Up) {
        result[current] = seen[current]!!
        current = chain[current]!!
      }

      return result
    }

    fun dijkstra(start: Point, direction: Direction): Pair<Int, Map<Position, Int>> {
      val queue = PriorityQueue<Triple<Position, Int, Position>>(compareBy { it.second })
      val seen = mutableMapOf<Position, Int>()
      val previous = mutableMapOf<Position, Position>()
      queue.add(Triple(start to direction, 0, Point(-1, -1) to Direction.Up))

      while (queue.isNotEmpty()) {
        val (position, score, prev) = queue.poll()

        if (position.first == finish) {
          seen[position] = score
          previous[position] = prev
          return score to buildTrace(previous, seen, position)
        }

        if (seen[position] != null && seen[position]!! < score) continue
        seen[position] = score
        previous[position] = prev

        val (point, direction) = position

        val target = point + direction.moveList()
        if (map[target.x][target.y] != 1)
          queue.add(Triple(target to direction, score + 1, position))

        queue.add(Triple(point to direction.toRight(), score + 1_000, position))
        queue.add(Triple(point to direction.toLeft(), score + 1_000, position))
      }

      return 0 to mutableMapOf()
    }

    var (lowestScore, trace) = dijkstra(start, Direction.Right)
    trace = trace.toMutableMap()

    var uniqueDots = trace.keys.map { it.first }.toSet()
    var stepsToTry = trace.entries
      .map { it.key to it.value }
      .filter { (position, _) ->
        position.first.neighbours().any { map[it.x][it.y] != 1 && it !in uniqueDots }
      }

    fun bfs(from: Position, fromScore: Int): List<Pair<Position, Int>> {
      val queue = mutableListOf(Triple(from, fromScore, mapOf<Position, Int>()))
      val tried = mutableSetOf<Position>()

      while (queue.isNotEmpty()) {
        val (curr, currScore, path) = queue.removeFirst()

        if (currScore > lowestScore) continue
        if (path.size > trace.size) continue

        if (trace[curr] != null) {
          if (trace[curr] == currScore) {
            trace = trace + path
            return path.entries.map { it.key to it.value }
          }

          continue
        }

        if (curr in tried) continue
        tried.add(curr)

        val footPrint = curr to currScore

        val target = curr.first + curr.second.moveList()
        if (map[target.x][target.y] != 1)
          queue.add(Triple(target to curr.second, currScore + 1, path + footPrint))

        queue.add(Triple(curr.first to curr.second.toRight(), currScore + 1_000, path + footPrint))
        queue.add(Triple(curr.first to curr.second.toLeft(), currScore + 1_000, path + footPrint))
      }

      return emptyList()
    }

    while (stepsToTry.isNotEmpty()) {
      stepsToTry = stepsToTry.flatMap { (position, score) ->
        listOf(
          Triple(position.first + position.second.moveList(), position.second, 1),
          Triple(position.first, position.second.toRight(), 1_000),
          Triple(position.first, position.second.toLeft(), 1_000)
        )
          .filter { (target) -> target.within2DArray(map) && map[target.x][target.y] == 0 }
          .flatMap { (target, targetDirection, scoreDiff) ->
            bfs(target to targetDirection, score + scoreDiff)
          }
      }
        .filter { (position) -> position.first !in uniqueDots }
        .filter { (position) -> position.first.neighbours().any { map[it.x][it.y] != 1 && it !in uniqueDots } }

      uniqueDots = uniqueDots + (stepsToTry.map { it.first.first })
    }

    println("${lowestScore to uniqueDots.size}")

    return lowestScore to uniqueDots.size
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2024/2024_16_test")
  check(solve(readInput("2024/2024_16_test")) == Pair(7036, 45))
  check(solve(readInput("2024/2024_16_test_2")) == Pair(11048, 64))

  val input = readInput("2024/2024_16")
  check(solve(input) == Pair(73_404, 449))
//  solve(input).println()
//  part2(input).println()
}
