fun main() {
  data class Brick(
    val id: Int,
    val x: IntRange,
    val y: IntRange,
    val z: IntRange,
  )

  val comparator = object : Comparator<Brick> {
    override fun compare(p0: Brick, p1: Brick): Int {
      if (p0.z.first == p1.z.first) {
        return p0.z.last - p1.z.last
      }

      return p0.z.first - p1.z.first
    }
  }

  fun fallBricks(bricks: List<Brick>): Pair<List<Brick>, Int> {
    val newBricks = bricks.toMutableList()
    val fallenBricks = mutableSetOf<Int>()

    for (i in newBricks.indices) {
      var canFall = true
      val brick = newBricks[i]
      val fallenBrick = Brick(
        brick.id,
        brick.x,
        brick.y,
        (brick.z.first - 1 until brick.z.last)
      )

      if (fallenBrick.z.first < 1) continue

      for (j in i - 1 downTo 0) {
        val current = newBricks[j]

        if (fallenBrick.z.intersect(current.z).isNotEmpty()) {
          if (brick.x.intersect(current.x).isNotEmpty() &&
            brick.y.intersect(current.y).isNotEmpty()
          ) {
            canFall = false
          }
        }

        if (fallenBrick.z.first - current.z.last > 10) {
          break
        }
      }

      if (canFall) {
        newBricks[i] = fallenBrick
        fallenBricks.add(fallenBrick.id)
      }
    }

    return Pair(newBricks, fallenBricks.size)
  }

  fun solve(input: List<String>): Pair<Int, Int> {
    var bricks = input.mapIndexed { index, line ->
      val (left, right) = line.split("~")
      val (x1, y1, z1) = left.split(",").map { it.toInt() }
      val (x2, y2, z2) = right.split(",").map { it.toInt() }

      Brick(index + 1, x1..x2, y1..y2, z1..z2)
    }

    bricks = bricks.sortedWith(comparator)

    while (true) {
      val (newBricks, fallenCount) = fallBricks(bricks)
      bricks = newBricks

      if (fallenCount == 0) break
    }

    bricks = bricks.sortedWith(comparator)

    var saveToRemove = 0
    var fallsCount = 0

    for (i in bricks.indices) {
//            println("Process brick $i")

      val brick = bricks[i]

      val (_, fallenCount) = fallBricks(bricks.filter { it.id != brick.id })

      when (fallenCount) {
        0 -> saveToRemove += 1
        else -> fallsCount += fallenCount
      }
    }

    println("Answer - ($saveToRemove, $fallsCount)")

    return Pair(saveToRemove, fallsCount)
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2023/2023_22_test")
  check(solve(testInput) == Pair(5, 7))

  val input = readInput("2023/2023_22")
  check(solve(input) == Pair(503, 98431))
}
