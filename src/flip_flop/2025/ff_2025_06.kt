import helpers.point.Point

fun main() {

  fun process(birds: List<Point>, speeds: List<Pair<Int, Int>>, sky: Int): List<Point> {
    return birds.mapIndexed { index, bird ->
      val (x, y) = speeds[index]

      var newX = bird.x + x
      var newY = bird.y + y

      if (newX >= sky) newX -= sky
      if (newX < 0) newX += sky

      if (newY >= sky) newY -= sky
      if (newY < 0) newY += sky

      Point(newX, newY)
    }
  }

  fun solver(input: List<String>, sky: Int, frame: IntRange, timePoints: List<Long>): Int {
    val speeds = input
      .map { line -> line.split(",").map { it.toInt() } }
      .map { Pair(it[0], it[1]) }

    var birds = List(speeds.size) { Point(0, 0) }

    val snapshots = mutableMapOf(0 to birds)
    var cycleCounter = 0

    while (true) {
      cycleCounter += 1
      val newBirds = process(birds, speeds, sky)
      snapshots[cycleCounter] = newBirds

      if (newBirds.all { it == Point(0, 0) }) break

      birds = newBirds
      snapshots[cycleCounter] = birds
    }

    return timePoints
      .map { (it % cycleCounter.toLong()).toInt() }
      .sumOf { index ->
        snapshots.getValue(index).count { it.x in frame && it.y in frame }
      }
  }

  val part2TimePoints = List(1_000) { index -> (index + 1) * 3_600L }
  val part3TimePoints = List(1_000) { index -> (index + 1) * 31_556_926L }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("flip_flop/2025/ff_2025_06_test")
  check(solver(testInput, 8, 3..4, listOf(100)) == 4)
  check(solver(testInput, 8, 3..4, part2TimePoints) == 0)
  check(solver(testInput, 8, 3..4, part3TimePoints) == 1000)

  val input = readInput("flip_flop/2025/ff_2025_06")
  check(solver(input, 1000, 250..749, listOf(100)) == 252)
  check(solver(input, 1000, 250..749, part2TimePoints) == 135_600)
  check(solver(input, 1000, 250..749, part3TimePoints) == 247_526)
  solver(input, 1000, 250..749, listOf(100)).println()
  solver(input, 1000, 250..749, part2TimePoints).println()
  solver(input, 1000, 250..749, part3TimePoints).println()
}
