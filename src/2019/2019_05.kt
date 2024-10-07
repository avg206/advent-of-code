fun main() {
  fun retrieveParamAddress(commands: List<Int>, position: Int, mode: Int): Int {
    return when (mode) {
      0 -> commands[position]
      1 -> position

      else -> throw Exception("Unknown mode $mode for retrieve")
    }
  }

  fun run(program: String, defaultInput: Int): List<Int> {
    val commands = program.split(',').map { it.toInt() }.toMutableList()
    val output = mutableListOf<Int>()

    var position = 0

    while (true) {
      val command = commands[position].toString()
      val opcode = command.takeLast(2).toInt()
      val modes = sequence {
        yieldAll(command.dropLast(2).reversed().map { it.digitToInt() })
        yieldAll(generateSequence { 0 })
      }.iterator()

      when (opcode) {
        1 -> {
          val a = retrieveParamAddress(commands, position + 1, modes.next())
          val b = retrieveParamAddress(commands, position + 2, modes.next())
          val address = retrieveParamAddress(commands, position + 3, modes.next())

          commands[address] = commands[a] + commands[b]
          position += 4
        }

        2 -> {
          val a = retrieveParamAddress(commands, position + 1, modes.next())
          val b = retrieveParamAddress(commands, position + 2, modes.next())
          val address = retrieveParamAddress(commands, position + 3, modes.next())

          commands[address] = commands[a] * commands[b]
          position += 4
        }

        3 -> {
          val address = retrieveParamAddress(commands, position + 1, modes.next())
          commands[address] = defaultInput

          position += 2
        }

        4 -> {
          val address = retrieveParamAddress(commands, position + 1, modes.next())
          output.add(commands[address])

          position += 2
        }

        5 -> {
          val a = retrieveParamAddress(commands, position + 1, modes.next())
          val address = retrieveParamAddress(commands, position + 2, modes.next())

          if (commands[a] != 0) {
            position = commands[address]
          } else {
            position += 3
          }
        }

        6 -> {
          val a = retrieveParamAddress(commands, position + 1, modes.next())
          val address = retrieveParamAddress(commands, position + 2, modes.next())

          if (commands[a] == 0) {
            position = commands[address]
          } else {
            position += 3
          }
        }

        7 -> {
          val a = retrieveParamAddress(commands, position + 1, modes.next())
          val b = retrieveParamAddress(commands, position + 2, modes.next())
          val address = retrieveParamAddress(commands, position + 3, modes.next())

          if (commands[a] < commands[b]) {
            commands[address] = 1
          } else {
            commands[address] = 0
          }

          position += 4
        }

        8 -> {
          val a = retrieveParamAddress(commands, position + 1, modes.next())
          val b = retrieveParamAddress(commands, position + 2, modes.next())
          val address = retrieveParamAddress(commands, position + 3, modes.next())

          if (commands[a] == commands[b]) {
            commands[address] = 1
          } else {
            commands[address] = 0
          }

          position += 4
        }

        99 -> break

        else -> throw Exception("Unknown opcode - $opcode")
      }
    }

    return output
  }

  fun part1(input: String): Int {
    return run(input, 1).last()
  }

  fun part2(input: String): Int {
    return run(input, 5).last()
  }

  // test if implementation meets criteria from the description, like:
  check(part1("3,0,4,0,99") == 1)
  check(part1("1002,6,3,6,4,0,33") == 1002)

  check(part2("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99") == 999)
  check(part2("3,9,8,9,10,9,4,9,99,-1,8") == 0)
  check(part2("3,9,7,9,10,9,4,9,99,-1,8") == 1)
  check(part2("3,3,1108,-1,8,3,4,3,99") == 0)
  check(part2("3,3,1107,-1,8,3,4,3,99") == 1)
  check(part2("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9") == 1)
  check(part2("3,3,1105,-1,9,1101,0,0,12,4,12,99,1") == 1)

  val input = readInput("2019/2019_05").first()
  check(part1(input) == 7259358)
  check(part2(input) == 11826654)
}
