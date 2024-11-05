import helpers.point.Direction
import helpers.point.Point
import helpers.point.get
import intCode.intCodeRunner

fun main() {
  fun readWorld(program: String): List<List<Char>> {
    val input = generateSequence { 0L }
    val output = intCodeRunner(program, input.iterator()).iterator()

    var world = mutableListOf<List<Char>>()
    var row = mutableListOf<Char>()

    while (output.hasNext()) {
      when (val char = output.next().toInt()) {
        10 -> {
          world.add(row)
          row = mutableListOf()
        }

        else -> row.add(char.toChar())
      }
    }

    world = world.dropLast(1).toMutableList()

    return world
  }

  fun visualiseWorld(world: List<List<Char>>) {
    world.joinToString("\r\n") { it.joinToString(" ") }.println()
  }

  fun part1(program: String): Int {
    val world = readWorld(program)

    var alignment = 0

    for (i in world.indices) {
      for (j in world[i].indices) {
        if (world[i][j] != '#') continue

        val current = Point(i, j)
        val neighbours = current
          .neighbours()
          .filter { it.x in world.indices && it.y in world[i].indices }
          .count { world[it.x][it.y] == '#' }

        if (neighbours == 4) {
          alignment += i * j
        }
      }
    }

    return alignment
  }

  fun findRobot(world: List<List<Char>>): Point {
    for (i in world.indices) {
      for (j in world[i].indices) {
        if (world[i][j] != '.' && world[i][j] != '#') return Point(i, j)
      }
    }

    throw Exception("Robot not found")
  }

  fun part2(program: String): Int {
    val world = readWorld(program)

    fun queryWorld(at: Point): Char? {
      if (at.x !in world.indices || at.y !in world.first().indices) return null

      return world[at]
    }

//    visualiseWorld(world)

    var robot = findRobot(world)
    var direction = when (world[robot]) {
      '^' -> Direction.Up
      'v' -> Direction.Down
      '<' -> Direction.Left
      '>' -> Direction.Right

      else -> throw Exception("Unknown direction")
    }

    var commands = mutableListOf<String>()
    var path = 0

    while (true) {
      // Can move ?
      if (queryWorld(robot + direction.moveList()) == '#') {
        path += 1
        robot += direction.moveList()
        continue
      }

      // Cannot move
      commands.add(path.toString())
      path = 0

      // Can turn left ?
      if (queryWorld(robot + direction.left()) == '#') {
        commands.add("L")
        direction = direction.toLeft()
        continue
      }

      // Can turn right ?
      if (queryWorld(robot + direction.right()) == '#') {
        commands.add("R")
        direction = direction.toRight()
        continue
      }

      // It is over
      break
    }
    commands = commands.drop(1).toMutableList()

    println("Commands list:")
    commands.joinToString(",").println()

    // A,B,A,B,C,C,B,A,B,C

    // A
    // L,12,L,10,R,8,L,12
    // B
    // R,8,R,10,R,12
    // C
    // L,10,R,12,R,8

    val input = sequence {
      "A,B,A,B,C,C,B,A,B,C".forEach { yield(it.code.toLong()) }
      yield(10L)
      "L,12,L,10,R,8,L,12".forEach { yield(it.code.toLong()) }
      yield(10L)
      "R,8,R,10,R,12".forEach { yield(it.code.toLong()) }
      yield(10L)
      "L,10,R,12,R,8".forEach { yield(it.code.toLong()) }
      yield(10L)
      yield('y'.code.toLong())
      yield(10L)
    }
    val output = intCodeRunner(
      "2" + program.slice(1..<program.length),
      input.iterator()
    )

    return output.toList().last().toInt()
  }


  val input = readInput("2019/2019_17")
  part1(input.first()).println()
  println("---")
  part2(input.first()).println()
}
