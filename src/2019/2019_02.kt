fun main() {
  fun runProgram(input: List<Int>): List<Int> {
    val program = input.toMutableList()
    var position = 0

    while (true) {
      val command = program[position]

      when (command) {
        1 -> program[program[position + 3]] = program[program[position + 1]] + program[program[position + 2]]
        2 -> program[program[position + 3]] = program[program[position + 1]] * program[program[position + 2]]
        99 -> {
          break
        }
      }

      position += 4
    }

    return program
  }

  fun part1(input: String): Int {
    val program = runProgram(input.split(",").map { it.toInt() })

    return program[0]
  }

  fun part2(input: String): Int {
    val target = 19_690_720
    val initProgram = input.split(",").map { it.toInt() }.toMutableList()

    for (i in 0..99) {
      for (j in 0..99) {
        initProgram[1] = i
        initProgram[2] = j

        val program = runProgram(initProgram)

        if (program[0] == target) {
          return i * 100 + j
        }
      }
    }

    throw Exception("not found")
  }

  // test if implementation meets criteria from the description, like:
  check(part1("1,9,10,3,2,3,11,0,99,30,40,50") == 3_500)
  check(part1("1,0,0,0,99") == 2)
  check(part1("2,3,0,3,99") == 2)
  check(part1("2,4,4,5,99,0") == 2)
  check(part1("1,1,1,4,99,5,6,0,99") == 30)

  val input = readInput("2019/2019_02")
  part1(input[0]).println()
  part2(input[0]).println()
}
