fun main() {

    fun callStack(inputs: List<String>): List<Pair<String, Int>> = inputs.map { input ->
        val line = input.split(" ")
        line[0] to line.getOrElse(1) { "0" }.toInt()
    }

    fun execute(callStack: List<Pair<String, Int>>): List<Pair<Int, Int>> =
        callStack.fold(mutableListOf(1 to 1)) { history, current ->
            val (cmd, arg) = current
            val (cycle, register) = history.last()
            val currentExecution = mutableListOf(cycle + 1 to register)
            if (cmd == "addx") currentExecution.add(cycle + 2 to register + arg)
            history + currentExecution
        }

    fun part1(input: List<String>): Int {
        //cycle to register
        val callStack = callStack(input)
        val executionHistory = execute(callStack)
        return executionHistory.sumOf { (cycle, register) ->
            if (cycle < 20) return@sumOf 0
            if ((cycle - 20) % 40 != 0) return@sumOf 0
            cycle * register
        }
    }

    fun part2(input: List<String>): String {
        val callStack = callStack(input)
        val executionHistory = execute(callStack)
        val crt = MutableList<String>(240) { "." }
        executionHistory.forEach { (cycle, register) ->
            println("cycle: $cycle, register: $register")
            val currentCrtIndex = cycle - 1
            if (currentCrtIndex % 40 in register - 1..register + 1) {
                crt[currentCrtIndex] = "#"
            }
        }
        return crt.chunked(40) { it.joinToString("") }.joinToString("\n")
    }

    val testInput2 = readInput("Day10_test2")
    val testInput1 = readInput("Day10_test1")
    val testInput = readInput("Day10_test")
    println("part1(testInput): " + part1(testInput))
    println("part2(testInput): ")
    println(part2(testInput))
    check(part1(testInput) == 13140)
//    check(part2(testInput) == 24000)

    val input = readInput("Day10")
    println("part1(input): " + part1(input))
    println("part2(input): ")
    println(part2(input))
}
