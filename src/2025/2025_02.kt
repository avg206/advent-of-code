import kotlin.math.max
import kotlin.math.min

fun main() {
  fun part1(input: String): Long {
    var result = 0L

    val ranges = input.split(",")

    ranges.forEach {
      val (a, b) = it.split("-").map { it.toLong() }

      val aString = a.toString()
      var first = if (a < 10) 1 else aString.substring(0..(aString.length / 2 - 1)).toLong()

      while (true) {
        val number = "$first$first".toLong()

        if (number >= a && number <= b) {
          result += number
        }

        if (number > b) {
          break
        }

        first += 1
      }
    }

    return result
  }

  fun part2(input: String): Long {
    val result = mutableSetOf<Long>()
    val ranges = input.split(",")

    fun blockSize(a: Long, b: Long, size: Int) {
      val multiTo = (b.toString().length / size)

      val from = ("1" + "0".repeat(size - 1)).toLong()
      val to = "9".repeat(size).toLong()

      for (multi in 2..multiTo) {
        for (i in from..to) {
          val number = "$i".repeat(multi).toLong()

          if (number >= a && number <= b) {
            result += number
          }
        }
      }
    }

    ranges.forEach {
      val (a, b) = it.split("-").map { it.toLong() }

      for (size in 1..6) {
        blockSize(a, b, size)
      }
    }

    return result.sum()
  }

  fun solverOld(input: String, times: IntRange): Long {
    val result = mutableSetOf<Long>()
    val ranges = input.split(",")

    fun foo(a: Long, b: Long, multiple: Int) {
      val sizeTo = min(10 / multiple, b.toString().length / multiple)

      for (size in 1..sizeTo) {
        val from = ("1" + "0".repeat(size - 1)).toLong()
        val to = "9".repeat(size).toLong()

        for (i in from..to) {
          val number = "$i".repeat(multiple).toLong()

          if (number >= a && number <= b) {
            result += number
          }
        }
      }
    }

    ranges.forEach { range ->
      val (a, b) = range.split("-").map { it.toLong() }

      for (multiple in times) {
        foo(a, b, multiple)
      }
    }

    return result.sum()
  }

  fun solver(input: String, times: IntRange): Long {
    val result = mutableSetOf<Long>()
    val ranges = input.split(",").map { range ->
      val (a, b) = range.split("-").map { it.toLong() }

      a..b
    }

    for (i in 1..99999) {
      for (multiple in times) {
        if (multiple * "$i".length > 10) break

        val number = "$i".repeat(multiple).toLong()

        if (ranges.any { number in it }) {
          result += number
        }
      }
    }

    return result.sum()
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2025/2025_02_test")
  check(solver(testInput.first(), 2..2) == 1_227_775_554L)
  check(solver(testInput.first(), 2..10) == 4_174_379_265L)

  println("============")

  val input = readInput("2025/2025_02")
  check(solver(input.first(), 2..2) == 32_976_912_643L)
  check(solver(input.first(), 2..10) == 54_446_379_122L)
  solver(input.first(), 2..2).println()
  // - 32976912632
  // + 32976912643
  solver(input.first(), 2..10).println()
  // - 54446379166
  // + 54446379122
}
