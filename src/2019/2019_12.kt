import helpers.lcm
import helpers.point.Point3D

fun main() {
  val keys = listOf("x", "y", "z")

  fun parseMoons(input: List<String>): List<Pair<Point3D, Point3D>> {
    return input.map { line ->
      val (x, y, z) = line
        .substring(1..<line.length - 1)
        .split(", ")
        .map { it.split("=").last().toInt() }

      Pair(Point3D(x, y, z), Point3D(0, 0, 0))
    }
  }

  fun updateMoonsKey(moons: List<Pair<Point3D, Point3D>>, key: String) {
    for (i in moons.indices) {
      for (j in i + 1..<moons.size) {
        when {
          moons[i].first[key] < moons[j].first[key] -> {
            moons[i].second[key] += 1
            moons[j].second[key] -= 1
          }

          moons[i].first[key] > moons[j].first[key] -> {
            moons[i].second[key] -= 1
            moons[j].second[key] += 1
          }
        }
      }
    }
  }

  fun part1(input: List<String>, steps: Int): Int {
    var moons = parseMoons(input)

    var step = 0
    while (step < steps) {
      step += 1

      keys.forEach { key ->
        updateMoonsKey(moons, key)
      }
      moons = moons.map { Pair(it.first + it.second, it.second) }
    }

    return moons.sumOf { it.first.absoluteSum() * it.second.absoluteSum() }
  }

  fun part2(input: List<String>): Long {
    var moons = parseMoons(input)

    val cycleLengths = keys.map { key ->
      var step = 0
      val initialStates = moons.joinToString("|") { "${it.first[key]} -- ${it.second[key]}" }

      while (true) {
        step++

        updateMoonsKey(moons, key)
        moons = moons.map { Pair(it.first + it.second, it.second) }

        val currentState = moons.joinToString("|") { "${it.first[key]} -- ${it.second[key]}" }
        if (currentState == initialStates) break
      }

      step
    }

    return cycleLengths.map { it.toLong() }.reduce { acc, l -> lcm(acc, l) }
  }

  // test if implementation meets criteria from the description, like:
  check(part1(readInput("2019/2019_12_test"), 10) == 179)
  check(part1(readInput("2019/2019_12_test_2"), 100) == 1940)
  check(part2(readInput("2019/2019_12_test")) == 2772L)
  check(part2(readInput("2019/2019_12_test_2")) == 4_686_774_924L)

  val input = readInput("2019/2019_12")
  part1(input, 1_000).println()
  part2(input).println()
}
