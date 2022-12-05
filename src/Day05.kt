fun main() {
    data class Rearrangement(val amount: Int, val from: Int, val to: Int)

    fun parseRearrangement(line: String): Rearrangement {
        val regex = """\D+(\d+)\D+(\d+)\D+(\d+)""".toRegex()
        val (amount, from, to) = regex.find(line)?.destructured ?: error("could not parse $line")
        return Rearrangement(amount.toInt(), (from.toInt() - 1), (to.toInt() - 1))
    }

    fun readPuzzleInput(input: String): List<List<String>> = input.split("\n\n").map { it.lines() }

    fun countStacks(stackIndices: String): Int = stackIndices.length / 3

    fun separateStackIndices(startStacks: List<String>): Pair<List<String>, String> {
        val (stacks, stackIndices) = startStacks.chunked(startStacks.lastIndex)
        return stacks to stackIndices.single()
    }

    fun parseStacks(stacksPuzzleInput: List<String>): List<ArrayDeque<Char>> {
        val (stacksString, stackIndices) = separateStackIndices(stacksPuzzleInput)
        val stackCount = countStacks(stackIndices)

        return stacksString.fold(List(stackCount) { ArrayDeque() }) { stacks, line ->
            val potentialStacks = line.chunked(4)
            for ((stackIndex, potentialStack) in potentialStacks.withIndex()) {
                if (potentialStack.isBlank()) continue
                val crateId = potentialStack[1]
                stacks[stackIndex].addFirst(crateId)
            }
            stacks
        }
    }

    fun topOfStack(stacks: List<ArrayDeque<Char>>) = stacks.fold("") { topOfStacks, stack ->
        if (stack.isNotEmpty()) topOfStacks + stack.last()
        else topOfStacks
    }

    fun List<Rearrangement>.rearrange(stacks: List<ArrayDeque<Char>>) = forEach { (amount, fromIndex, toIndex) ->
        repeat(amount) {
            val stackToMove = stacks[fromIndex].removeLast()
            stacks[toIndex].add(stackToMove)
        }
    }

    fun List<Rearrangement>.rearrange2(stacks: List<ArrayDeque<Char>>) = forEach { (amount, fromIndex, toIndex) ->
        val stacksToMove = ArrayDeque<Char>()
        repeat(amount) {
            stacksToMove.addFirst(stacks[fromIndex].removeLast())
        }
        stacks[toIndex].addAll(stacksToMove)
    }

    fun part1(input: String): String {
        val (stacksInput, rearrangementInput) = readPuzzleInput(input)
        val stacks = parseStacks(stacksInput)
        val rearrangements = rearrangementInput.map(::parseRearrangement)
        rearrangements.rearrange(stacks)
        return topOfStack(stacks)
    }

    fun part2(input: String): String {
        val (stacksInput, rearrangementInput) = readPuzzleInput(input)
        val stacks = parseStacks(stacksInput)
        val rearrangements = rearrangementInput.map(::parseRearrangement)
        rearrangements.rearrange2(stacks)
        return topOfStack(stacks)
    }

    val testInput = readTextInput("Day05_test")
    println("part1(testInput): " + part1(testInput))
    println("part2(testInput): " + part2(testInput))
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readTextInput("Day05")
    println("part1(input): " + part1(input))
    println("part2(input): " + part2(input))
}
