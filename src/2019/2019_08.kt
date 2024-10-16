fun main() {
  fun part1(input: String, width: Int, height: Int): Int {
    val square = width * height

    return input
      .windowed(square, square)
      .map { layer -> layer.groupBy { it }.mapValues { it.value.size } }
      .minBy { it.get('0') ?: Int.MAX_VALUE }
      .let {
        it.getOrDefault('1', 0) * it.getOrDefault('2', 0)
      }
  }

  fun part2(input: String, width: Int, height: Int) {
    val square = width * height
    val layers = input.windowed(square, square)

    List(square) { index -> layers.first { it[index] != '2' }[index] }
      .windowed(width, width)
      .joinToString("\r\n") { row ->
        row
          .joinToString(" ") {
            when (it) {
              '0' -> " "
              else -> "#"
            }
          }
      }
      .println()
  }

  // test if implementation meets criteria from the description, like:
  check(part1("111456781012", 3, 2) == 2)

  val input = readInput("2019/2019_08")
  check(part1(input.first(), 25, 6) == 1584)
  part1(input.first(), 25, 6).println()
  part2(input.first(), 25, 6)
}
