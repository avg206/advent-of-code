package helpers.graph

import java.util.*

fun <Key, Value> bfs(
  start: List<Pair<Key, Value>>,
  target: Key,
  comparator: Comparator<Pair<Key, Value>>,
  nextSteps: (from: Pair<Key, Value>) -> List<Pair<Key, Value>>
): Pair<Key, Value>? {
  val queue = PriorityQueue(comparator)
  queue.addAll(start)
  val visited = mutableSetOf<Key>()

  while (queue.isNotEmpty()) {
    val (key, value) = queue.poll()

    if (key == target) return key to value

    if (key in visited) continue
    visited.add(key)

    queue.addAll(nextSteps(key to value))
  }

  return null
}
