fun main() {
  fun solve(input: String, totalBlinks: Int): Long {
    val memory = mutableMapOf<Pair<Long, Int>, Long>()

    fun dfs(stone: Long, step: Int): Long {
      if (step == totalBlinks) return 1L

      val key = Pair(stone, step)
      if (key in memory) return memory[key]!!

      val result = when {
        stone == 0L -> dfs(1, step + 1)

        stone.toString().length % 2 == 0 -> {
          val string = stone.toString()
          val middle = string.length / 2

          val left = string.substring(0..<middle)
          val right = string.substring(middle)

          dfs(left.toLong(), step + 1) + dfs(right.toLong(), step + 1)
        }

        else -> dfs(stone * 2024, step + 1)
      }

      memory[key] = result

      return result
    }

    return input
      .split(" ")
      .map { it.toLong() }
      .sumOf { dfs(it, 0) }
  }

  // test if implementation meets criteria from the description, like:
  check(solve("0 1 10 99 999", 1) == 7L)
  check(solve("125 17", 6) == 22L)
  check(solve("125 17", 25) == 55_312L)

  val input = readInput("2024/2024_11")
  check(solve(input.first(), 25) == 233_875L)
  check(solve(input.first(), 75) == 277_444_936_413_293L)
  solve(input.first(), 25).println()
  solve(input.first(), 75).println()
}
