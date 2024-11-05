package helpers.point

enum class Direction {
  Up, Left, Right, Down;

  fun moveSpace() = when (this) {
    Up -> Point(1, 0)
    Right -> Point(0, 1)
    Down -> Point(-1, 0)
    Left -> Point(0, -1)
  }

  fun moveList() = when (this) {
    Up -> Point(-1, 0)
    Left -> Point(0, -1)
    Right -> Point(0, 1)
    Down -> Point(1, 0)
  }

  fun left() = when (this) {
    Up -> Point(0, -1)
    Left -> Point(1, 0)
    Right -> Point(-1, 0)
    Down -> Point(0, 1)
  }

  fun right() = when (this) {
    Up -> Point(0, 1)
    Left -> Point(-1, 0)
    Right -> Point(1, 0)
    Down -> Point(0, -1)
  }

  fun toLeft() = when (this) {
    Up -> Left
    Left -> Down
    Right -> Up
    Down -> Right
  }

  fun toRight() = when (this) {
    Up -> Right
    Left -> Up
    Right -> Down
    Down -> Left
  }
}
