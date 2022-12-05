fun main() {
    data class Rearrangement(val amount: Int, val from: Int, val to: Int)

    fun readPuzzleInput(input: String): List<List<String>> = input.split("\n\n").map { it.lines() }

    fun countStacks(startStacks: String): Int = startStacks.length / 3

    fun separateStackIndizes(startStacks: List<String>): Pair<List<String>, String> {
        val (stacks, stackIndices) = startStacks.chunked(startStacks.lastIndex)
        return stacks to stackIndices.single()
    }

    fun parseStacks(stacksPuzzleInput: List<String>): List<ArrayDeque<Char>> {
        val (stacksString, stackIndices) = separateStackIndizes(stacksPuzzleInput)
        val stackCount = countStacks(stackIndices)

        return stacksString.fold(List(stackCount) { ArrayDeque<Char>() }) { stacks, line ->
            val potentialStacks = line.chunked(4)
            potentialStacks.forEachIndexed { index, potentialStack ->
                if (potentialStack.isBlank()) return@forEachIndexed
                val crateChar = potentialStack[1]
                stacks[index].addFirst(crateChar)
            }
            stacks
        }
    }

    fun topOfStack(stacks: List<ArrayDeque<Char>>) = stacks.fold("") { topOfStacks, stack ->
        if (stack.isNotEmpty()) topOfStacks + stack.last()
        else topOfStacks
    }

    fun List<String>.asRearrangements(): List<Rearrangement> = map { line ->
        val regex = "move (\\d+) from (\\d+) to (\\d+)".toRegex()
        val (amount, from, to) = regex.find(line)?.destructured ?: error("could not parse $line")
        Rearrangement(amount.toInt(), (from.toInt() - 1), (to.toInt() - 1))
    }

    fun rearrange(stacks: List<ArrayDeque<Char>>, rearrangementProcess: List<String>) {
        rearrangementProcess.asRearrangements().forEach { (amount, fromIndex, toIndex) ->
            repeat(amount) {
                val stackToMove = stacks[fromIndex].removeLast()
                stacks[toIndex].add(stackToMove)
            }
        }
    }

    fun rearrange2(stacks: List<ArrayDeque<Char>>, rearrangementProcess: List<String>) {
        rearrangementProcess.asRearrangements().forEach { (amount, fromIndex, toIndex) ->
            val stacksToMove = ArrayDeque<Char>()
            repeat(amount) {
                stacksToMove.addFirst(stacks[fromIndex].removeLast())
            }
            stacks[toIndex].addAll(stacksToMove)
        }
    }

    fun part1(input: String): String {
        val (startStacks, rearrangementProcess) = readPuzzleInput(input)
        val stacks = parseStacks(startStacks)
        rearrange(stacks, rearrangementProcess)
        return topOfStack(stacks)
    }

    fun part2(input: String): String {
        val (startStacks, rearrangementProcess) = readPuzzleInput(input)
        val stacks = parseStacks(startStacks)
        rearrange2(stacks, rearrangementProcess)
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
