import kotlin.math.max

fun main() {
  fun buildNumber(values: List<Pair<String, Int>>) =
    values
      .sortedByDescending { it.first }
      .map { it.second }
      .joinToString("")
      .toLong(2)

  fun process(initialValues: Map<String, Int>, gates: List<Pair<List<String>, String>>): Map<String, Int> {
    val values = initialValues.toMutableMap()
    val lookingFor = gates.map { it.second }.filter { it.startsWith("z") }.toMutableSet()

    var ops = 0

    while (lookingFor.isNotEmpty()) {
      ops += 1

      if (ops == 100) break

      val interactionGates = gates.filter { (input, _) ->
        val (a, _, b) = input
        a in values && b in values
      }


      interactionGates.forEach { (input, output) ->
        val (a, op, b) = input

        values[output] = when (op) {
          "AND" -> values[a]!! and values[b]!!
          "OR" -> values[a]!! or values[b]!!
          "XOR" -> values[a]!! xor values[b]!!

          else -> throw Exception("Unknown op - $op")
        }

        lookingFor.remove(output)
      }
    }

    return values
  }

  fun parseInput(input: List<String>): Pair<Map<String, Int>, List<Pair<List<String>, String>>> {
    val (initials, rules) = input.joinToString("\r\n").split("\r\n\r\n").map { it.lines() }

    val values = buildMap {
      initials.map { it.split(": ") }.forEach { (wire, value) ->
        put(wire, value.toInt())
      }
    }

    val gates = rules
      .map {
        it
          .split(" -> ")
          .let { (rule, output) -> rule.split(" ") to output }
      }

    return values to gates
  }

  fun part1(input: List<String>): Long {
    val (values, gates) = parseInput(input)

    return buildNumber(process(values, gates).filter { it.key.startsWith("z") }.toList())
  }

  fun part2(input: List<String>): String {
    val (values, gates) = parseInput(input).let { it.first to it.second.toMutableList() }

    val x = buildNumber(values.filter { it.key.startsWith("x") }.toList())
    val y = buildNumber(values.filter { it.key.startsWith("y") }.toList())
    val expectedOutput = x + y

    fun swapGates(a: String, b: String) {
      val aIndex = gates.indexOfFirst { it.second == a }
      val bIndex = gates.indexOfFirst { it.second == b }

      val tmp = gates[aIndex].second
      gates[aIndex] = gates[aIndex].first to gates[bIndex].second
      gates[bIndex] = gates[bIndex].first to tmp
    }

    fun dfs(current: String): Map<String, Int> {
      val (gate, _) = gates.find { it.second == current } ?: return mapOf()

      val left = dfs(gate[0])
      val right = dfs(gate[2])

      val result = left.toMutableMap()

      listOf("AND", "OR", "XOR").forEach { op -> result[op] = (result[op] ?: 0) + right.getOrDefault(op, 0) }
      result[gate[1]] = (result[gate[1]] ?: 0) + 1

      return result
    }

    fun correctCombination(digit: Int): Map<String, Int> {
      return mapOf(
        "AND" to max(digit * 2 - 1, 0),
        "OR" to max(digit - 1, 0),
        "XOR" to digit + 1
      )
    }

    val outputs = gates.map { it.second }.filter { it.startsWith("z") }.sorted()
    val swaps = mutableListOf<String>()


    for (output in outputs) {
      val digit = output.substring(1).toInt()
      val connectCombination = correctCombination(digit)

      if (dfs(output) == connectCombination) {
        continue
      }

      for ((_, with) in gates) {
        if (with == output) continue
        swapGates(output, with)

        if (dfs(output) == connectCombination) {
          swaps.addAll(listOf(output, with))
          break
        }

        swapGates(output, with)
      }
    }

    if (swaps.size != 6) {
      throw Exception("I cannot find the answer")
    }

//    swapGates("z06", "ksv")
//    swapGates("nbd", "kbs")
//    swapGates("z20", "tqq")
//    swapGates("z39", "ckb")

    for ((_, from) in gates) {
      for ((_, to) in gates) {
        if (from == to) continue
        swapGates(from, to)

        val output = buildNumber(process(values, gates).filter { it.key.startsWith("z") }.toList())

        if (output == expectedOutput) {
          return (swaps + listOf(from, to)).sorted().joinToString(",")
        }

        swapGates(from, to)
      }
    }

    throw Exception("I cannot find the answer")
  }

// test if implementation meets criteria from the description, like:
  check(part1(readInput("2024/2024_24_test")) == 4L)
  check(part1(readInput("2024/2024_24_test_2")) == 2024L)

  val input = readInput("2024/2024_24")
  check(part1(input) == 49_574_189_473_968L)
  check(part2(input) == "ckb,kbs,ksv,nbd,tqq,z06,z20,z39")
  part1(input).println()
  part2(input).println()
}
