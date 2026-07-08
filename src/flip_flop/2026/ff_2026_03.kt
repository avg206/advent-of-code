fun main() {
  fun addLongestSequence(score: Int, line: String): Int {
    val longest = line.toList().longestSequence().second

    return score + when {
      longest >= 3 -> longest * longest
      else -> 0
    }
  }

  fun addScore(add: Int) = { score: Int, _: String -> score + add }
  fun multiplyScore(multiply: Int) = { score: Int, _: String -> score * multiply }
  fun multiplyScoreByLength(score: Int, line: String) = score * line.length

  fun calculateScore(line: String, extended: Boolean): Int {
    val scoringRules = buildList {
      add({ line: String -> line.any { it in 'A'..'Z' } } to addScore(1))
      add({ line: String -> line.any { it in 'a'..'z' } } to addScore(1))
      add({ line: String -> line.any { it in '0'..'9' } } to addScore(1))

      if (extended) {
        add({ line: String -> line.any { it == '7' } && line.none { it in '0'..'6' || it in '8'..'9' } } to addScore(7))
        add({ _: String -> true } to ::addLongestSequence)
        add({ line: String -> line.contains(Regex("(red|green|blue)")) } to multiplyScore(3))
      }

      add({ _: String -> true } to ::multiplyScoreByLength)
    }

    return scoringRules.fold(0) { acc, (condition, calculation) ->
      when (condition(line)) {
        true -> calculation(acc, line)
        false -> acc
      }
    }
  }

  fun part1(input: List<String>): String {
    return input.map { line ->
      line to calculateScore(line, false)
    }.maxBy { it.second }.first
  }

  fun part2(input: List<String>): String {
    return input.map { line ->
      line to calculateScore(line, true)
    }.maxBy { it.second }.first
  }

  fun part3(input: List<String>): Int {
    val chars = buildList {
      addAll('0'..'9')
      addAll('a'..'z')
      addAll('A'..'Z')
    }

    return chars.maxOf { appendix -> input.sumOf { calculateScore("$it$appendix", true) } }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("flip_flop/2026/ff_2026_03_test")
  check(part1(testInput) == "DkoGreenD7")
  check(part2(testInput) == "de333333")
  check(part3(testInput) == 1_453)

  val input = readInput("flip_flop/2026/ff_2026_03")
  check(part1(input) == "DBuY33bOVo2Nbbgreenbbnn")
  check(part2(input) == "JAWblueHH777juu7iksAH")
  check(part3(input) == 15_348)
  part1(input).println()
  part2(input).println()
  part3(input).println()
}
