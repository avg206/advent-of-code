fun main() {
  val memory = mutableMapOf(Triple(0, 0, 0) to 1)
  fun solver(w: Int, h: Int, z: Int): Int {
    if (w < 0 || h < 0 || z < 0) return 0

    val key = Triple(w, h, z)

    if (key !in memory) {
      memory[key] = solver(w - 1, h, z) + solver(w, h - 1, z) + solver(w, h, z - 1)
    }

    return memory.getValue(key)
  }

  fun part1(input: List<String>): Int {
    return input
      .map { line ->
        line.split(" ").map { it.toInt() }
      }
      .sumOf { solver(it[0] - 1, it[1] - 1, 0) }
  }

  fun part2(input: List<String>): Int {
    return input
      .map { line ->
        line.split(" ").map { it.toInt() }
      }
      .sumOf { solver(it[0] - 1, it[1] - 1, it[0] - 1) }
  }

  val complexMemory = mutableMapOf<String, Long>()
  fun complexSolver(state: List<Int>): Long {
    if (state.all { it == 0 }) return 1L
    if (state.any { it < 0 }) return 0L

    val key = state.joinToString(",")

    if (key !in complexMemory) {
      complexMemory[key] =
        List(state.size) { position -> complexSolver(state.mapIndexed { index, i -> if (index == position) i - 1 else i }) }.sum()
    }

    return complexMemory.getValue(key)
  }

  fun part3(input: List<String>): Long {
    return input
      .map { line ->
        line.split(" ").map { it.toInt() }
      }
      .sumOf { config -> complexSolver(List(config[0]) { config[1] - 1 }) }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("flip_flop/2025/ff_2025_07_test")
  check(part1(testInput) == 11)
  check(part2(testInput) == 108)
  check(part3(listOf("2 2")) == 2L)
  check(part3(listOf("3 3")) == 90L)
  check(part3(listOf("2 3")) == 6L)

  val input = readInput("flip_flop/2025/ff_2025_07")
  check(part1(input) == 213)
  check(part2(input) == 13_178_198)
  check(part3(input) == 2_376_198_187_741_224L)
  part1(input).println()
  part2(input).println()
  part3(input).println()
}
