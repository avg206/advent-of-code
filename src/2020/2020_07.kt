fun main() {
    fun readBags(input: List<String>): List<Pair<String, List<Pair<String, Int>>>> {
        return input
                .map { string ->
                    val (name, contains) = string.dropLast(1).split(" bags contain ")

                    if (contains == "no other bags") {
                        Pair(name, listOf())
                    } else {
                        Pair(name, contains.split(", ").map { bag ->
                            val items = bag.split(" ")

                            Pair(items.drop(1).dropLast(1).joinToString(" "), items[0].toInt())
                        })
                    }
                }
    }

    fun part1(input: List<String>): Int {
        val graph = readBags(input).fold(mutableMapOf<String, MutableList<Pair<String, Int>>>()) { acc, pair ->
            pair.second.forEach {
                when (it.first in acc) {
                    true -> acc[it.first]?.add(Pair(pair.first, it.second))
                    false -> acc[it.first] = mutableListOf(Pair(pair.first, it.second))
                }
            }; acc
        }

        val queue = mutableListOf("shiny gold")
        val visited = mutableSetOf<String>()

        while (queue.isNotEmpty()) {
            val bag = queue.removeFirst()

            if (bag in visited) {
                continue
            }

            visited.add(bag)

            if (bag in graph) {
                graph[bag]?.forEach { queue.add(it.first) }
            }
        }

        return visited.size - 1
    }

    fun part2(input: List<String>): Int {
        val graph = readBags(input).fold(mutableMapOf<String, List<Pair<String, Int>>>()) { acc, pair ->
            acc[pair.first] = pair.second; acc
        }

        fun dfs(bag: String): Int {
            if (graph[bag]?.isEmpty() == true) {
                return 1
            }


            return (graph[bag]?.sumOf { it.second * dfs(it.first) } ?: 0) + 1
        }

        return dfs("shiny gold") - 1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2020/2020_07_test")
    check(part1(testInput) == 4)
    check(part2(testInput) == 32)

    val input = readInput("2020/2020_07")
    part1(input).println()
    part2(input).println()
}
