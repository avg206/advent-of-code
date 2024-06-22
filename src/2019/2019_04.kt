fun main() {
  fun part1(input: String): Int {
    val (start, end) = input.split("-").map { it.toInt() }

    return (start..end).count { password ->
      val digits = password.toString().map { it.toString().toInt() }

      val isIncreasing = digits.zipWithNext().all { it.first <= it.second }
      val hasDouble = digits.groupBy { it }.any { it.value.size >= 2 }

      hasDouble && isIncreasing
    }
  }

  fun part2(input: String): Int {
    val (start, end) = input.split("-").map { it.toInt() }

    return (start..end).count { password ->
      val digits = password.toString().map { it.toString().toInt() }

      val isIncreasing = digits.zipWithNext().all { it.first <= it.second }
      val hasPureDouble = digits.groupBy { it }.any { it.value.size == 2 }

      hasPureDouble && isIncreasing
    }
  }

  assert(part1("138241-674034") == 1890)
  assert(part2("138241-674034") == 1277)

  part1("138241-674034").println()
  part2("138241-674034").println()
}

