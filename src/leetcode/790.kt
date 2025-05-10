package leetcode

import println
import strikt.api.expectThat
import strikt.assertions.isEqualTo

fun main() {
  fun numTilings(n: Int): Int {
    val modulo = 1_000_000_007L

    val memory = mutableMapOf<Int, Long>()
    fun dp(i: Int): Long {
      if (i == 0) return 1L
      if (i < 0) return 0L
      if (i !in memory) {
        memory[i] = (dp(i - 1) + dp(i - 2) + (2 * dp(i - 3))) % modulo
      }

      return memory.getValue(i)
    }


    val result = dp(n).toInt()

    memory.println()

    return result
  }

  expectThat(
    numTilings(
      n = 3
    )
  ).isEqualTo(5)

  expectThat(
    numTilings(
      n = 1
    )
  ).isEqualTo(1)

  expectThat(
    numTilings(
      n = 4
    )
  ).isEqualTo(11)

  expectThat(
    numTilings(
      n = 5
    )
  ).isEqualTo(24)





  println("Finished")
}

