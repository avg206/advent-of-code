import intCode.intCodeRunner

enum class RobotDirection {
  Up, Right, Left, Down
}

fun main() {
  fun newDirection(current: RobotDirection, change: Int): RobotDirection {
    return when (change) {
      0 -> {
        when (current) {
          RobotDirection.Up -> RobotDirection.Left
          RobotDirection.Left -> RobotDirection.Down
          RobotDirection.Down -> RobotDirection.Right
          RobotDirection.Right -> RobotDirection.Up
        }
      }

      1 -> {
        when (current) {
          RobotDirection.Up -> RobotDirection.Right
          RobotDirection.Right -> RobotDirection.Down
          RobotDirection.Down -> RobotDirection.Left
          RobotDirection.Left -> RobotDirection.Up
        }
      }

      else -> throw Exception()
    }
  }

  fun solution(programCode: String): Int {
    val cells = mutableMapOf(Point(0, 0) to 1)

    var current = Point(0, 0)
    var direction = RobotDirection.Up

    val input = generateSequence { cells.getOrDefault(current, 0).toLong() }
    val program = intCodeRunner(programCode, input.iterator()).iterator()

    while (program.hasNext()) {
      val newColor = program.next().toInt()
      val directionChange = program.next().toInt()

      cells[current] = newColor
      direction = newDirection(direction, directionChange)

      current += when (direction) {
        RobotDirection.Up -> Point(1, 0)
        RobotDirection.Right -> Point(0, 1)
        RobotDirection.Down -> Point(-1, 0)
        RobotDirection.Left -> Point(0, -1)
      }
    }

    for (x in 0 downTo -5) {
      for (y in 0..40) {
        val char = when (cells.getOrDefault(Point(x, y), 0)) {
          1 -> "#"
          else -> "."
        }
        print("$char ")
      }
      println("")
    }

    return cells.size
  }

  val input = readInput("2019/2019_11")
  solution(input.first()).println()
}
