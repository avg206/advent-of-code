import strikt.api.expectThat
import strikt.assertions.isEqualTo

fun main() {
  fun isNumeric(s: String) = Regex("[0-9]+").matches(s)

  fun operator(values: Map<String, UShort>, a: String, b: String, ops: (UShort, UShort) -> UShort) = when {
    a in values.keys && b in values.keys -> ops(values.getValue(a), values.getValue(b))
    a in values.keys && isNumeric(b) -> ops(values.getValue(a), b.toUShort())
    isNumeric(a) && b in values.keys -> ops(a.toUShort(), values.getValue(b))
    isNumeric(a) && isNumeric(b) -> ops(a.toUShort(), b.toUShort())

    else -> null
  }

  fun operator(values: Map<String, UShort>, a: String, ops: (UShort) -> UShort) = when {
    a in values.keys -> ops(values.getValue(a))
    isNumeric(a) -> ops(a.toUShort())

    else -> null
  }

  fun buildConnector(rule: String): (Map<String, UShort>) -> UShort? {
    return when {
      Regex("AND").containsMatchIn(rule) -> { values ->
        val (a, b) = rule.split(" AND ")
        operator(values, a, b) { x, y -> x and y }
      }

      Regex("OR").containsMatchIn(rule) -> { values ->
        val (a, b) = rule.split(" OR ")
        operator(values, a, b) { x, y -> x or y }
      }

      Regex("LSHIFT").containsMatchIn(rule) -> { values ->
        val (a, b) = rule.split(" LSHIFT ")
        operator(values, a, b) { x, y -> x.toInt().shl(y.toInt()).toUShort() }
      }

      Regex("RSHIFT").containsMatchIn(rule) -> { values ->
        val (a, b) = rule.split(" RSHIFT ")
        operator(values, a, b) { x, y -> x.toInt().shr(y.toInt()).toUShort() }
      }

      Regex("NOT").containsMatchIn(rule) -> { values ->
        operator(values, rule.substring(4)) { x -> x.inv() }
      }

      else -> { values ->
        operator(values, rule) { x -> x }
      }
    }
  }

  fun parseInput(input: List<String>): List<Pair<(Map<String, UShort>) -> UShort?, String>> {
    return input
      .map { it.split(" -> ") }
      .map { buildConnector(it[0]) to it[1] }
  }

  fun calculate(rules: List<Pair<(Map<String, UShort>) -> UShort?, String>>, target: String): UShort {
    val values = mutableMapOf<String, UShort>()

    while (target !in values.keys) {
      rules.forEach { (ops, output) ->
        if (output in values) {
          return@forEach
        }

        val result = ops(values)

        if (result != null) {
          values[output] = result
        }
      }
    }

    return values.getValue(target)
  }

  fun solve(input: List<String>, target: String): UShort {
    return calculate(parseInput(input), target)
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2015/2015_07_test")
  expectThat(solve(testInput, "h")).isEqualTo(65_412u)

  val input = readInput("2015/2015_07")
  expectThat(solve(input, "a")).isEqualTo(3176u)
  expectThat(solve(input.map {
    when (Regex("[0-9]+ -> b").matches(it)) {
      true -> "3176 -> b"
      else -> it
    }
  }, "a")).isEqualTo(14_710u)

  println("Finished")
}


