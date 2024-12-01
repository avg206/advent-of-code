fun main() {
  fun calculateStrightForward(list: List<Int>, length: Int): Int {
    val sequence = list.toMutableList()

    while (sequence.size < length) {
      val lastNumber = sequence.last()

      val lastTwoIndices = sequence.indices
        .filter { sequence[it] == lastNumber }
        .toList()
        .takeLast(2)

      if (lastTwoIndices.size == 1) {
        sequence.add(0)
        continue
      }

      sequence.add(lastTwoIndices[1] - lastTwoIndices[0])
    }

    return sequence.last()
  }

  fun calculateClever(list: List<Int>, index: Int): Int {
    val lastIndexOf = list.mapIndexed { ind, i -> i to ind + 1 }.toMap().toMutableMap()
    var lastNumber = list.last()

    for (i in list.size..index - 1) {
      val lastIndex = lastIndexOf[lastNumber]
      val oldLastNumber = lastNumber

      lastNumber = when (lastIndex) {
        null, i -> 0
        else -> i - lastIndex
      }

      lastIndexOf[oldLastNumber] = i
    }

    return lastNumber
  }

  fun part1(input: String): Int {
    val list = input.split(",").map { it.toInt() }

    return calculateClever(list, 2020)
  }

  fun part2(input: String): Int {
    val list = input.split(",").map { it.toInt() }

    return calculateClever(list, 30_000_000)
  }

  // test if implementation meets criteria from the description, like:
  check(part1("0,3,6") == 436)
  check(part1("1,3,2") == 1)
  check(part1("2,1,3") == 10)
  check(part1("1,2,3") == 27)
  check(part1("2,3,1") == 78)
  check(part1("3,2,1") == 438)
  check(part1("0,3,6") == 436)
  check(part1("3,1,2") == 1836)

  println("----")

  check(part2("0,3,6") == 175594)
  check(part2("1,3,2") == 2578)
  check(part2("2,1,3") == 3544142)

  println("----")

  part1("6,3,15,13,1,0").println()
  part2("6,3,15,13,1,0").println()
}
