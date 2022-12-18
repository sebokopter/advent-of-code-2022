import java.util.*

data class Cube(val x: Int, val y: Int, val z: Int) {

    fun adjacentNeighbours(): Set<Cube> = setOf(
        Cube(x + 1, y, z),
        Cube(x - 1, y, z),
        Cube(x, y + 1, z),
        Cube(x, y - 1, z),
        Cube(x, y, z + 1),
        Cube(x, y, z - 1),
    )

    companion object {
        fun fromCsv(commaSeparatedValues: String): Cube {
            val (x, y, z) = commaSeparatedValues.split(",").map { it.toInt() }
            return Cube(x, y, z)
        }
    }

}

fun main() {

    fun cubes(input: List<String>) = input
        .map { line -> Cube.fromCsv(line) }.toSet()

    fun part1(input: List<String>): Int {
        val cubes = cubes(input)
        return cubes.sumOf { cube ->
            cube.adjacentNeighbours().count { neighbour -> neighbour !in cubes }
        }
    }


    fun part2(input: List<String>): Int {
        val cubes = cubes(input)

        val xRange = cubes.minOf { (x, _, _) -> x - 1 }..cubes.maxOf { (x, _, _) -> x + 1 }
        val yRange = cubes.minOf { (_, y, _) -> y - 1 }..cubes.maxOf { (_, y, _) -> y + 1 }
        val zRange = cubes.minOf { (_, _, z) -> z - 1 }..cubes.maxOf { (_, _, z) -> z + 1 }

        fun Cube.inBounds() = x in xRange && y in yRange && z in zRange

        fun surroundingAir(cubes: Set<Cube>): Set<Cube> {
            tailrec fun fill(surrounding: Set<Cube>, current: Set<Cube>): Set<Cube> {
                if (current.isEmpty()) return surrounding
                val nextSet = current
                    .asSequence()
                    .flatMap { it.adjacentNeighbours() }
                    .filter { it.inBounds() }
                    .filter { it !in cubes && it !in current && it !in surrounding }
                    .toSet()
                return fill(surrounding + current, nextSet)
            }

            return fill(emptySet(), setOf(Cube(xRange.first, yRange.first, zRange.first)))
        }

        val surroundingAir = surroundingAir(cubes)

        return cubes.sumOf { cube ->
            cube.adjacentNeighbours().count { neighbour -> neighbour in surroundingAir }
        }
    }

    val testInput = readInput("Day18_test")
    println("part1(testInput): " + part1(testInput))
    println("part2(testInput): " + part2(testInput))
    check(part1(testInput) == 10)
    check(part2(testInput) == 10)
    val testInput2 = readInput("Day18_test2")
    println("part1(testInput): " + part1(testInput2))
    println("part2(testInput): " + part2(testInput2))
    check(part1(testInput2) == 64)
    check(part2(testInput2) == 58)

    val input = readInput("Day18")
    println("part1(input): " + part1(input))
    println("part2(input): " + part2(input))
}
