fun main() {
    val regex = """Valve (\w{2}) has flow rate=(\d+); tunnels? leads? to valves? ((\w{2})(, \w{2})*)""".toRegex()

    fun mostPressure(nodes: List<Node>, currentValve: String, minutesLeft: Int, totalFlowRate: Int, valvesOpened: Set<String>): Int {
        if (minutesLeft <= 0) return totalFlowRate
        println("currentValvue: $currentValve")
        println("minutesLeft: $minutesLeft")
        val currentValveNode = nodes.first { it.valve == currentValve }
        if (currentValveNode.valve in valvesOpened) {
            return currentValveNode.nextValves.maxOf {nextValve ->
                mostPressure(nodes, nextValve, minutesLeft - 1, totalFlowRate, valvesOpened)
            }
        }
        return maxOf(
            currentValveNode.nextValves.maxOf { nextValve ->
                mostPressure(nodes, nextValve, minutesLeft - 1, totalFlowRate, valvesOpened)
            },
            currentValveNode.nextValves.maxOf { nextValve ->
                mostPressure(nodes, nextValve, minutesLeft - 2, totalFlowRate + currentValveNode.flowRate, valvesOpened + currentValveNode.valve)
            }
        )
    }

    fun part1(input: List<String>): Int {
        val currentValve = "AA"
        val minutesLeft = 30
        val totalFlowRate = 0
        val nodes = input.map { line ->
            val (valve, flowRate, tunnelValves) = regex.find(line)?.destructured ?: error("asdf")
            val nextValves = tunnelValves.split(", ")
            Node(valve, flowRate.toInt(), nextValves)
        }
        mostPressure(nodes, currentValve, minutesLeft, totalFlowRate, emptySet())
        return input.size
    }

    fun part2(input: List<String>): Int = input.size

    val testInput = readInput("Day16_test")
    println("part1(testInput): " + part1(testInput))
    println("part2(testInput): " + part2(testInput))
    check(part1(testInput) == 24000)
    check(part2(testInput) == 24000)

    val input = readInput("Day16")
    println("part1(input): " + part1(input))
    println("part2(input): " + part2(input))
}

data class Node(val valve: String, val flowRate: Int, val nextValves: List<String>)
