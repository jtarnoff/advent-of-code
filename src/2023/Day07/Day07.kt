package `2023`.Day07

import println
import readInput

const val dayString = "Day07"

fun main() {
    fun part1(input: List<String>): Int {
        val map = parseInput(input)
        map.println()

        val hands = map.map {
            it.key.map {
                CamelCard.from(it)
            } to it.value
        }
        hands.println()

        val handValues = hands.map {
            getCamelCardHand(it.first) to it.second
        }
        handValues.println()

        val sortedHandValues = hands.sortedWith(
            compareBy( { getCamelCardHand(it.first) },
                { it.first[0] },
                { it.first[1] },
                { it.first[2] },
                { it.first[3] },
                { it.first[4] },
            )
        )
        sortedHandValues.println()

        return sortedHandValues.reversed().mapIndexed { index, pair ->
            (index + 1) * pair.second
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val map = parseInput(input)
        map.println()

        val hands = map.map {
            it.key.map {
                CamelCardJoker.from(it)
            } to it.value
        }
        hands.println()

        val handValues = hands.map {
            getCamelCardHandJoker(it.first) to it.second
        }
        handValues.println()

        val newHands = hands.map {
            replaceJokers(it.first) to it.second
        }
        newHands.println()

        hands.forEach {
            replaceJokers(it.first).println()
        }

        hands.forEach {
            getCamelCardHandJoker(replaceJokers(it.first)).println()
        }

        val sortedHandValues = hands.sortedWith(
            compareBy( { getCamelCardHandJoker(replaceJokers(it.first)) },
                { it.first[0] },
                { it.first[1] },
                { it.first[2] },
                { it.first[3] },
                { it.first[4] },
            )
        )
        sortedHandValues.println()

        return sortedHandValues.reversed().mapIndexed { index, pair ->
            (index + 1) * pair.second
        }.sum()
    }

    val testInput = readInput("${dayString}_test")
    val input = readInput("$dayString")

//    part1(testInput).println()
//    part1(input).println()

//    part2(testInput).println()
    part2(input).println()
}

fun parseInput(input: List<String>): Map<String, Int> {
    return input.associate {
        it.substringBefore(' ') to it.substringAfter(' ').toInt()
    }
}

enum class CamelCard(val value: Char, val rank: Int) {
    ACE('A', 14),
    KING('K', 13),
    QUEEN('Q', 12),
    JACK('J', 11),
    TEN('T', 10),
    NINE('9', 9),
    EIGHT('8', 8),
    SEVEN('7', 7),
    SIX('6', 6),
    FIVE('5', 5),
    FOUR('4', 4),
    THREE('3', 3),
    TWO('2', 2);

    operator fun CamelCard.compareTo(other: CamelCard) : Int {
        return compareValues(this.rank, other.rank)
    }
    companion object {
        fun from(findValue: Char): CamelCard = CamelCard.values().first { it.value == findValue }
    }
}

enum class CamelCardJoker(val value: Char, val rank: Int) {
    ACE('A', 14),
    KING('K', 13),
    QUEEN('Q', 12),
    TEN('T', 10),
    NINE('9', 9),
    EIGHT('8', 8),
    SEVEN('7', 7),
    SIX('6', 6),
    FIVE('5', 5),
    FOUR('4', 4),
    THREE('3', 3),
    TWO('2', 2),
    JOKER('J', 1);

//    JOKER('J', 1),
//    TWO('2', 2),
//    THREE('3', 3),
//    FOUR('4', 4),
//    FIVE('5', 5),
//    SIX('6', 6),
//    SEVEN('7', 7),
//    EIGHT('8', 8),
//    NINE('9', 9),
//    TEN('T', 10),
//    QUEEN('Q', 12),
//    KING('K', 13),
//    ACE('A', 14);

    operator fun CamelCardJoker.compareTo(other: CamelCardJoker) : Int {
        return compareValues(this.rank, other.rank)
    }
    companion object {
        fun from(findValue: Char): CamelCardJoker = CamelCardJoker.values().first { it.value == findValue }
    }
}

data class Player(
    val hand: CamelCardsHand,
    val bid: Int
)

enum class CamelCardsHand(val rank: Int) {
//    HIGH_CARD(1),
//    PAIR(2),
//    TWO_PAIR(3),
//    THREE_OF_A_KIND(4),
//    FULL_HOUSE(5),
//    FOUR_OF_A_KIND(6),
//    FIVE_OF_A_KIND(7);

    FIVE_OF_A_KIND(7),
    FOUR_OF_A_KIND(6),
    FULL_HOUSE(5),
    THREE_OF_A_KIND(4),
    TWO_PAIR(3),
    PAIR(2),
    HIGH_CARD(1);

    operator fun CamelCardsHand.compareTo(other: CamelCardsHand) : Int {
        return compareValues(this.rank, other.rank)
    }

    companion object {
        fun from(hand: Map<CamelCard, Int>) =
            when {
                hand.values.any { it == 5 } -> FIVE_OF_A_KIND
                hand.values.any { it == 4 } -> FOUR_OF_A_KIND
                hand.values.any { it == 3 }
                        && hand.values.any { it == 2 } -> FULL_HOUSE
                hand.values.any { it == 3 } -> THREE_OF_A_KIND
                hand.values.count { it == 2 } == 2 -> TWO_PAIR
                hand.values.any { it == 2 } -> PAIR
                else -> HIGH_CARD
            }

        fun fromJokerHand(hand: Map<CamelCardJoker, Int>) =
            when {
                hand.values.any { it == 5 } -> FIVE_OF_A_KIND
                hand.values.any { it == 4 } -> FOUR_OF_A_KIND
                hand.values.any { it == 3 }
                        && hand.values.any { it == 2} -> FULL_HOUSE
                hand.values.any { it == 3 } -> THREE_OF_A_KIND
                hand.values.count { it == 2 } == 2 -> TWO_PAIR
                hand.values.any { it == 2 } -> PAIR
                else -> HIGH_CARD
            }
    }
}

fun getCamelCardHand(hand: List<CamelCard>): CamelCardsHand {
    val handMap = CamelCard.values().associateWith { card -> hand.count { it == card } }
    return CamelCardsHand.from(handMap)
}

fun getCamelCardHandJoker(hand: List<CamelCardJoker>): CamelCardsHand {
    val handMap = CamelCardJoker.values().associateWith { card -> hand.count { it == card } }
    return CamelCardsHand.fromJokerHand(handMap)
}

fun replaceJokers(hand: List<CamelCardJoker>): List<CamelCardJoker> {
    // get highest card in hand
    val handWithCounts = hand.associateWith { card ->
        hand.count { it == card }
    }.map {
        it.key to if (it.key == CamelCardJoker.JOKER) 0 else it.value
    }

    val t = handWithCounts.toList().sortedWith(
        compareBy(
            { it.second },
            { it.first.rank }
        )
    )
    t.println()

    return hand.map { card ->
        if (card == CamelCardJoker.JOKER) t.last().first else card
    }
}