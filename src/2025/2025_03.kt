fun main() {
  fun part1(input: List<String>): Int {
    return input.sumOf { line ->
      var max = 0

      for (i in line.indices) {
        for (j in i + 1..line.lastIndex) {
          val number = "${line[i]}${line[j]}".toInt()

          if (number > max) max = number
        }
      }

      max
    }
  }

  fun part2(input: List<String>): Long {
    println("-----------")
    fun foo(row: List<Int>, last: Int, left: Int): String {
      if (left == 0) return ""

      println("$last, $left")
      var maxIndex = last + 1

      for (i in (last + 1)..row.lastIndex - left + 1) {
        if (row[i] > row[maxIndex]) {
          maxIndex = i
        }
      }

      println("maxIndex: $maxIndex")

      return row[maxIndex].toString() + foo(row, maxIndex, left - 1)
    }


    return input.sumOf { line ->
      println("--")
      val sum = foo(line.map { it.digitToInt() }, -1, 12).toLong()
      println(sum)
      sum
    }
  }

  fun solver(input: List<String>, length: Int): Long {
    fun foo(row: List<Int>, last: Int, left: Int): String {
      if (left == 0) return ""

      var maxIndex = last + 1

      for (i in (last + 1)..row.lastIndex - left + 1) {
        if (row[i] > row[maxIndex]) {
          maxIndex = i
        }
      }

      return row[maxIndex].toString() + foo(row, maxIndex, left - 1)
    }

    return input.sumOf { line ->
      foo(line.map { it.digitToInt() }, -1, length).toLong()
    }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2025/2025_03_test")
  check(solver(testInput, 2) == 357L)
  check(solver(testInput, 12) == 3_121_910_778_619L)

  println("==============")

  val input = readInput("2025/2025_03")
  check(solver(input, 2) == 17092L)
  check(solver(input, 12) == 170_147_128_753_455L)
  solver(input, 2).println()
  solver(input, 12).println()
}
