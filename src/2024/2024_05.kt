fun main() {
  fun solve(input: List<String>): Pair<Int, Int> {
    val (rules, manuals) = input.joinToString("\r\n").split("\r\n\r\n")
    val orderRules = rules
      .lines()
      .map { row -> row.split("|").map { it.toInt() } }
      .map { (a, b) -> Pair(a, b) }

    val items = manuals.lines().map { row -> row.split(",").map { it.toInt() } }
    val sortedItems = items.map { manual ->
      Pair(
        manual,
        manual.sortedWith { a, b -> if (orderRules.any { it == Pair(b, a) }) 1 else -1 }
      )
    }

    return Pair(
      sortedItems.filter { (a, b) -> a == b }.sumOf { (a, _) -> a[a.size / 2] },
      sortedItems.filter { (a, b) -> a != b }.sumOf { (_, b) -> b[b.size / 2] },
    )
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2024/2024_05_test")
  check(solve(testInput) == Pair(143, 123))

  val input = readInput("2024/2024_05")
  check(solve(input) == Pair(5_713, 5_180))
  solve(input).println()
}
