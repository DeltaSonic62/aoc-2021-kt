fun main() {
    fun part1(input: List<String>): Int {
        val grid = inputToGrid(input)

        val lowestNums = getLowestNumbers(grid)

        var riskLevelSum = 0

        for (num in lowestNums) {
            riskLevelSum += num + 1
        }

        return riskLevelSum
    }

    fun part2(input: List<String>): Int {
        val grid = inputToGrid(input)

        val basins = getBasinLocations(grid)

        val basinSizes = mutableListOf<Int>()

        for (basin in basins) {
            basinSizes.add(basin.size)
        }

        basinSizes.sortDescending()

        return basinSizes[0] * basinSizes[1] * basinSizes[2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

fun inputToGrid(input: List<String>): MutableList<MutableList<String>> {
    val grid = mutableListOf<MutableList<String>>()
    for (line in input) {
        val tempList = mutableListOf<String>()
        for (char in line.toCharArray()) {
            tempList.add(char.toString())
        }
        grid.add(tempList)
    }

    return grid
}

fun getLowestNumbers(grid: List<List<String>>): List<Int> {
    val lowestNums = mutableListOf<Int>()

    for (i in grid.indices) {
        for (j in grid[i].indices) {
            val currentVal = grid[i][j].toInt()
            var isLocalLowest = true

            if (i > 0) {
                isLocalLowest = currentVal < grid[i - 1][j].toInt()
            }
            if (i < grid.size - 1 && isLocalLowest) {
                isLocalLowest = currentVal < grid[i + 1][j].toInt()
            }
            if (j > 0 && isLocalLowest) {
                isLocalLowest = currentVal < grid[i][j - 1].toInt()
            }
            if (j < grid[i].size - 1 && isLocalLowest) {
                isLocalLowest = currentVal < grid[i][j + 1].toInt()
            }

            if (isLocalLowest) {
                lowestNums.add(currentVal)
            }
        }
    }

    return lowestNums
}

fun getLowestNumLoc(grid: List<List<String>>): List<List<Int>> {
    val lowestNumsLoc = mutableListOf<List<Int>>()

    for (y in grid.indices) {
        for (x in grid[y].indices) {
            val currentVal = grid[y][x].toInt()
            var isLocalLowest = true

            if (y > 0) {
                isLocalLowest = currentVal < grid[y - 1][x].toInt()
            }
            if (y < grid.size - 1 && isLocalLowest) {
                isLocalLowest = currentVal < grid[y + 1][x].toInt()
            }
            if (x > 0 && isLocalLowest) {
                isLocalLowest = currentVal < grid[y][x - 1].toInt()
            }
            if (x < grid[y].size - 1 && isLocalLowest) {
                isLocalLowest = currentVal < grid[y][x + 1].toInt()
            }

            if (isLocalLowest) {
                lowestNumsLoc.add(listOf(x, y))
            }
        }
    }

    return lowestNumsLoc
}

fun getBasinLocations(grid: MutableList<MutableList<String>>): List<List<List<Int>>> {
    val locOfLowestNums = getLowestNumLoc(grid)
    val basinLocs = mutableListOf<List<List<Int>>>()

    for (loc in locOfLowestNums) {
        val surroundings = mutableListOf<List<Int>>()
        val x = loc[0]
        val y = loc[1]

        val minX = getMinX(grid[y], x)
        val maxX = getMaxX(grid[y], x)

        // Search through row y to find min and max y values for each column
        for (i in minX..maxX) {
            val minY = getMinY(grid, i, y)
            val maxY = getMaxY(grid, i, y)

            // Search through column i to find min and max x values for each row
            for (j in minY..maxY) {
                val minI = getMinX(grid[j], i)
                val maxI = getMaxX(grid[j], i)

                // Search through row j to find min and max y values for each column
                for (k in minI..maxI) {
                    val minJ = getMinY(grid, k, j)
                    val maxJ = getMaxY(grid, k, j)

                    // Search through column k to find min and max x values for each row
                    for (l in minJ..maxJ) {
                        if (!surroundings.contains(listOf(k, l))) {
                            surroundings.add(listOf(k, l))
                        }
                    }
                }
            }
        }

        basinLocs.add(surroundings)
    }

    return basinLocs
}

fun getMinX(row: List<String>, x: Int): Int {
    var minX = x
    var a = x
    while (a >= 0) {
        if (row[a] != "9") {
            minX = a
        } else {
            break
        }

        a--
    }

    return minX
}

fun getMaxX(row: List<String>, x: Int): Int {
    var maxX = x
    var a = x
    while (a < row.size) {
        if (row[a] != "9") {
            maxX = a
        } else {
            break
        }

        a++
    }

    return maxX
}

fun getMinY(grid: List<List<String>>, x: Int, y: Int): Int {
    var minY = y
    var a = y

    while (a >= 0) {
        if (grid[a][x] != "9") {
            minY = a
        } else {
            break
        }

        a--
    }

    return minY
}

fun getMaxY(grid: List<List<String>>, x: Int, y: Int): Int {
    var maxY = y
    var a = y
    while (a < grid.size) {
        if (grid[a][x] != "9") {
            maxY = a
        } else {
            break
        }

        a++
    }

    return maxY
}