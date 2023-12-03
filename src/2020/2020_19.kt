fun main() {
    fun buildTree(rules: List<String>): Pair<Map<Int, List<List<Int>>>, Map<Int, String>> {
        val links = mutableMapOf<Int, List<List<Int>>>()
        val values = mutableMapOf<Int, String>()

        rules.sorted().map { it.split(": ") }.forEach { line ->
            val index = line[0].toInt()
            val rule = line[1]

            when (rule.contains('"')) {
                true -> values[index] = rule.substring(1..1)
                false -> links[index] = rule.split(" | ").map { set -> set.split(' ').map { it.toInt() } }
            }
        }

        return Pair(links, values)
    }

    fun buildVariants(links: Map<Int, List<List<Int>>>, values: Map<Int, String>, index: Int): List<String> {
        val value = values[index]
        val link = links[index]

        if (value != null) {
            return listOf(value)
        }

        if (link == null) {
            return listOf()
        }

        val variants = link
            .map { variant -> variant.map { buildVariants(links, values, it) } }
            .map { variant ->
                val results = mutableListOf<String>()

                when (variant.size) {
                    1 -> results.addAll(variant[0])

                    2 -> {
                        for (i in variant[0].indices) {
                            for (j in variant[1].indices) {
                                results.add(variant[0][i] + variant[1][j])
                            }
                        }
                    }

                    3 -> {
                        for (i in variant[0].indices) {
                            for (j in variant[1].indices) {
                                for (f in variant[2].indices) {
                                    results.add(variant[0][i] + variant[1][j] + variant[2][f])
                                }
                            }
                        }
                    }
                }

                results
            }
            .flatten()

        return variants
    }

    fun part1(input: List<String>): Int {
        val divider = input.indexOf("")

        val (links, values) = buildTree(input.subList(0, divider))
        val possibleValues = buildVariants(links, values, 0).toSet()

        return input.subList(divider + 1, input.size).count { possibleValues.contains(it) }
    }

    fun part2(input: List<String>): Int {
        val divider = input.indexOf("")

        val (links, values) = buildTree(input.subList(0, divider))

        val variants42 = buildVariants(links, values, 42).toSet()
        val variants31 = buildVariants(links, values, 31).toSet()

        val chunkSize = variants42.first().length

        fun check(stringToCheck: String): Boolean {
            val times = stringToCheck.length / chunkSize

            for (rule11Count in 2 until times step 2) {
                val rule8Count = times - rule11Count

                val pattern8 = List(rule8Count) { 42 }
                val pattern11 = List(rule11Count) { index -> if (index < rule11Count / 2) 42 else 31 }

                val pattern = pattern8 + pattern11

                val isValid = stringToCheck.chunked(chunkSize).mapIndexed { index, s ->
                    when (pattern[index]) {
                        42 -> variants42.contains(s)
                        31 -> variants31.contains(s)
                        else -> false
                    }
                }.all { it }

                if (isValid) {
                    return true
                }
            }

            return false
        }

        return input.subList(divider + 1, input.size).count { check(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2020/2020_19_test")
    check(part1(testInput) == 2)
    val testInput2 = readInput("2020/2020_19_test_2")
    check(part2(testInput2) == 12)

    println("-----")

    val input = readInput("2020/2020_19")
    check(part1(input) == 265)
    part1(input).println()
    val input2 = readInput("2020/2020_19_2")
    check(part2(input2) == 394)
    part2(input2).println()
}
