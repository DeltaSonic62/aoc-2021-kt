import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>): Int {
        val crabs = mutableListOf<Int>()

        for (line in input) {
            for (crab in input[0].split(',')) {
                crabs.add(crab.toInt())
            }
        }

        var lowestFuelUsed = -1

        for (i in 0..crabs.maxOrNull()!!) {
            var fuelUsed = 0
            for (crab in crabs) {
                fuelUsed += (crab - i).absoluteValue
            }

            if (fuelUsed < lowestFuelUsed || lowestFuelUsed == -1) {
                lowestFuelUsed = fuelUsed
            }
        }

        return lowestFuelUsed
    }

    fun part2(input: List<String>): Int {
        val crabs = mutableListOf<Int>()

        for (line in input) {
            for (crab in input[0].split(',')) {
                crabs.add(crab.toInt())
            }
        }

        var lowestFuelUsed = -1

        for (i in 0..crabs.maxOrNull()!!) {
            var fuelUsed = 0
            for (crab in crabs) {
                var extraFuelPerStep = 0
                for (j in 1..(crab - i).absoluteValue) {
                    extraFuelPerStep += j
                }

                fuelUsed += extraFuelPerStep
            }

            if (fuelUsed < lowestFuelUsed || lowestFuelUsed == -1) {
                lowestFuelUsed = fuelUsed
            }
        }

        return lowestFuelUsed
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
