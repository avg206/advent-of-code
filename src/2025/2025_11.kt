import java.util.Collections.addAll

fun main() {
  fun parseInput(input: List<String>): Map<String, MutableList<String>> {
    val graph = mutableMapOf<String, MutableList<String>>()

    input.forEach { line ->
      val (from, tos) = line.split(": ")

      tos.split(" ").forEach { target ->
        graph.computeIfAbsent(target) { mutableListOf() }.add(from)
      }
    }

    return graph
  }

  fun part1(input: List<String>): Int {
    val graph = parseInput(input)

    val memory = mutableMapOf<String, Int>()
    fun foo(node: String): Int {
      if (node == "you") return 1

      if (node !in memory) {
        memory[node] = graph[node]?.sumOf { foo(it) } ?: 0
      }

      return memory.getValue(node)
    }

    return foo("out")
  }

  fun part2(input: List<String>): Long {
    val graph = parseInput(input)

    val memory = mutableMapOf<String, Long>()
    fun foo(node: String, visitedA: Boolean, visitedB: Boolean): Long {
      if (node == "svr") return if (visitedA && visitedB) 1 else 0

      val key = "$node:$visitedA:$visitedB"

      val newVisitedA = if (visitedA) true else node == "fft"
      val newVisitedB = if (visitedB) true else node == "dac"

      if (key !in memory) {
        memory[key] = graph[node]?.sumOf { foo(it, newVisitedA, newVisitedB) } ?: 0L
      }

      return memory.getValue(key)
    }

    return foo("out", false, false)
  }

  // test if implementation meets criteria from the description, like:
  check(part1(readInput("2025/2025_11_test")) == 5)
  check(part2(readInput("2025/2025_11_test_2")) == 2L)

  println("=======================")

  val input = readInput("2025/2025_11")
  check(part1(input) == 719)
  check(part2(input) == 337_433_554_149_492L)
  part1(input).println()
  part2(input).println()
}
