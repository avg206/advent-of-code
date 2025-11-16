package leetcode

import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.math.max
import kotlin.math.min

fun main() {
  fun numSub(s: String): Int {
    val modulo = 1_000_000_007L

    var count = 0L
    var result = 0L

    s.forEach {
      when (it) {
        '1' -> count += 1
        else -> {
          result += (count * (count + 1) / 2) % modulo
          count = 0
        }
      }
    }

    result += (count * (count + 1) / 2) % modulo

    return result.toInt()
  }

  expectThat(
    numSub(
      s = "0110111"
    )
  ).isEqualTo(9)

  expectThat(
    numSub(
      s = "101"
    )
  ).isEqualTo(2)

  expectThat(
    numSub(
      s = "111111"
    )
  ).isEqualTo(21)

  println("Finished")
}

