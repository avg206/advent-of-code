data class Tile(val id: Int, val snapshot: List<List<Char>>)

enum class Side {
    TOP, BOTTOM, LEFT, RIGHT
}

fun main() {
    fun readTiles(input: List<String>): List<Tile> {
        val result = mutableListOf<Tile>()
        var i = 0


        while (i < input.size) {
            val id = input[i].substring(5..8).toInt()
            val snapshot = mutableListOf<List<Char>>()

            for (j in 1..10) {
                snapshot.add(input[i + j].toList())
            }

            result.add(Tile(id, snapshot))

            i += 12
        }

        return result
    }

    fun compareTiles(tileA: Tile, sideA: Side, tileB: Tile, sideB: Side): Boolean {
        fun prepareList(tile: Tile, side: Side) = sequence {
            when (side) {
                Side.TOP -> yieldAll(tile.snapshot.first())
                Side.BOTTOM -> yieldAll(tile.snapshot.last())
                Side.LEFT -> {
                    for (i in 0..9) {
                        yield(tile.snapshot[i].first())
                    }
                }

                Side.RIGHT -> {
                    for (i in 0..9) {
                        yield(tile.snapshot[i].last())
                    }
                }

                else -> throw Exception()
            }
        }

        val a = prepareList(tileA, sideA)
        val b = prepareList(tileB, sideB)

        val set = setOf(
            a.take(10).toList().joinToString(""),
            a.take(10).toList().joinToString("").reversed(),
            b.take(10).toList().joinToString(""),
            b.take(10).toList().joinToString("").reversed(),
        )

        return set.size != 4
    }

    fun part1(input: List<String>): Long {
        val tiles = readTiles(input)

        var answer = 1L

        val corners = listOf(
            Pair(Side.TOP, Side.RIGHT),
            Pair(Side.RIGHT, Side.BOTTOM),
            Pair(Side.BOTTOM, Side.LEFT),
            Pair(Side.LEFT, Side.TOP),
        )

        for (tile in tiles) {
            println("-- ${tile.id}")

            cornerLoop@ for (corner in corners) {
                var match = false

                candidateLoop@ for (candidate in tiles) {
                    if (tile.id != candidate.id) {
                        for (candidateSide in Side.values()) {
                            if (compareTiles(tile, corner.first, candidate, candidateSide)) {
                                match = true

                                break@candidateLoop
                            }

                            if (compareTiles(tile, corner.second, candidate, candidateSide)) {
                                match = true

                                break@candidateLoop
                            }
                        }
                    }
                }

                if (!match) {
                    println("--- ${tile.id} - $corner")

                    answer *= tile.id.toLong()

                    break@cornerLoop
                }
            }
        }

        return answer
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("2020/Day20_test")
    check(part1(testInput) == 20899048083289L)

    val input = readInput("2020/Day20")
    part1(input).println()
    part2(input).println()
}
