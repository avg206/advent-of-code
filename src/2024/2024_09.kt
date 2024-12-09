fun main() {
  fun part1(input: String): Long {
    val memory = mutableListOf<Int>()

    for (i in input.indices) {
      val char = if (i % 2 == 1) -1 else i / 2

      memory.addAll(List(input[i].digitToInt()) { char })
    }

//    memory.map { if (it == -1) '.' else it.digitToChar() }.joinToString(" ").println()

    var end = memory.size - 1
    while (memory[end] == -1) end -= 1

    var start = 0
    while (memory[start] != -1) start += 1

    while (start < end) {
      memory[start] = memory[end]
      memory[end] = -1
      start += 1
      end -= 1

      while (memory[end] == -1) end -= 1
      while (memory[start] != -1) start += 1
    }

//    memory.map { if (it == -1) '.' else it.digitToChar() }.joinToString(" ").println()

    memory.indexOfFirst { it == -1 }.println()

    var answer = 0L

    for (i in memory.indices) {
      if (memory[i] == -1) break

      answer += (memory[i] * i).toLong()
    }

    return answer
  }

  fun part2(input: String): Long {
    val memory = mutableListOf<Pair<Int, Int>>()

    for (i in input.indices) {
      var char = if (i % 2 == 1) -1 else i / 2

      memory.add(Pair(char, input[i].digitToInt()))
    }

    fun normalise() {
      var i = 0

      while (i < memory.size - 1) {
        if (memory[i].first == -1 && memory[i + 1].first == -1) {
          val size = memory[i].second + memory[i + 1].second

          memory.removeAt(i)
          memory.removeAt(i)
          memory.add(i, Pair(-1, size))

          i -= 1
        }

        i += 1
      }
    }

    for (i in memory.indices.reversed()) {
      for (j in memory.indices) {
        if (j >= i) break
        if (memory[j].first != -1) continue

        if (memory[j].second >= memory[i].second) {
          val leftover = memory[j].second - memory[i].second
          var diff = 0
          val size = memory[i].second

          if (leftover != 0) {
            memory.removeAt(j)
            memory.addAll(j, listOf(memory[i - 1], Pair(-1, leftover)))
            diff = 1
          } else {
            memory[j] = memory[i]
          }

          memory[i + diff] = Pair(-1, size)

          break
        }
      }

      normalise()
    }

    val memory2 = mutableListOf<Int>()

    for (part in memory) {
      memory2.addAll(List(part.second) { part.first })
    }

    var answer = 0L

    for (i in memory2.indices) {
      val num = if (memory2[i] == -1) 0 else memory2[i]

      answer += (num * i).toLong()
    }

    return answer
  }

  // test if implementation meets criteria from the description, like:
  check(part1("12345") == 60L)
  check(part1("2333133121414131402") == 1928L)
  check(part2("2333133121414131402") == 2858L)

  val input = readInput("2024/2024_09")

  check(part1(input.first()) == 6_307_275_788_409L)
  check(part2(input.first()) == 6_327_174_563_252L)
  part1(input.first()).println()
  part2(input.first()).println()
}
