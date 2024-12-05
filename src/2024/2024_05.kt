fun main() {
  fun solve(input: List<String>): Pair<Int, Int> {
    val rules = mutableMapOf<Int, List<Int>>()
    val rulesEnd = input.indexOfFirst { it.isBlank() }

    for (i in 0..rulesEnd) {
      if (input[i].isBlank()) {
        break
      }

      val (from, to) = input[i].split("|").map { it.toInt() }
      rules[to] = (rules[to] ?: listOf()) + from
    }

    val manuals = input.subList(rulesEnd + 1, input.size).map { row -> row.split(",").map { it.toInt() } }

    var sumOfCorrect = 0
    val toBeFixed = mutableListOf<List<Int>>()

    for (manual in manuals) {
      val printed = mutableSetOf<Int>()

      val valid = manual.all { page ->
        val toBePrinted = rules[page] ?: listOf()
        printed.add(page)

        toBePrinted.filter { it in manual }.all { it in printed }
      }

      if (valid) {
        sumOfCorrect += manual[manual.size / 2]
      } else {
        toBeFixed.add(manual)
      }
    }

    fun dfs(printed: List<Int>, remaining: Set<Int>, manual: Set<Int>): List<Int>? {
      if (remaining.isEmpty()) return printed

      for (page in remaining) {
        val toBePrinted = rules[page] ?: listOf()

        val canBeNext = toBePrinted.filter { it in manual }.all { it in printed }

        if (canBeNext) {
          val attempt = dfs(printed + page, remaining - page, manual)

          if (attempt != null) return attempt
        }
      }

      return null
    }

    var sumOfFixed = 0

    for (manual in toBeFixed) {
      val fixedVersion = dfs(listOf(), manual.toSet(), manual.toSet()) ?: throw Exception("Unfixable manual - $manual")

      sumOfFixed += fixedVersion[fixedVersion.size / 2]
    }

    return Pair(sumOfCorrect, sumOfFixed)
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2024/2024_05_test")
  check(solve(testInput) == Pair(143, 123))

  val input = readInput("2024/2024_05")
  check(solve(input) == Pair(5_713, 5_180))
  solve(input).println()
}
