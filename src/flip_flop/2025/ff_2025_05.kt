import kotlin.math.max
import kotlin.math.min

fun main() {

  fun solver(input: String, calculator: (Char, Int, Int) -> Int): Pair<Int, String> {
    val map = mutableMapOf<Char, Pair<Int, Int>>()
    val tunnels = mutableSetOf<Char>()

    for (i in input.indices) {
      val char = input[i]

      if (char in map) {
        val prev = map.getValue(char).first
        map[char] = min(prev, i) to max(prev, i)
      } else {
        map[char] = i to -1
        tunnels += char
      }
    }

    var distance = 0
    var position = 0

    while (position != input.length) {
      val char = input[position]

      tunnels -= char

      val (from, to) = map.getValue(char)

      distance += calculator(char, from, to)

      position = when {
        position == from -> to
        else -> from
      } + 1
    }

    return distance to tunnels.joinToString("")
  }


  fun part1(input: String) = solver(input) { _, from, to -> (to - from) }.first

  fun part2(input: String) = solver(input) { _, from, to -> (to - from) }.second

  fun part3(input: String) = solver(input) { char, from, to ->
    when {
      char in 'A'..'Z' -> -1 * (to - from)
      else -> (to - from)
    }
  }.first

  // test if implementation meets criteria from the description, like:
  check(part1("ABccksiPiBAksP") == 38)
  check(part2("ABccksiPiBAksP") == "Bc")
  check(part3("ABccksiPiBAksP") == -6)

  val input = readInput("flip_flop/2025/ff_2025_05")
  check(part1(input.first()) == 1_916)
  check(part2(input.first()) == "WgCjUTOb")
  check(part3(input.first()) == 48)
  part1(input.first()).println()
  part2(input.first()).println()
  part3(input.first()).println()
}
