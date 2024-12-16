import kotlin.math.min

fun main() {
  fun calculateDistance(speed: Int, timeForFly: Int, timeForRest: Int, timestamp: Int): Int {
    val timesFly = timestamp / (timeForFly + timeForRest)
    val leftover = (timestamp % (timeForFly + timeForRest))

    return speed * timeForFly * timesFly + (min(leftover, timeForFly) * speed)
  }

  fun part1(input: List<String>, timestamp: Int): Int {
    val reindeers = input.map { row -> Regex("(\\d+)").findAll(row).map { it.value.toInt() }.toList() }

    return reindeers.maxOf { calculateDistance(it[0], it[1], it[2], timestamp) }
  }

  fun part2(input: List<String>, timestamp: Int): Int {
    val reindeers = input.map { row -> Regex("(\\d+)").findAll(row).map { it.value.toInt() }.toList() }
    val scores = MutableList(reindeers.size) { 0 }

    for (i in 1..timestamp) {
      val scoring = reindeers.map { calculateDistance(it[0], it[1], it[2], i) }

      val leaderIndex = scoring.indices.maxBy { scoring[it] }
      scores[leaderIndex] += 1
    }

    return scores.max()
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2015/2015_14_test")
  check(part1(testInput, 1_000) == 1_120)
  check(part2(testInput, 1_000) == 688)

  val input = readInput("2015/2015_14")
  part1(input, 2_503).println()
  part2(input, 2_503).println()
}
