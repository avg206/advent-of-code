fun main() {
  fun part1(key: String): Long {
    var i = 1L

    while (true) {
      val input = "$key$i"

      val md5 = input.md5()

      if (md5.startsWith("00000")) {
        return i
      }

      i += 1
    }
  }

  fun part2(key: String): Long {
    var i = 1L

    while (true) {
      val input = "$key$i"

      val md5 = input.md5()

      if (md5.startsWith("000000")) {
        return i
      }

      i += 1
    }
  }

  // test if implementation meets criteria from the description, like:
  check(part1("abcdef") == 609_043L)
  check(part1("pqrstuv") == 1_048_970L)

  val input = readInput("2015/2015_04")
  part1(input.first()).println()
  part2(input.first()).println()
}
