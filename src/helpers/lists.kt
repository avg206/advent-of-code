fun <E> List<E>.sequences(): List<Pair<E, Int>> {
  val result = mutableListOf<Pair<E, Int>>()
  var current = 1

  for (i in 1 until this.size) {
    if (this[i] == this[i - 1]) {
      current += 1
    } else {
      result += this[i] to current
      current = 1
    }
  }

  result += this.last() to current

  return result
}

fun <E> List<E>.longestSequence() = this.sequences().maxBy { it.second }
