fun main() {
    fun calculateMaxColumn(map: List<String>): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()

        for (i in 0 until map[0].length - 1) {
            var count = 0

            for (gap in map[0].indices) {
                val left = i - gap
                val right = i + 1 + gap

                if (left !in map[0].indices || right !in map[0].indices) {
                    break
                }

                var isEqual = true

                for (j in map.indices) {
                    if (map[j][left] != map[j][right]) {
                        isEqual = false
                    }
                }

                if (isEqual) {
                    count += 1
                } else {
                    break
                }
            }


            if (count > 0 && (count + i + 1 == map[0].length || i - count == -1)) {
                result.add(Pair(i, count))
            }
        }

        return result
    }

    fun calculateMaxRow(map: List<String>): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()

        for (i in 0 until map.size - 1) {
            var count = 0

            for (gap in map.indices) {
                val left = i - gap
                val right = i + 1 + gap

                if (left !in map.indices || right !in map.indices) {
                    break
                }

                var isEqual = true

                for (j in map[0].indices) {
                    if (map[left][j] != map[right][j]) {
                        isEqual = false
                    }
                }

                if (isEqual) {
                    count += 1
                } else {
                    break
                }
            }

            if (count > 0 && (count + i + 1 == map.size || i - count == -1)) {
                result.add(Pair(i, count))
            }
        }

        return result
    }

    fun part1(input: List<String>): Int {
        fun processMap(map: List<String>): Int {
            val columns = calculateMaxColumn(map)
            val rows = calculateMaxRow(map)

            if (columns.isNotEmpty()) {
                return columns[0].first + 1
            }

            if (rows.isNotEmpty()) {
                return (rows[0].first + 1) * 100
            }

            throw Exception("nothing found")
        }

        var map = input
        var result = 0


        while (map.contains("")) {
            val subMap = map.slice(0 until map.indexOf(""))
            map = map.subList(map.indexOf("") + 1, map.size)

            result += processMap(subMap)
        }

        result += processMap(map)

        return result
    }

    fun part2(input: List<String>): Int {
        fun processMap(map: List<String>): Int {
            val tmp = map.map { it.toMutableList() }.toMutableList()

            val columns = calculateMaxColumn(map)
            val rows = calculateMaxRow(map)

            val existingColumn = columns.firstOrNull()?.first ?: -1
            val existingRow = rows.firstOrNull()?.first ?: -1

            for (i in tmp.indices) {
                for (j in tmp[0].indices) {
                    val current = tmp[i][j]
                    tmp[i][j] = when (current) {
                        '#' -> '.'
                        '.' -> '#'
                        else -> throw Exception("unknown symbol")
                    }

                    val newColumns = calculateMaxColumn(tmp.map { it.joinToString("") })
                    val newRows = calculateMaxRow(tmp.map { it.joinToString("") })

                    if (newColumns != columns && newColumns.isNotEmpty()) {
                        return newColumns.find { it.first != existingColumn }!!.first + 1
                    }

                    if (newRows != rows && newRows.isNotEmpty()) {
                        return (newRows.find { it.first != existingRow }!!.first + 1) * 100
                    }

                    tmp[i][j] = current
                }
            }

            throw Exception("nothing found")
        }

        var map = input
        var result = 0


        while (map.contains("")) {
            val subMap = map.slice(0 until map.indexOf(""))
            map = map.subList(map.indexOf("") + 1, map.size)

            result += processMap(subMap)
        }

        result += processMap(map)

        return result
    }

    // Part 1
    // 33052
    // 37996
    // 33735

    // Part 2
    // 24885
    // 38063

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2023/2023_13_test")
    check(part1(testInput) == 405)
    check(part2(testInput) == 400)

    val input = readInput("2023/2023_13")
    check(part1(input) == 33735)
    check(part2(input) == 38063)
    part1(input).println()
    part2(input).println()
}
