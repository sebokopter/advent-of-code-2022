import Outcome.*
import Shape.*

enum class Shape(val value: Int) {
    Rock(1), Paper(2), Scissors(3);
}

enum class Outcome(val points: Int) {
    Win(6), Lose(0), Draw(3);
}

val outcomeMap = mapOf(
    (Rock to Paper) to Win,
    (Paper to Scissors) to Win,
    (Scissors to Rock) to Win,
    (Paper to Rock) to Lose,
    (Scissors to Paper) to Lose,
    (Rock to Scissors) to Lose,
    (Paper to Paper) to Draw,
    (Scissors to Scissors) to Draw,
    (Rock to Rock) to Draw,
)

fun main() {
    // A|X Rock
    // B|Y Paper
    // C|Z Scissors
    fun charToShape(shape: String) = when (shape) {
        "A", "X" -> Rock
        "B", "Y" -> Paper
        "C", "Z" -> Scissors
        else -> error("$shape cannot be mapped to a Shape")
    }

    // X Lose
    // Y Draw
    // Z Win
    fun charToOutcome(outcome: String) = when (outcome) {
        "X" -> Lose
        "Y" -> Draw
        "Z" -> Win
        else -> error("$outcome cannot be mapped to a Outcome")
    }

    fun part1(input: List<String>): Int = input.sumOf { line ->
        val (opShape, myShape) = line.split(" ").map(::charToShape)
        val result = outcomeMap.getValue(opShape to myShape).points
        myShape.value + result
    }

    fun findOutMyShape(opShape: Shape, outcome: Outcome): Shape {
        val possibleGames = outcomeMap.filterValues { it == outcome }
        return possibleGames.keys.toMap().getValue(opShape)
    }

    fun part2(input: List<String>): Int = input.sumOf { line ->
        val (shapeChar, outcomeChar) = line.split(" ")
        val (opShape, outcome) = charToShape(shapeChar) to charToOutcome(outcomeChar)
        val myShape = findOutMyShape(opShape, outcome)
        myShape.value + outcome.points
    }

    val testInput = readInput("Day02_test")
    println("part1(testInput): " + part1(testInput))
    println("part2(testInput): " + part2(testInput))
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println("part1(input): " + part1(input))
    println("part2(input): " + part2(input))
}
