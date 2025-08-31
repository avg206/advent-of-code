package leetcode

fun main() {
  fun solveSudoku(mainBoard: Array<CharArray>): Unit {
    fun prettyPrint(board: Array<CharArray>) = println(board.joinToString("\r\n") { it.toList().joinToString(" ") })

    fun isFilled(board: Array<CharArray>) = (0..8).all { row -> (0..8).all { col -> board[row][col] != '.' } }

    fun mergeResult(target: Array<CharArray>, source: Array<CharArray>) {
      (0..8).forEach { row ->
        (0..8).forEach { col ->
          target[row][col] = source[row][col]
        }
      }
    }

    fun possibleValues(board: Array<CharArray>, row: Int, col: Int): Set<Char> {
      fun quadrantStart(value: Int) = when (value) {
        in 0..2 -> 0
        in 3..5 -> 3
        else -> 6
      }

      val values = ('1'..'9').toMutableSet();

      (0..8).forEach { values -= board[row][it] }
      (0..8).forEach { values -= board[it][col] }

      val x = quadrantStart(row)
      val y = quadrantStart(col)

      (0..2).forEach { i ->
        (0..2).forEach { j -> values -= board[x + i][y + j] }
      }

      return values
    }

    fun simpleSolver(board: Array<CharArray>) {
      while (true) {
        var success = false

        for (row in 0..8) {
          for (col in 0..8) {
            if (board[row][col] != '.') continue

            val values = possibleValues(board, row, col)

            if (values.size == 1) {
              board[row][col] = values.first()
              success = true
            }
          }
        }

        if (!success) break
      }
    }

    fun complexSolving(board: Array<CharArray>) {
      val emptyCells = (0..8).flatMap { row ->
        (0..8).map { col -> (row to col) to board[row][col] }.filter { it.second == '.' }
          .map { it.first to possibleValues(board, it.first.first, it.first.second) }
      }

      val hasUnsolvableCells = emptyCells.any { it.second.isEmpty() }
      if (hasUnsolvableCells || emptyCells.isEmpty()) return

      val (pos, values) = emptyCells.minBy { it.second.size }

      for (value in values) {
        val copy = board.map { it.copyOf() }.toTypedArray()
        copy[pos.first][pos.second] = value

        simpleSolver(copy)

        if (isFilled(copy)) {
          mergeResult(board, copy)

          println("Successful solved $pos")
          return
        }

        // Try another guess
        complexSolving(copy)

        if (isFilled(copy)) {
          mergeResult(board, copy)

          println("Successful solved $pos after complex solving")
          return
        }
      }
    }

    // Try straight-forward solving
    simpleSolver(mainBoard)

    // Solving with guess logic if needed
    complexSolving(mainBoard)

    // Render solved board
    println("Solved board:")
    prettyPrint(mainBoard)
    println("----")
  }

  solveSudoku(
    mainBoard =
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

  solveSudoku(
    mainBoard =
      arrayOf(
        charArrayOf('.', '.', '9', '7', '4', '8', '.', '.', '.'),
        charArrayOf('7', '.', '.', '.', '.', '.', '.', '.', '.'),
        charArrayOf('.', '2', '.', '1', '.', '9', '.', '.', '.'),
        charArrayOf('.', '.', '7', '.', '.', '.', '2', '4', '.'),
        charArrayOf('.', '6', '4', '.', '1', '.', '5', '9', '.'),
        charArrayOf('.', '9', '8', '.', '.', '.', '3', '.', '.'),
        charArrayOf('.', '.', '.', '8', '.', '3', '.', '2', '.'),
        charArrayOf('.', '.', '.', '.', '.', '.', '.', '.', '6'),
        charArrayOf('.', '.', '.', '2', '7', '5', '9', '.', '.')
      )
  )

  solveSudoku(
    mainBoard =
      arrayOf(
        charArrayOf('.', '.', '.', '.', '.', '.', '.', '.', '.'),
        charArrayOf('.', '9', '.', '.', '1', '.', '.', '3', '.'),
        charArrayOf('.', '.', '6', '.', '2', '.', '7', '.', '.'),
        charArrayOf('.', '.', '.', '3', '.', '4', '.', '.', '.'),
        charArrayOf('2', '1', '.', '.', '.', '.', '.', '9', '8'),
        charArrayOf('.', '.', '.', '.', '.', '.', '.', '.', '.'),
        charArrayOf('.', '.', '2', '5', '.', '6', '4', '.', '.'),
        charArrayOf('.', '8', '.', '.', '.', '.', '.', '1', '.'),
        charArrayOf('.', '.', '.', '.', '.', '.', '.', '.', '.')
      )
  )


  println("Finished")
}

