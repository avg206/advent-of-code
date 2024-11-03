import kotlin.math.abs

fun main() {
  fun process(input: String, phases: Int): List<Long> {
    fun calculateOutput(signals: List<Long>, position: Int): Long {
      fun calculate(from: Int): Long {
        var answer = 0L
        var sign = 1L
        var current = from

        while (current < signals.size) {
          answer += signals[current] * sign

          sign = if (sign == 1L) -1L else 1L
          current += 2 * position
        }

        return answer
      }

      return List(position) { index -> calculate(index + position - 1) }.sum()
    }

    var signals = input.toList().map { it.digitToInt().toLong() }

    for (phase in 1..phases) {
      signals = List(signals.size) { index ->
        val output = calculateOutput(signals, index + 1)

        return@List abs(output) % 10L
      }
    }

    return signals
  }

  fun part1(input: String, phases: Int): String {
    return process(input, phases).take(8).joinToString("")
  }

  fun part2(input: String, phases: Int): String {
    val offset = input.take(7).toInt()
    val repeatedInput = input
      .repeat(10_000)
      .drop(offset)
      .map { it.digitToInt().toInt() }
      .toMutableList()

    for (phase in 1..phases) {
      var suffixSum = 0

      for (i in repeatedInput.indices.reversed()) {
        suffixSum = (suffixSum + repeatedInput[i]) % 10

        repeatedInput[i] = suffixSum
      }
    }

    return repeatedInput.take(8).joinToString("")
  }

  // test if implementation meets criteria from the description, like:
  check(part1("12345678", 4) == "01029498")
  check(part1("80871224585914546619083218645595", 100) == "24176176")
  check(part1("19617804207202209144916044189917", 100) == "73745418")
  check(part1("69317163492948606335995924319873", 100) == "52432133")

  check(part2("03036732577212944063491565474664", 100) == "84462026")
  check(part2("02935109699940807407585447034323", 100) == "78725270")
  check(part2("03081770884921959731165446850517", 100) == "53553731")

  val input = readInput("2019/2019_16")
  part1(input.first(), 100).println()
  part2(input.first(), 100).println()
}
