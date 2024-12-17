import kotlin.math.pow
import kotlin.math.truncate

fun main() {
  //  Legacy solver for initial solution, don't want to remove :)
  //  fun solver(a: Long): Long {
  //    return (((((a % 8) xor 7) xor (a shr ((a % 8) xor 2).toInt())) xor 2) % 8)
  //  }

  class Program(input: String, initA: Long) {
    val opcodes = input.split(",").map { it.toInt() }.windowed(2, 2).map { it[0] to it[1] }

    var a = initA
    var b = 0L
    var c = 0L

    fun comboOperand(operand: Int): Long {
      return when (operand) {
        0, 1, 2, 3 -> operand.toLong()
        4 -> a
        5 -> b
        6 -> c
        else -> throw Exception("7 found")
      }
    }

    val runner = sequence<Int> {
      var stopped = false
      while (!stopped) {
        opcodes.forEach { (code, operand) ->
          when (code) {
            0 -> a = truncate(a.toDouble() / (2.0).pow(comboOperand(operand).toDouble())).toLong()
            1 -> b = b xor operand.toLong()
            2 -> b = comboOperand(operand) % 8
            4 -> b = b xor c
            3 -> if (a == 0L) stopped = true
            5 -> yield((comboOperand(operand) % 8L).toInt())
            6 -> b = truncate(a.toDouble() / (2.0).pow(comboOperand(operand).toInt())).toLong()
            7 -> c = truncate(a.toDouble() / (2.0).pow(comboOperand(operand).toInt())).toLong()
          }
        }
      }
    }
  }

  fun part1(input: String, initialAValue: Long): String {
    val program = Program(input, initialAValue)

    return program.runner.toList().joinToString(",")
  }

  fun part2(input: String): Long {
    var solutions = (0L..8L).toList()

    input.split(",").map { it.toInt() }.reversed().forEach { target ->
      solutions = solutions.flatMap { attempt ->
        (0L..8L)
          .map { (attempt shl 3) + it }
          .filter { Program(input, it).runner.first() == target }
      }
    }

    return solutions.min()
  }

  // test if implementation meets criteria from the description, like:
  check(part1("0,1,5,4,3,0", 729L) == "4,6,3,5,6,3,5,2,1,0")
  check(part2("0,3,5,4,3,0") == 117_440L)

  check(part1("2,4,1,2,7,5,0,3,1,7,4,1,5,5,3,0", 27_334_280L) == "7,6,5,3,6,5,7,0,4")
  check(part2("2,4,1,2,7,5,0,3,1,7,4,1,5,5,3,0") == 190_615_597_431_823L)
  part1("2,4,1,2,7,5,0,3,1,7,4,1,5,5,3,0", 27_334_280L).println()
  part2("2,4,1,2,7,5,0,3,1,7,4,1,5,5,3,0").println()
}
