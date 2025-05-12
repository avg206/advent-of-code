package leetcode

import strikt.api.expectThat
import strikt.assertions.isEqualTo

fun main() {
  fun findEvenNumbers(digits: IntArray): IntArray {
    val result = mutableSetOf<Int>()

    for (i in digits.indices) {
      for (j in digits.indices) {
        for (k in digits.indices) {
          if (digits[k] % 2 == 1) continue
          if (i == j || j == k || i == k) continue

          val number = (digits[i] * 100) + (digits[j] * 10) + digits[k]
          if (number < 100) continue

          result += number
        }
      }
    }

    return result.toIntArray().sortedArray()
  }

  expectThat(
    findEvenNumbers(
      digits = intArrayOf(2, 1, 3, 0)
    )
  ).isEqualTo(intArrayOf(102, 120, 130, 132, 210, 230, 302, 310, 312, 320))

  expectThat(
    findEvenNumbers(
      digits = intArrayOf(2, 2, 8, 8, 2)
    )
  ).isEqualTo(intArrayOf(222, 228, 282, 288, 822, 828, 882))





  println("Finished")
}

