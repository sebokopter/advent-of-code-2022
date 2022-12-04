fun main() {
    fun String.toAssigendSections(): Pair<LongRange,LongRange> {
        val (assignedSections1,assignedSections2) = split(",")
            .map { assignment ->
                val (begin, end) = assignment.split("-")
                LongRange(begin.toLong(), end.toLong())
            }
        return assignedSections1 to assignedSections2
    }

    fun part1(input: List<String>): Int = input.count { pairs ->
        val (assignedSections1, assignedSections2) = pairs.toAssigendSections()
        (assignedSections1 subtract assignedSections2).isEmpty() || (assignedSections2 subtract assignedSections1).isEmpty()
    }

    fun part2(input: List<String>): Int = input.count {pairs ->
        val (assignedSections1, assignedSections2) = pairs.toAssigendSections()
        (assignedSections1 intersect assignedSections2).isNotEmpty()
    }

    val testInput = readInput("Day04_test")
    println("part1(testInput): " + part1(testInput))
    println("part2(testInput): " + part2(testInput))
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println("part1(input): " + part1(input))
    println("part2(input): " + part2(input))
}