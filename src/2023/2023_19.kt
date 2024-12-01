import kotlin.math.max
import kotlin.math.min

fun main() {
  data class Rule(val side: Char, val sign: Char, val value: Int, val where: String)

  fun intersection(a: IntRange, b: IntRange): IntRange {
    return max(a.first, b.first)..min(a.last, b.last)
  }

  fun readRules(input: List<String>): Map<String, List<Rule>> {
    val rules = mutableMapOf<String, List<Rule>>()

    input.forEach { line ->
      val (name, rest) = line.substring(0, line.length - 1).split("{")

      rules[name] = rest.split(",").map { rule ->
        if (!rule.contains(":")) {
          return@map Rule('-', '-', 0, rule)
        }

        val colonIndex = rule.indexOfFirst { it == ':' }

        val value = rule.substring(2, colonIndex).toInt()
        val where = rule.substring(colonIndex + 1)

        return@map Rule(rule[0], rule[1], value, where)
      }
    }

    return rules
  }

  fun part1(input: List<String>): Int {
    val rules = readRules(input.subList(0, input.indexOfFirst { it == "" }))

    fun travel(part: Map<Char, Int>): Int {
      var current = "in"

      while (current != "A" && current != "R") {
        val rule = rules[current]!!

        for (i in rule.indices) {
          val step = rule[i]

          when (step.sign) {
            '-' -> {
              current = step.where
              break
            }

            '>' -> {
              if (part[step.side]!! > step.value) {
                current = step.where
                break
              }
            }

            '<' -> {
              if (part[step.side]!! < step.value) {
                current = step.where
                break
              }
            }

            else -> throw Exception("Unknown sign")
          }
        }
      }

      if (current == "A") {
        return part.values.sum()
      }

      return 0
    }


    return input.subList(input.indexOfFirst { it == "" } + 1, input.size).sumOf { line ->
      val part: Map<Char, Int> = line
        .substring(1, line.length - 1)
        .split(",")
        .fold(mutableMapOf()) { acc, s ->
          val (name, value) = s.split("=")

          acc[name[0]] = value.toInt()
          acc
        }

      travel(part)
    }
  }

  fun part2(input: List<String>): Long {
    val rules = readRules(input.subList(0, input.indexOfFirst { it == "" }))


    fun travel(part: Map<Char, IntRange>, current: String): Long {
      if (current == "A") {
        return part
          .values
          .map { it.last - it.first + 1 }
          .fold(1L) { acc, i -> acc * i.toLong() }
      }

      if (current == "R") {
        return 0
      }

      var result = 0L
      val rule = rules[current]!!
      val currentPart = part.toMutableMap()

      for (i in rule.indices) {
        val step = rule[i]

        when (step.sign) {
          '-' -> {
            result += travel(currentPart, step.where)
          }

          '>' -> {
            val newRange = intersection(currentPart[step.side]!!, step.value + 1..4000)

            if (!newRange.isEmpty()) {
              val tmp = currentPart.toMutableMap()
              tmp[step.side] = newRange

              result += travel(tmp, step.where)
            }

            val opposite = intersection(currentPart[step.side]!!, 1..step.value)
            currentPart[step.side] = opposite
          }

          '<' -> {
            val newRange = intersection(currentPart[step.side]!!, 1 until step.value)

            if (!newRange.isEmpty()) {
              val tmp = currentPart.toMutableMap()
              tmp[step.side] = newRange

              result += travel(tmp, step.where)
            }

            val opposite = intersection(currentPart[step.side]!!, step.value..4000)
            currentPart[step.side] = opposite
          }

          else -> throw Exception("Unknown sign")
        }
      }

      return result
    }

    return travel(
      mapOf('x' to 1..4000, 'm' to 1..4000, 'a' to 1..4000, 's' to 1..4000),
      "in",
    )
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2023/2023_19_test")
  check(part1(testInput) == 19_114)
  check(part2(testInput) == 167_409_079_868_000L)

  val input = readInput("2023/2023_19")
  check(part1(input) == 425_811)
  check(part2(input) == 131_796_824_371_749L)
  part1(input).println()
  part2(input).println()
}
