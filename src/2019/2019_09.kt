import intCode.intCodeRunner

fun main() {
  fun part1(program: String): Long {
    val input = generateSequence { 1L }

    val output = intCodeRunner(program, input.iterator()).toList()
    return output.first()
  }

  fun part2(program: String): Long {
    val input = generateSequence { 2L }

    val output = intCodeRunner(program, input.iterator()).toList()
    return output.first()
  }

  val input = readInput("2019/2019_09")
  check(part1(input.first()) == 3_380_552_333L)
  check(part2(input.first()) == 78_831L)
  part1(input.first()).println()
  part2(input.first()).println()
}
