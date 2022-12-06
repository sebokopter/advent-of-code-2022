fun main() {

    fun findFirstUniqueChars(input: String, uniqueChars: Int): Int {
        for (i in (uniqueChars-1)..input.lastIndex) {
            val lastFourChars = input.substring(i - (uniqueChars-1)..i)
            fun areUnique(lastFourChars: String) = lastFourChars.toSet().size == uniqueChars
            if (areUnique(lastFourChars)) return i + 1
        }
        error("No start-of-packet marker found")
    }

    fun part1(input: String): Int = findFirstUniqueChars(input, 4)

    fun part2(input: String): Int = findFirstUniqueChars(input, 14)

    readInput("Day06_test_part1").forEach { line ->
        val (input,solution) = line.split(" ")
        println("part1(testInput): " + part1(input))
        check(part1(input) == solution.toInt())
    }
    readInput("Day06_test_part2").forEach { line ->
        val (input,solution) = line.split(" ")
        println("part2(testInput): " + part2(input))
        check(part2(input) == solution.toInt())
    }

    val input = readTextInput("Day06")
    println("part1(input): " + part1(input))
    println("part2(input): " + part2(input))
}
