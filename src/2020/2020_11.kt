fun main() {
  data class Position(val x: Int, val y: Int) {
    operator fun plus(newPosition: Position): Position {
      return Position(x + newPosition.x, y + newPosition.y)
    }
  }

  fun printWorld(n: Int, m: Int, seats: Set<Position>, occupiedSeats: Set<Position>) {
    println("----")
    for (i in 0 until n) {
      for (j in 0 until m) {
        val seat = Position(i, j)
        when (occupiedSeats.contains(seat)) {
          true -> print("#")
          false -> {
            when (seats.contains(seat)) {
              true -> print("L")
              false -> print(".")
            }
          }
        }
      }

      println("")
    }
  }

  val moves = buildList {
    add(Position(-1, -1))
    add(Position(-1, 0))
    add(Position(-1, 1))
    add(Position(0, 1))
    add(Position(0, -1))
    add(Position(1, 0))
    add(Position(1, 1))
    add(Position(1, -1))
  }

  fun simulateRound(
    seats: Set<Position>,
    occupiedSeats: Set<Position>,
    acceptableNumberOfSeats: Int,
    calculateNearbyOccupiedSeats: (seat: Position, seats: Set<Position>, occupiedSeats: Set<Position>) -> Int
  ): Set<Position> = seats.fold(mutableSetOf()) { acc, seat ->
    val occupiedSeatsNearby = calculateNearbyOccupiedSeats(seat, seats, occupiedSeats)

    when (occupiedSeats.contains(seat)) {
      false -> {
        if (occupiedSeatsNearby == 0) {
          acc.add(seat)
        }
      }

      true -> {
        if (occupiedSeatsNearby < acceptableNumberOfSeats) {
          acc.add(seat)
        }
      }
    }

    return@fold acc
  }

  fun solve(
    input: List<String>,
    acceptableNumberOfSeats: Int,
    calculateNearbyOccupiedSeats: (seat: Position, seats: Set<Position>, occupiedSeats: Set<Position>) -> Int
  ): Set<Position> {
    val seats = mutableSetOf<Position>()
    var occupiedSeats = setOf<Position>()

    for (i in input.indices) {
      for (j in input[0].indices) {
        if (input[i][j] == 'L') {
          seats.add(Position(i, j))
        }
      }
    }

    while (true) {
      val newOccupiedSeats =
        simulateRound(seats, occupiedSeats, acceptableNumberOfSeats, calculateNearbyOccupiedSeats)

      if (newOccupiedSeats == occupiedSeats) {
        return newOccupiedSeats
      }

      occupiedSeats = newOccupiedSeats
    }
  }


  fun part1(input: List<String>): Int {
    fun calculateNearbyOccupiedSeats(seat: Position, seats: Set<Position>, occupiedSeats: Set<Position>) =
      moves.count { move ->
        val checkSeat = seat + move
        occupiedSeats.contains(checkSeat)
      }

    val finalSeats = solve(input, 4) { seat, seats, occupiedSeats ->
      calculateNearbyOccupiedSeats(seat, seats, occupiedSeats)
    }

    return finalSeats.size
  }

  fun part2(input: List<String>): Int {
    val n = input.size
    val m = input[0].length

    fun calculateNearbyOccupiedSeats(seat: Position, seats: Set<Position>, occupiedSeats: Set<Position>) =
      moves.count { move ->
        var checkSeat = seat + move

        while (checkSeat.x in 0..n && checkSeat.y in 0..m) {
          if (occupiedSeats.contains(checkSeat)) {
            return@count true
          }

          if (seats.contains(checkSeat)) {
            return@count false
          }

          checkSeat += move
        }

        return@count false
      }

    val finalSeats = solve(input, 5) { seat, seats, occupiedSeats ->
      calculateNearbyOccupiedSeats(seat, seats, occupiedSeats)
    }

    return finalSeats.size
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2020/2020_11_test")
  check(part1(testInput) == 37)
  check(part2(testInput) == 26)

  val input = readInput("2020/2020_11")
  part1(input).println()
  part2(input).println()
}
