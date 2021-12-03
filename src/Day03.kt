fun main() {
    fun part1(input: List<String>): Int {
        // Binary strings
        var gammaBin = ""
        var epsilonBin = ""

        // Get most popular number for each char index
        for (i in 0 until input[0].length) {
            var numOfZeros = 0
            var numOfOnes = 0

            for (line in input) {
                if (line[i] == '0') {
                    numOfZeros++
                } else {
                    numOfOnes++
                }
            }

            // Create binary strings
            gammaBin += if (numOfZeros > numOfOnes) {
                "0"
            } else {
                "1"
            }

            epsilonBin += if (numOfZeros < numOfOnes) {
                "0"
            } else {
                "1"
            }
        }

        // Decimals + binary multiplier
        var gamma = 0
        var epsilon = 0
        var mul = 1

        // Multiplies each character (end to start) by the multiplier,
        // then adds to the respective variable
        for (i in 0 until input[0].length) {
            gamma += gammaBin[gammaBin.length - 1 - i].toString().toInt() * mul
            epsilon += epsilonBin[epsilonBin.length - 1 - i].toString().toInt() * mul
            mul *= 2
        }

        return gamma * epsilon
    }

    fun part2(input: List<String>): Int {
        // Set both lists to be the same as the input
        var oxyList = input
        var co2List = input

        // Loop through each char index
        for (i in 0 until input[0].length) {
            var numOfZeros = 0
            var numOfOnes = 0

            // Determine most common number
            for (line in oxyList) {
                if (line[i] == '0') {
                    numOfZeros++
                } else {
                    numOfOnes++
                }
            }

            // Add strings that start with the most common number to the temp list
            val oxyTemp = mutableListOf<String>()
            for (line in oxyList) {
                if (line[i] == '0' && numOfZeros > numOfOnes) {
                    oxyTemp.add(line)
                } else if (line[i] == '1' && numOfZeros <= numOfOnes) {
                    oxyTemp.add(line)
                }
            }

            // Set original list to be equal to temp list
            // (if there is more than 1 item in the temp list)
            if (oxyTemp.size > 0) {
                oxyList = oxyTemp
            }

            // Repeat for CO2
            numOfZeros = 0
            numOfOnes = 0

            for (line in co2List) {
                if (line[i] == '0') {
                    numOfZeros++
                } else {
                    numOfOnes++
                }
            }

            // Add strings that start with the most least number to the temp list
            val co2Temp = mutableListOf<String>()
            for (line in co2List) {
                if (line[i] == '0' && numOfZeros <= numOfOnes) {
                    co2Temp.add(line)
                } else if (line[i] == '1' && numOfZeros > numOfOnes) {
                    co2Temp.add(line)
                }
            }

            if (co2Temp.size > 0) {
                co2List = co2Temp
            }
        }

        // Set binary string as the first string in the respective list
        val oxyBin = oxyList[0]
        val co2Bin = co2List[0]

        // Decimals + binary multiplier
        var oxyRating = 0
        var co2Rating = 0
        var mul = 1

        // Multiplies each character (end to start) by the multiplier,
        // then adds to the respective variable
        for (i in 0 until input[0].length) {
            oxyRating += oxyBin[co2Bin.length - 1 - i].toString().toInt() * mul
            co2Rating += co2Bin[co2Bin.length - 1 - i].toString().toInt() * mul
            mul *= 2
        }

        return oxyRating * co2Rating
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}