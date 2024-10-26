package helpers.point

import kotlin.math.abs

data class Point3D(var x: Int, var y: Int, var z: Int) {
  fun absoluteSum() = abs(x) + abs(y) + abs(z)

  operator fun plus(point: Point3D): Point3D {
    return Point3D(x + point.x, y + point.y, z + point.z)
  }

  operator fun set(field: String, value: Int) {
    when (field) {
      "x" -> x = value
      "y" -> y = value
      "z" -> z = value
      else -> throw Exception("Unknown field - $field")
    }
  }

  operator fun get(field: String): Int {
    return when (field) {
      "x" -> x
      "y" -> y
      "z" -> z
      else -> throw Exception("Unknown field - $field")
    }
  }

  override fun toString() = String.format("%d, %d, %d", x, y, z)
}
