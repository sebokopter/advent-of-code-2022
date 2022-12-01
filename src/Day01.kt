fun main() {
    fun caloriesByElves(input: List<String>): List<Int> {
        return input
            .joinToString("\n")
            .split("\n\n")
            .map { caloriesStrings ->
                caloriesStrings.split("\n")
                    .sumOf { string -> string.toInt() }
            }
    }

    fun part1(input: List<String>): Int = caloriesByElves(input).max()

    fun part2(input: List<String>): Int = caloriesByElves(input).sorted().takeLast(3).sum()

    val testInput = readInput("Day01_test")
    println("part1(testInput): " + part1(testInput))
    check(part1(testInput) == 24000)

    val input = readInput("Day01")
    println("part1(input): " + part1(input))
    println("part2(input): " + part2(input))
}
