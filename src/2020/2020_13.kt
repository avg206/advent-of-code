import kotlin.math.ceil

fun main() {
  fun part1(input: List<String>): Int {
    val timestamp = input[0].toDouble()
    val buses = input[1].split(",")

    val (id, div) = buses
      .filter { it != "x" }
      .map { it.toInt() }
      .map { Pair(it, timestamp / it) }
      .minBy { it.second }

    val waitTime = (id * ceil(div)) - timestamp

    return (waitTime * id).toInt()
  }

  fun part2(input: String): Long {
    val buses = input
      .split(",")
      .mapIndexed { index, s ->
        when (s) {
          "x" -> Pair(-1L, -1)
          else -> Pair(s.toLong(), index)
        }
      }
      .filter { it.first != -1L }
      .toMutableList()

    val (id) = buses.removeFirst()
    var timestamp = 0L
    var step = id

    buses.forEach { (id, index) ->
      while ((timestamp + index) % id != 0L) {
        timestamp += step
      }

      step *= id
    }

    return timestamp
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2020/2020_13_test")
  check(part1(testInput) == 295)

  check(part2(testInput[1]) == 1068781L)
  check(part2("17,x,13,19") == 3417L)
  check(part2("67,7,59,61") == 754018L)
  check(part2("67,x,7,59,61") == 779210L)
  check(part2("67,7,x,59,61") == 1261476L)
  check(part2("1789,37,47,1889") == 1202161486L)

  val input = readInput("2020/2020_13")
  part1(input).println()
  part2(input[1]).println()
}
