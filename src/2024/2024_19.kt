fun main() {
  fun solve(input: List<String>): Pair<Int, Long> {
    val patterns = input.first().split(", ").sortedByDescending { it.length }

    fun numberOfOptions(design: String): Long {
      val dp = MutableList(design.length) { 0L }

      patterns.filter { design.startsWith(it) }.forEach { dp[it.length - 1] += 1L }

      for (i in dp.indices.drop(1)) {
        val subDesign = design.substring(0..i)

        patterns.filter { subDesign.endsWith(it) && it != subDesign }.forEach {
          dp[i] += dp[i - it.length]
        }
      }

      return dp.last()
    }

    val numberOfValid = input.drop(2).count { numberOfOptions(it) > 0 }
    val possibleOptions = input.drop(2).sumOf { numberOfOptions(it) }

    return numberOfValid to possibleOptions
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2024/2024_19_test")
  check(solve(testInput) == 6 to 16L)

  val input = readInput("2024/2024_19")
  check(solve(input) == 293 to 623_924_810_770_264L)
  solve(input).println()
}
