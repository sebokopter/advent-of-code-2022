fun main() {

    /**
     * a..z => 1..26
     * A..Z => 27..52
     */
    fun priority(char: Char): Int {
        val priority = if (char.isLowerCase()) char - 'a'
        else char - 'A' + 26
        //priority starts with 1
        return priority + 1
    }

    fun commonItemByCompartments(it: String): Char {
        val (length, items) = it.length to it
        assert(length % 2 == 0) { "Size $length can not be split equally into two." }
        val (compartment1, compartment2) = items.chunked(length / 2) { it.toSet() }
        return (compartment1 intersect compartment2).first()
    }

    fun part1(input: List<String>): Int = input.sumOf { it ->
        val commonItem = commonItemByCompartments(it)
        priority(commonItem)
    }

    fun commonItemByRucksacks(rucksacks: List<String>): Char {
        val (rucksack1, rucksack2, rucksack3) = rucksacks
        return rucksack1.first { it in rucksack2 && it in rucksack3 }
    }

    fun part2(input: List<String>): Int = input.chunked(3).sumOf { rucksacks ->
        val commonItem = commonItemByRucksacks(rucksacks)
        priority(commonItem)
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
