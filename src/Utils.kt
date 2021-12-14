import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

/**
 * Perform  polymerization
 */
fun allSteps(polymere: String, insertionRules: MutableMap<String, String>, combien: Int): MutableMap<String, Long> {

    var newPolymere = mutableMapOf<String, Long>()
    val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    for (i in 0..25) {

        for (j in 0..25) {
            newPolymere[alphabet[i].toString() + alphabet[j].toString()] = 0L
        }
    }
    polymere.windowed(2, 1).forEach {
        newPolymere[it] = newPolymere[it]?.plus(1) ?: 0
    }

    for (steps in 1..combien) {
        val temp = mutableMapOf<String, Long>()
        for (i in 0..25) {

            for (j in 0..25) {
                temp[alphabet[i].toString() + alphabet[j].toString()] = 0L
            }
        }
        newPolymere.forEach { (key, value) ->

            if (value != 0L) {

                val cible = insertionRules[key]?.split(",")
                temp[cible?.get(0) ?: ""] = temp[cible?.get(0)]?.plus(value) ?: value
                temp[cible?.get(1) ?: ""] = temp[cible?.get(1)]?.plus(value) ?: value
            }

        }
        newPolymere = temp
    }

    return newPolymere
}

/**
 *
 */
fun letter_count(polymere: MutableMap<String, Long>, original: String): MutableMap<String, Long> {

    val stats = mutableMapOf<String, Long>()
    val filtre = polymere.filter { it.value != 0L }
    filtre.forEach { (key, value) ->
        stats[key[0].toString()] = stats[key[0].toString()]?.plus(value) ?: value

    }

    stats[original[original.length - 1].toString()] = stats[original[original.length - 1].toString()]?.plus(1) ?: 1
    return stats
}

fun generateInsertionRules(input: List<String>): MutableMap<String, String> {
    val insertionRule = mutableMapOf<String, String>()
    input.filter { it.contains("->") }.forEach {
        val decode = it.split("->")
        insertionRule[decode.first().trim(' ')] =
            decode.first().trim(' ')[0] + decode.last().trim(' ') + "," + decode.last().trim(' ') + decode.first()
                .trim(' ')[1]
    }
    return insertionRule
}