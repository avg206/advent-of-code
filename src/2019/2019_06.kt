fun main() {
  fun checksum(graph: Map<String, List<String>>, current: String, depth: Int): Int {
    if (current !in graph) {
      return depth
    }

    return graph[current]!!.sumOf { checksum(graph, it, depth + 1) } + depth
  }

  fun part1(input: List<String>): Int {
    val graph = mutableMapOf<String, MutableList<String>>()

    input.map { it.split(")") }.forEach { (from, to) ->
      if (from !in graph) {
        graph[from] = mutableListOf()
      }

      graph[from]!!.add(to)
    }

    return checksum(graph, "COM", 0)
  }

  fun findDistance(graph: Map<String, List<String>>, from: String, to: String): Int {
    val distances = buildMap { graph.keys.forEach { put(it, -1) } }.toMutableMap()
    distances[from] = 0

    val queue = mutableListOf(from)

    while (queue.isNotEmpty()) {
      val current = queue.removeFirst()

      for (next in graph[current]!!) {
        if (distances[next] != -1) continue

        distances[next] = distances[current]!! + 1
        queue.add(next)
      }
    }

    return distances[to]!!
  }

  fun part2(input: List<String>): Int {
    val graph = mutableMapOf<String, MutableList<String>>()

    input.map { it.split(")") }.forEach { (from, to) ->
      if (from !in graph) graph[from] = mutableListOf()
      if (to !in graph) graph[to] = mutableListOf()

      graph[from]!!.add(to)
      graph[to]!!.add(from)
    }

    return findDistance(graph, "YOU", "SAN") - 2
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2019/2019_06_test")
  check(part1(testInput) == 54)
  check(part2(testInput) == 4)

  val input = readInput("2019/2019_06")

  check(part1(input) == 145_250)
  check(part2(input) == 274)

  part1(input).println()
  part2(input).println()
}
