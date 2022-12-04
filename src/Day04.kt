package benchmark

import org.openjdk.jmh.annotations.*
import readInput
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 0)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
open class Day04 {

    var input: List<String> = listOf()

    @Setup
    fun setUp() {
        input = readInput("Day04")
    }
    private fun String.toAssignedSections(): Pair<LongRange, LongRange> {
        val (assignedSections1, assignedSections2) = split(",")
            .map { assignment ->
                val (begin, end) = assignment.split("-")
                LongRange(begin.toLong(), end.toLong())
            }
        return assignedSections1 to assignedSections2
    }

    @Benchmark
    fun part1(): Int = input.count { pairs ->
        val (assignedSections1, assignedSections2) = pairs.toAssignedSections()
        (assignedSections1 subtract assignedSections2).isEmpty() || (assignedSections2 subtract assignedSections1).isEmpty()
    }

   @Benchmark
    fun part2(): Int = input.count { pairs ->
        val (assignedSections1, assignedSections2) = pairs.toAssignedSections()
        (assignedSections1 intersect assignedSections2).isNotEmpty()
    }

}

fun main() {
    val day04 = Day04()
    day04.input = readInput("Day04_test")
    println("part1(): " + day04.part1())
    println("part2(): " + day04.part2())
    check(day04.part1() == 2)
    check(day04.part2() == 4)

    day04.input = readInput("Day04")
    println("part1(): " + day04.part1())
    println("part2(): " + day04.part2())
}
