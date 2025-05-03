package leetcode

import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.math.min

fun main() {
  fun minDominoRotations(tops: IntArray, bottoms: IntArray): Int {
    fun foo(top: IntArray, bottom: IntArray): Int {
      val noAnswer = 1_000_000

      fun calc(target: Int): Int {
        var toFlip = 0

        for (i in top.indices) {
          if (top[i] != target && bottom[i] != target) return noAnswer

          if (top[i] != target && bottom[i] == target) {
            toFlip += 1
          }
        }

        return toFlip
      }

      return (1..6).map { calc(it) }.filter { it != noAnswer }.minOrNull() ?: -1
    }

    val a = foo(tops, bottoms)
    val b = foo(bottoms, tops)

    return when {
      a == -1 && b == -1 -> -1
      a == -1 -> b
      b == -1 -> a
      else -> min(a, b)
    }
  }

  expectThat(
    minDominoRotations(
      tops = intArrayOf(2, 1, 2, 4, 2, 2), bottoms = intArrayOf(5, 2, 6, 2, 3, 2)
    )
  ).isEqualTo(2)

  expectThat(
    minDominoRotations(
      tops = intArrayOf(3, 5, 1, 2, 3), bottoms = intArrayOf(3, 6, 3, 3, 4)
    )
  ).isEqualTo(-1)





  println("Finished")
}

