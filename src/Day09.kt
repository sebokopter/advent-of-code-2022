import kotlin.math.abs

data class Move(val dx: Int, val dy: Int)
enum class Direction(val move: Move) {
    R(Move(1, 0)),
    L(Move(-1, 0)),
    U(Move(0, 1)),
    D(Move(0, -1));
}

fun main() {

    fun lostTouch(head: Point, tail: Point): Boolean =
        !(abs(head.x - tail.x) <= 1 && abs(head.y - tail.y) <= 1)

    fun pullTail(head: Point, tail: Point): Point {
        val dX = (head.x - tail.x).coerceIn(-1, 1)
        val dY = (head.y - tail.y).coerceIn(-1, 1)
        return tail.copy(tail.x + dX, tail.y + dY)
    }

    fun List<String>.readDirections(): List<Pair<Direction, Int>> = map { (directionString, stepsString) ->
        val direction = Direction.valueOf(directionString)
        val steps = stepsString.toInt()
        direction to steps
    }

    fun part1(input: List<String>): Int {
        var head = Point(0, 0)
        var tail = head
        val tailVisited = mutableSetOf(tail)
        input.readDirections().forEach { (direction, steps) ->
            repeat(steps) {
                head = Point(head.x + direction.move.dx, head.y + direction.move.dy)
                if (lostTouch(head, tail)) {
                    tail = pullTail(head, tail)
                    tailVisited.add(tail)
                }
            }
        }
        return tailVisited.size
    }

    fun part2(input: List<String>): Int {
        val knots = MutableList(10) { Point(0, 0) } // head = 0, tail = 9
        val tailVisited = mutableSetOf(Point(0, 0))
        input.readDirections().forEach { (direction,steps) ->
            repeat(steps) {
                knots[0] = Point(knots[0].x + direction.move.dx, knots[0].y + direction.move.dy)
                for (i in 0..8) {
                    if (lostTouch(knots[i], knots[i+1])) {
                        knots[i+1] = pullTail(knots[i], knots[i+1])
                        if (i+1 == 9) tailVisited.add(knots[9])
                    }
                }
            }
        }
        return tailVisited.size
    }

    val testInput = readInput("Day09_test")
    println("part1(testInput): " + part1(testInput))
    println("part2(testInput): " + part2(testInput))
    check(part1(testInput) == 13)
    check(part2(testInput) == 1)

    val testInput2 = readInput("Day09_test2")
    println("part2(testInput2): " + part2(testInput2))
    check(part2(testInput2) == 36)

    val input = readInput("Day09")
    println("part1(input): " + part1(input))
    println("part2(input): " + part2(input))
}
