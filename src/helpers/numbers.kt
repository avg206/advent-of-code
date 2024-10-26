package helpers

// Function to calculate the Greatest Common Divisor (GCD) using the Euclidean algorithm
fun gcd(a: Long, b: Long): Long {
  var x = a
  var y = b

  while (y != 0L) {
    val temp = y
    y = x % y
    x = temp
  }

  return x
}

fun gcd(a: Int, b: Int) = gcd(a.toLong(), b.toLong())

// Function to calculate the Least Common Multiple (LCM)
fun lcm(a: Long, b: Long): Long {
  return (a * b) / gcd(a, b)
}

fun lcm(a: Int, b: Int) = lcm(a.toLong(), b.toLong())

