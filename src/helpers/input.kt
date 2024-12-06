package helpers

/**
 * Read grid input list
 */
fun gridReader(input: List<String>, processor: (char: Char, row: Int, column: Int) -> Unit) {
  input.forEachIndexed { i, row ->
    row.forEachIndexed { j, char ->
      processor(char, i, j)
    }
  }
}
