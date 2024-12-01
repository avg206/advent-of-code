fun main() {
  val encryption_base = 20_201_227L

  fun calculateLoopSize(target: Long): Int {
    val subject = 7L

    var value = 1L
    var loop = 0

    while (true) {
      loop++

      value = (value * subject).rem(encryption_base)

      if (value == target) {
        return loop
      }
    }
  }

  fun encrypt(subject: Long, loop: Int): Long {
    var value = 1L

    for (i in 1..loop) {
      value = (value * subject).rem(encryption_base)
    }

    return value
  }

  fun part1(input: List<String>): Long {
    val loopSize = calculateLoopSize(input[0].toLong())

    return encrypt(input[1].toLong(), loopSize)
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2020/2020_25_test")
  check(part1(testInput) == 14_897_079L)

  val input = readInput("2020/2020_25")
  part1(input).println()
}
