import helpers.point.Point3D


typealias Bush = Point3D

fun main() {
  fun part1(input: List<String>): String {
    val bushes = mutableMapOf<Bush, Int>()

    input.forEach { line ->
      val (r, g, b) = line.split(",").map { it.toInt() }
      val bush = Bush(r, g, b)

      bushes[bush] = (bushes.getOrDefault(bush, 0) + 1)
    }

    return bushes.maxBy { it.value }.key.toString().replace(" ", "")
  }

  fun part2(input: List<String>): Int {
    return input.filter { line ->
      val (r, g, b) = line.split(",").map { it.toInt() }

      // Special
      if (r == g || r == b || g == b) return@filter false

      return@filter g > r && g > b
    }.count()
  }

  fun part3(input: List<String>): Int {
    return input.sumOf { line ->
      val (r, g, b) = line.split(",").map { it.toInt() }

      when {
        r == g || r == b || g == b -> 10
        r > g && r > b -> 5
        g > r && g > b -> 2
        else -> 4
      }
    }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("flip_flop/2025/ff_2025_03_test")
  check(part1(testInput) == "10,20,30")
  check(part2(testInput) == 0)
  check(part3(testInput) == 37)

  val input = readInput("flip_flop/2025/ff_2025_03")
  check(part1(input) == "43,15,53")
  check(part2(input) == 756)
  check(part3(input) == 8609)
  part1(input).println()
  part2(input).println()
  part3(input).println()
}
