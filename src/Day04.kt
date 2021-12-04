fun main() {
    fun part1(input: List<String>): Int {
        val allNums = input[0].split(',')
        val boards = createBoards(input)

        //
        val res = result(boards, allNums)
        val winningBoard = res["winningBoard"] as List<*>
        val numsCalled = res["numsCalled"] as List<*>
        val lastNumCalled: Int = res["lastNumCalled"] as Int
        val unmarkedSum = getUnmarkedSum(winningBoard, numsCalled)

        return unmarkedSum * lastNumCalled
    }

    fun part2(input: List<String>): Int {
        val allNums = input[0].split(',')
        val boards = createBoards(input)

         do {
            val res = result(boards, allNums)
            val winningBoard = res["winningBoard"] as List<*>
            boards.remove(winningBoard)
        } while (boards.size > 1)

        val res = result(boards, allNums)
        val winningBoard = res["winningBoard"] as List<*>
        val numsCalled = res["numsCalled"] as List<*>
        val lastNumCalled: Int = res["lastNumCalled"] as Int
        val unmarkedSum = getUnmarkedSum(winningBoard, numsCalled)

        return unmarkedSum * lastNumCalled
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}


fun createBoards(input: List<String>): MutableList<List<List<String>>> {
    val boards = mutableListOf<List<List<String>>>()

    // Create boards
    var x = 2
    do {
        boards.add(
            listOf(
                input[x].split(','),
                input[x + 1].split(','),
                input[x + 2].split(','),
                input[x + 3].split(','),
                input[x + 4].split(',')
            )
        )
        x += 6
    } while (x < input.size - 1)

    // Cleanup board lists
    for ((i, board) in boards.withIndex()) {
        val tempBoard = mutableListOf<List<String>>()

        // Split each row by spaces
        for (row in board) {
            val a = row[0].trim().split(' ')
            val b = mutableListOf<String>()

            // Remove unnecessary spaces
            for (k in a.indices) {
                if (a[k] != "") {
                    b.add(a[k])
                }
            }
            tempBoard.add(b)
        }
        boards[i] = tempBoard
    }

    return boards
}

fun result(boards: MutableList<List<List<String>>>, allNums: List<String>): Map<String, Any> {
    var winningBoard = mutableListOf<List<String>>()
    val numsCalled = mutableListOf<String>()
    var lastNumCalled = -1

    for (i in allNums.indices) {
        numsCalled.add(allNums[i])

        for (board in boards) {
            for (row in board) {
                var marksInRow = 0

                // Check rows first
                for (num in row) {
                    if (num in numsCalled) {
                        marksInRow++
                    }
                }

                if (marksInRow == 5) {
                    winningBoard = board as MutableList<List<String>>
                    lastNumCalled = numsCalled.last().toInt()
                    break
                }
            }

            // If board has won be row, exit loop
            if (lastNumCalled != -1) {
                break
            }

            // Check columns
            for (j in board.indices) {
                var marksInCol = 0
                for (k in board.indices) {
                    if (board[k][j] in numsCalled) {
                        marksInCol++
                    }
                }

                if (marksInCol == 5) {
                    winningBoard = board as MutableList<List<String>>
                    lastNumCalled = numsCalled.last().toInt()
                    break
                }
            }
        }

        if (lastNumCalled != -1) {
            break
        }

    }

    return mapOf("winningBoard" to winningBoard, "numsCalled" to numsCalled, "lastNumCalled" to lastNumCalled)

}

fun getUnmarkedSum(board: List<*>, numsCalled: List<*>): Int {
    var unmarkedSum = 0
    for (row in board) {
        if (row is List<*>) {
            for (num in row) {
                if (num !in numsCalled && num is String) {
                    unmarkedSum += num.toInt()
                }
            }
        }
    }

    return unmarkedSum
}
