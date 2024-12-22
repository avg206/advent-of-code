fun main() {
  fun mix(number: Long, previous: Long) = number xor previous
  fun prune(number: Long) = number % 16_777_216L

  fun simulateWithMemory(initial: Long, steps: Int): Pair<Long, Map<List<Int>, Int>> {
    var number = initial

    val options = mutableMapOf<List<Int>, Int>()
    val prices = mutableListOf((number % 10).toInt())
    val changes = mutableListOf<Int>()

    for (i in 1..steps) {
      number = prune(mix((number * 64L), number))
      number = prune(mix((number / 32L), number))
      number = prune(mix((number * 2048), number))

      changes.add((number % 10).toInt() - (prices.last()))
      prices.add((number % 10).toInt())
    }

    for (from in changes.indices) {
      if (changes.size - from - 1 < 4) break

      val key = mutableListOf<Int>()
      for (j in 0..3) key.add(changes[from + j])

      options.putIfAbsent(key, prices[from + 4])
    }

    return number to options
  }

  fun part1(input: List<String>): Long {
    return input.map { it.toLong() }.sumOf { simulateWithMemory(it, 2000).first }
  }

  fun part2(input: List<String>): Int {
    val memory = mutableMapOf<List<Int>, Int>()

    input.map { it.toLong() }.forEach { number ->
      val options = simulateWithMemory(number, 2000).second

      for ((key, value) in options) {
        memory[key] = (memory[key] ?: 0) + value
      }
    }

    return memory.maxOf { it.value }
  }


  // test if implementation meets criteria from the description, like:
  check(part1(listOf("1", "10", "100", "2024")) == 37_327_623L)
  check(part2(listOf("1", "2", "3", "2024")) == 23)

  val input = readInput("2024/2024_22")
  check(part1(input) == 17_577_894_908L)
  check(part2(input) == 1931)
  part1(input).println()
  part2(input).println()
}
