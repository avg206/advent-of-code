import helpers.point.Point
import java.math.BigInteger

fun main() {
  fun eratosthenes(limit: Int): Set<Int> {
    val primes = (1..limit).toMutableSet()

    for (i in 2..limit) {
      var pointer = i * i

      while (pointer <= limit) {
        primes -= pointer
        pointer *= i
      }
    }

    return primes
  }

  // Constants
  val primes = eratosthenes(200)
  val capitalLetterDiff = 'A' - 'a'

  fun runGears(
    matrix: MutableList<MutableList<Char>>,
    teleports: Map<Char, Point>,
    start: Point,
    lights: List<Point>
  ): BigInteger {
    val visited = mutableSetOf<Point>()
    val queue = ArrayDeque(listOf(start to 'L'))

    while (queue.isNotEmpty()) {
      val (current, direction) = queue.removeFirst()
      val symbol = matrix[current.x][current.y]
      val nextDirection = (if (direction == 'L') 'R' else 'L')

      if (current in visited) continue
      visited += current
      matrix[current.x][current.y] = direction

      if (symbol in 'a'..'z') {
        if ((symbol + capitalLetterDiff) in teleports) {
          queue.add(teleports.getValue(symbol + capitalLetterDiff) to nextDirection)
        }
        continue
      }

      queue.addAll(
        current.neighbours()
          .filter { it.x in matrix.indices && it.y in matrix.first().indices && matrix[it.x][it.y] != '.' }
          .map { it to nextDirection }
      )
    }

    val result = lights
      .map { light ->
        val neighbours = light.neighbours()
          .filter { it.x in matrix.indices && it.y in matrix.first().indices && matrix[it.x][it.y] in listOf('L', 'R') }
          .map { matrix[it.x][it.y] }

        when {
          neighbours.size != 1 -> -1
          neighbours.first() == 'L' -> 0
          neighbours.first() == 'R' -> 1
          else -> throw IllegalArgumentException()
        }
      }
      .filter { it != -1 }

    return result.joinToString("").toBigInteger(2)
  }

  fun part1(input: List<String>): BigInteger {
    val matrix = MutableList(input.size) { MutableList<Char>(input.first().length) { '.' } }

    val lights = mutableListOf<Point>()
    var start = Point(0, 0)

    for (i in matrix.indices) {
      for (j in matrix[i].indices) {
        when (input[i][j]) {
          'S' -> start = Point(i, j)
          '*' -> lights += Point(i, j)
          '#' -> matrix[i][j] = '#'
        }
      }
    }

    return runGears(matrix, mapOf(), start, lights)
  }

  fun part2(input: List<String>): BigInteger {
    val matrix = MutableList(input.size) { MutableList<Char>(input.first().length) { '.' } }

    val lights = mutableListOf<Point>()
    val bluetooth = mutableMapOf<Char, Point>()
    var start = Point(0, 0)

    for (i in matrix.indices) {
      for (j in matrix[i].indices) {
        when (input[i][j]) {
          'S' -> start = Point(i, j)
          '*' -> lights += Point(i, j)
          '#', '3' -> matrix[i][j] = '#'
          in 'A'..'Z' -> bluetooth[input[i][j]] = Point(i, j)
          in 'a'..'z' -> matrix[i][j] = input[i][j]
        }
      }
    }

    return runGears(matrix, bluetooth, start, lights)
  }

  fun part3(input: List<String>): BigInteger {
    fun bfs(from: Point): Int {
      val queue = ArrayDeque(listOf(from))
      val visited = mutableSetOf<Point>()

      while (queue.isNotEmpty()) {
        val current = queue.removeFirst()

        if (current in visited) continue
        visited.add(current)

        queue.addAll(
          current.neighbours()
            .filter { it.x in input.indices && it.y in input.first().indices && input[it.x][it.y] == '3' })
      }

      return visited.size - 1
    }

    val matrix = MutableList(input.size) { MutableList<Char>(input.first().length) { '.' } }

    val lights = mutableListOf<Point>()
    val bluetooth = mutableMapOf<Char, Point>()
    var start = Point(0, 0)

    for (i in matrix.indices) {
      for (j in matrix[i].indices) {
        when (input[i][j]) {
          'S' -> start = Point(i, j)
          '*' -> lights += Point(i, j)
          '#', '3' -> matrix[i][j] = '#'
          in 'A'..'Z' -> {
            val size = bfs(Point(i, j))

            if (size !in primes) {
              bluetooth[input[i][j]] = Point(i, j)
            }
          }

          in 'a'..'z' -> matrix[i][j] = input[i][j]
        }
      }
    }

    return runGears(matrix, bluetooth, start, lights)
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("flip_flop/2026/ff_2026_06_test")
  check(part1(testInput) == BigInteger("4"))
  check(part2(testInput) == BigInteger("1195"))
  check(part3(testInput) == BigInteger("148"))

  val input = readInput("flip_flop/2026/ff_2026_06")
  check(part1(input) == BigInteger("272411993501"))
  check(part2(input) == BigInteger("18444154755041004529"))
  check(part3(input) == BigInteger("2187967407517"))
  part1(input).println()
  part2(input).println()
  part3(input).println()
}

