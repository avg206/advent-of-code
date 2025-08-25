package leetcode

import println
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.math.max

fun main() {
  fun maximumLength(nums: IntArray): Int {
    val diffs = IntArray(nums.size - 1) { 0 }
      .mapIndexed { index, _ -> (nums[index] + nums[index + 1]) % 2 }

    var maxLength = 1
    var currentLength = 1
    var current = diffs.first()

    for (i in 1 until diffs.size) {
      when (diffs[i]) {
        current -> currentLength = currentLength + 1
        else -> {
          maxLength = max(maxLength, currentLength)
          currentLength = 1
          current = diffs[i]
        }
      }
    }

    maxLength = max(maxLength, currentLength)


    diffs.toList().println()


    return maxLength + 1
  }

  expectThat(
    maximumLength(
      nums = intArrayOf(1, 2, 3, 4)
    )
  ).isEqualTo(4)

  expectThat(
    maximumLength(
      nums = intArrayOf(1, 2, 1, 1, 2, 1, 2)
    )
  ).isEqualTo(6)

  expectThat(
    maximumLength(
      nums = intArrayOf(1, 3)
    )
  ).isEqualTo(2)

  println("Finished")
}

