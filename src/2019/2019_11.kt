import helpers.point.Direction
import helpers.point.Point
import intCode.intCodeRunner


fun main() {
  fun newDirection(current: Direction, change: Int): Direction {
    return when (change) {
      0 -> current.toLeft()
      1 -> current.toRight()

      else -> throw Exception()
    }
  }

  fun solution(programCode: String): Int {
    val cells = mutableMapOf(Point(0, 0) to 1)

    var current = Point(0, 0)
    var direction = Direction.Up

    val input = generateSequence { cells.getOrDefault(current, 0).toLong() }
    val program = intCodeRunner(programCode, input.iterator()).iterator()

    while (program.hasNext()) {
      val newColor = program.next().toInt()
      val directionChange = program.next().toInt()

      cells[current] = newColor
      direction = newDirection(direction, directionChange)

      current += direction.moveSpace()
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
