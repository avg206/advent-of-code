fun main() {
  data class Tile(var x: Int, var y: Int) {
    override fun toString(): String {
      return "$x - $y"
    }

    operator fun plus(tile: Tile): Tile {
      return Tile(x + tile.x, y + tile.y)
    }
  }

  val twoLetterDirections = setOf("se", "sw", "nw", "ne")

  fun calculateBlackTiles(input: List<String>): Set<Tile> {
    val memory = mapOf<Tile, Int>().toMutableMap()


    input.map { "$it-" }.forEach { line ->
      var i = 0
      val tile = Tile(0, 0)

      while (i < line.length - 1) {
        val twoLetters = line.substring(i..i + 1)

        if (twoLetters in twoLetterDirections) {
          when (twoLetters) {
            "se" -> {
              tile.y -= 1
              tile.x += 1
            }

            "sw" -> {
              tile.y -= 1
              tile.x -= 1
            }

            "ne" -> {
              tile.y += 1
              tile.x += 1
            }

            "nw" -> {
              tile.y += 1
              tile.x -= 1
            }

            else -> throw Exception()
          }

          i += 2
          continue
        }

        when (line[i]) {
          'e' -> tile.x += 2
          'w' -> tile.x -= 2
        }

        i += 1
      }

      memory[tile] = memory.getOrDefault(tile, 0) + 1
    }


    return memory.filter { it.value % 2 == 1 }.map { it.key }.toSet()
  }

  fun part1(input: List<String>): Int {
    val blackTiles = calculateBlackTiles(input)

    return blackTiles.size
  }

  val moves = listOf(
    Tile(2, 0),   // E
    Tile(-2, 0),  // W
    Tile(1, 1),   // NE
    Tile(1, -1),  // SE
    Tile(-1, 1),  // NW
    Tile(-1, -1), // SW
  )

  fun part2(input: List<String>, times: Int): Int {
    var blackTiles = calculateBlackTiles(input)

    for (time in 1..times) {
      val newBlackTiles = mutableSetOf<Tile>()
      val whiteTiles = mutableSetOf<Tile>()

      // Condition one
      blackTiles.forEach { tile ->
        val neighbors = moves.map { tile + it }
        val blackNeighborsCount = neighbors.count { it in blackTiles }

        whiteTiles.addAll(neighbors.filter { it !in blackTiles })

        if (blackNeighborsCount == 1 || blackNeighborsCount == 2) {
          newBlackTiles.add(tile)
        }
      }

      // Condition two
      whiteTiles.forEach { tile ->
        val blackNeighborsCount = moves.map { tile + it }.count { it in blackTiles }

        if (blackNeighborsCount == 2) {
          newBlackTiles.add(tile)
        }
      }

      blackTiles = newBlackTiles
    }

    return blackTiles.size
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2020/2020_24_test")
  check(part1(testInput) == 10)
  check(part2(testInput, 100) == 2208)

  val input = readInput("2020/2020_24")
  part1(input).println()
  part2(input, 100).println()
}
