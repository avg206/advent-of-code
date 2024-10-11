import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
  .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
  .toString(16)
  .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

/**
 * Generate all possible permutations of the list
 */
fun permutations(list: IntRange) = sequence {
  val stack = mutableListOf(Pair(listOf<Int>(), list.toSet()))

  while (stack.isNotEmpty()) {
    val (current, rest) = stack.removeLast()

    if (rest.isEmpty()) yield(current)
    else for (item in rest) stack.add(Pair(current + item, rest - item))
  }
}

/*
 * Memorization
 */
interface Memo1<A, R> { // 1
  fun recurse(a: A): R
}

// 2
fun <A, R> (Memo1<A, R>.(A) -> R).memoize(): (A) -> R {
  val memoized = object : Memoized1<A, R>() { // 3
    override fun Memo1<A, R>.function(a: A): R = this@memoize(a)
  }
  return { a -> // 4
    memoized.execute(a)
  }
}

abstract class Memoized1<A, R> { // 5
  private val cache = mutableMapOf<A, R>()
  private val memo = object : Memo1<A, R> {
    override fun recurse(a: A): R = cache.getOrPut(a) { function(a) }
  }

  protected abstract fun Memo1<A, R>.function(a: A): R

  fun execute(a: A): R = memo.recurse(a)
}

interface Memo2<A, B, R> {
  fun recurse(a: A, b: B): R
}

abstract class Memoized2<A, B, R> {
  private data class Input<A, B>(
    val a: A,
    val b: B
  )

  private val cache = mutableMapOf<Input<A, B>, R>()
  private val memo = object : Memo2<A, B, R> {
    override fun recurse(a: A, b: B): R =
      cache.getOrPut(Input(a, b)) { function(a, b) }
  }

  protected abstract fun Memo2<A, B, R>.function(a: A, b: B): R

  fun execute(a: A, b: B): R = memo.recurse(a, b)
}

fun <A, B, R> (Memo2<A, B, R>.(A, B) -> R).memoize(): (A, B) -> R {
  val memoized = object : Memoized2<A, B, R>() {
    override fun Memo2<A, B, R>.function(a: A, b: B): R = this@memoize(a, b)
  }
  return { a, b ->
    memoized.execute(a, b)
  }
}
