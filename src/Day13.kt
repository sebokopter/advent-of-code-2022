fun main() {
    fun parseInput(testInput: String): List<Pair<String, String>> =
        testInput
            .split("\n\n")
            .map {
                val (left, right) = it.split("\n")
                left to right
            }

    fun arePacketsInOrder(pair: Pair<String, String>): Boolean {
        val (left, right) = pair
        println("Compare $left to $right")
        if (left.isEmpty()) return true
        if (right.isEmpty() && left.isNotEmpty()) return false
        val currentLeftElement = left.first()
        val currentRightElement = right.first()
        if (currentLeftElement.isDigit() && currentRightElement.isDigit()) {
            println("Compare2 ${currentLeftElement.digitToInt()} to ${currentRightElement.digitToInt()}")
            return when {
                currentLeftElement.digitToInt() < currentRightElement.digitToInt() -> true.also { println("result 1: $it") }
                currentLeftElement.digitToInt() > currentRightElement.digitToInt() -> false.also { println("result 2: $it") }
                else -> arePacketsInOrder(left.drop(2) to right.drop(2)).also { println("result 3: $it") } // drop digit plus ",". E.g. "8,"
            }
        }
        return if (left.startsWith("[[") && right.startsWith("[[")) {
            println("Compare3 $currentLeftElement $currentRightElement")
            val newLeft = left.drop(1).dropLast(1).split(",")
            val newRight = right.drop(1).dropLast(1).split(",")
            println("new compare $newLeft $newRight")
            arePacketsInOrder(newLeft.joinToString(",") to newRight.joinToString(","))
        } else {
            when  {
                currentLeftElement == '[' && currentRightElement == '[' -> {
                    arePacketsInOrder(
                        left.drop(1).takeWhile { it != ']'} to right.drop(1).takeWhile { it != ']'}
                    )
                }
                currentLeftElement == '[' -> {
                    arePacketsInOrder(left.drop(1).takeWhile { it != ']'} to right)
                }
                else -> {
                    arePacketsInOrder(left to right.drop(1).takeWhile { it != ']'})
                }
            }
        }
    }

    fun part1(input: List<Pair<String, String>>): Int {
        return input.withIndex().sumOf { (index, pair) ->
            if (arePacketsInOrder(pair)) index + 1
            else 0
        }
    }

    fun part2(input: List<Pair<String, String>>): Int = input.size

    val testInput = readTextInput("Day13_test")
    val parsedTestInput = parseInput(testInput)
    println("part1(testInput): " + part1(parsedTestInput))
//    println("part2(parsedInput): " + part2(parsedTestInput))
    check(part1(parsedTestInput) == 13)
//    check(part2(parsedTestInput) == 24000)

    val input = readTextInput("Day13")
    val parsedInput = parseInput(input)
    println("part1(parsedInput): " + part1(parsedInput))
//    println("part2(parsedInput): " + part2(parsedInput))
}

