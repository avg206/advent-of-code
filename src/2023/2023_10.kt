fun main() {
  data class Dot(var x: Int, var y: Int) {
    override fun toString(): String {
      return "$x - $y"
    }

    operator fun plus(dot: Dot): Dot {
      return Dot(x + dot.x, y + dot.y)
    }
  }

  val moves = buildList {
    add(Dot(-1, 0))
    add(Dot(0, 1))
    add(Dot(0, -1))
    add(Dot(1, 0))
  }

  fun calculateLoop(map: List<List<Char>>): List<List<Int>> {
    var start = Dot(0, 0)

    for (i in map.indices) {
      for (j in map[i].indices) {
        if (map[i][j] == 'S') {
          start = Dot(i, j)
        }
      }
    }

    fun movingDirection(dot: Dot) = when (map[dot.x][dot.y]) {
      '.' -> null
      '-' -> Pair(dot + Dot(0, -1), dot + Dot(0, 1))
      '|' -> Pair(dot + Dot(-1, 0), dot + Dot(1, 0))
      '7' -> Pair(dot + Dot(0, -1), dot + Dot(1, 0))
      'J' -> Pair(dot + Dot(-1, 0), dot + Dot(0, -1))
      'L' -> Pair(dot + Dot(-1, 0), dot + Dot(0, 1))
      'F' -> Pair(dot + Dot(1, 0), dot + Dot(0, 1))
      'S' -> null

      else -> throw Exception("")
    }


    val dist = List(map.size) { List(map[0].size) { 0 }.toMutableList() }.toMutableList()
    dist[start.x][start.y] = 1

    val queue = mutableListOf<Dot>()

    moves.forEach { move ->
      val target = start + move

      if (target.x !in map.indices || target.y !in map[0].indices) {
        return@forEach
      }

      val dir = movingDirection(target) ?: return@forEach

      if (dir.first == start || dir.second == start) {
        queue.add(target)
        dist[target.x][target.y] = 2
      }
    }

    while (queue.size > 0) {
      val point = queue.removeFirst()

      val dir = movingDirection(point) ?: continue

      dir.toList().forEach {
        if (dist[it.x][it.y] != 0) {
          return@forEach
        }

        dist[it.x][it.y] = dist[point.x][point.y] + 1
        queue.add(it)
      }
    }

    return dist
  }

  fun prettyPrint(map: List<List<Char>>) {
    fun mapper(x: Char) = when (x) {
      'F' -> '┌'
      'L' -> '└'
      'J' -> '┘'
      '7' -> '┐'
      '-' -> '─'
      '|' -> '│'
      'S' -> ' '
      else -> x
    }

    println(map.joinToString("\r\n") { line -> line.map { mapper(it) }.joinToString(" ") })
  }

  fun part1(input: List<String>): Int {
    val map = input.map { it.toList() }
    val distances = calculateLoop(map)

    return distances.maxOf { line -> line.maxOf { it } } - 1
  }

  fun part2(input: List<String>): Int {


    var map = input.map { line -> line.toList() }
    val distances = calculateLoop(map)

    map = map.mapIndexed { x, chars ->
      chars.mapIndexed { y, c ->
        when (distances[x][y]) {
          0 -> '.'
          else -> c
        }
      }.toMutableList()
    }.toMutableList()

    // Replace "S" with corresponding symbol
    map[128][88] = '|'

    var result = 0

    for (i in map.indices) {
      var walls = 0
      var lastCorner = ' '

      for (j in map[0].indices) {
        if (distances[i][j] == 0) {
          if (walls % 2 == 1) {
            result++
            map[i][j] = '+'
          }
          continue
        }

        when (val char = map[i][j]) {
          '-' -> continue
          '|' -> {
            walls += 1
            continue
          }

          else -> {
            lastCorner = if (lastCorner != ' ') {
              if (
                (lastCorner == 'L' && char == '7') ||
                (lastCorner == 'F' && char == 'J')
              ) {
                walls++
              }
              ' '
            } else {
              char
            }
          }
        }
      }
    }

//        prettyPrint(map)

    return result
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2023/2023_10_test")
  check(part1(testInput) == 8)

  val input = readInput("2023/2023_10")
  check(part1(input) == 6_640)
  check(part2(input) == 411)
  part1(input).println()
  part2(input).println()
}
