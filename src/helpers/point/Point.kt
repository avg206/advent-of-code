package helpers.point

data class Point(val x: Int, val y: Int) {
    operator fun plus(point: Point): Point {
        return Point(x + point.x, y + point.y)
    }

    operator fun times(times: Int): Point {
        return Point(x * times, y * times)
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


//operator fun <E> List<List<E>>.get(point: Point) = this[point.y][point.x]
//operator fun <E> List<MutableList<E>>.set(point: Point, value: E) {
//    this[point.y][point.x] = value
//}

