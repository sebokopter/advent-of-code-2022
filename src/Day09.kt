import kotlin.math.abs

fun main() {

    fun lostTouch(head: Pair<Int, Int>, tail: Pair<Int, Int>): Boolean =
        head.first !in tail.first - 1..tail.first + 1 || head.second !in tail.second - 1..tail.second + 1

    fun pullTail(head: Pair<Int, Int>, tail: Pair<Int, Int>): Pair<Int, Int> {
        var newX = tail.second
        var newY = tail.first
        if (head.first > tail.first) newY = tail.first + 1
        if (head.first < tail.first) newY = tail.first - 1
        if (head.second > tail.second) newX = tail.second + 1
        if (head.second < tail.second) newX = tail.second - 1
        return newY to newX
    }

    fun part1(input: List<String>): Int {
        var head = 0 to 0  // y to x
        var tail = 0 to 0  // y to x
        val diagram: MutableList<MutableList<Char>> = MutableList(10_000) { MutableList(10_000) { '.' } }
        diagram[0][0] = '#'
        input.forEach { (direction, stepsString) ->
            val steps = stepsString.toInt()
            when (direction) {
                "R" -> {
                    for (x in head.second..head.second + steps) {
                        head = head.first to x
                        if (lostTouch(head, tail)) {
                            tail = pullTail(head, tail)
                            diagram[tail.first][tail.second] = '#'
                        }
                    }
                }

                "L" -> {
                    for (x in head.second downTo (head.second - steps).coerceAtLeast(0)) {
                        head = head.first to x
                        if (lostTouch(head, tail)) {
                            tail = pullTail(head, tail)
                            diagram[tail.first][tail.second] = '#'
                        }
                    }
                }

                "U" -> {
                    for (y in head.first..head.first + steps) {
                        head = y to head.second
                        if (lostTouch(head, tail)) {
                            tail = pullTail(head, tail)
                            diagram[tail.first][tail.second] = '#'
                        }
                    }
                }

                "D" -> {
                    for (y in head.first downTo (head.first - steps).coerceAtLeast(0)) {
                        head = y to head.second
                        if (lostTouch(head, tail)) {
                            tail = pullTail(head, tail)
                            diagram[tail.first][tail.second] = '#'
                        }
                    }
                }
            }
        }
//        println(
//            diagram.reversed().joinToString("\n") { row ->
//            row.joinToString("")
//        }
//        )
        return diagram.sumOf { row ->
            row.count { it == '#' }
        }
    }

    fun part2(input: List<String>): Int = input.size

    val testInput = readInput("Day09_test")
    println("part1(testInput): " + part1(testInput))
//    println("part2(testInput): " + part2(testInput))
    check(part1(testInput) == 13)
//    check(part2(testInput) == 24000)

    val input = readInput("Day09")
    println("part1(input): " + part1(input))
//    println("part2(input): " + part2(input))
}
