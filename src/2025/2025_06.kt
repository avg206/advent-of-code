import kotlin.math.min

fun main() {
  fun calculateBreaks(input: List<String>): List<IntRange> {
    val breaks = mutableListOf<IntRange>()
    var from = 0

    for (col in input[0].indices) {
      if (input.indices.all { input[it][col] == ' ' }) {
        breaks += from..<col
        from = col + 1
      }
    }

    // End last group
    breaks += from..input.maxOf { it.length }

    return breaks
  }

  fun part1(input: List<String>): Long {
    val ranges = calculateBreaks(input)

    return ranges.map { range ->
      val list = mutableListOf<Long>()

      for (row in input.indices) {
        if (row == input.lastIndex) {
          return@map when (input[row][range.first]) {
            '+' -> list.sum()
            '*' -> list.reduce { acc, lng -> acc * lng }
            else -> throw IllegalStateException()
          }
        } else {
          val substringRange = range.first..min(range.last, input[row].lastIndex)
          list += input[row].substring(substringRange).filter { it != ' ' }.toLong()
        }
      }

      return@map 0L
    }.sum()
  }

  fun part2(input: List<String>): Long {
    val ranges = calculateBreaks(input)

    return ranges.map { range ->
      val list = mutableListOf<Long>()

      for (col in range) {
        var number = 0L

        for (row in input.indices) {
          if (col in input[row].indices && input[row][col].isDigit()) {
            number = (number * 10) + input[row][col].digitToInt()
          }
        }

        list += number
      }

      when (input.last()[range.first]) {
        '+' -> list.sum()
        '*' -> list.filter { it > 0 }.reduce { acc, lng -> acc * lng }
        else -> throw IllegalStateException()
      }
    }.sum()
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2025/2025_06_test")
  check(part1(testInput) == 4_277_556L)
  check(part2(testInput) == 3_263_827L)

  val input = readInput("2025/2025_06")
  check(part1(input) == 3_525_371_263_915L)
  check(part2(input) == 6_846_480_843_636L)
  part1(input).println()
  // + 3525371263915
  part2(input).println()
  // - 6846455999172
  // + 6846480843636

  fun part1Old(input: List<String>): Long {
    var result = 0L

    val grid = input.map { row -> row.split(" ") }
    var lastIndex = grid.lastIndex

    for (col in grid[0].indices) {
      val list = mutableListOf<Long>()

      for (row in grid.indices) {
        val value = grid[row][col]

        if (row != lastIndex) {
          list.add(value.toLong())
        } else {
          when (value) {
            "+" -> result += list.sum()
            "*" -> result += list.reduce { acc, lng -> acc * lng }
          }
        }
      }
    }

    return result
  }

  fun part2Old(input: List<String>): Long {
    var result = 0L

    val breaks = mutableListOf<Int>()

    for (col in input[0].indices) {
      var empty = true

      for (row in input.indices) {
        if (input[row][col] != ' ') {
          empty = false
          break
        }
      }

      if (empty) {
        breaks += col
      }
    }

    // End last group
    breaks += input.maxOf { it.length } + 1
    var from = 0

    for (last in breaks) {
      val list = mutableListOf<Long>()

      for (col in from..last - 1) {
        var number = 0L

        for (row in input.indices) {
          if (col in input[row].indices && input[row][col] != ' ' && input[row][col] != '+' && input[row][col] != '*') {
            number *= 10
            number += input[row][col].digitToInt()
          }
        }

        list += number
      }

      list.println()

      val value = when (input.last()[from]) {
        '+' -> list.sum()
        '*' -> list.filter { it > 0 }.reduce { acc, lng -> acc * lng }
        else -> 0
      }

      value.println()

      result += value
      from = last + 1
    }

    breaks.println()
    result.println()

    return result
  }

}
