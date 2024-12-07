fun main() {
  fun isValid(row: String, ops: List<String>): Boolean {
    val (test, rest) = row.split(": ")

    val numbers = rest.split(" ").map { it.toLong() }
    val target = test.toLong()

    fun dfs(operations: List<String>): Boolean {
      if (operations.size == numbers.size - 1) {
        var sum = numbers.first()

        operations.forEachIndexed { index, op ->
          sum = when (op) {
            "+" -> sum + numbers[index + 1]
            "*" -> sum * numbers[index + 1]
            "||" -> (sum.toString() + numbers[index + 1].toString()).toLong()

            else -> throw Exception("Unknown operation - $op")
          }

          if (sum > target) return false
        }

        return sum == target
      }

      return ops.any { dfs(operations + it) }
    }

    return dfs(listOf())
  }

  fun part1(input: List<String>): Long {
    return input
      .filter { isValid(it, listOf("+", "*")) }
      .sumOf { it.split(": ").first().toLong() }
  }

  fun part2(input: List<String>): Long {
    return input
      .filter { isValid(it, listOf("+", "*", "||")) }
      .sumOf { it.split(": ").first().toLong() }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2024/2024_07_test")
  check(part1(testInput) == 3_749L)
  check(part2(testInput) == 11_387L)

  val input = readInput("2024/2024_07")
  check(part1(input) == 7_885_693_428_401L)
  check(part2(input) == 348_360_680_516_005L)
  part1(input).println()
  part2(input).println()
}
