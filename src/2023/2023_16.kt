import kotlin.math.max

enum class Dir {
  TOP, BOTTOM, LEFT, RIGHT
}

data class Point(val x: Int, val y: Int) {
  operator fun plus(newPoint: Point): Point {
    return Point(x + newPoint.x, y + newPoint.y)
  }
}

fun main() {
  val moves = mapOf(
    Dir.TOP to Point(-1, 0),
    Dir.BOTTOM to Point(1, 0),
    Dir.LEFT to Point(0, -1),
    Dir.RIGHT to Point(0, 1),
  )

  // -
  val horizontalSplitter = mapOf(
    Dir.RIGHT to listOf(Dir.RIGHT),
    Dir.LEFT to listOf(Dir.LEFT),
    Dir.TOP to listOf(Dir.RIGHT, Dir.LEFT),
    Dir.BOTTOM to listOf(Dir.RIGHT, Dir.LEFT)
  )

  // |
  val verticalSplitter = mapOf(
    Dir.RIGHT to listOf(Dir.TOP, Dir.BOTTOM),
    Dir.LEFT to listOf(Dir.TOP, Dir.BOTTOM),
    Dir.TOP to listOf(Dir.TOP),
    Dir.BOTTOM to listOf(Dir.BOTTOM)
  )

  // \
  val leftToRightSplitter = mapOf(
    Dir.RIGHT to Dir.BOTTOM,
    Dir.LEFT to Dir.TOP,
    Dir.TOP to Dir.LEFT,
    Dir.BOTTOM to Dir.RIGHT
  )

  // /
  val rightToLeftSplitter = mapOf(
    Dir.RIGHT to Dir.TOP,
    Dir.LEFT to Dir.BOTTOM,
    Dir.TOP to Dir.RIGHT,
    Dir.BOTTOM to Dir.LEFT
  )

  fun calculateEnergy(input: List<String>, start: Point, startDir: Dir): Int {
    val map = MutableList(input.size) { MutableList(input[0].length) { mutableListOf<Dir>() } }

    val queue = mutableListOf(Pair(start, startDir))

    while (queue.isNotEmpty()) {
      val (current, dir) = queue.removeFirst()

      if (current.x !in input.indices || current.y !in input[0].indices) {
        continue
      }

      if (map[current.x][current.y].contains(dir)) {
        continue
      }

      map[current.x][current.y].add(dir)

      when (input[current.x][current.y]) {
        '.' -> {
          queue.add(Pair(current + moves[dir]!!, dir))
        }

        '-' -> {
          horizontalSplitter[dir]!!.forEach {
            queue.add(Pair(current + moves[it]!!, it))
          }
        }

        '|' -> {
          verticalSplitter[dir]!!.forEach {
            queue.add(Pair(current + moves[it]!!, it))
          }
        }

        '\\' -> {
          val newDir = leftToRightSplitter[dir]!!

          queue.add(Pair(current + moves[newDir]!!, newDir))
        }

        '/' -> {
          val newDir = rightToLeftSplitter[dir]!!

          queue.add(Pair(current + moves[newDir]!!, newDir))
        }
      }
    }

    return map.sumOf { line -> line.map { if (it.size == 0) 0 else 1 }.sum() }
  }

  fun part1(input: List<String>): Int {
    return calculateEnergy(input, Point(0, 0), Dir.RIGHT)
  }

  fun part2(input: List<String>): Int {
    var maximum = 0

    // Left side
    for (i in input.indices) {
      maximum = max(maximum, calculateEnergy(input, Point(i, 0), Dir.RIGHT))
    }
    // Right side
    for (i in input.indices) {
      maximum = max(maximum, calculateEnergy(input, Point(i, input[i].length - 1), Dir.LEFT))
    }
    // Top side
    for (i in input[0].indices) {
      maximum = max(maximum, calculateEnergy(input, Point(0, i), Dir.BOTTOM))
    }
    // Bottom side
    for (i in input[0].indices) {
      maximum = max(maximum, calculateEnergy(input, Point(input.size - 1, i), Dir.TOP))
    }

    return maximum
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2023/2023_16_test")
  check(part1(testInput) == 46)
  check(part2(testInput) == 51)

  val input = readInput("2023/2023_16")
  part1(input).println()
  part2(input).println()
}
