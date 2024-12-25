import helpers.point.Point

fun main() {
  fun nextLocation(location: Point, direction: Char): Point {
    return location + when (direction) {
      '^' -> Point(-1, 0)
      'v' -> Point(1, 0)
      '<' -> Point(0, -1)
      '>' -> Point(0, 1)

      else -> throw Exception("Unknow direction - $direction")
    }
  }

  fun part1(input: String): Int {
    var santa = Point(0, 0)
    val visits = mutableSetOf<Point>(santa)

    input.forEach { direction ->
      santa = nextLocation(santa, direction)

      visits.add(santa)
    }

    return visits.size
  }

  fun part2(input: String): Int {
    var santa = Point(0, 0)
    var robot = Point(0, 0)

    val visits = mutableSetOf<Point>(santa)

    input.toList().windowed(2, 2, true) { commands ->
      santa = nextLocation(santa, commands[0])

      if (commands.size == 2) {
        robot = nextLocation(robot, commands[1])
      }

      visits.addAll(listOf(santa, robot))
    }

    return visits.size
  }

  // test if implementation meets criteria from the description, like:
  check(part1(">") == 2)
  check(part1("^>v<") == 4)
  check(part1("^v^v^v^v^v") == 2)

  check(part2("^v") == 3)
  check(part2("^>v<") == 3)
  check(part2("^v^v^v^v^v") == 11)

  val input = readInput("2015/2015_03")
  part1(input.first()).println()
  part2(input.first()).println()
}
