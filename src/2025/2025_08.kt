import helpers.point.Point3D

fun main() {
  fun solver(input: List<String>, connections: Int): Pair<Int, Long> {
    val world = input.map { row -> row.split(",").map { it.toInt() } }

    // Calculate all pairs
    val pairs = mutableListOf<Pair<Long, Pair<Int, Int>>>()

    for (i in world.indices) {
      for (j in i + 1..world.lastIndex) {
        val a = Point3D(world[i][0], world[i][1], world[i][2])
        val b = Point3D(world[j][0], world[j][1], world[j][2])

        val distance = a.distanceLong(b)

        pairs += distance to (i to j)
      }
    }

    pairs.sortBy { it.first }

    // Paint boxes according to the pairs
    val circuits = MutableList(world.size) { it + 1 }
    var situationAtLimit = 0

    for (i in pairs.indices) {
      val (_, pair) = pairs[i]
      val (a, b) = pair

      if (circuits[a] != circuits[b]) {
        val aI = circuits[a]
        val bI = circuits[b]

        for (j in circuits.indices) {
          if (circuits[j] == bI) {
            circuits[j] = aI
          }
        }

        if (circuits.distinct().size == 1) {
          return situationAtLimit to (world[a][0].toLong() * world[b][0].toLong())
        }
      }

      if (i + 1 == connections) {
        situationAtLimit =
          circuits.groupBy { it }.map { it.value.size }.sortedDescending().take(3).reduce { acc, i -> acc * i }
      }
    }

    throw IllegalStateException()
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2025/2025_08_test")
  check(solver(testInput, 10) == 40 to 25_272L)

  println("=======================")

  val input = readInput("2025/2025_08")
  check(solver(input, 1_000) == 140008 to 9253260633L)
  solver(input, 1_000).println()
  // Part 1:
  // - 1452
  // - 695520
  // - 27300
  // - 329220
  // + 140008
  // Part 2:
  // - 663326041
  // + 9253260633
}
