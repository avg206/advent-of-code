typealias PresentShape = Pair<Int, List<String>>

fun main() {
  fun parsePresents (inout: List<String>): MutableList<Int> {
    val presentShapes = mutableListOf<Int>()

    for (k in 0..5) {
      var total = 0

      for (i in 0..2) {
        for (j in 0..2) {
          if (inout[(k * 5) + 1 + i][j] == '#') total++
        }
      }

      presentShapes += total
    }

    return presentShapes
  }

  fun part1(input: List<String>): Int {
    val presents = parsePresents(input.subList(0, 5 * 6))

    fun process(region: String): Boolean {
      val (dimensions, rest) = region.split(": ")
      val (w, h) = dimensions.split("x").map { it.toInt() }
      val numbers = rest.split(" ").map { it.toInt() }

      val totalArea = w * h
      val areaNeeded = numbers.mapIndexed { index, i -> presents[index] * i }.sum()

      return totalArea >= areaNeeded
    }

    return input.subList(5 * 6, input.lastIndex + 1).count { process(it) }
  }

  val input = readInput("2025/2025_12")
  check(part1(input) == 583)
  part1(input).println()
}
