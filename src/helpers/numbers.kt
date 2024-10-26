package helpers

// Function to calculate the Greatest Common Divisor (GCD) using the Euclidean algorithm
fun gcd(a: Long, b: Long): Long {
  if (b == 0L) return a
  return gcd(b, a % b)
}

fun gcd(a: Int, b: Int) = gcd(a.toLong(), b.toLong())

// Function to calculate the Least Common Multiple (LCM)
fun lcm(a: Long, b: Long): Long {
  return a / gcd(a, b) * b
}

fun lcm(a: Int, b: Int) = lcm(a.toLong(), b.toLong())
