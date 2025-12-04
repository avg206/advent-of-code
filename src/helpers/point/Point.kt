package helpers.point

import kotlin.math.abs

data class Point(val x: Int, val y: Int) {
  operator fun plus(point: Point): Point {
    return Point(x + point.x, y + point.y)
  }

  operator fun times(times: Int): Point {
    return Point(x * times, y * times)
  }

  fun neighbours() = listOf(
    Point(x + 1, y), Point(x - 1, y), Point(x, y + 1), Point(x, y - 1)
  )

  fun extendedNeighbours() = listOf(
    Point(x + 1, y), Point(x - 1, y), Point(x, y + 1), Point(x, y - 1),
    Point(x + 1, y + 1), Point(x + 1, y - 1), Point(x - 1, y - 1), Point(x - 1, y + 1)
  )

  fun within2DArray(array: List<List<*>>) = x in array.indices && y in array[0].indices

  fun within2DArrayString(array: List<String>) = x in array.indices && y in array[0].indices

  fun manhattan(start: Point): Int {
    return abs(x - start.x) + abs(y - start.y)
  }
}

data class LongPoint(val x: Long, val y: Long) {
  operator fun plus(point: LongPoint): LongPoint {
    return LongPoint(x + point.x, y + point.y)
  }

  operator fun times(times: Long): LongPoint {
    return LongPoint(x * times, y * times)
  }
}


operator fun <E> List<List<E>>.get(point: Point) = this[point.x][point.y]

operator fun List<String>.get(point: Point) = this[point.x][point.y]

operator fun <E> List<MutableList<E>>.set(point: Point, value: E) {
  this[point.x][point.y] = value
}

