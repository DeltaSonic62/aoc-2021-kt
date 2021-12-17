fun main() {
    fun part1(input: List<String>): Int {
        val grid = getGrid(input)

        var flashes = 0

        for (i in 1..100) {
            increaseEnergy(grid)
            flashes += flash(grid, getFlashers(grid))
        }

        return flashes
    }

    fun part2(input: List<String>): Int {
        val grid = getGrid(input)
        val maxFlashes = grid.size * grid[0].size

        for (i in 1..1000) {
            var flashes = 0
            increaseEnergy(grid)
            flashes += flash(grid, getFlashers(grid))

            if (flashes == maxFlashes) {
                return i
            }
        }

        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}

fun getGrid(input: List<String>): MutableList<MutableList<Int>> {
    val grid = mutableListOf<MutableList<Int>>()
    for (line in input) {
        val tempList = mutableListOf<Int>()
        for (char in line.toCharArray()) {
            tempList.add(char.toString().toInt())
        }
        grid.add(tempList)
    }

    return grid
}

fun increaseEnergy(grid: MutableList<MutableList<Int>>) {
    for (y in grid.indices) {
        for (x in grid[y].indices) {
            grid[y][x] += 1
        }
    }
}

fun getFlashers(grid: MutableList<MutableList<Int>>): List<List<Int>> {
    val coordinates = mutableListOf<List<Int>>()
    for (y in grid.indices) {
        for (x in grid[y].indices) {
            if (grid[y][x] > 9) {
                coordinates.add(listOf(x, y))
            }
        }
    }

    return coordinates
}

fun flash(grid: MutableList<MutableList<Int>>, flashers: List<List<Int>>, prevFlashers: MutableList<List<Int>> = mutableListOf()): Int {
    for (flasher in flashers) {

            prevFlashers.add(flasher)

            val x = flasher[0]
            val y = flasher[1]

            // Left
            if (x > 0) grid[y][x - 1] += if (prevFlashers.contains(listOf(x - 1, y))) 0 else 1

            // Right
            if (x < grid[y].size - 1) grid[y][x + 1] += if (prevFlashers.contains(listOf(x + 1, y))) 0 else 1

            // Up
            if (y > 0) {
                grid[y - 1][x] += if (prevFlashers.contains(listOf(x, y - 1))) 0 else 1

                // Up-Left
                if (x > 0) grid[y - 1][x - 1] += if (prevFlashers.contains(listOf(x - 1, y - 1))) 0 else 1

                // Up-Right
                if (x < grid[y].size - 1) grid[y - 1][x + 1] += if (prevFlashers.contains(listOf(x + 1, y - 1))) 0 else 1
            }

            // Down
            if (y < grid.size - 1) {
                grid[y + 1][x] += if (prevFlashers.contains(listOf(x, y + 1))) 0 else 1

                // Down-Left
                if (x > 0) grid[y + 1][x - 1] += if (prevFlashers.contains(listOf(x - 1, y + 1))) 0 else 1

                // Down-Right
                if (x < grid[y].size - 1 && !prevFlashers.contains(listOf(x + 1, y + 1))) grid[y + 1][x + 1] += if (prevFlashers.contains(listOf(x + 1, y + 1))) 0 else 1
            }

            // Set to 0
            grid[y][x] = 0
        }


    if (getFlashers(grid).isNotEmpty()) {
        flash(grid, getFlashers(grid), prevFlashers)
    }

    return prevFlashers.size
}
