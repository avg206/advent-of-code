package leetcode

import strikt.api.expectThat
import strikt.assertions.isEqualTo

fun main() {
  fun pushDominoes(dominoes: String): String {
    var queue = ArrayDeque<Pair<Int, Char>>().apply {
      dominoes.forEachIndexed { index, c ->
        if (c != '.') {
          add(index to c)
        }
      }
    }
    val answer = dominoes.toMutableList()

    fun next(index: Int, direction: Char) = when (direction) {
      'L' -> index - 1
      else -> index + 1
    }

    while (true) {
      val mapping = mutableMapOf<Int, Char>()

      while (queue.isNotEmpty()) {
        val (index, direction) = queue.removeFirst()

        val target = next(index, direction)

        if (target !in dominoes.indices || answer[target] != '.') continue

        if (target in mapping) {
          mapping -= target
        } else {
          mapping[target] = direction
        }
      }

      queue = ArrayDeque<Pair<Int, Char>>().apply {
        mapping.forEach { (t, u) ->
          add(t to u)
          answer[t] = u
        }
      }

      if (queue.isEmpty()) break
    }

    return answer.joinToString("")
  }

  expectThat(
    pushDominoes(
      dominoes = "RR.L"
    )
  ).isEqualTo("RR.L")

  expectThat(
    pushDominoes(
      dominoes = ".L.R...LR..L.."
    )
  ).isEqualTo("LL.RR.LLRRLL..")





  println("Finished")
}

