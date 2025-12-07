fun main() {
  fun solver(input: List<String>): Pair<Int, Long> {
    val grid = mutableListOf<String>()

    for (row in 0..input.lastIndex step 2) {
      grid += input[row]
    }

    val beam = grid.first().indexOf('S');
    var count = 0

    val dp = Array(grid.size) { LongArray(grid.first().length) { 0L } }
    dp[0][beam] = 1

    for (row in 1..grid.lastIndex) {
      for (col in 0..grid.first().lastIndex) {
        when (grid[row][col]) {
          '^' -> {
            dp[row][col - 1] += dp[row - 1][col]
            dp[row][col + 1] += dp[row - 1][col]

            if (dp[row - 1][col] > 0) {
              count++
            }
          }

          else -> {
            dp[row][col] += dp[row - 1][col]
          }
        }
      }
    }

    return count to dp.last().sum()
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2025/2025_07_test")
  check(solver(testInput) == 21 to 40L)

  println("=======================")

  val input = readInput("2025/2025_07")
  check(solver(input) == 1622 to 10_357_305_916_520L)
  solver(input).println()
}
