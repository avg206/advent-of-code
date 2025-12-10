import com.microsoft.z3.Context
import com.microsoft.z3.IntExpr
import com.microsoft.z3.IntNum
import com.microsoft.z3.IntSort
import com.microsoft.z3.Status
import java.util.stream.IntStream
import kotlin.streams.toList


fun main() {
  fun part1(input: List<String>): Int {
    fun schemaToBinary(schema: String): Int {
      return schema.substring(1, schema.length - 1).map {
        when (it) {
          '#' -> 1
          else -> 0
        }
      }.joinToString("").toInt(2)
    }

    fun buttonToBinary(button: String, size: Int): Int {
      val binary = IntArray(size) { 0 }
      button.substring(1, button.length - 1).split(",").forEach { binary[it.toInt()] = 1 }
      return binary.joinToString("").toInt(2)
    }


    fun process(line: List<String>): Int {
      var numberOfButtons = Int.MAX_VALUE

      val schema = schemaToBinary(line.first())
      val schemaSize = line.first().length - 2
      val buttons = line.subList(1, line.size - 1).map { buttonToBinary(it, schemaSize) }

      for (i in 0 until (1 shl (buttons.size))) {
        var state = 0
        var count = 0

        val stepPattern = i.toString(2).padStart(buttons.size, '0')

        stepPattern.forEachIndexed { index, ch ->
          if (ch == '1') {
            state = state xor buttons[index]
            count += 1
          }
        }

        if (state == schema) {
          if (count < numberOfButtons) {
            numberOfButtons = count
          }
        }
      }

      return numberOfButtons
    }

    return input.sumOf { process(it.split(" ")) }
  }

  fun part2(input: List<String>): Int {
    fun process(line: List<String>): Int {
      val joltage = line.last().substring(1 until line.last().length - 1).split(",").map { it.toInt() }
      val buttons = line.subList(1, line.size - 1)
        .map { button -> button.substring(1, button.length - 1).split(",").map { it.toInt() } }

      val ctx = Context()
      val opt = ctx.mkOptimize()
      val presses = ctx.mkIntConst("presses")

      val buttonVariables = (buttons.indices).map { it -> ctx.mkIntConst("button_$it") }
      val countersToButtons = mutableMapOf<Int, List<IntExpr>>()

      for (i in buttons.indices) {
        val variable = buttonVariables[i]

        for (position in buttons[i]) {
          countersToButtons[position] = (countersToButtons[position] ?: mutableListOf()) + variable
        }
      }

      for ((position, variables) in countersToButtons) {
        val targetValue = ctx.mkInt(joltage[position])

        val buttonPressesArray = variables.toTypedArray<IntExpr>()
        val sumOfButtonPresses = ctx.mkAdd(*buttonPressesArray)

        val equation = ctx.mkEq(targetValue, sumOfButtonPresses)
        opt.Add(equation)
      }

      val zero = ctx.mkInt(0)

      for (variable in buttonVariables) {
        val nonNegative = ctx.mkGe(variable, zero)
        opt.Add(nonNegative)
      }

      val buttonVariablesArray = buttonVariables.toTypedArray<IntExpr>()
      val sumOfAllButtonVars = ctx.mkAdd(*buttonVariablesArray)

      val totalPressesEq = ctx.mkEq(presses, sumOfAllButtonVars)
      opt.Add(totalPressesEq)

      opt.MkMinimize(presses)

      val status = opt.Check()

      if (status === Status.SATISFIABLE) {
        val model = opt.getModel()
        val outputValue = model.evaluate(presses, false) as IntNum

        return outputValue.getInt()
      } else {
        throw IllegalStateException("No solution found")
      }
    }

    return input.sumOf { process(it.split(" ")) }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2025/2025_10_test")
  check(part1(testInput) == 7)
  check(part2(testInput) == 33)

  println("=======================")

  val input = readInput("2025/2025_10")
  part1(input).println()
  // + 409
  part2(input).println()
}
