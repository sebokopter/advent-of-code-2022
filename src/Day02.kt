fun main() {

    fun part1(input: List<String>): Int = input.size

    fun part2(input: List<String>): Int = input.size

    val testInput = readInput("Day02_test")
    println("part1(testInput): " + part1(testInput))
    check(part1(testInput) == 24000)

    val input = readInput("Day02")
    println("part1(input): " + part1(input))
    println("part2(input): " + part2(input))
}
