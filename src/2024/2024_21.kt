import helpers.point.Direction
import helpers.point.Point
import kotlin.math.abs

fun main() {
  val numKeypad = listOf(
    listOf('7', '8', '9'),
    listOf('4', '5', '6'),
    listOf('1', '2', '3'),
    listOf(' ', '0', 'A')
  )
  val numKeypadStart = Point(3, 2)

  val dirKeypad = listOf(
    listOf(' ', '^', 'A'),
    listOf('<', 'v', '>')
  )
  val dirKeypadStart = Point(0, 2)

  fun locateChar(map: List<List<Char>>, char: Char): Point {
    for (i in map.indices) {
      for (j in map[i].indices) {
        if (map[i][j] == char) return Point(i, j)
      }
    }

    throw Exception("No char in map")
  }

  fun verifyPath(map: List<List<Char>>, from: Point, path: List<Char>): Boolean {
    var current = from

    path.forEach { char ->
      current += when (char) {
        'A' -> Point(0, 0)
        else -> Direction.charToDirection(char).moveList()
      }

      if (map[current.x][current.y] == ' ') return false
    }

    return true
  }

  fun unWrapPath(map: List<List<Char>>, from: Point, target: List<Char>): List<List<List<Char>>> {
    var options = listOf(listOf<List<Char>>())
    var current = from

    target.forEach { char ->
      val position = locateChar(map, char)

      options = options.flatMap { path ->
        val yDiff = current.y - position.y
        val xDiff = current.x - position.x

        val yChar = if (yDiff > 0) '<' else '>'
        val xChar = if (xDiff > 0) '^' else 'v'

        val pathA = listOf((List(abs(yDiff)) { yChar }) + (List(abs(xDiff)) { xChar }) + 'A')
        val pathB = listOf((List(abs(xDiff)) { xChar }) + (List(abs(yDiff)) { yChar }) + 'A')

        return@flatMap listOf(
          path + pathA,
          path + pathB,
        ).filter { verifyPath(map, from, it.flatten()) }
      }.distinct()

      current = position
    }

    return options
  }

  fun unWrappedCodeLength(code: String, targetDepth: Int): Long {
    val memory = mutableMapOf<Pair<String, Int>, Long>()

    fun dfs(subpath: List<Char>, depth: Int): Long {
      if (depth == targetDepth) return subpath.size.toLong()

      val key = (subpath.joinToString("") to depth)
      if (key in memory) return memory[key]!!

      val answer = unWrapPath(dirKeypad, dirKeypadStart, subpath)
        .minOf { option -> option.sumOf { dfs(it, depth + 1) } }

      memory[key] = answer

      return answer
    }

    return unWrapPath(numKeypad, numKeypadStart, code.toList())
      .minOf { option -> option.sumOf { dfs(it, 0) } }
  }

  fun solve(input: List<String>, targetDepth: Int): Long {
    return input.sumOf { unWrappedCodeLength(it, targetDepth) * it.substring(0..2).toLong() }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2024/2024_21_test")
  check(solve(testInput, 2) == 126_384L)

  val input = readInput("2024/2024_21")
  check(solve(input, 2) == 242_484L)
  check(solve(input, 25) == 294_209_504_640_384L)
  solve(input, 2).println()
  solve(input, 25).println()
}
