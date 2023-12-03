import kotlin.math.pow

typealias Mask = List<Pair<Int, Char>>

fun main() {
    fun initializeMask(string: String): Mask {
        val mask = string.substring(7, string.length)

        return mask.foldIndexed(mutableListOf()) { index, acc, c ->
            when (c) {
                '0', '1' -> acc.add(Pair(index, c))
            }; acc
        }
    }

    fun applyMask(number: Long, mask: Mask): Long {
        val result = number.toString(2).padStart(36, '0').toMutableList()

        mask.forEach {
            val (index, bit) = it

            result[index] = bit
        }

        return result.joinToString("").toLong(2)
    }

    fun applyMask2(number: Long, mask: List<Char>): List<Long> {
        val numberInBinary = number.toString(2).padStart(36, '0').toMutableList()

        val result = numberInBinary.mapIndexed { index, c ->
            when (mask[index]) {
                '1' -> '1'
                'X' -> 'X'
                else -> c
            }
        }

        val countX = result.count { it == 'X' }

        fun combinations(size: Int) = sequence {
            val maxNumber = (2.0.pow(size)).toInt()

            yieldAll(List(maxNumber) { it })
        }

        fun bits(number: Int, length: Int) = sequence {
            yieldAll(number.toString(2).padStart(length, '0').toList())
        }

        return combinations(countX).toList().map { combination ->
            val bitGenerator = bits(combination, countX).iterator()

            result
                .map {
                    when (it) {
                        'X' -> bitGenerator.next()
                        else -> it
                    }
                }
                .joinToString("")
                .toLong(2)
        }
    }

    fun part1(input: List<String>): Long {
        var currentMask: Mask = listOf()
        val memory = mutableMapOf<String, Long>()

        input.forEach {
            when (it.substring(0, 3)) {
                "mas" -> currentMask = initializeMask(it)
                "mem" -> {
                    val (address, value) = it.split(" = ")
                    val number = value.toLong()

                    memory[address] = applyMask(number, currentMask)
                }
            }
        }

        return memory.values.sumOf { it }
    }

    fun part2(input: List<String>): Long {
        var currentMask: List<Char> = listOf()
        val memory = mutableMapOf<Long, Long>()

        input.forEach {
            when (it.substring(0, 3)) {
                "mas" -> currentMask = it.substring(7, it.length).toList()
                "mem" -> {
                    val (addressString, value) = it.split(" = ")
                    val address = addressString
                        .substring(4, addressString.length - 1)
                        .toLong()

                    val addresses = applyMask2(address, currentMask)
                    addresses.forEach { memory[it] = value.toLong() }
                }
            }
        }

        return memory.values.sumOf { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2020/2020_14_test")
    check(part1(testInput) == 165L)
    val testInput2 = readInput("2020/2020_14_test_2")
    check(part2(testInput2) == 208L)

    val input = readInput("2020/2020_14")
    part1(input).println()
    part2(input).println()
}
