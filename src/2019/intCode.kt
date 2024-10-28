package intCode

fun retrieveParamAddress(commands: MutableMap<Long, Long>, position: Long, mode: Int, relativeBase: Long) =
  when (mode) {
    0 -> commands.getOrDefault(position, 0L)
    1 -> position
    2 -> commands.getOrDefault(position, 0L) + relativeBase

    else -> throw Exception("Unknown mode $mode for retrieve")
  }

fun modesSequence(command: String) = sequence {
  yieldAll(command.dropLast(2).reversed().map { it.digitToInt() })
  yieldAll(generateSequence { 0 })
}

fun intCodeRunner(program: String, input: Iterator<Long>) = sequence {
  var relativeBase = 0L
  val commands =
    program.split(',').mapIndexed { index, s -> index.toLong() to s.toLong() }.associate { it }.toMutableMap()

  var position = 0L

  while (true) {
    val command = commands[position].toString()
    val opcode = command.takeLast(2).toInt()
    val modes = modesSequence(command).iterator()

    when (opcode) {
      1 -> {
        val a = retrieveParamAddress(commands, position + 1, modes.next(), relativeBase)
        val b = retrieveParamAddress(commands, position + 2, modes.next(), relativeBase)
        val address = retrieveParamAddress(commands, position + 3, modes.next(), relativeBase)

        commands[address] = commands.getOrDefault(a, 0) + commands.getOrDefault(b, 0)
        position += 4
      }

      2 -> {
        val a = retrieveParamAddress(commands, position + 1, modes.next(), relativeBase)
        val b = retrieveParamAddress(commands, position + 2, modes.next(), relativeBase)
        val address = retrieveParamAddress(commands, position + 3, modes.next(), relativeBase)

        commands[address] = commands.getOrDefault(a, 0) * commands.getOrDefault(b, 0)
        position += 4
      }

      3 -> {
        val address = retrieveParamAddress(commands, position + 1, modes.next(), relativeBase)
        commands[address] = input.next()

        position += 2
      }

      4 -> {
        val address = retrieveParamAddress(commands, position + 1, modes.next(), relativeBase)
        yield(commands.getOrDefault(address, 0))

        position += 2
      }

      5 -> {
        val a = retrieveParamAddress(commands, position + 1, modes.next(), relativeBase)
        val address = retrieveParamAddress(commands, position + 2, modes.next(), relativeBase)

        if (commands[a] != 0L) {
          position = commands.getOrDefault(address, 0)
        } else {
          position += 3
        }
      }

      6 -> {
        val a = retrieveParamAddress(commands, position + 1, modes.next(), relativeBase)
        val address = retrieveParamAddress(commands, position + 2, modes.next(), relativeBase)

        if (commands[a] == 0L) {
          position = commands.getOrDefault(address, 0)
        } else {
          position += 3
        }
      }

      7 -> {
        val a = retrieveParamAddress(commands, position + 1, modes.next(), relativeBase)
        val b = retrieveParamAddress(commands, position + 2, modes.next(), relativeBase)
        val address = retrieveParamAddress(commands, position + 3, modes.next(), relativeBase)

        if (commands.getOrDefault(a, 0) < commands.getOrDefault(b, 0)) {
          commands[address] = 1
        } else {
          commands[address] = 0
        }

        position += 4
      }

      8 -> {
        val a = retrieveParamAddress(commands, position + 1, modes.next(), relativeBase)
        val b = retrieveParamAddress(commands, position + 2, modes.next(), relativeBase)
        val address = retrieveParamAddress(commands, position + 3, modes.next(), relativeBase)

        if (commands.getOrDefault(a, 0) == commands.getOrDefault(b, 0)) {
          commands[address] = 1
        } else {
          commands[address] = 0
        }

        position += 4
      }

      9 -> {
        val a = retrieveParamAddress(commands, position + 1, modes.next(), relativeBase)
        relativeBase += commands.getOrDefault(a, 0L)

        position += 2
      }

      99 -> break

      else -> throw Exception("Unknown opcode - $opcode")
    }
  }
}
