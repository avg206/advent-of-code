fun main() {
  fun part1(input: String): Int {
    val commands = Regex("mul\\(([0-9]+),([0-9]+)\\)").findAll(input)

    return commands.sumOf { match ->
      val (_, a, b) = match.groupValues

      a.toInt() * b.toInt()
    }
  }

  fun part2(input: String): Int {
    val commands = Regex("mul\\(([0-9]+),([0-9]+)\\)|do\\(\\)|don't\\(\\)").findAll(input)
    var enabled = true

    return commands.sumOf { command ->
      return@sumOf when (command.value) {
        "do()" -> {
          enabled = true
          0
        }

        "don't()" -> {
          enabled = false
          0
        }

        else -> {
          if (!enabled) return@sumOf 0

          val (_, a, b) = command.groupValues

          a.toInt() * b.toInt()
        }
      }
    }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2024/2024_03_test")
  check(part1(testInput.joinToString("")) == 161)
  check(part2("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))") == 48)

  val input = readInput("2024/2024_03")
  check(part1(input.joinToString("")) == 182_619_815)
  check(part2(input.joinToString("")) == 80_747_545)
  part1(input.joinToString("")).println()
  part2(input.joinToString("")).println()
}
