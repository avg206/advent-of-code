import intCode.intCodeRunner

fun main() {
  fun part1(program: String): Int {
    val input = generateSequence { 1L }.iterator()
    return intCodeRunner(program, input).last().toInt()
  }

  fun part2(program: String): Int {
    val input = generateSequence { 5L }.iterator()
    return intCodeRunner(program, input).last().toInt()
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
