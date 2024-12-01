import kotlin.math.max

typealias HandMap = MutableMap<Char, Int>

data class Hand(val cards: String, val bid: Int)

fun main() {
  fun getHandMap(hand: Hand): MutableMap<Char, Int> {
    return hand.cards.toList().fold(mutableMapOf()) { acc, s ->
      acc[s] = (acc[s] ?: 0) + 1

      acc
    }
  }

  fun isFiveOfKind(map: HandMap) = map.size == 1

  fun isFourOfKing(map: HandMap): Boolean {
    return map.values.sorted() == listOf(1, 4)
  }

  fun isFullHouse(map: HandMap): Boolean {
    return map.values.sorted() == listOf(2, 3)
  }

  fun isThreeOfKind(map: HandMap): Boolean {
    return map.values.sorted() == listOf(1, 1, 3)
  }

  fun isTwoPairs(map: HandMap): Boolean {
    return map.values.sorted() == listOf(1, 2, 2)
  }

  fun isOnePair(map: HandMap) = map.size == 4

  fun mapToType(map: HandMap) = when {
    isFiveOfKind(map) -> 7
    isFourOfKing(map) -> 6
    isFullHouse(map) -> 5
    isThreeOfKind(map) -> 4
    isTwoPairs(map) -> 3
    isOnePair(map) -> 2
    else -> 1
  }

  fun part1(input: List<String>): Int {
    fun strengthOfCard(card: Char) = when (card) {
      'A' -> 13
      'K' -> 12
      'Q' -> 11
      'J' -> 10
      'T' -> 9
      else -> card.digitToInt() - 1
    }

    fun handType(hand: Hand) = mapToType(getHandMap(hand))

    val comparator = object : Comparator<Hand> {
      override fun compare(p0: Hand, p1: Hand): Int {
        val p0Rank = handType(p0)
        val p1Rank = handType(p1)

        if (p0Rank == p1Rank) {
          for (i in p0.cards.indices) {
            val diff = strengthOfCard(p0.cards[i]) - strengthOfCard(p1.cards[i])

            if (diff != 0) {
              return diff
            }
          }

          return 0
        }

        return p0Rank - p1Rank
      }
    }

    val hands = input
      .map {
        val (hand, bid) = it.split(" ")
        Hand(hand, bid.toInt())
      }
      .sortedWith(comparator)

    return hands.foldIndexed(0) { index, acc, hand -> acc + (index + 1) * hand.bid }
  }

  fun part2(input: List<String>): Int {
    fun strengthOfCard(card: Char): Int {
      return when (card) {
        'A' -> 13
        'K' -> 12
        'Q' -> 11
        'J' -> 1
        'T' -> 10
        else -> card.digitToInt()
      }
    }

    fun handType(hand: Hand): Int {
      val map = getHandMap(hand)

      if (map.containsKey('J')) {
        val numberOfJs = map['J']!!
        map.remove('J')

        var maxType = 0

        if (numberOfJs == 1) {
          for (i in map.keys) {
            val newMap = map.toMutableMap()
            newMap[i] = (newMap[i] ?: 0) + 1

            maxType = max(maxType, mapToType(newMap))
          }
        }

        if (numberOfJs == 2) {
          for (i in map.keys) {
            for (j in map.keys) {
              val newMap = map.toMutableMap()
              newMap[i] = (newMap[i] ?: 0) + 1
              newMap[j] = (newMap[j] ?: 0) + 1

              maxType = max(maxType, mapToType(newMap))
            }
          }
        }

        if (numberOfJs == 3) {
          return when (map.values.size) {
            1 -> 7 // can build 5 of kind
            else -> 6  // can build 4 of kind
          }
        }

        if (numberOfJs == 4 || numberOfJs == 5) {
          return 7 // can build 5 of kind
        }

        return maxType
      }

      return mapToType(map)
    }

    val comparator = object : Comparator<Hand> {
      override fun compare(p0: Hand, p1: Hand): Int {
        val p0Rank = handType(p0)
        val p1Rank = handType(p1)

        if (p0Rank == p1Rank) {
          for (i in p0.cards.indices) {
            val diff = strengthOfCard(p0.cards[i]) - strengthOfCard(p1.cards[i])

            if (diff != 0) {
              return diff
            }
          }

          return 0
        }

        return p0Rank - p1Rank
      }
    }

    val hands = input
      .map {
        val (hand, bid) = it.split(" ")
        Hand(hand, bid.toInt())
      }
      .sortedWith(comparator)

//        hands.forEach {
//            println("$it - ${handType(it)}")
//        }

    return hands.foldIndexed(0) { index, acc, hand -> acc + (index + 1) * hand.bid }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2023/2023_07_test")
  check(part1(testInput) == 6_440)
  check(part2(testInput) == 5_905)

  val input = readInput("2023/2023_07")
  check(part1(input) == 251_545_216)
  check(part2(input) == 250_384_185)
  part1(input).println()
  part2(input).println()
}
