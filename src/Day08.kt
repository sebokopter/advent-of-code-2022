fun main() {

    fun forest(input: List<String>): List<List<Int>> =
        input.map { row -> row.map { tree -> tree.digitToInt() } }

    fun isVisible(view: List<Int>): Boolean {
        val treeSize = view.last()
        return view.dropLast(1).all { it < treeSize }
    }

    fun countVisible(view: List<Int>, currentTree: Int): Int {
        var sum = 0
        for (size in view) {
            sum++
            if (size >= currentTree) return sum
        }
        return sum
    }

    fun part1(input: List<String>): Int {
        val forest = forest(input)
        val visibleTrees = MutableList(forest.size) { MutableList(forest.first().size) { 0 to false } }
        val lastRowIndex = forest.lastIndex
        val lastColumnIndex = forest.first().lastIndex
        for (rowIndex in 0..lastRowIndex) {
            for (columnIndex in 0..lastColumnIndex) {
                if (rowIndex == 0 || rowIndex == lastRowIndex || columnIndex == 0 || columnIndex == lastColumnIndex) {
                    visibleTrees[rowIndex][columnIndex] = forest[rowIndex][columnIndex] to true
                } else {
                    if (visibleTrees[rowIndex][columnIndex].second) continue
                    val viewFromLeft = forest[rowIndex].subList(0, columnIndex + 1)
                    val viewFromRight = forest[rowIndex].reversed().subList(0, lastColumnIndex - columnIndex + 1)
                    val viewFromTop = forest.map { it[columnIndex] }.subList(0, rowIndex + 1)
                    val viewFromBottom =
                        forest.map { it[columnIndex] }.reversed().subList(0, lastRowIndex - rowIndex + 1)
                    val visible = isVisible(viewFromLeft) ||
                            isVisible(viewFromRight) ||
                            isVisible(viewFromTop) ||
                            isVisible(viewFromBottom)
                    if (visible) visibleTrees[rowIndex][columnIndex] = forest[rowIndex][columnIndex] to true
                    else visibleTrees[rowIndex][columnIndex] = forest[rowIndex][columnIndex] to false
                }
            }
        }
        return visibleTrees.sumOf { row -> row.count { it.second } }
    }

    fun part2(input: List<String>): Int {
        val forest = forest(input)
        val scenicScores = MutableList(forest.size) { MutableList(forest.first().size) { 0 to 0 } }
        val lastRowIndex = forest.lastIndex
        val lastColumnIndex = forest.first().lastIndex
        for (rowIndex in 0..lastRowIndex) {
            for (columnIndex in 0..lastColumnIndex) {
                if (rowIndex == 0 || rowIndex == lastRowIndex || columnIndex == 0 || columnIndex == lastColumnIndex) {
                    scenicScores[rowIndex][columnIndex] = forest[rowIndex][columnIndex] to 0
                } else {
                    val viewToLeft = forest[rowIndex].subList(0, columnIndex + 1).reversed()
                    val viewToRight = forest[rowIndex].subList(columnIndex, lastColumnIndex + 1)
                    val viewToTop = forest.map { it[columnIndex] }.subList(0, rowIndex + 1).reversed()
                    val viewToBottom = forest.map { it[columnIndex] }.subList(rowIndex, lastRowIndex + 1)
                    val scenicScore = countVisible(viewToLeft.drop(1), forest[rowIndex][columnIndex]) *
                            countVisible(viewToRight.drop(1), forest[rowIndex][columnIndex]) *
                            countVisible(viewToTop.drop(1), forest[rowIndex][columnIndex]) *
                            countVisible(viewToBottom.drop(1), forest[rowIndex][columnIndex])
                    scenicScores[rowIndex][columnIndex] = forest[rowIndex][columnIndex] to scenicScore
                }
            }
        }
        return scenicScores.maxOf { row ->
            row.maxOf { it.second }
        }
    }

    val testInput = readInput("Day08_test")
    println("part1(testInput): " + part1(testInput))
    println("part2(testInput): " + part2(testInput))
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println("part1(input): " + part1(input))
    println("part2(input): " + part2(input))
}

