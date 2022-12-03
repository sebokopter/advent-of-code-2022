import java.io.File

fun main() {
    fun CharSequence.chunkedBy(predicate: () -> String): List<String> = split(predicate())

    fun caloriesByElves(input: String): List<Int> = input
        .chunkedBy { "\n\n" }
        .map { caloriesStrings ->
            caloriesStrings.lines().sumOf { it.toInt() }
        }

    fun sumHighestCalories(input: String, amount: Int): Int = caloriesByElves(input).sortedDescending().take(amount).sum()

    fun part1(input: String): Int = sumHighestCalories(input,1)

    fun part2(input: String): Int = sumHighestCalories(input, 3)

    val testInput = File("src/Day01_test.txt").readText()
    println("part1(testInput): " + part1(testInput))
    check(part1(testInput) == 24000)

    val input = File("src/Day01.txt").readText()
    println("part1(input): " + part1(input))
    println("part2(input): " + part2(input))
}