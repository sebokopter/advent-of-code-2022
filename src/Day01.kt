fun main() {
    fun caloriesByElves(input: List<String>): List<Int> {
        val (groupedCalories, _) = input.fold(mutableListOf(mutableListOf<String>()) to 0) { (list, elfIndex), string ->
            if (string.isEmpty()) {
                val newElfIndex = elfIndex + 1
                list.add(mutableListOf())
                list to newElfIndex
            } else {
                list[elfIndex].add(string)
                list to elfIndex
            }
        }
        return groupedCalories.map { strings -> strings.sumOf { it.toInt() } }
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
