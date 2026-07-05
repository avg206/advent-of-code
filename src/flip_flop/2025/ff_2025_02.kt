import kotlin.math.max

fun main() {
  val memory = mutableMapOf<Int, Int>(0 to 0, 1 to 1)
  fun fib(n: Int): Int {
    if (n in memory) return memory[n]!!

    memory[n] = fib(n - 1) + fib(n - 2)

    return memory[n]!!
  }

  fun solver(input: String, power: (Int) -> Int): Int {
    var result = 0
    var currentHeight = 0

    var consecutive = 1

    for (i in 1 until input.length) {
      if (input[i] == input[i - 1]) {
        consecutive += 1

        if (i != input.length - 1) continue
      }

      val change = power(consecutive)
      when (input[i - 1]) {
        '^' -> currentHeight += change
        'v' -> currentHeight -= change
      }

      consecutive = 1

      result = max(result, currentHeight)
    }

    return result
  }

  fun part1(input: String) = solver(input) { it }

  fun part2(input: String) = solver(input) { it * (it + 1) / 2 }

  fun part3(input: String) = solver(input, ::fib)


  // test if implementation meets criteria from the description, like:
  check(part1("^v^v^v^v^v") == 1)
  check(part1("^^^v^^^^vvvvvvv") == 6)
  check(part2("^^^v^^^^vvvvvvv") == 15)
  check(part3("^^^v^^^^vvvvvvv") == 4)
  check(part3("^^^^^^^^^^^^vvvvvvvvv^") == 144)

  val input = readInput("flip_flop/2025/ff_2025_02")
  check(part1(input.first()) == 133)
  check(part2(input.first()) == 1_276)
  check(part3(input.first()) == 28_448)
  part1(input.first()).println()
  part2(input.first()).println()
  part3(input.first()).println()
}
