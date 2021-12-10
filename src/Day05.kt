import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>): Int {
        val grid = createGrid(input)

        for (line in input) {
            if (isHori(line)) {
                if (getX2(line) >= getX1(line)) {
                    for (i in getX1(line)..getX2(line)) {
                        grid[getY1(line)][i] += 1
                    }
                } else {
                    for (i in getX2(line)..getX1(line)) {
                        grid[getY1(line)][i] += 1
                    }
                }
            } else if (isVert(line)) {
                if (getY2(line) > getY1(line)) {
                    for (i in getY1(line)..getY2(line)) {
                        grid[i][getX1(line)] += 1
                    }
                } else {
                    for (i in getY2(line)..getY1(line)) {
                        grid[i][getX1(line)] += 1
                    }
                }
            }
        }

        return getOverlaps(grid)
    }

    fun part2(input: List<String>): Int {
        val grid = createGrid(input)

        for (line in input) {
            if (isHori(line)) {
                if (getX2(line) >= getX1(line)) {
                    for (i in getX1(line)..getX2(line)) {
                        grid[getY1(line)][i] += 1
                    }
                } else {
                    for (i in getX2(line)..getX1(line)) {
                        grid[getY1(line)][i] += 1
                    }
                }
            } else if (isVert(line)) {
                if (getY2(line) >= getY1(line)) {
                    for (i in getY1(line)..getY2(line)) {
                        grid[i][getX1(line)] += 1
                    }
                } else {
                    for (i in getY2(line)..getY1(line)) {
                        grid[i][getX1(line)] += 1
                    }
                }
            } else {
                val diff = (getX2(line) - getX1(line)).absoluteValue

                if (getX2(line) >= getX1(line) && getY2(line) >= getY1(line)) {
                    for (i in 0..diff) {
                        grid[getY1(line) + i][getX1(line) + i] += 1
                    }
                } else if (getX2(line) < getX1(line) && getY2(line) >= getY1(line)) {
                    for (i in 0..diff) {
                        grid[getY1(line) + i][getX1(line) - i] += 1
                    }
                } else if (getX2(line) >= getX1(line) && getY2(line) < getY1(line)) {
                    for (i in 0..diff) {
                        grid[getY1(line) - i][getX1(line) + i] += 1
                    }
                } else {
                    for (i in 0..diff) {
                        grid[getY1(line) - i][getX1(line) - i] += 1
                    }
                }
            }
        }

        return getOverlaps(grid)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

fun createGrid(input: List<String>): MutableList<MutableList<Int>> {
    var max = 0
    for (line in input) {
        val ls = listOf(getX1(line), getY1(line), getX2(line), getY2(line))
        val lsMax = ls.maxOrNull() ?: 0

        if (lsMax > max) {
            max = lsMax
        }

    }

    return MutableList(max + 1) {
        MutableList(max + 1) { 0 }
    }
}

fun getX1(lineSeg: String): Int {
    return lineSeg.split(",")[0].toInt()
}

fun getY1(lineSeg: String): Int {
    return lineSeg.split(",")[1].split(' ')[0].toInt()
}

fun getX2(lineSeg: String): Int {
    return lineSeg.split("->")[1].trim().split(",")[0].toInt()
}

fun getY2(lineSeg: String): Int {
    return lineSeg.split("->")[1].trim().split(",")[1].toInt()
}

fun isHori(lineSeg: String): Boolean {
    if (getX1(lineSeg) != getX2(lineSeg) && getY1(lineSeg) == getY2(lineSeg)) {
        return true
    }

    return false
}

fun isVert(lineSeg: String): Boolean {
    if (getY1(lineSeg) != getY2(lineSeg) && getX1(lineSeg) == getX2(lineSeg)) {
        return true
    }

    return false
}

fun getOverlaps(grid: MutableList<MutableList<Int>>): Int {
    var overlaps = 0

    for (line in grid) {
        for (point in line) {
            if (point > 1) {
                overlaps++
            }
        }
    }

    return overlaps
}