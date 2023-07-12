fun main() {
    fun calculateExpression(expression: String, priority: (Char) -> Int): Long {
        val trimmed = "(${expression.replace(Regex(" "), "")})"

        val values = mutableListOf<Long>()
        val operations = mutableListOf<Char>()

        fun processOperation(operation: Char) {
            val a = values.removeLast()
            val b = values.removeLast()

            when (operation) {
                '+' -> values.add(a + b)
                '*' -> values.add(a * b)
            }
        }

        for (i in trimmed.indices) {
            when (trimmed[i]) {
                '(' -> operations.add('(')
                ')' -> {
                    while (operations.last() != '(') {
                        processOperation(operations.removeLast())
                    }

                    operations.removeLast()
                }

                '+', '*' -> {
                    val operation = trimmed[i]

                    while (priority(operations.last()) >= priority(operation)) {
                        processOperation(operations.removeLast())
                    }

                    operations.add(operation)
                }

                else -> values.add(trimmed[i].digitToInt().toLong())
            }
        }

        return values.first()
    }

    fun part1(input: List<String>): Long {
        fun priority(operation: Char) = when (operation) {
            '+', '*' -> 1
            else -> -1
        }

        return input.sumOf { calculateExpression(it) { operation -> priority(operation) } }
    }

    fun part2(input: List<String>): Long {
        fun priority(operation: Char) = when (operation) {
            '+' -> 2
            '*' -> 1
            else -> -1
        }

        return input.sumOf { calculateExpression(it) { operation -> priority(operation) } }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2020/Day18_test")
    check(part1(testInput) == 26_335L)

    val input = readInput("2020/Day18")
    check(part1(input) == 36_382_392_389_406L)
    part1(input).println()
    part2(input).println()
}
