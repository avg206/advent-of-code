import kotlin.Long.Companion.MAX_VALUE

fun main() {
  fun part1(input: List<String>): Long {
    var seeds = input[0].split(": ")[1].split(" ").map { it.toLong() }

    var index = 3

    while (index < input.size) {
      var snapshot = seeds
      val newSeeds = mutableListOf<Long>()

      while (index < input.size && input[index] != "") {
        val (destination, source, range) = input[index].split(" ").map { it.toLong() }

        snapshot = snapshot.map {
          if (it >= source && it - source < range) {
            newSeeds.add(destination + (it - source))
            return@map -1 * MAX_VALUE
          }

          return@map it
        }

        index++
      }

      snapshot.forEach {
        if (it > 0) {
          newSeeds.add(it)
        }
      }

      seeds = newSeeds
      index += 2
    }

    return seeds.min()
  }

  data class Range(val s: Long, val e: Long)

  fun part2(input: List<String>): Long {
    var seeds = input[0]
      .split(": ")[1]
      .split(" ")
      .map { it.toLong() }
      .windowed(2, 2)
      .map { Range(it[0], it[0] + it[1] - 1) }

    var index = 3

    while (index < input.size) {
      val newSeeds = mutableListOf<Range>()
      var snapshot = seeds.toMutableList()

      while (index < input.size && input[index] != "") {
        val newSnapshot = mutableListOf<Range>()
        val (destination, start, range) = input[index].split(" ").map { it.toLong() }

        val end = start + range - 1
        val offset = destination - start

        snapshot.forEach { seed ->
          when {
            // Over
            seed.s < start && seed.e > end -> {
              newSeeds.add(Range(start + offset, end + offset))
              newSnapshot.add(Range(seed.s, start - 1))
              newSnapshot.add(Range(end + 1, seed.e))
            }

            // Fit
            seed.s >= start && seed.e <= end -> {
              newSeeds.add(Range(seed.s + offset, seed.e + offset))
            }

            // Not fit
            seed.e < start || seed.s > end -> {
              newSnapshot.add(seed)
            }

            // Left Fit
            seed.s < start && seed.e <= end -> {
              newSnapshot.add(Range(seed.s, start - 1))
              newSeeds.add(Range(start + offset, seed.e + offset))
            }

            // Right fit
            seed.s >= start && seed.e > end -> {
              newSnapshot.add(Range(end + 1, seed.e))
              newSeeds.add(Range(seed.s + offset, end + offset))
            }
          }
        }

        snapshot = newSnapshot

        index++
      }

      newSeeds.addAll(snapshot)
      seeds = newSeeds

      index += 2
    }

    return seeds.minOf { it.s }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2023/2023_05_test")
  check(part1(testInput) == 35L)
  check(part2(testInput) == 46L)

  val input = readInput("2023/2023_05")
  check(part1(input) == 525_792_406L)
  check(part2(input) == 79_004_094L)
  part1(input).println()
  part2(input).println()
}
