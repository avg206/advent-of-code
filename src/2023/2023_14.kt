typealias MirrorMap = MutableList<MutableList<Char>>

fun main() {
  fun tiltVertical(map: MirrorMap, process: (Char, Char) -> Pair<Char, Char>): MirrorMap {
    for (i in map[0].indices) {
      var changed: Boolean

      do {
        changed = false

        for (j in 0 until map.size - 1) {
          val (a, b) = process(map[j][i], map[j + 1][i])

          if (map[j][i] != a) {
            changed = true
          }

          map[j][i] = a
          map[j + 1][i] = b
        }
      } while (changed)
    }

    return map
  }

  fun processNorth(top: Char, bottom: Char) = when {
    top == '.' && bottom == 'O' -> Pair('O', '.')
    else -> Pair(top, bottom)
  }

  fun processSouth(top: Char, bottom: Char) = when {
    top == 'O' && bottom == '.' -> Pair('.', 'O')
    else -> Pair(top, bottom)
  }

  fun tiltHorizontal(map: MirrorMap, process: (Char, Char) -> Pair<Char, Char>): MirrorMap {
    for (i in map.indices) {
      var changed: Boolean

      do {
        changed = false

        for (j in 0 until map[0].size - 1) {
          val (a, b) = process(map[i][j], map[i][j + 1])

          if (map[i][j] != a) {
            changed = true
          }

          map[i][j] = a
          map[i][j + 1] = b
        }
      } while (changed)
    }

    return map
  }

  fun processWest(left: Char, right: Char) = when {
    left == '.' && right == 'O' -> Pair('O', '.')
    else -> Pair(left, right)
  }

  fun processOost(left: Char, right: Char) = when {
    left == 'O' && right == '.' -> Pair('.', 'O')
    else -> Pair(left, right)
  }

  fun calculateLoad(map: MirrorMap): Int {
    var result = 0

    for (i in map.indices) {
      val count = map[i].filter { it == 'O' }.size

      result += count * (map.size - i)
    }

    return result
  }

  fun part1(input: List<String>): Int {
    var map = input.map { it.toMutableList() }.toMutableList()
    map = tiltVertical(map, ::processNorth)

    return calculateLoad(map)
  }

  fun preCalculate(map: MirrorMap): Pair<Int, List<Int>> {
    val numberOfCyclesToTry = 350

    var localMap = map
    val results = mutableListOf<Int>()

    for (i in 1..numberOfCyclesToTry) {
      localMap = tiltVertical(localMap, ::processNorth)
      localMap = tiltHorizontal(localMap, ::processWest)
      localMap = tiltVertical(localMap, ::processSouth)
      localMap = tiltHorizontal(localMap, ::processOost)

      results.add(calculateLoad(localMap))
    }

    // Look for cycle
    for (cycleSize in 2..results.size / 2) {
      var start = 0

      while (start + 3 * cycleSize < results.size) {
        val sequenceOne = results.subList(start, start + cycleSize)
        val sequenceTwo = results.subList(start + cycleSize, start + cycleSize + cycleSize)
        val sequenceThree = results.subList(
          start + cycleSize + cycleSize,
          start + cycleSize + cycleSize + cycleSize
        )

        if (sequenceOne == sequenceTwo && sequenceOne == sequenceThree) {
//                    println("Found - $cycleSize, $start")

          return Pair(start, sequenceTwo)
        }

        start++
      }
    }

    throw Exception("Cycle not found")
  }

  fun part2(input: List<String>): Int {
    val map = input.map { it.toMutableList() }.toMutableList()

    val (start, cycle) = preCalculate(map)

    val targetPosition = 1_000_000_000
    val position = ((targetPosition - start) % cycle.size) - 1

    return cycle[position]
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2023/2023_14_test")
  check(part1(testInput) == 136)
  check(part2(testInput) == 64)

  val input = readInput("2023/2023_14")
  check(part1(input) == 108_792)
  check(part2(input) == 99_118)
  part1(input).println()
  part2(input).println()
}
