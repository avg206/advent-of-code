package leetcode

import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.util.ArrayDeque

fun main() {
  fun isValid(word: String): Boolean {
    if (word.length < 3) return false
    if (!Regex("[0-9a-zA-Z]+").matches(word)) return false

    var hasVowel = false
    var hasConsonant = false

    word.lowercase().forEach {
      when (it) {
        'a','e','i','o','u' -> hasVowel = true
        !in '0' .. '9' -> hasConsonant = true
      }
    }

    return hasVowel && hasConsonant
  }

  expectThat(
    isValid(
      word = "234Adas"
    )
  ).isEqualTo(true)

  expectThat(
    isValid(
      word = "b3"
    )
  ).isEqualTo(false)

  expectThat(
    isValid(
      word = "bb3"
    )
  ).isEqualTo(false)

  expectThat(
    isValid(
      word = "a3\$e"
    )
  ).isEqualTo(false)

  println("Finished")
}

