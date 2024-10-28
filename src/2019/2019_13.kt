import intCode.intCodeRunner

fun main() {
  fun part1(program: String): Int {
    val input = generateSequence { 0L }
    val output = intCodeRunner(program, input.iterator()).toList().map { it.toInt() }

    val tiles = output.windowed(3, 3).associate { (x, y, t) ->
      Pair(x, y) to t
    }

    return tiles.count { it.value == 2 }
  }


  fun part2(program: String): Int {
    val screen = MutableList(22) { MutableList(38) { 0 } }
    var score = 0

    fun printId(id: Int) = when (id) {
      1 -> "#"
      2 -> "X"
      3 -> "_"
      4 -> "O"
      else -> " "
    }

    fun findBlock(screen: List<List<Int>>, id: Int): Pair<Int, Int> {
      for (i in screen.indices) {
        for (j in screen[i].indices) {
          if (screen[i][j] == id) return Pair(i, j)
        }
      }

      return Pair(-1, -1)
    }

    val input = generateSequence {
      val (_, ballX) = findBlock(screen, 4)
      val (_, barX) = findBlock(screen, 3)

      if (ballX == barX) {
        return@generateSequence 0L
      }

      if (ballX < barX) {
        return@generateSequence -1L
      }

      return@generateSequence 1L
    }
    val output = intCodeRunner(
      program.replaceFirst("1", "2"),
      input.iterator()
    ).iterator()

    for (i in 0..835) {
      val x = output.next().toInt()
      val y = output.next().toInt()
      val id = output.next().toInt()

      screen[y][x] = id
    }

    while (output.hasNext()) {
      val x = output.next().toInt()
      val y = output.next().toInt()
      val id = output.next().toInt()

      if (x == -1 && y == 0) {
        score = id
      } else {
        screen[y][x] = id
      }

//      for (i in 0..2) println()
//      screen.joinToString("\r\n") {
//        it.joinToString("") { id -> printId(id) }
//      }.println()
//      println(score)
//      for (i in 0..2) println()
    }

    return score
  }

  val input = readInput("2019/2019_13")
  part1(input.first()).println()
  part2(input.first()).println()
}
