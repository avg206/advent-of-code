fun main() {
  fun findPath(graph: Map<String, List<String>>, from: String, to: String): List<String> {
    val paths = graph.keys.associateWith { listOf<String>() }.toMutableMap()
    val queue = mutableListOf(from)

    while (queue.isNotEmpty() && paths[to]!!.isEmpty()) {
      val current = queue.removeFirst()

      for (step in graph[current]!!) {
        if (paths[step]!!.isEmpty()) {
          paths[step] = buildList { addAll(paths[current]!!); add(current) }
          queue.add(step)
        }
      }
    }

    return paths[to]!!
  }

  fun graphComponents(graph: Map<String, List<String>>): List<List<String>> {
    val remainingNodes = graph.keys.toMutableSet()
    val components = mutableListOf<List<String>>()

    while (remainingNodes.isNotEmpty()) {
      val from = remainingNodes.first()
      remainingNodes.remove(from)

      val component = mutableListOf(from)
      val queue = mutableListOf(from)

      while (queue.isNotEmpty()) {
        val current = queue.removeFirst()

        for (step in graph[current]!!) {
          if (step in remainingNodes) {
            queue.add(step)
            component.add(step)

            remainingNodes.remove(step)
          }
        }
      }

      components.add(component)
    }

    return components
  }

  fun generatePairs(toBeRemoved: List<String>): Sequence<List<Pair<String, String>>> = sequence {
    for (from1 in toBeRemoved) {
      for (to1 in toBeRemoved) {
        for (from2 in toBeRemoved) {
          for (to2 in toBeRemoved) {
            for (from3 in toBeRemoved) {
              for (to3 in toBeRemoved) {
                if (setOf(from1, to1, from2, to2, from3, to3).size != 6) continue

                yield(
                  listOf(Pair(from1, to1), Pair(from2, to2), Pair(from3, to3))
                )
              }
            }
          }
        }
      }
    }
  }

  fun solve(input: List<String>): Int {
    val nodesSet = mutableSetOf<String>()

    for (line in input) {
      val (from, tos) = line.split(": ")

      nodesSet.add(from)

      for (to in tos.split(" ")) {
        nodesSet.add(to)
      }
    }

    val graph = nodesSet.associateWith { mutableListOf<String>() }.toMutableMap()

    for (line in input) {
      val (from, tos) = line.split(": ")

      for (to in tos.split(" ")) {
        graph[from]!!.add(to)
        graph[to]!!.add(from)
      }
    }

    val randomNodes = nodesSet.toList().shuffled().take(100)
    val popularNodes = nodesSet.associateWith { 0 }.toMutableMap()

    for (i in randomNodes.indices) {
      for (j in i + 1..<randomNodes.size) {
        val path = findPath(graph, randomNodes[i], randomNodes[j])

        for (node in path) {
          popularNodes[node] = popularNodes[node]!! + 1
        }
      }
    }

    val toBeRemoved = popularNodes.toList().sortedByDescending { it.second }.take(6).map { it.first }

    for (iteration in generatePairs(toBeRemoved)) {
      val newGraph = HashMap(graph)

      for ((from, to) in iteration) {
        newGraph[from] = newGraph[from]!!.filter { it != to }.toMutableList()
        newGraph[to] = newGraph[to]!!.filter { it != from }.toMutableList()
      }

      val components = graphComponents(newGraph)

      if (components.size == 2) {
        val answer = components.map { it.size }.fold(1) { acc, i -> acc * i }
        answer.println()

        return answer
      }
    }

    throw Exception("No solution is found, re-run")
  }

  val input = readInput("2023/2023_25")
  check(solve(input) == 555_856)
}
