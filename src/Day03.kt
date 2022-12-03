fun main() {

    fun priority(commonItem: Char): Int {
        return if (commonItem.isLowerCase()) commonItem.code - 96
        else commonItem.code - 38
    }

    fun commonItemByCompartments(it: String): Char {
        val (length, items) = it.length to it
        assert(length.mod(2) == 0) { "Size $length can not be split equally into two." }
        val (compartment1, compartment2) = items.chunked(length / 2)
        return compartment1.first { it in compartment2 }
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
