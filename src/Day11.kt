import java.math.BigInteger

fun main() {

    fun parseInput(input: String): Pair<List<List<BigInteger>>, List<Inspection>> {
        val monkeyInputs = input.split("\n\n")
        val items = monkeyInputs.map { it ->
            val lines = it.lines().map { it.trim() }
            return@map lines[1].substringAfter(": ").split(", ").map { it.toBigInteger() }
        }
        val inspections = monkeyInputs.map { monkeyInput ->
            val lines = monkeyInput.lines().map { it.trim() }
            val operationElements = lines[2].substringAfter(" = ").split(" ")
            val operation = Operation(operationElements[1], operationElements[0], operationElements[2])
            val divisionOperator = lines[3].split(" by ").last().toBigInteger()
            val testSuccess = lines[4].split(" monkey ").last().toInt()
            val testFail = lines[5].split(" monkey ").last().toInt()
            Inspection(operation, Route(divisionOperator, testSuccess, testFail))
        }
        return items to inspections
    }

    fun keepAway(items: List<List<BigInteger>>, inspections: List<Inspection>, rounds: Int, worryLevel: Int): List<BigInteger> {
        val mutableItems = items.map { it.toMutableList() }.toMutableList()
        val mutableInspection = inspections.toMutableList()
        val maxModulo: Int = inspections.fold(1) { acc, it -> it.route.divisionOperator.toInt() * acc }
        val inspectionCounter = MutableList(mutableInspection.size) { 0.toBigInteger() }
        for (round in 1..rounds) {
            for ((monkey, inspection) in mutableInspection.withIndex()) {
                for (item in mutableItems[monkey]) {
                    val (operand, operator1String, operator2String) = inspection.operation
                    val operator1 = if (operator1String == "old") item else operator1String.toBigInteger()
                    val operator2 = if (operator2String == "old") item else operator2String.toBigInteger()
                    val route = inspection.route
                    val newItem = when (operand) {
                        "*" -> operator1 * operator2
                        "+" -> operator1 + operator2
                        else -> error("Operation $operand not supported")
                    }.div(worryLevel.toBigInteger())
                    val newMonkey = if (newItem % route.divisionOperator == 0.toBigInteger()) route.onSuccess
                    else route.onFailure
                    mutableItems[monkey] = mutableListOf()
                    mutableItems[newMonkey].add(newItem % maxModulo.toBigInteger())
                    inspectionCounter[monkey] = inspectionCounter[monkey] + 1.toBigInteger()
                }
            }
        }
        return inspectionCounter.toList()
    }

    fun monkeyBusiness(inspectionCounter: List<BigInteger>): BigInteger =
        inspectionCounter.sorted().reversed().take(2).reduce { first, second -> first * second }

    fun part1(input: String): BigInteger {
        val rounds = 20
        val worryLevel = 3
        val (items, inspections) = parseInput(input)
        val inspectionCounter = keepAway(items, inspections, rounds, worryLevel)
        return monkeyBusiness(inspectionCounter)
    }

    fun part2(input: String): BigInteger {
        val rounds = 10_000
        val worryLevel = 1
        val (items, inspections) = parseInput(input)
        val inspectionCounter = keepAway(items, inspections, rounds, worryLevel)
        return monkeyBusiness(inspectionCounter)

    }

    val testInput = readTextInput("Day11_test")
    println("part1(testInput): " + part1(testInput))
    println("part2(testInput): " + part2(testInput))
    check(part1(testInput) == 10605.toBigInteger())
    check(part2(testInput) == 2713310158.toBigInteger())

    val input = readTextInput("Day11")
    println("part1(input): " + part1(input))
    println("part2(input): " + part2(input))
}

data class Inspection(val operation: Operation, val route: Route)

data class Operation(val operand: String, val operator1: String, val operator2: String)

data class Route(val divisionOperator: BigInteger, val onSuccess: Int, val onFailure: Int)