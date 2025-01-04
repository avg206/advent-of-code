fun main() {
  fun part1(input: List<String>): Int {
    fun isNiceString(string: String): Boolean {
      val vowelsCount = string.count { it in "aeiou" }
      val containsRestrictedStrings = listOf("ab", "cd", "pq", "xy").any { it in string }
      val hasDoubles = string.windowed(2, 1).any { it.first() == it.last() }

      return (vowelsCount >= 3) && !containsRestrictedStrings && hasDoubles
    }

    return input.count { isNiceString(it) }
  }

  fun part2(input: List<String>): Int {
    fun hasTwoPairs(string: String): Boolean {
      for (i in string.indices) {
        for (j in (i + 2) until (string.length) - 1) {
          if (string[i] == string[j] && string[i + 1] == string[j + 1]) return true
        }
      }

      return false
    }

    fun isNiceString(string: String): Boolean {
      val repeatsWithInBetween = string.windowed(3, 1).any { it.first() == it.last() }

      return repeatsWithInBetween && hasTwoPairs(string)
    }

    return input.count { isNiceString(it) }
  }

  // test if implementation meets criteria from the description, like:
  check(part1(listOf("ugknbfddgicrmopn", "aaa", "jchzalrnumimnmhp", "haegwjzuvuyypxyu", "dvszwmarrgswjxmb")) == 2)
  check(part2(listOf("qjhvhtzxzqqjkmpb", "xxyxx", "uurcxstgmygtbstg", "ieodomkazucvgmuy")) == 2)

  val input = readInput("2015/2015_05")
  part1(input).println()
  part2(input).println()
}
