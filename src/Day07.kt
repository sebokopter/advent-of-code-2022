import java.util.*

operator fun String.component1() = split(" ")[0]
operator fun String.component2() = split(" ")[1]
operator fun String.component3() = split(" ")[2]

const val TOTAL_DISK_SPACE = 70_000_000
const val REQUIRED_SPACE = 30_000_000
fun Stack<String>.toAbsolutePath(subDir: String = ""): String {
    val dirs = if (subDir.isNotBlank()) subList(1, this.size) + subDir else subList(1, this.size)
    return dirs.joinToString("/", "/")
}

fun main() {

    fun findAllDirs(input: List<String>): Set<String> {
        val dirs = mutableSetOf<String>()
        val currentDir = Stack<String>()
        input.forEach { line ->
            if (line.startsWith("$ ls ")) return@forEach
            if (line.startsWith("$ cd ")) {
                val (_, _, cdDir) = line
                if (cdDir == "..") currentDir.pop()
                else currentDir.push(cdDir)
                dirs.add(currentDir.toAbsolutePath())
            }
            if (line.startsWith("dir")) {
                val (_, dirName) = line
                dirs.add((currentDir.toAbsolutePath(dirName)))
            }
        }
        return dirs
    }

    fun dirSize(input: List<String>, dir: String): Int {
        var size = 0
        val currentDir = Stack<String>()

        input.forEachIndexed { index, line ->
            if (line.startsWith("$ cd ")) {
                val (_, _, cdDir) = line
                if (cdDir == "..") currentDir.pop()
                else currentDir.push(cdDir)
            }
            if (line.startsWith("$ ls") && currentDir.toAbsolutePath() == dir) {
                val lsOutputs = input.subList(index + 1, input.size).takeWhile { !it.startsWith("$") }
                size += lsOutputs.sumOf { lsOutput ->
                    return@sumOf if (lsOutput.startsWith("dir")) {
                        val (_, dirName) = lsOutput
                        dirSize(input, currentDir.toAbsolutePath(dirName))
                    } else {
                        val (fileSize) = lsOutput
                        fileSize.toInt()
                    }
                }
            }
        }
        return size
    }

    fun part1(input: List<String>): Int {
        val dirs = findAllDirs(input)
        val dirSizes = dirs.map { dir ->
            dir to dirSize(input, dir)
        }
        return dirSizes.sumOf { if (it.second < 100_000) it.second else 0 }

    }

    fun part2(input: List<String>): Int {
        val dirs = findAllDirs(input)
        val dirSizes = dirs.map { dir ->
            dir to dirSize(input, dir)
        }
        val currentFreeSpace = TOTAL_DISK_SPACE - dirSizes.first { (dirName, _) -> dirName == "/" }.second
        val spaceToBeFreed = REQUIRED_SPACE - currentFreeSpace
        return dirSizes.filter { it.second > spaceToBeFreed }.minOf { it.second }
    }

    val testInput = readInput("Day07_test")
    println("part1(testInput): " + part1(testInput))
    println("part2(testInput): " + part2(testInput))
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println("part1(input): " + part1(input))
    println("part2(input): " + part2(input))
}
