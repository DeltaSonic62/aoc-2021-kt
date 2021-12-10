fun main() {
    fun part1(input: List<String>): Int {
        val fishList = getFishList(input)

        for (day in 1..80) {
            for (j in fishList.indices) {
                if (fishList[j] == 0) {
                    fishList[j] = 6
                    fishList.add(8)
                } else {
                    fishList[j] -= 1
                }
            }
        }

        return fishList.size
    }

    fun part2(input: List<String>): Long {
        val fishList = getFishList(input)

        // Fishes with timer 0, 1, 2, ..., 8
        val fishTimerList = mutableListOf<Long>(0, 0, 0 ,0 ,0 ,0 ,0, 0, 0)

        for (f in fishList) {
            fishTimerList[f] += 1.toLong()
        }

        for (i in 1 .. 256) {
            val tempFTList = mutableListOf<Long>()
            for (j in 1..8) {
                tempFTList.add(fishTimerList[j])
            }
            tempFTList.add(fishTimerList[0])
            tempFTList[6] += fishTimerList[0]

            for (j in fishTimerList.indices) {
                fishTimerList[j] = tempFTList[j]
            }
        }

        return fishTimerList.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934)
    check(part2(testInput) == 26984457539)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}

fun getFishList(input: List<String>): MutableList<Int> {
    val fishList = mutableListOf<Int>()
    for (line in input) {
        val a = line.split(',')
        for (s in a) {
            fishList.add(s.trim().toInt())
        }
    }

    return fishList
}