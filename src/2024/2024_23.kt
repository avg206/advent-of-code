import kotlin.math.pow

fun main() {
  fun part1(input: List<String>): Int {
    val edges = mutableMapOf<String, Set<String>>()

    input.map { it.split("-") }.forEach { (from, to) ->
      edges[from] = (edges[from] ?: emptySet()) + to
      edges[to] = (edges[to] ?: emptySet()) + from
    }

    val triples = mutableSetOf<Set<String>>()

    for ((main, connections) in edges) {
      for (second in connections) {
        for (third in connections) {
          if (second == third) continue

          if (third in edges[second]!!) {
            triples.add(setOf(main, second, third))
          }
        }
      }
    }

    return triples.count { set -> set.any { it.startsWith("t") } }
  }

  fun part2(input: List<String>): String {
    val edges = mutableMapOf<String, List<String>>()

    input.map { it.split("-") }.forEach { (from, to) ->
      if (from < to)
        edges[from] = (edges[from] ?: emptyList()) + to
      else
        edges[to] = (edges[to] ?: emptyList()) + from
    }

    fun isPartyValid(partySet: Set<String>): Boolean {
      val party = partySet.toList().sorted()

      for (i in party.indices) {
        for (j in (i + 1) until party.size) {
          if (edges[party[i]] == null || party[j] !in edges[party[i]]!!) return false
        }
      }

      return true
    }

    var answer = setOf<String>()

    for ((firstBuddy, connections) in edges) {
      val maxNumber = 2.0.pow(connections.size).toInt()

      for (pattern in 1..maxNumber) {
        val party = mutableSetOf(firstBuddy)

        for (j in connections.indices) {
          if (pattern and (1 shl j) != 0) party += connections[j]
        }

        if (isPartyValid(party) && party.size > answer.size) {
          answer = party
        }
      }
    }

    return answer.sorted().joinToString(",")
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2024/2024_23_test")
  check(part1(testInput) == 7)
  check(part2(testInput) == "co,de,ka,ta")

  val input = readInput("2024/2024_23")
  check(part1(input) == 1_240)
  check(part2(input) == "am,aq,by,ge,gf,ie,mr,mt,rw,sn,te,yi,zb")
  part1(input).println()
  part2(input).println()
}
