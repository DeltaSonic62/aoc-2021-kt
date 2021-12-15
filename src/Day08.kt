fun main() {
    fun part1(input: List<String>): Int {
        var count = 0

        for (line in input) {
            val outputValues = line.split("|")[1].trim().split(" ")

            for (num in outputValues) {
                if (decodeNum(num) != -1) {
                    count++
                }
            }
        }

        return count
    }

    fun part2(input: List<String>): Int {
        val seqList = mutableListOf<String>()

        for (line in input) {
            val outputValues = line.split("|")[1].trim().split(" ")
            val mappings = getMappings(line)
            seqList.add(outputValuesToSeq(outputValues, mappings))
        }

        var sum = 0

        for (seq in seqList) {
            sum += seq.toInt()
        }

        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

fun decodeNum(input: String): Int {
    return when (input.length) {
        2 -> 1
        4 -> 4
        3 -> 7
        7 -> 8
        else -> -1
    }
}

fun isIn(original: String, searchStr: String): Boolean {
    for (char in searchStr) {
        if (original.indexOf(char) == -1) {
            return false
        }
    }

    return true
}

fun getMappings(line: String): List<String> {
    val patterns = line.split("|")[0].trim().split(" ")
    val mappings = mutableListOf("", "", "", "", "", "", "", "", "", "")
    for (input in patterns) {
        when (decodeNum(input)) {
            1 -> mappings[1] = input
            4 -> mappings[4] = input
            7 -> mappings[7] = input
            8 -> mappings[8] = input
        }
    }

    // Length 6 inputs (0, 6, 9)
    for (input in patterns) {
        if (input.length == 6 && isIn(input, mappings[4])) {
            mappings[9] = input
        }
        if (input.length == 6 && isIn(input, mappings[7]) && !isIn(input, mappings[4])) {
            mappings[0] = input
        }
        if (input.length == 6 && !isIn(input, mappings[7]) && !isIn(input, mappings[4])) {
            mappings[6] = input
        }
    }

    // Length 5 inputs (2, 3, 5)
    for (input in patterns) {
        if (input.length == 5 && isIn(input, mappings[1])) {
            mappings[3] = input
        }
        if (input.length == 5 && isIn(mappings[6], input)) {
            mappings[5] = input
        }
        if (input.length == 5 && !isIn(mappings[6], input) && !isIn(input, mappings[1])) {
            mappings[2] = input
        }
    }

    return mappings
}

fun outputValuesToSeq(outputValues: List<String>, mappings: List<String>): String {
    var seq = ""

    for (signal in outputValues) {
        seq += when (signal.toCharArray().sorted().joinToString("")) {
            mappings[0].toCharArray().sorted().joinToString("") -> "0"
            mappings[1].toCharArray().sorted().joinToString("") -> "1"
            mappings[2].toCharArray().sorted().joinToString("") -> "2"
            mappings[3].toCharArray().sorted().joinToString("") -> "3"
            mappings[4].toCharArray().sorted().joinToString("") -> "4"
            mappings[5].toCharArray().sorted().joinToString("") -> "5"
            mappings[6].toCharArray().sorted().joinToString("") -> "6"
            mappings[7].toCharArray().sorted().joinToString("") -> "7"
            mappings[8].toCharArray().sorted().joinToString("") -> "8"
            else -> "9"
        }
    }

    return seq
}