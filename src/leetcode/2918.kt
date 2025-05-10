package leetcode

import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.math.max

fun main() {
  fun minSum(nums1: IntArray, nums2: IntArray): Long {
    fun arrayStats(arr: IntArray) = arr.sumOf { it.toLong() } to arr.count { it == 0 }

    val (sum1, zeros1) = arrayStats(nums1)
    val (sum2, zeros2) = arrayStats(nums2)

    if (sum1 == sum2 && zeros1 == 0 && zeros2 == 0) return sum1

    val min1 = sum1 + zeros1.toLong()
    val min2 = sum2 + zeros2.toLong()

    if (zeros1 > 0 && zeros2 > 0) {
      return max(min1, min2)
    }

    if (zeros1 > 0) {
      if (min1 <= sum2)
        return sum2
      else
        return -1
    }

    if (zeros2 > 0) {
      if (min2 <= sum1)
        return sum1
      else
        return -1
    }

    if (sum1 == sum2) return sum1

    return -1L
  }

  expectThat(
    minSum(
      nums1 = intArrayOf(3, 2, 0, 1, 0), nums2 = intArrayOf(6, 5, 0)
    )
  ).isEqualTo(12)

  expectThat(
    minSum(
      nums1 = intArrayOf(2, 0, 2, 0), nums2 = intArrayOf(1, 4)
    )
  ).isEqualTo(-1)


  println("Finished")
}

