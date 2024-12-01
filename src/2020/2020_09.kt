fun main() {
  fun part1(input: List<String>, preamble: Int): Long {
    val numbers = input.map { it.toLong() }

    for (i in preamble..numbers.size) {
      val number = numbers[i]

      var valid = false

      loop@ for (j in 1..preamble) {
        for (f in 1..preamble) {
          if (j == f) {
            continue
          }

          if (numbers[i - j] + numbers[i - f] == number) {
            valid = true

            break@loop
          }
        }
      }

      if (!valid) {
        return number
      }
    }

    throw Exception()
  }

  fun part2(input: List<String>, target: Long): Long {
    val numbers = input.map { it.toLong() }

    for (start in numbers.indices) {
      var end = start
      var sum = numbers[start]

      while (sum < target) {
        end++
        sum += numbers[end]
      }

      if (sum == target) {
        return numbers.subList(start, end).min() + numbers.subList(start, end).max()
      }
    }

    throw Exception()
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2020/2020_09_test")
  check(part1(testInput, 5) == 127L)
  check(part2(testInput, 127L) == 62L)

  val input = readInput("2020/2020_09")
  part1(input, 25).println()
  part2(input, 776203571L).println()
}
