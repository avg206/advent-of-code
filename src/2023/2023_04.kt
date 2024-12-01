import kotlin.math.pow

fun main() {
  fun processCard(line: String): Int {
    val (_, card) = line.split(": ")
    val (left, right) = card.split(" | ")

    val rules = left.split(" ").mapNotNull { it.toIntOrNull() }
    val numbers = right.split(" ").mapNotNull { it.toIntOrNull() }

    return numbers.filter { rules.contains(it) }.size
  }

  fun part1(input: List<String>): Int {
    return input.map { processCard(it) }.sumOf {
      when (it) {
        0 -> 0
        else -> 2.0.pow(it - 1).toInt()
      }
    }
  }

  fun part2(input: List<String>): Int {
    val cards = input.map { processCard(it) }
    val boards = MutableList(cards.size) { 1 }

    cards.forEachIndexed { i, card ->
      for (j in 1..card) {
        boards[i + j] += boards[i]
      }
    }

    return boards.sum()
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2023/2023_04_test")
  check(part1(testInput) == 13)
  check(part2(testInput) == 30)

  val input = readInput("2023/2023_04")
  check(part1(input) == 27059)
  check(part2(input) == 5744979)
  part1(input).println()
  part2(input).println()
}
