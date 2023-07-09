typealias Passport = Map<String, String>

fun main() {
    fun readPassports(input: List<String>): List<Passport> {
        val result = mutableListOf<Passport>()
        var passport = mutableMapOf<String, String>()

        input.forEach {
            when (it) {
                "" -> {
                    result.add(passport)
                    passport = mutableMapOf()
                }

                else -> {
                    it.split(" ").forEach { pair ->
                        val (key, value) = pair.split(":"); passport[key] = value
                    }
                }
            }
        }

        result.add(passport)

        return result
    }

    fun part1(input: List<String>): Int {
        val fields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

        return readPassports(input).fold(0) { acc: Int, passport: Passport ->
            when (fields.all { passport.containsKey(it) }) {
                true -> acc + 1
                false -> acc
            }
        }
    }
//    byr (Birth Year) - four digits; at least 1920 and at most 2002.
//    iyr (Issue Year) - four digits; at least 2010 and at most 2020.
//    eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
//    hgt (Height) - a number followed by either cm or in:
//        If cm, the number must be at least 150 and at most 193.
//        If in, the number must be at least 59 and at most 76.
//    hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
//    ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
//    pid (Passport ID) - a nine-digit number, including leading zeroes.
    fun part2(input: List<String>): Int {
        val validators = mapOf<String, (String) -> Boolean>(
                "byr" to { value -> value.length == 4 && value.toInt() in 1920..2002 },
                "iyr" to { value -> value.length == 4 && value.toInt() in 2010..2020 },
                "eyr" to { value -> value.length == 4 && value.toInt() in 2020..2030 },
                "hgt" to { value ->
                    when (value.takeLast(2)) {
                        "cm" -> {
                            value.dropLast(2).toInt() in 150..193
                        }

                        "in" -> {
                            value.dropLast(2).toInt() in 59..76
                        }

                        else -> false
                    }
                },
                "hcl" to { it.length == 7 && it.first() == '#' && it.drop(1).matches(Regex("^[a-f0-9]+$")) },
                "ecl" to { listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(it) },
                "pid" to { it.length == 9 && it.matches(Regex("^[0-9]+$")) },
        )

        return readPassports(input).fold(0) { acc: Int, passport: Passport ->
            when (validators.all { passport[it.key]?.let { it1 -> it.value(it1) } == true }) {
                true -> acc + 1
                false -> acc
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2020/Day04_test")
    println(part2(testInput))
    check(part2(testInput) == 4)

    val input = readInput("2020/Day04")
    part1(input).println()
    part2(input).println()
}
