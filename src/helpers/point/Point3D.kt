package helpers.point

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sqrt

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

  fun manhattan(start: Point3D): Int {
    return abs(x - start.x) + abs(y - start.y) + abs(z - start.z)
  }

  fun distanceLong(to: Point3D): Long {
    val dx = abs(x - to.x).toLong()
    val dy = abs(y - to.y).toLong()
    val dz = abs(z - to.z).toLong()

    return dx * dx + dy * dy + dz * dz
  }

  fun distance(start: Point3D): Double {
    val dx = abs(x - start.x).toDouble()
    val dy = abs(y - start.y).toDouble()
    val dz = abs(z - start.z).toDouble()

    val max = max(max(dx, dy), dz)

    if (max == 0.0) {
      return 0.0
    }

    val rX = dx / max
    val rY = dy / max
    val rZ = dz / max

    val result = sqrt(rX * rX + rY * rY + rZ * rZ)
    return result * max
  }

  override fun toString() = String.format("%d, %d, %d", x, y, z)
}
