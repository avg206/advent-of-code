package helpers.point

data class Point(val x: Int, val y: Int) {
    operator fun plus(point: Point): Point {
        return Point(x + point.x, y + point.y)
    }

    operator fun times(times: Int): Point {
        return Point(x * times, y * times)
    }

    fun neighbours() = listOf(
        Point(x + 1, y),
        Point(x - 1, y),
        Point(x, y + 1),
        Point(x, y - 1)
    )
}

data class LongPoint(val x: Long, val y: Long) {
    operator fun plus(point: LongPoint): LongPoint {
        return LongPoint(x + point.x, y + point.y)
    }

    operator fun times(times: Long): LongPoint {
        return LongPoint(x * times, y * times)
    }
}


//operator fun <E> List<List<E>>.get(point: Point) = this[point.x][point.y]
//
//operator fun <E> List<MutableList<E>>.set(point: Point, value: E) {
//    this[point.x][point.y] = value
//}
//
//operator fun <E> MutableList<MutableList<E>>.set(point: Point, value: E) {
//    this[point.x][point.y] = value
//}

