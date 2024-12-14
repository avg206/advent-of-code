import helpers.point.Point

fun main() {
  fun parseNumbers(string: String) = string
    .split("=")
    .last()
    .split(",")
    .map { it.toInt() }
    .let { (x, y) -> Point(y, x) }

  fun calculateSafetyFactor(robots: List<Pair<Point, Point>>, width: Int, height: Int): Int {
    return listOf(
      Pair(0..<width / 2, 0..<height / 2),
      Pair(0..<width / 2, (height / 2) + 1..<height),
      Pair((width / 2) + 1..<width, 0..<height / 2),
      Pair((width / 2) + 1..<width, (height / 2) + 1..<height),
    )
      .map { (yRange, xRange) ->
        robots.count { it.first.x in xRange && it.first.y in yRange }
      }
      .fold(1) { acc, i -> acc * i }
  }

  fun simulate(robot: Pair<Point, Point>, width: Int, height: Int): Pair<Point, Point> {
    val (position, velocity) = robot

    var newPos = position + velocity

    if (newPos.x >= height) {
      newPos = Point(newPos.x - height, newPos.y)
    }

    if (newPos.x < 0) {
      newPos = Point(height + newPos.x, newPos.y)
    }

    if (newPos.y >= width) {
      newPos = Point(newPos.x, newPos.y - width)
    }

    if (newPos.y < 0) {
      newPos = Point(newPos.x, width + newPos.y)
    }

    return Pair(newPos, velocity)
  }

  fun visualise(robots: List<Pair<Point, Point>>, step: Int, width: Int, height: Int) {
    println("---- ${step}")
    for (i in 0..<height) {
      for (j in 0..<width) {
        when (val count = robots.count { it.first == Point(i, j) }) {
          0 -> print(" ")
          else -> print("*")
        }
      }
      println("")
    }
  }

  fun solve(input: List<String>, width: Int, height: Int, targetSeconds: Int): Pair<Int, Int> {
    var robots = listOf<Pair<Point, Point>>()

    input.forEach { row ->
      val (position, velocity) = row.split(" ").map { parseNumbers(it) }

      robots = robots + Pair(position, velocity)
    }

    var safetyFactor = 0

    for (step in 1..10_000) {
      robots = robots.map { simulate(it, width, height) }

      if (step == targetSeconds) {
        safetyFactor = calculateSafetyFactor(robots, width, height)
      }

      if (robots.map { it.first }.distinct().size == robots.size) {
        visualise(robots, step, width, height)

        return Pair(safetyFactor, step)
      }
    }

    throw Exception("X-Mas tree is not found")
  }

  val input = readInput("2024/2024_14")
  check(solve(input, 101, 103, 100) == Pair(222_901_875, 6243))
  solve(input, 101, 103, 100).println()
}
