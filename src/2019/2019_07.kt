import intCode.intCodeRunner

fun main() {
  fun runPipeline(combination: List<Int>, program: String) = combination.fold(0) { input, phase ->
    val inputs = sequence { yieldAll(listOf(phase, input)) }
    intCodeRunner(program, inputs.iterator()).iterator().next()
  }

  fun runEnhancedPipeline(combination: List<Int>, program: String): Int {
    val signals = mutableListOf(0, 0, 0, 0, 0)
    val inputs = combination.map { mutableListOf(it) }
    val programs = inputs.map {
      val input = generateSequence { it.removeFirst() }
      intCodeRunner(program, input.iterator()).iterator()
    }

    var currentInput = 0

    while (true) {
      for (i in programs.indices) {
        inputs[i].add(currentInput)

        if (!programs[i].hasNext()) {
          return signals.last()
        }

        val output = programs[i].next()

        signals[i] = output
        currentInput = output
      }
    }
  }

  fun part1(input: String): Int {
    val combinations = permutations(0..4)

    return combinations.maxOf { runPipeline(it, input) }
  }

  fun part2(input: String): Int {
    val combinations = permutations(5..9)

    return combinations.maxOf { runEnhancedPipeline(it, input) }
  }

  // test if implementation meets criteria from the description, like:
  check(part1("3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0") == 43_210)
  check(part1("3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0") == 54_321)
  check(part1("3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0") == 65_210)

  check(part2("3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5") == 139_629_729)
  check(part2("3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10") == 18_216)

//  val input = readInput("2019/2019_07")
//  check(part1(input.first()) == 273_814)
//  check(part2(input.first()) == 34_579_864)
//  part1(input.first()).println()
//  part2(input.first()).println()
}
