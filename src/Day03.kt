fun main() {

    /**
     * a..z => 1..26
     * A..Z => 27..52
     */
    fun priority(char: Char): Int {
        val priority = if (char.isLowerCase()) char - 'a'
        else char - 'A' + 26
        //priority index starts with 1
        return priority + 1
    }

    fun findCommonItem(rucksacks: List<Set<Char>>): Char = rucksacks.reduce { rucksack1, rucksack2 ->
        rucksack1 intersect rucksack2
    }.single()

    fun commonItemByCompartments(it: String): Char {
        val (length, items) = it.length to it
        return findCommonItem(items.chunked(length / 2) { it.toSet() })
    }

    fun part1(input: List<String>): Int = input.sumOf {
        priority(commonItemByCompartments(it))
    }

    fun commonItemByRucksacks(rucksacks: List<String>): Char = findCommonItem(rucksacks.map { it.toSet() })

    fun part2(input: List<String>): Int = input.chunked(3).sumOf { rucksacks ->
        priority(commonItemByRucksacks(rucksacks))
    }

    val testInput = readInput("Day03_test")
    println("part1(testInput): " + part1(testInput))
    println("part2(testInput): " + part2(testInput))
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println("part1(input): " + part1(input))
    println("part2(input): " + part2(input))
}
