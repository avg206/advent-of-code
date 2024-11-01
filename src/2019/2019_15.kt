import helpers.point.Point
import intCode.intCodeRunner
import kotlin.math.abs
import kotlin.math.min

typealias World = MutableMap<Point, Int>

enum class Commands(val code: Int, val move: Point) {
  North(1, Point(0, -1)),
  East(4, Point(1, 0)),
  South(2, Point(0, 1)),
  West(3, Point(-1, 0));

  fun opposite() = when (this) {
    North -> South
    East -> West
    South -> North
    West -> East
  }
}

fun main() {
  fun visualise(world: World) {
    if (world.isEmpty()) return

    val xRange = world.keys.minOf { it.x }..world.keys.maxOf { it.x }
    val yRange = world.keys.minOf { it.y }..world.keys.maxOf { it.y }

    println("Current world:")
    for (y in yRange) {
      for (x in xRange) {
        print(
          when (world[Point(x, y)]) {
            0 -> ". "
            1 -> "# "
            2 -> "O "
            else -> "  "
          }
        )
      }
      println()
    }
    println("---")
  }

  fun solve(program: String): Pair<Int, Int> {
    val world: World = mutableMapOf()
    val reach: World = mutableMapOf()
    val commands = mutableListOf<Int>()

    val input = generateSequence {
      if (commands.isNotEmpty()) {
        val nextCommand = commands.removeFirst()
        return@generateSequence nextCommand.toLong()
      }

      throw Exception("No commands available")
    }
    val output = intCodeRunner(program, input.iterator()).iterator()

    fun dfs(now: Point, step: Int) {
      val next = mutableListOf<Commands>()

      listOf(Commands.North, Commands.East, Commands.South, Commands.West).forEach { command ->
        commands.add(command.code)

        when (val view = output.next().toInt()) {
          0 -> world[now + command.move] = 1

          1, 2 -> {
            if ((now + command.move) !in world) {
              next.add(command)
            }

            reach[now + command.move] = step + 1
            world[now + command.move] = if (view == 1) 0 else 2

            commands.add(command.opposite().code)
            output.next()
          }
        }
      }

      next.forEach { nextCommand ->
        commands.add(nextCommand.code)
        output.next()

        dfs(now + nextCommand.move, step + 1)

        commands.add(nextCommand.opposite().code)
        output.next()
      }
    }

    world[Point(0, 0)] = 0
    dfs(Point(0, 0), 0)

    val oxygen = world.entries.find { it.value == 2 }!!.key
    val stepsToOxygen = reach.getValue(oxygen)


    fun bfs(from: Point): Int {
      val queue = mutableListOf(from)
      var minimal = 0

      while (queue.isNotEmpty()) {
        val now = queue.removeFirst()


        now.neighbours()
          .filter { world[it] == 0 }
          .forEach { neighbour ->
            world[neighbour] = world[now]!! - 1

            minimal = min(minimal, world[neighbour]!!)

            queue.add(neighbour)
          }
      }

      return abs(minimal)
    }

    world[oxygen] = 0
    val timeToFill = bfs(oxygen)

    println("Steps to oxygen - $stepsToOxygen")
    println("Time to fill - $timeToFill")

    return Pair(stepsToOxygen, timeToFill)
  }

  val input = readInput("2019/2019_15")
  check(solve(input.first()) == Pair(220, 334))
}
