fun main() {
    fun part1(input: List<String>): Int {

        val insertionRule = generateInsertionRules(input)

        var polymere = mutableMapOf<String, Long>()
        polymere = allSteps(input.first(), insertionRule, 10)

        val stats: MutableMap<String, Long> = letter_count(polymere, input.first())
        val max = stats.maxOf { it.value }
        val min = stats.minOf { it.value }
        return (max - min).toInt()
    }

    fun part2(input: List<String>): Long {

        val insertionRule = generateInsertionRules(input)
        var polymere = mutableMapOf<String, Long>()
        polymere = allSteps(input.first(), insertionRule, 40)

        val stats: MutableMap<String, Long> = letter_count(polymere, input.first())
        val max = stats.maxOf { it.value }
        val min = stats.minOf { it.value }
        return (max - min)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 1588)


    val input = readInput("Day14")
    check(part1(input) == 2915)
    println(part1(input))

    check(part2(testInput) == 2188189693529)
    println(part2(input))

}
