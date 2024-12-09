fun main() {
  fun part1(input: String): Long {
    val memory = input.flatMapIndexed { index, c ->
      List(c.digitToInt()) { Pair(if (index % 2 == 1) -1 else index / 2, 1) }
    }.toMutableList()

    var left = memory.indexOfFirst { it.first == -1 }
    var right = memory.indexOfLast { it.first != -1 }

    while (left < right) {
      memory[left] = memory[right]
      memory[right] = Pair(-1, 1)
      left += 1
      right -= 1

      while (memory[left].first != -1) left += 1
      while (memory[right].first == -1) right -= 1
    }

    return memory.mapIndexed { index, (id, _) -> if (id == -1) 0L else (id * index).toLong() }.sum()
  }

  fun part2(input: String): Long {
    val memory = input.mapIndexed { index, c ->
      Pair(if (index % 2 == 1) -1 else index / 2, c.digitToInt())
    }.toMutableList()

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

    for (from in memory.indices.reversed()) {
      if (memory[from].first == -1) continue
      for (to in memory.indices) {
        if (to >= from) break
        if (memory[to].first != -1) continue
        if (memory[to].second < memory[from].second) continue

        val fromItem = memory[from]

        when (val leftover = memory[to].second - memory[from].second) {
          0 -> {
            memory[to] = fromItem
            memory[from] = Pair(-1, fromItem.second)
          }

          else -> {
            memory[from] = Pair(-1, fromItem.second)
            memory.removeAt(to)
            memory.addAll(to, listOf(fromItem, Pair(-1, leftover)))
          }
        }

        break
      }

      normalise()
    }

    return memory.foldIndexed(Pair(0, 0L)) { index, (distance, acc), pair ->
      when (pair.first) {
        -1 -> Pair(distance + pair.second, acc)
        else -> {
          val indexesSum = pair.second.toLong() * (distance.toLong() * 2L + pair.second.toLong() - 1L) / 2L

          Pair(distance + pair.second, acc + (pair.first * indexesSum).toLong())
        }
      }
    }.second
  }

//  // test if implementation meets criteria from the description, like:
  check(part1("12345") == 60L)
  check(part1("2333133121414131402") == 1928L)
  check(part2("2333133121414131402") == 2858L)

  val input = readInput("2024/2024_09")

//  check(part1(input.first()) == 6_307_275_788_409L)
  check(part2(input.first()) == 6_327_174_563_252L)
  part1(input.first()).println()
  part2(input.first()).println()
}
