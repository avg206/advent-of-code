fun main() {
  fun parseLine(line: String): List<Long> =
    line.substring(9..<line.length).split(", ").map { it.split("+").last().toLong() }

  fun parsePriseLine(line: String): List<Long> =
    line.substring(7..<line.length).split(", ").map { it.split("=").last().toLong() }

  fun costToFindPrize(set: List<String>, prizePositionModifier: (x: Long) -> Long): Long {
    val (dAx, dAy) = parseLine(set[0])
    val (dBx, dBy) = parseLine(set[1])
    val (px, py) = parsePriseLine(set[2]).map(prizePositionModifier)

    val timesB = (py * dAx - px * dAy) / (dBy * dAx - dBx * dAy)
    val timesA = (px - timesB * dBx) / dAx

    val tx = timesA * dAx + timesB * dBx
    val ty = timesA * dAy + timesB * dBy

    if (tx == px && ty == py) {
//      println("found answer - ${timesA * 3 + timesB}")
      return timesA * 3 + timesB
    }

//    println("answer not found")
    return 0
  }

  fun part1(input: List<String>): Long {
    return input
      .windowed(4, 4, true)
      .sumOf { set -> costToFindPrize(set) { it + 0 } }
  }

  fun part2(input: List<String>): Long {
    return input
      .windowed(4, 4, true)
      .sumOf { set -> costToFindPrize(set) { it + 10_000_000_000_000L } }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2024/2024_13_test")
  check(part1(testInput) == 480L)
  check(part2(testInput) == 875_318_608_908L)

  val input = readInput("2024/2024_13")
  check(part1(input) == 29_438L)
  check(part2(input) == 104_958_599_303_720L)
  part1(input).println()
  part2(input).println()
}
