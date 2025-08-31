package leetcode

import println
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.math.max

fun main() {
  fun isValidSudoku(board: Array<CharArray>): Boolean {
    fun checker(indices: List<Pair<Int, Int>>): Boolean {
      val values = indices.map { board[it.first][it.second] }.filter { it != '.' }

      return values.size == values.distinct().size
    }

    val cases = buildList<List<Pair<Int, Int>>> {
      addAll((0..8).map { row -> (0..8).map { row to it } })
      addAll((0..8).map { col -> (0..8).map { it to col } })

      (0..8 step 3).forEach { row ->
        (0..8 step 3).forEach { col ->

          add((0..2).flatMap { i -> (0..2).map { j -> (row + i) to (col + j) } })
        }
      }
    }

    cases.joinToString("\r\n").println()
    println("---")

    return cases.all { checker(it) }
  }

  expectThat(
    isValidSudoku(
      board =
        arrayOf(
          charArrayOf('5', '3', '.', '.', '7', '.', '.', '.', '.'),
          charArrayOf('6', '.', '.', '1', '9', '5', '.', '.', '.'),
          charArrayOf('.', '9', '8', '.', '.', '.', '.', '6', '.'),
          charArrayOf('8', '.', '.', '.', '6', '.', '.', '.', '3'),
          charArrayOf('4', '.', '.', '8', '.', '3', '.', '.', '1'),
          charArrayOf('7', '.', '.', '.', '2', '.', '.', '.', '6'),
          charArrayOf('.', '6', '.', '.', '.', '.', '2', '8', '.'),
          charArrayOf('.', '.', '.', '4', '1', '9', '.', '.', '5'),
          charArrayOf('.', '.', '.', '.', '8', '.', '.', '7', '9')
        )
    )
  ).isEqualTo(true)

  expectThat(
    isValidSudoku(
      board =
        arrayOf(
          charArrayOf('8', '3', '.', '.', '7', '.', '.', '.', '.'),
          charArrayOf('6', '.', '.', '1', '9', '5', '.', '.', '.'),
          charArrayOf('.', '9', '8', '.', '.', '.', '.', '6', '.'),
          charArrayOf('8', '.', '.', '.', '6', '.', '.', '.', '3'),
          charArrayOf('4', '.', '.', '8', '.', '3', '.', '.', '1'),
          charArrayOf('7', '.', '.', '.', '2', '.', '.', '.', '6'),
          charArrayOf('.', '6', '.', '.', '.', '.', '2', '8', '.'),
          charArrayOf('.', '.', '.', '4', '1', '9', '.', '.', '5'),
          charArrayOf('.', '.', '.', '.', '8', '.', '.', '7', '9')
        )
    )
  ).isEqualTo(false)

  println("Finished")
}

