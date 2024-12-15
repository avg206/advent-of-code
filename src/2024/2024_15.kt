import helpers.gridReader
import helpers.point.Point

data class Box(val from: Point, val to: Point)

fun main() {
  fun visualise(map: List<List<Int>>, boxes: Set<Point>, robot: Point) {
    for (i in map.indices) {
      for (j in map[i].indices) {
        when {
          map[i][j] == 1 -> print("#")
          Point(i, j) in boxes -> print("O")
          Point(i, j) == robot -> print("@")
          else -> print(".")
        }
      }

      println("")
    }
  }

  fun visualise(map: List<List<Int>>, boxes: Set<Box>, robot: Point) {
    val froms = boxes.map { it.from }.toSet()
    val tos = boxes.map { it.to }.toSet()

    for (i in map.indices) {
      for (j in map[i].indices) {
        when {
          map[i][j] == 1 -> print("#")
          Point(i, j) in froms -> print("[")
          Point(i, j) in tos -> print("]")
          Point(i, j) == robot -> print("@")
          else -> print(".")
        }
      }

      println("")
    }
  }

  fun part1(input: List<String>): Int {
    val position = input.indexOfFirst { it == "" }

    val grid = input.subList(0, position)
    val moves = input.subList(position + 1, input.size).joinToString("")

    val map = MutableList(grid.size) { MutableList(grid.first().length) { 0 } }
    var robot = Point(0, 0)
    val boxes = mutableSetOf<Point>()

    gridReader(grid) { char: Char, row: Int, column: Int ->
      when (char) {
        '#' -> map[row][column] = 1
        'O' -> boxes.add(Point(row, column))
        '@' -> robot = Point(row, column)
      }
    }

    fun tryToMove(diff: Point) {
      val subBoxes = mutableSetOf<Point>()
      var newPos = robot + diff

      while (map[newPos.x][newPos.y] == 0 && newPos in boxes) {
        subBoxes.add(newPos)
        boxes.remove(newPos)

        newPos += diff
      }

      if (map[newPos.x][newPos.y] != 1) {
        robot += diff
        boxes.addAll(subBoxes.map { it + diff })
      } else {
        boxes.addAll(subBoxes)
      }
    }

//    visualise(map, boxes, robot)

    moves.forEach { move ->
      when (move) {
        '^' -> tryToMove(Point(-1, 0))
        'v' -> tryToMove(Point(1, 0))
        '<' -> tryToMove(Point(0, -1))
        '>' -> tryToMove(Point(0, 1))
      }

//      println("Move $move:")
//      visualise(map, boxes, robot)
    }

    return boxes.sumOf { it.x * 100 + it.y }
  }

  fun part2(input: List<String>): Int {
    val position = input.indexOfFirst { it == "" }

    val grid = input.subList(0, position).map { row ->
      row.flatMap {
        when (it) {
          '#' -> listOf('#', '#')
          'O' -> listOf('[', ']')
          '@' -> listOf('@', '.')
          else -> listOf('.', '.')
        }
      }.joinToString("")
    }
    val moves = input.subList(position + 1, input.size).joinToString("")

    val map = MutableList(grid.size) { MutableList(grid.first().length) { 0 } }
    var robot = Point(0, 0)
    val boxes = mutableSetOf<Box>()

    gridReader(grid) { char: Char, row: Int, column: Int ->
      when (char) {
        '#' -> map[row][column] = 1
        '[' -> boxes.add(Box(Point(row, column), Point(row, column + 1)))
        '@' -> robot = Point(row, column)
      }
    }

    fun tryToMove(diff: Point) {
      val newPos = robot + diff

      if (map[newPos.x][newPos.y] == 1) {
        return
      }

      fun dfs(curr: Point): Set<Box> {
        val box = boxes.find { it.from == curr + diff || it.to == curr + diff }

        if (box == null) return emptySet()
        boxes.remove(box)

        return setOf(box) + dfs(box.from) + dfs(box.to)
      }

      fun canMove(box: Box): Boolean {
        val from = box.from + diff
        val to = box.to + diff

        return map[from.x][from.y] == 0 && map[to.x][to.y] == 0
      }

      val handledBoxes = dfs(robot)

      if (handledBoxes.all { canMove(it) }) {
        robot += diff
        boxes.addAll(handledBoxes.map { Box(it.from + diff, it.to + diff) })
      } else {
        boxes.addAll(handledBoxes)
      }
    }

//    visualiseBig(map, boxes, robot)

    moves.forEach { move ->
      when (move) {
        '^' -> tryToMove(Point(-1, 0))
        'v' -> tryToMove(Point(1, 0))
        '<' -> tryToMove(Point(0, -1))
        '>' -> tryToMove(Point(0, 1))
      }

//      println("Move $move:")
//      visualiseBig(map, boxes, robot)
    }

    return boxes.map { it.from }.sumOf { it.x * 100 + it.y }
  }

  // test if implementation meets criteria from the description, like:
  check(part1(readInput("2024/2024_15_test")) == 2028)
  check(part1(readInput("2024/2024_15_test_2")) == 10_092)
  check(part2(readInput("2024/2024_15_test_3")) == 618)
  check(part2(readInput("2024/2024_15_test_2")) == 9_021)

  val input = readInput("2024/2024_15")
  check(part1(input) == 1_471_826)
  check(part2(input) == 1_457_703)
  part1(input).println()
  part2(input).println()
}
