fun main() {
    fun part1(input: List<String>): Int {
        val invalidClosingBrackets = mutableListOf<Char>()

        for (line in input) {
            val icb = getInvalidClosingBracket(line)
            if (icb != '?') {
                invalidClosingBrackets.add(icb)
            }
        }

        var score = 0

        for (bracket in invalidClosingBrackets) {
            when (bracket) {
                ')' -> score += 3
                ']' -> score += 57
                '}' -> score += 1197
                '>' -> score += 25137
            }
        }

        return score
    }

    fun part2(input: List<String>): Long {
        val incompleteStrings = getIncompleteStrings(input)
        val scores = mutableListOf<Long>()

        for (str in incompleteStrings) {
            var score: Long = 0
            for (char in getClosings(str)) {
                when (char) {
                    ')' -> score = (score * 5) + 1
                    ']' -> score = (score * 5) + 2
                    '}' -> score = (score * 5) + 3
                    '>' -> score = (score * 5) + 4
                }
            }

            scores.add(score)
        }

        scores.sort()

        return scores.elementAt(scores.size / 2)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957.toLong())

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}

fun getInvalidClosingBracket(line: String): Char {
    val openingBrackets = listOf('(', '[', '{', '<')
    val closingBrackets = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')
    var invalidClosingBracket = '?'

    val stack = mutableListOf<Char>()
    for (char in line) {
        if (openingBrackets.contains(char)) {
            stack.add(char)
        } else if (closingBrackets.containsValue(char)) {
            val popped = stack.removeLast()
            if (closingBrackets[popped] != char) {
                invalidClosingBracket = char
            }
        }
    }


    return invalidClosingBracket
}

fun getIncompleteStrings(lines: List<String>): List<String> {
    val incompleteStrings = mutableListOf<String>()

    for (line in lines) {
        if (getInvalidClosingBracket(line) == '?') {
            incompleteStrings.add(line)
        }
    }

    return incompleteStrings
}

fun getClosings(line: String): List<Char> {
    val openingBrackets = listOf('(', '[', '{', '<')
    val closingBrackets = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')
    val closings = mutableListOf<Char>()

    val stack = mutableListOf<Char>()
    for (char in line) {
        if (openingBrackets.contains(char)) {
            stack.add(char)
        } else if (closingBrackets.containsValue(char)) {
            stack.removeLast()
        }
    }

    for (char in stack) {
        closings.add(closingBrackets.getOrDefault(char, char))
    }

    return closings.reversed()
}