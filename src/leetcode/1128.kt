package leetcode

import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.math.max
import kotlin.math.min

fun main() {
  fun numEquivDominoPairs(dominoes: Array<IntArray>): Int {
    val pairs = mutableMapOf<Pair<Int, Int>, Int>()

    dominoes.forEach { (a, b) ->
      val key = min(a, b) to max(a, b)
      pairs[key] = (pairs[key] ?: 0) + 1
    }

    val memory = mutableMapOf<Int, Int>()
    fun factorial(n: Int): Int {
      if (n !in memory) {
        memory[n] = when {
          n > 0 -> n + factorial(n - 1)
          else -> 0
        }
      }

      return memory.getValue(n)
    }

    return pairs.values.filter { it > 1 }.sumOf { factorial(it - 1) }
  }

  expectThat(
    numEquivDominoPairs(
      dominoes = arrayOf(intArrayOf(1, 2), intArrayOf(2, 1), intArrayOf(3, 4), intArrayOf(5, 6))
    )
  ).isEqualTo(1)

  expectThat(
    numEquivDominoPairs(
      dominoes = arrayOf(intArrayOf(1, 2), intArrayOf(1, 2), intArrayOf(1, 1), intArrayOf(1, 2), intArrayOf(2, 2))
    )
  ).isEqualTo(3)





  println("Finished")
}

