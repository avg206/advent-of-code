fun main() {
    data class Range(val from: Int, val to: Int) {
        fun inRange(number: Int) = number in from..to
    }

    fun readTicket(line: String): List<Int> = line.split(",").map { it.toInt() }
    fun readCondition(line: String): List<Range> {
        val (_, conditions) = line.split(": ")

        return conditions.split(" or ")
                .map { it.split("-").map { number -> number.toInt() } }
                .map { Range(it[0], it[1]) }
    }

    fun part1(input: List<String>): Int {
        val firstBreak = input.indexOf("")

        val fields = mutableListOf<List<Range>>()

        // Read rulles
        for (i in 0 until firstBreak) {
            val ranges = readCondition(input[i])

            fields.add(ranges)
        }

        var sum = 0

        // Read tickets
        for (i in (firstBreak + 5) until input.size) {
            val ticket = readTicket(input[i])

            val invalidNumber = ticket.find { number -> !fields.any { field -> field.any { it.inRange(number) } } }

            sum += invalidNumber ?: 0
        }

        return sum
    }

    fun part2(input: List<String>): Long {
        val firstBreak = input.indexOf("")

        val fields = mutableListOf<List<Range>>()

        // Read rulles
        for (i in 0 until firstBreak) {
            val ranges = readCondition(input[i])

            fields.add(ranges)
        }

        val myTicket = readTicket(input[firstBreak + 2])

        val tickets = input.subList(firstBreak + 5, input.size)
                .map { readTicket(it) }
                .filter { it.find { number -> !fields.any { field -> field.any { range -> range.inRange(number) } } } == null }
                .toMutableList()

        var distribution = List(fields.size) { index ->
            fields
                    .mapIndexed { innerIndex, field ->
                        when {
                            tickets.all { ticket -> field.any { it.inRange(ticket[index]) } } -> innerIndex
                            else -> null
                        }
                    }
                    .filterNotNull()
        }


        val positions = MutableList(fields.size) { -1 }

        for (i in fields.indices) {
            val indexWithOneField = distribution.indexOfLast { it.size == 1 }
            val fieldIndex = distribution[indexWithOneField][0]

            positions[indexWithOneField] = fieldIndex

            distribution = distribution.map { list -> list.filter { it != fieldIndex } }
        }

        return myTicket.mapIndexed { index, i ->
            when {
                positions[index] in 0..5 -> i
                else -> 1
            }
        }.fold(1L) { acc, i -> acc * i.toLong() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2020/Day16_test")
    check(part1(testInput) == 71)
    val testInput2 = readInput("2020/Day16_test_2")
    check(part2(testInput2) == 1716L)

    val input = readInput("2020/Day16")
    part1(input).println()
    part2(input).println()
}
