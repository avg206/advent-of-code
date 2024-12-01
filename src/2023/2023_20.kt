import kotlin.math.min

fun main() {
  data class Module(val name: String, val type: Char, val dest: List<String>, val input: MutableList<String>)

  fun readModules(input: List<String>): Map<String, Module> {
    val modules = mutableMapOf<String, Module>()

    input.forEach { line ->
      val (left, right) = line.split(" -> ")

      modules[left.substring(1)] = Module(
        left.substring(1),
        left[0],
        right.split(", "),
        mutableListOf()
      )

      right.split(", ").forEach {
        if (!modules.containsKey(it)) {
          modules[it] = Module(
            it,
            '-',
            listOf(),
            mutableListOf()
          )
        }
      }
    }


    modules.keys.forEach { name ->
      modules[name]!!.dest.forEach {
        modules[it]?.input!!.add(name)
      }
    }

    return modules
  }

  fun part1(input: List<String>): Long {
    val modules = readModules(input)

    fun process(times: Int): Long {
      var low = 0L
      var high = 0L

      val state1 = mutableMapOf<String, Int>()
      val state2 = mutableMapOf<String, MutableMap<String, Int>>()

      modules.entries.forEach { (name, module) ->
        when (module.type) {
          '%' -> {
            state1[name] = 0
          }

          '&' -> {
            state2[name] = mutableMapOf()
            module.input.forEach { state2[name]!![it] = 0 }
          }
        }
      }

      for (i in 1..times) {
//                println("Pulse $i")

        val queue = mutableListOf(
          Triple("roadcaster", 0, "button")
        )

        while (queue.isNotEmpty()) {
          val (name, pulse, sender) = queue.removeFirst()
          val module = modules[name]!!

          when (pulse) {
            0 -> low += 1
            1 -> high += 1
          }

          when (module.type) {
            'b' -> {
              module.dest.forEach {
                queue.add(Triple(it, pulse, name))
              }
            }

            '%' -> {
              if (pulse == 1) continue

              state1[module.name] = when (state1[module.name]) {
                0 -> 1
                1 -> 0
                else -> throw Exception("Unknown module name")
              }

              module.dest.forEach {
                queue.add(Triple(it, state1[module.name]!!, name))
              }
            }

            '&' -> {
              state2[name]!![sender] = pulse

              val isAllHigh = state2[name]!!.all { (_, value) -> value == 1 }
              val newSignal = if (isAllHigh) 0 else 1

              module.dest.forEach {
                queue.add(Triple(it, newSignal, name))
              }
            }

            '-' -> {}

            else -> throw Exception("Unknown module type")

          }
        }
      }

      return low * high
    }

    return process(1000)
  }

  fun part2(input: List<String>): Long {
    val modules = readModules(input)

    fun process(): Long {
      val state1 = mutableMapOf<String, Int>()
      val state2 = mutableMapOf<String, MutableMap<String, Int>>()

      modules.entries.forEach { (name, module) ->
        when (module.type) {
          '%' -> {
            state1[name] = 0
          }

          '&' -> {
            state2[name] = mutableMapOf()
            module.input.forEach { state2[name]!![it] = 0 }
          }
        }
      }


      val lower = mutableMapOf<String, Int>()
      var i = 0

      val targets = modules[
        modules["rx"]!!.input[0]
      ]!!.input

      while (lower.size != targets.size) {
        i += 1

        val gotLow = mutableListOf<String>()

        val queue = mutableListOf(
          Triple("roadcaster", 0, "button")
        )

        while (queue.isNotEmpty()) {
          val (name, pulse, sender) = queue.removeFirst()
          val module = modules[name]!!

          if (pulse == 0 && targets.contains(module.name)) {
            gotLow.add(module.name)
          }

          when (module.type) {
            'b' -> {
              module.dest.forEach {
                queue.add(Triple(it, pulse, name))
              }
            }

            '%' -> {
              if (pulse == 1) continue

              state1[module.name] = when (state1[module.name]) {
                0 -> 1
                1 -> 0
                else -> throw Exception("Unknown module name")
              }

              module.dest.forEach {
                queue.add(Triple(it, state1[module.name]!!, name))
              }
            }

            '&' -> {
              state2[name]!![sender] = pulse

              val isAllHigh = state2[name]!!.all { (_, value) -> value == 1 }
              val newSignal = if (isAllHigh) 0 else 1

              module.dest.forEach {
                queue.add(Triple(it, newSignal, name))
              }
            }

            '-' -> {}

            else -> throw Exception("Unknown module type")
          }
        }

        gotLow.forEach {
          lower[it] = min(i, lower[it] ?: Int.MAX_VALUE)
        }
      }


      return lower.values.fold(1L) { acc, tick -> acc * tick.toLong() }
    }

    return process()
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2023/2023_20_test")
  check(part1(testInput) == 32_000_000L)

  val input = readInput("2023/2023_20")
  check(part1(input) == 879_834_312L)
  check(part2(input) == 243_037_165_713_371L)
  part1(input).println()
  part2(input).println()
}
