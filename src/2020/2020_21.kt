fun main() {
    fun solve(input: List<String>): Pair<Int, String> {
        val allergens = input.fold(mutableMapOf<String, MutableList<MutableList<String>>>()) { acc, line ->
            val (ingredients, allergens) = line.substring(0 until line.length - 1).split(" (contains ")

            allergens.split(", ").forEach { allergen ->
                if (allergen !in acc) {
                    acc[allergen] = mutableListOf()
                }

                acc[allergen]!!.add(ingredients.split(" ").toMutableList())
            }

            acc
        }

        val allergicIngredients = allergens.keys.fold(mutableSetOf<String>()) { acc, allergen ->
            val occurrences = mutableMapOf<String, Int>()
            val lists = allergens[allergen]!!

            lists.forEach { meal ->
                meal.forEach {
                    if (it !in occurrences) {
                        occurrences[it] = 0
                    }

                    occurrences[it] = occurrences[it]!! + 1
                }
            }

            acc.addAll(occurrences.keys.filter { occurrences[it] == lists.size })

            acc
        }

        val notAllergens = input.fold(mutableMapOf<String, Int>()) { acc, line ->
            val (ingredients, _) = line.split(" (contains ")

            ingredients
                .split(" ")
                .filter { it !in allergicIngredients }
                .forEach {
                    if (it !in acc) {
                        acc[it] = 0
                    }

                    acc[it] = acc[it]!! + 1;
                }

            acc
        }

        val identifiedAllergens = mutableMapOf<String, String>()

        while (true) {
            val potentialMatches =
                allergens.keys.fold(mutableMapOf<String, Set<String>>()) { acc, allergen ->
                    val occurrences = mutableMapOf<String, Int>()
                    val lists = allergens[allergen]!!

                    lists.forEach { meal ->
                        meal
                            .filter { it !in notAllergens.keys }
                            .filter { it !in identifiedAllergens.values }
                            .forEach {
                                if (it !in occurrences) {
                                    occurrences[it] = 0
                                }

                                occurrences[it] = occurrences[it]!! + 1
                            }
                    }

                    acc[allergen] = buildSet { addAll(occurrences.keys.filter { occurrences[it] == lists.size }) }

                    acc
                }

            val key = potentialMatches.keys.find { potentialMatches[it]!!.size == 1 } ?: break

            identifiedAllergens[key] = potentialMatches[key]!!.first()
        }

        val allergensList = identifiedAllergens.keys.sorted().map { identifiedAllergens[it] }.joinToString(",")


        return Pair(notAllergens.values.sum(), allergensList)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2020/2020_21_test")
    check(solve(testInput) == Pair(5, "mxmxvkd,sqjhc,fvjkl"))

    val input = readInput("2020/2020_21")
    solve(input).println()
}
