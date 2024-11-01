import kotlin.math.ceil

typealias Receipts = MutableMap<String, Triple<Long, Long, List<Pair<String, Long>>>>

fun main() {
  fun readInput(input: List<String>): Receipts = input
    .associate { line ->
      val (from, to) = line.split(" => ")

      val (quantity, name) = to.split(" ")

      name to Triple(quantity.toLong(), 0L, from.split(", ").map {
        val (q, n) = it.split(" ")
        Pair(n, q.toLong())
      })
    }
    .toMutableMap()

  fun part1(input: List<String>): Long {
    val receipts = readInput(input)
    var oreQuantity = 0L

    fun foo(name: String, quantity: Long) {
      if (name == "ORE") {
        oreQuantity += quantity
        return
      }

      val (multiply, currentStock, recept) = receipts.getValue(name)

      if (currentStock >= quantity) {
        receipts[name] = Triple(multiply, currentStock - quantity, recept)
        return
      }

      val neededTimes = ceil((quantity - currentStock).toDouble() / multiply.toDouble()).toLong()
      receipts[name] = Triple(multiply, currentStock + (neededTimes * multiply) - quantity, recept)

      recept.forEach {
        foo(it.first, it.second * neededTimes)
      }
    }

    foo("FUEL", 1)

    return oreQuantity
  }

  fun part2(input: List<String>): Long {
    var receipts = readInput(input)

    var oreQuantity = 1_000_000_000_000L

    fun foo(name: String, quantity: Long, currentReceipts: Receipts): Receipts {
      if (name == "ORE") {
        if (oreQuantity >= quantity) {
          oreQuantity -= quantity
          return currentReceipts
        } else {
          throw Exception("Not enough ORE")
        }
      }

      val (multiply, currentStock, recept) = currentReceipts.getValue(name)

      if (currentStock >= quantity) {
        currentReceipts[name] = Triple(multiply, currentStock - quantity, recept)
        return currentReceipts
      }

      val neededTimes = ceil((quantity - currentStock).toDouble() / multiply.toDouble()).toLong()

      try {
        recept.forEach {
          foo(it.first, it.second * neededTimes, currentReceipts)
        }
        currentReceipts[name] = Triple(multiply, currentStock + (neededTimes * multiply) - quantity, recept)
      } catch (error: Exception) {
        throw error
      }

      return currentReceipts
    }

    var answer = 0L
    var currentScope = 10_000L
    var previousOreQuality = oreQuantity

    while (true) {
      try {
        previousOreQuality = oreQuantity

        val localReceipts = buildMap {
          receipts.forEach { (t, u) -> put(t, u.copy()) }
        }.toMutableMap()

        receipts = foo("FUEL", currentScope, localReceipts)

        answer += currentScope
      } catch (error: Exception) {
        if (currentScope > 1) {
          oreQuantity = previousOreQuality
          currentScope /= 10
        } else {
          break
        }
      }
    }

    return answer
  }

  // test if implementation meets criteria from the description, like:
  check(part1(readInput("2019/2019_14_test")) == 31L)
  check(part1(readInput("2019/2019_14_test_2")) == 165L)
  check(part1(readInput("2019/2019_14_test_3")) == 180_697L)
  check(part1(readInput("2019/2019_14_test_4")) == 2_210_736L)

  check(part2(readInput("2019/2019_14_test_3")) == 5_586_022L)
  check(part2(readInput("2019/2019_14_test_4")) == 460_664L)

  val input = readInput("2019/2019_14")
  check(part1(input) == 374_457L)
  check(part2(input) == 3_568_888L)
  part1(input).println()
  part2(input).println()
}
