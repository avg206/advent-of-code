import kotlin.math.atan2

fun main() {
  fun readAsteroids(input: List<String>): List<Pair<Int, Int>> {
    val asteroids = mutableListOf<Pair<Int, Int>>()

    for (i in input.indices) {
      for (j in input[i].indices) {
        if (input[i][j] == '#') {
          asteroids.add(Pair(i, j))
        }
      }
    }

    return asteroids
  }

  fun visibleAsteroids(asteroids: List<Pair<Int, Int>>, from: Pair<Int, Int>): Map<Double, Pair<Int, Int>> {
    val angles = mutableMapOf<Double, Pair<Int, Int>>()

    asteroids
      .filter { it != from }
      .forEach {
        val angle = atan2((from.second - it.second).toDouble(), (from.first - it.first).toDouble())

        if (angle !in angles) {
          angles[angle] = it
        }
      }

    return angles
  }

  fun part1(input: List<String>): Int {
    val asteroids = readAsteroids(input)

    val byVisibility = asteroids.associateWith { visibleAsteroids(asteroids, it) }

    return byVisibility.maxOf { it.value.size }
  }

  fun part2(input: List<String>): Int {
    val asteroids = readAsteroids(input).toMutableList()

    val byVisibility = asteroids.associateWith { visibleAsteroids(asteroids, it) }
    val (position) = byVisibility.maxBy { it.value.size }

    val removed = mutableListOf<Pair<Int, Int>>()

    while (removed.size < 200) {
      val visible = visibleAsteroids(asteroids, position)

      val right = visible.keys.filter { it <= 0 }.sortedDescending()
      val left = visible.keys.filter { it > 0 }.sortedDescending()

      check(right.size + left.size == visible.size) { "Left + Right != Visible" }

      buildList { addAll(right); addAll(left) }
        .forEach {
          val asteroid = visible[it]!!

          removed.add(asteroid)
          asteroids.remove(asteroid)
        }
    }

    val answerPosition = 199

    return removed[answerPosition].second * 100 + removed[answerPosition].first
  }

  // test if implementation meets criteria from the description, like:
  check(part1(readInput("2019/2019_10_test")) == 33)
  check(part1(readInput("2019/2019_10_test_2")) == 41)
  check(part1(readInput("2019/2019_10_test_3")) == 210)
  check(part2(readInput("2019/2019_10_test_3")) == 802)

  val input = readInput("2019/2019_10")
  check(part1(input) == 299)
  check(part2(input) == 1419)
  part1(input).println()
  part2(input).println()
}
