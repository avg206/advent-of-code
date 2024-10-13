package intCode

fun retrieveParamAddress(commands: List<Int>, position: Int, mode: Int) = when (mode) {
  0 -> commands[position]
  1 -> position

  else -> throw Exception("Unknown mode $mode for retrieve")
}

fun modesSequence(command: String) = sequence {
  yieldAll(command.dropLast(2).reversed().map { it.digitToInt() })
  yieldAll(generateSequence { 0 })
}

fun intCodeRunner(program: String, input: Iterator<Int>) = sequence {
  val commands = program.split(',').map { it.toInt() }.toMutableList()

  var position = 0

  while (true) {
    val command = commands[position].toString()
    val opcode = command.takeLast(2).toInt()
    val modes = modesSequence(command).iterator()

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
        commands[address] = input.next()

        position += 2
      }

      4 -> {
        val address = retrieveParamAddress(commands, position + 1, modes.next())
        yield(commands[address])

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
}
