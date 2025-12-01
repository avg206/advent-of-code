fun main() {
  fun part1(input: List<String>): Int {
    var pointer = 50
    var count = 0

    for (line in input) {
      val dir = line.first()
      var number = line.substring(1).toInt() % 100

      when (dir) {
        'R' -> {
          pointer = (pointer + number) % 100
        }

        'L' -> {
          if (number <= pointer) {
            pointer -= number
          } else {
            number -= pointer
            pointer = 100
            pointer -= number
          }
        }
      }

      if (pointer == 0) count++
    }

    return count
  }

  fun part2(input: List<String>): Int {
    var pointer = 50
    var count = 0

    for (line in input) {
      val dir = line.first()
      var number = line.substring(1).toInt()

//      println("$dir --- $number")

      while (number >= 100) {
        count += 1
        number -= 100
      }

      when (dir) {
        'R' -> {
          if (pointer + number > 100) {
            count += 1
          }

          pointer = (pointer + number) % 100
        }

        'L' -> {
          if (number <= pointer) {
            pointer -= number
          } else {
            if (pointer != 0) {
              count += 1
            }

            number -= pointer
            pointer = 100
            pointer -= number
          }
        }
      }

      if (pointer == 0) count++

//      println("$pointer, $count")
    }

    return count
  }

  fun solver(input: List<String>, whileRotation: Boolean = false): Int {
    var pointer = 50
    var count = 0

    for (line in input) {
      val dir = line.first()
      var number = line.substring(1).toInt()

      if (number >= 100 && whileRotation) {
        count += number / 100
      }

      number %= 100

      when (dir) {
        'R' -> {
          pointer += number

          if (pointer > 100 && whileRotation) count += 1

          pointer %= 100
        }

        'L' -> {
          if (pointer != 0 && pointer < number && whileRotation) count++

          pointer -= number

          if (pointer < 0) {
            pointer = (pointer % 100) + 100
          }
        }
      }

      if (pointer == 0) count++
    }

    return count
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2025/2025_01_test")
  check(solver(testInput) == 3)
  check(solver(testInput, true) == 6)
  check(solver(listOf("R1000", "L1000"), true) == 20)

  val input = readInput("2025/2025_01")
  check(solver(input) == 1172)
  check(solver(input, true) == 6932)
  solver(input).println()
  solver(input, true).println()
  // - 4747
  // - 6967
  // + 6932
}
