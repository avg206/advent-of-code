package leetcode

import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.util.ArrayDeque

fun main() {
  fun matchPlayersAndTrainers(players: IntArray, trainers: IntArray): Int {
    players.sortDescending()
    trainers.sortDescending()

    var answer = 0
    val availableTrainers = ArrayDeque(trainers.toList())

    for (player in players) {
      if (player <= (availableTrainers.firstOrNull() ?: 0)) {
        answer += 1
        availableTrainers.removeFirst()
      }
    }

    return answer
  }

  expectThat(
    matchPlayersAndTrainers(
      players = intArrayOf(4,7,9), trainers = intArrayOf(8,2,5,8)
    )
  ).isEqualTo(2)

  expectThat(
    matchPlayersAndTrainers(
      players = intArrayOf(1,1,1), trainers = intArrayOf(10)
    )
  ).isEqualTo(1)

  println("Finished")
}

