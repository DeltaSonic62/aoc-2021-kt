fun main() {
    fun part1(input: List<String>): Int {
        var prevNum = input[0].toInt()
        var numOfIncreases = 0

        for (num in input) {
            val i = num.toInt()

            if (i > prevNum) {
                numOfIncreases++
            }

            prevNum = i
        }
        return numOfIncreases
    }

    fun part2(input: List<String>): Int {
        val newList: MutableList<String> = mutableListOf()

        for (i in 0..input.size - 3) {
            newList.add((input[i].toInt() + input[i + 1].toInt() + input[i + 2].toInt()).toString())
        }

        return part1(newList)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))

}
