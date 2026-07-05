import helpers.point.Point
import kotlin.math.abs
import kotlin.math.max

fun main() {

  fun reader(input: List<String>) = input
    .map { line -> line.split(",").map { it.toInt() } }
    .map { Point(it[0], it[1]) }

  fun solver(points: List<Point>, calculate: (Point, Point) -> Int): Int {
    var position = Point(0, 0)
    var distance = 0

    for (target in points) {
      distance += calculate(target, position)
      position = target
    }

    return distance
  }

  fun diagonallyDistance(a: Point, b: Point) = max(abs(a.x - b.x), abs(a.y - b.y))

  fun part1(input: List<String>) = solver(reader(input)) { a, b -> a.manhattan(b) }

  fun part2(input: List<String>) = solver(
    reader(input),
    ::diagonallyDistance
  )

  fun part3(input: List<String>) = solver(
    reader(input).sortedBy { Point(0, 0).manhattan(it) },
    ::diagonallyDistance
  )

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("flip_flop/2025/ff_2025_04_test")
  check(part1(testInput) == 24)
  check(part2(testInput) == 12)
  check(part3(testInput) == 9)

  val input = readInput("flip_flop/2025/ff_2025_04")
  check(part1(input) == 6_839)
  check(part2(input) == 4_941)
  check(part3(input) == 2_109)
  part1(input).println()
  part2(input).println()
  part3(input).println()
}
