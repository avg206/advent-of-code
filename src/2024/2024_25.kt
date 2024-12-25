fun main() {
  fun part1(input: List<String>): Int {
    val items = input.joinToString("\r\n").split("\r\n\r\n").map { it.lines() }

    val locks = items.filter { item -> item.first().all { it == '#' } }
    val keys = items.filter { item -> item.last().all { it == '#' } }

    val answer = mutableSetOf<Pair<Int, Int>>()

    for (i in locks.indices) {
      for (j in keys.indices) {
        val lock = locks[i]
        val key = keys[j]

        var valid = true

        loop@ for (x in lock.indices) {
          for (y in lock[x].indices) {
            if (lock[x][y] == '#' && key[x][y] == '#') {
              valid = false
              break@loop
            }
          }
        }

        if (valid) {
          answer.add(i to j)
        }
      }
    }

    return answer.size
  }


  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2024/2024_25_test")
  check(part1(testInput) == 3)

  val input = readInput("2024/2024_25")
  part1(input).println()
}
