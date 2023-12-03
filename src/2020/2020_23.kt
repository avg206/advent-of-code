import java.util.Collections

fun main() {
    class Cup(val value: Int) {
        var next: Cup? = null
    }

    fun simulate(table: List<Int>, times: Int): List<Int> {
        val cupsLookup = List<Cup?>(table.size + 1) { null }.toMutableList()

        var head = Cup(table[0])
        cupsLookup[head.value] = head

        var tail = head

        table.subList(1, table.size).forEach {
            val cup = Cup(it)
            cup.next = head

            cupsLookup[cup.value] = cup

            tail.next = cup
            tail = cup
        }

        for (i in 1..times) {
            val current = head
            val numberOne = head.next
            val numberTwo = head.next!!.next
            val numberThree = head.next!!.next!!.next

            head.next = numberThree!!.next

            var targetValue = current.value

            while ((targetValue == numberOne!!.value)
                || (targetValue == numberTwo!!.value)
                || (targetValue == numberThree.value)
                || (targetValue == current.value)
            ) {
                targetValue -= 1

                if (targetValue == 0) {
                    targetValue = table.size
                }
            }

            val target = cupsLookup[targetValue]

            numberThree.next = target!!.next
            target.next = numberOne

            tail = current
            head = current.next!!
        }

        val finalList = listOf<Int>().toMutableList()
        var pointer: Cup? = head

        while (pointer != tail && pointer != null) {
            finalList.add(pointer.value)

            pointer = pointer.next
        }

        finalList.add(tail.value)

        return finalList
    }

    fun part1(input: String, times: Int): String {
        val table = input.toList().map { it.digitToInt() }

        val newTable = simulate(table, times)
        Collections.rotate(newTable, newTable.size - newTable.indexOf(1))

        return newTable.filter { it != 1 }.joinToString("")
    }

    fun part2(input: String, times: Int): Long {
        val cups = 1_000_000

        val initialTable = input.toList().map { it.digitToInt() }
        val additionalTable = List(cups - initialTable.size) { it + initialTable.size + 1 }

        val table = buildList { addAll(initialTable); addAll(additionalTable) }

        val newTable = simulate(table, times)
        Collections.rotate(newTable, newTable.size - newTable.indexOf(1))

        return newTable[1].toLong() * newTable[2].toLong()
    }

    // test if implementation meets criteria from the description, like:
    check(part1("389125467", 10) == "92658374")
    check(part1("389125467", 100) == "67384529")
    check(part1("614752839", 100) == "89372645")

    check(part2("389125467", 10_000_000) == 149_245_887_792L)

    part1("614752839", 100).println()
    part2("614752839", 10000000).println()
}
