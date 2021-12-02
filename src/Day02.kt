fun main() {
    fun part1(input: List<String>): Int {
        var xPos = 0
        var yPos = 0

        for (cmd in input) {
            when (cmd[0]) {
                'd' -> yPos += cmd[cmd.length - 1].toString().toInt()
                'u' -> yPos -= cmd[cmd.length - 1].toString().toInt()
                'f' -> xPos += cmd[cmd.length - 1].toString().toInt()
                else -> xPos -= cmd[cmd.length - 1].toString().toInt()
            }
        }

        return xPos * yPos
    }

    fun part2(input: List<String>): Int {
        var aim = 0
        var xPos = 0
        var yPos = 0

        for (cmd in input) {
            when (cmd[0]) {
                'd' -> aim += cmd[cmd.length - 1].toString().toInt()
                'u' -> aim -= cmd[cmd.length - 1].toString().toInt()
                'f' -> {
                    xPos += cmd[cmd.length - 1].toString().toInt()
                    yPos += aim * cmd[cmd.length - 1].toString().toInt()
                }
                else -> {
                    xPos -= cmd[cmd.length - 1].toString().toInt()
                    yPos -= aim * cmd[cmd.length - 1].toString().toInt()
                }
            }
        }

        return xPos * yPos
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
